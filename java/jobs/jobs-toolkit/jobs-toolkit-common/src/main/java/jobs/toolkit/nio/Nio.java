package jobs.toolkit.nio;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import jobs.toolkit.nio.event.NioEventDispatcher;
import jobs.toolkit.nio.event.NioMessageEvent;

/**
 * socket连接
 * 网络事件处理，和读写，管理 buffer
 * 通过接口 Filter 进行数据加解密，压缩，搭建高级协议层。
 * @author jobs
 *
 */
public class Nio implements Closeable{
	
	private final NioManager nioManager;
	
	private volatile SocketAddress peer; // 关闭以后看不到peer。开始就保存一个。
	
	private volatile SocketChannel channel; //通道
	
	private volatile SelectionKey selectionKey;
	
	
	private final NioDataTransfer transfer;
	
	
	Nio(NioManager manager){
		this.nioManager = manager;
		this.transfer = new NioDataTransfer(this);
	}
	
	/**
	 * 初始化Nio
	 * @param selector
	 * @param socketChannel
	 * @param op
	 * @throws Throwable
	 */
	void init(NioSelectorThread selector, SocketChannel socketChannel, int op) throws Throwable{
		final Socket socket = socketChannel.socket();
		this.peer = socket.getRemoteSocketAddress();
		this.channel = socketChannel;
		//将channel注册到对应的线程上,此处是否有性能影响？
		FutureTask<SelectionKey> registerTask = selector.submitRegsiterTask(socketChannel, op, this);
		this.selectionKey = registerTask.get(1000, TimeUnit.MILLISECONDS);
	}
	/**
	 * 在selectionKey上面注册新的事件
	 * @param ops
	 */
	public final void interestOps(int ops) {
		this.selectionKey.interestOps(ops);
		this.selectionKey.selector().wakeup();
	}
	/**
	 * 在selectionKey上面取消监听的事件, 并且监听新的事件
	 * @param deinterest 需要取消监听的事件
	 * @param add 需要添加新的监听的事件
	 */
	public final void interestOps(int deinterest, int interest) {
		int cur = this.selectionKey.interestOps();
		int ops = (cur & ~deinterest) | interest;
		if (cur != ops)
			this.interestOps(ops);
	}
	public final void deinterestOps(int deinterest){
		int cur = this.selectionKey.interestOps();
		int ops = cur & ~deinterest;
		if(cur != ops)
			this.interestOps(ops);
	}
	public final void interestWrite(){
		this.interestOps(SelectionKey.OP_WRITE);
	}
	public final void deinterestWrite(){
		this.deinterestOps(SelectionKey.OP_WRITE);
	}
	public void doRead(){
		try {
			int rc = this.transfer.read();
			if(rc < 0){
				this.close();
			}
		} catch (IOException e) {
			//抛出给处理器处理
			throw new IllegalStateException(String.format("Nio [%1$s] occur exception when do read.", this), e);
		}
	}
	public void doWrite(){
		try {
			int rc = this.transfer.write();
			if(rc == 0){
				synchronized (this) {
					if(this.transfer.isSendQueueEmpty()){
						//证明所有的数据发送完毕, 目前没有需要发送的数据, 再此处取消写事件的注册
						this.deinterestWrite();
					}		
				}
			}
		} catch (IOException e) {
			//抛出给处理器处理
			throw new IllegalStateException(String.format("Nio [%1$s] occur exception when do write.", this), e);
		}
	}
	public void recive(NioMessageEvent event){
		NioEventDispatcher.getInstance().dispatch(event);
	}
	
	public void send(NioMessageEvent event){
		synchronized (this) {
			this.transfer.add(event);
			//有新的数据需要发送, 此处在SelectionKey上注册写事件
			this.interestWrite();
		}
	}

	@Override
	public String toString() {
		return "Nio:" + this.peer.toString();
	}
	
	/**
	 * nio的关闭操作委托给NioManager执行
	 */
	@Override
	public void close() throws IOException {
		this.close(null);
	}
	public void close(Throwable e) throws IOException {
		this.nioManager.closeNio(this, e);
	}
	/**
	 * 关闭Socket, 由nioManager回调
	 * @throws IOException
	 */
	void closeChannel() throws IOException{
		if(this.channel != null)
			this.channel.close();
	}
	NioManager getNioManager(){
		return this.nioManager;
	}
	ScatteringByteChannel getReadableChannel(){
		return this.channel;
	}
	GatheringByteChannel getWriteableChannel(){
		return this.channel;
	}
	//////以下方法只用于单元测试
	protected final SelectionKey getSelectionKey() {
		return this.selectionKey;
	}
	protected SocketChannel getChannel() {
		return this.channel;
	}
	/////该构造函数用于单元测试
	Nio(){
		this.nioManager = createTestNioManager();
		this.transfer = null;
	};
	protected NioManager createTestNioManager(){
		return null;
	}
}
