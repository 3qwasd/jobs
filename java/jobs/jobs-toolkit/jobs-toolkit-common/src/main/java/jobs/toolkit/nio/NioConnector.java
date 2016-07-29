package jobs.toolkit.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import jobs.toolkit.service.BaseService;
import jobs.toolkit.nio.NioConfig.NioConnectorConfig;
import jobs.toolkit.nio.event.NioEventDispatcher;
import jobs.toolkit.nio.event.RunnableNioEvent;

/**
 * 网络连接创建者, 实现了EventHandler接口, 处理对应的创建NIO的事件
 * @author jobs
 *
 */
public abstract class NioConnector extends BaseService{

	private final NioManager nioManager;

	private volatile int inputBufferSize;
	private volatile int outputBufferSize;
	private volatile boolean keepInputBuffer;
	private volatile boolean keepOutputBuffer;

	private volatile boolean tcpNoDelay; // setup after accept
	private volatile int receiveBufferSize; // 配置在ServerSocket上，accept出来的连接通过继承获得。
	private volatile int sendBufferSize; // setup after accept

	private volatile InetSocketAddress address; // ugly. 在子类中初始化。

	public NioConnector(NioManager manager, String name) {
		super(name);
		this.nioManager = manager;
	}
	/**
	 * 该方法子类应该在initialize方法中调用来初始化adress
	 * @param ip
	 * @param port
	 */
	protected final void initSocketAddress(String ip, int port){
		if(ip == null || ip.isEmpty())
			this.address = new InetSocketAddress(port);
		else
			this.address = new InetSocketAddress(ip, port);
	}
	@Override
	protected void initialize() throws Exception {
		// TODO Auto-generated method stub
		super.initialize();
		NioConnectorConfig ncc = (NioConnectorConfig) this.getConfiguration();
		this.inputBufferSize = ncc.getInputBufferSize();
		this.outputBufferSize = ncc.getOutputBufferSize();
		this.keepInputBuffer = ncc.isKeepInputBuffer();
		this.keepOutputBuffer = ncc.isKeepOutputBuffer();
		this.tcpNoDelay = ncc.isTcpNoDelay();
		this.receiveBufferSize = ncc.getReceiveBufferSize();
		this.sendBufferSize = ncc.getSendBufferSize();
	}


	public void onConnectSucess(SocketChannel channel) throws Throwable{
		NioEventDispatcher.getInstance().dispatch(new OnConnectEvent(channel));
	}
	public abstract void onConnectAbort(SocketChannel channel, Throwable cause);
	private class OnConnectEvent extends RunnableNioEvent{

		private final SocketChannel channel;

		public OnConnectEvent(SocketChannel channel) {
			super();
			this.channel = channel;
		}

		@Override
		public void run() {
			Nio nio = null;
			try{
				nio = nioManager.createNewNio(NioEngine.selector(), channel, SelectionKey.OP_READ);
			}catch(Throwable _e){
				if(nio != null){
					try { nio.close(); } catch (IOException e) {/*skip*/}
				}
				//这个异常是抛出给EventHandler进行处理的
				throw new IllegalStateException(String.format("Nio manager %1$s create nio occur exception!", nioManager.getName()), _e);
			}
		}
	}

	void onClose(Nio nio, Throwable e) throws Throwable{}

	public NioManager getNioManager() {
		return nioManager;
	}

	public int getInputBufferSize() {
		return inputBufferSize;
	}

	public int getOutputBufferSize() {
		return outputBufferSize;
	}

	public boolean isKeepOutputBuffer() {
		return keepOutputBuffer;
	}

	public boolean isKeepInputBuffer() {
		return keepInputBuffer;
	}

	public boolean isTcpNoDelay() {
		return tcpNoDelay;
	}

	public int getReceiveBufferSize() {
		return receiveBufferSize;
	}

	public int getSendBufferSize() {
		return sendBufferSize;
	}

	public InetSocketAddress getAddress() {
		return address;
	}
}
