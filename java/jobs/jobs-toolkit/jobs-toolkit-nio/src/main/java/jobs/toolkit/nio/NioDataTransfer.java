package jobs.toolkit.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import jobs.toolkit.nio.event.NioMessageEvent;

/**
 * 负责从Nio中读写数据的类
 * @author jobs
 *
 */
final class NioDataTransfer {
	
	/*读取数据用的缓存*/
	private volatile ByteBuffer inputBuffer;
	/*写出数据用的缓存*/
	private volatile NioOutputBuffer outputBuffer;
	/*该dataTransfer服务的nio对象*/
	private final Nio nio;
	/*读写锁, 保证同一时刻只能由一个线程使用该dataTransfer对对应的nio进行读写*/
	private final ReadWriteLock lock;
	/*编码器, 用于解码和译码*/
	private final NioCoder coder;
	
	private final CopyOnWriteArrayList<ByteBuffer> sendQueue = new CopyOnWriteArrayList<ByteBuffer>();
	
	NioDataTransfer(Nio nio) {
		this.nio = nio;
		this.lock = new ReentrantReadWriteLock();
		this.coder = this.nio.getNioManager().getNioCoder();
	}
	/**
	 * 从服务的nio中读数据
	 * @return
	 * @throws IOException
	 */
	int read() throws IOException {
		this.lock.readLock().lock();
		try{
			if(this.inputBuffer == null){
				this.inputBuffer = ByteBuffer.allocate(this.nio.getNioManager().getNioConnector().getInputBufferSize());
			}
			/*从channel中读取数据并写入缓冲*/
			int rc = this.nio.getReadableChannel().read(this.inputBuffer);
			if(rc > 0){
				/*切换缓冲状态到读状态*/
				this.inputBuffer.flip();
				/*对缓冲中的字节数据进行译码*/
				NioMessageEvent event = null;
				/*如果编码器凑够一个包的数据, 会将这些数据解码成为一个消息事件返回, 如果解码器获取的数据不够一个协议包或在解码过程出错, 则会返回null*/
				while((event = this.coder.doDecode(this.inputBuffer)) != null){
					/*使用nio接受消息事件*/
					this.nio.recive(event);
				}
				/*压缩输入缓冲*/
				this.inputBuffer.compact();
			}
			/*如果inputBuffer没有可用的空间来接收数据, 抛出异常, 关闭连接. */
			/*发生这种情况意味这inputBuffer已经被填满, 但现有的数据无法凑够一个可以解码的序列, 
			 *说明这个协议包的大小大于inputBuffer的大小, 为了防止这种情况发生, inputBuffer的
			 *大小应该大于最大协议包的大小, 所以应该配置协议包的大小上限, 让inputBuffer的容量
			 *等于该上限*/
			if(this.inputBuffer != null && !this.inputBuffer.hasRemaining()){
				throw new IOException("Nio input buffer has no space to recive more bytes!");
			}
			
			return rc;
		}finally{
			this.lock.readLock().unlock();
		}

	}
	
	int write() throws IOException {
		this.lock.writeLock().lock();
		try {
			if(this.outputBuffer == null) this.outputBuffer = 
					NioOutputBuffer.newInstance(nio.getNioManager().getNioConnector().getOutputBufferSize());
			for(ByteBuffer bb : this.sendQueue){
				this.outputBuffer.put(bb);
			}
			int remainging = this.outputBuffer.output(this.nio.getWriteableChannel());
			if(remainging == 0 && !this.nio.getNioManager().getNioConnector().isKeepOutputBuffer()){
				//数据发送完毕, 如果配置不保存输出buffer, 则回收内存空间
				this.outputBuffer = null;
			}
			return remainging;
		} finally {
			this.lock.writeLock().unlock();
		}
	}
	
	void add(NioMessageEvent event){
		if(this.coder.checkEncode(event)){
			this.sendQueue.add(this.coder.doEncode(event));
		}
	}
	boolean isSendQueueEmpty(){
		return this.sendQueue.isEmpty();
	}
	//开放给单元测试
	ByteBuffer getInputBuffer() {
		return inputBuffer;
	}

	NioOutputBuffer getOutputBuffer() {
		return outputBuffer;
	}

	NioCoder getCoder() {
		return coder;
	}

	CopyOnWriteArrayList<ByteBuffer> getSendQueue() {
		return sendQueue;
	}
	
	
}
