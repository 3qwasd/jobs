package jobs.toolkit.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.util.ArrayDeque;

import jobs.toolkit.utils.NioUtils;

/**
 * 输出专用缓冲, 该类不是线程安全的, 并发机制由上层的NioDataTransfer来保证
 * 这个类的主要目的是通过分片防止大片内存的拷贝
 * @author jobs
 *
 */
final class NioOutputBuffer {
	
	private static final int DEFAULT_PIECE = 524288; //512K
	
	private volatile int size = 0;    //buffer中的字节总数
	private final int pieceSize; // 分片大小

	private final ArrayDeque<ByteBuffer> outputs = new ArrayDeque<ByteBuffer>(); //可以发送出去的分片
	private volatile ByteBuffer currPiece = null; // 当前可以填充的分片
	private final ArrayDeque<ByteBuffer> buffers = new ArrayDeque<ByteBuffer>(); //空的分片的集合
	private final int outputBufferSize;//buffer的空间
	
	private NioOutputBuffer(int outputBufferSize) {
		this.outputBufferSize = outputBufferSize;
		if(this.outputBufferSize < DEFAULT_PIECE) 
			this.pieceSize = this.outputBufferSize;
		else
			this.pieceSize = DEFAULT_PIECE;
	}
	static NioOutputBuffer newInstance(){
		return new NioOutputBuffer(NioUtils.MAX_BUFFER_SIZE);
	}
	static NioOutputBuffer newInstance(int outputBufferSize){
		return new NioOutputBuffer(NioUtils.roudup(outputBufferSize));
	}
	final NioOutputBuffer put(ByteBuffer src) throws IOException{
		/*先判断buffer是否有空间, 如果没有空间抛出异常*/
		if(this.size + src.remaining() > this.outputBufferSize)
			throw new IOException("Output buffer has no space!");
		this.size += src.remaining();
		while(src.hasRemaining()){
			if(currPiece == null){
				//如果当前需要填充的分片为空, 则从缓冲里面取出一个空分片, 
				//如果缓冲为空, 创建一个新的分片, 注意这里使用了direct内存
				currPiece = buffers.isEmpty() ? 
						ByteBuffer.allocateDirect(this.pieceSize) : buffers.pollFirst();
			}
			//注意如果src.reamining() > currPiece.remaining(), 直接使用put会抛出异常, 所以需要这样处理
			int len = currPiece.remaining() < src.remaining() ? currPiece.remaining() : src.remaining();
			if(currPiece.put((ByteBuffer) src.slice().limit(len)).remaining() == 0){
				//到这里证明currPiece被填满了
				currPiece.flip();
				outputs.addLast(currPiece);//将当前分片加入输出队列
				currPiece = null;
			}
			src.position(src.position() + len);
		}
		return this;
	}
	
	final int output(GatheringByteChannel channel) throws IOException {
		/*将当前分片加入到队列末尾进行输出*/
		if(this.currPiece != null){
			this.currPiece.flip();
			this.outputs.addLast(currPiece);
			this.currPiece = null;
		}
		if(outputs.isEmpty()) return this.size;
		/*将输出队列的数据写入到Nio中*/
		long wc = channel.write(outputs.toArray(new ByteBuffer[outputs.size()]));
		this.size -= wc;
		/*收集已经流干的buffer*/
		while(!this.outputs.isEmpty()){
			if(this.outputs.peekFirst().hasRemaining()) break;
			this.buffers.addLast((ByteBuffer) this.outputs.pollFirst().clear());
		}
		
		return this.size;
	}
	final int size(){
		return this.size;
	}
	
	//以下方法用于单元测试
	protected ArrayDeque<ByteBuffer> getOutputs() {
		return outputs;
	}
	protected ByteBuffer getCurrPiece() {
		return currPiece;
	}
	protected ArrayDeque<ByteBuffer> getBuffers() {
		return buffers;
	}
	protected int getPieceSize() {
		return pieceSize;
	}
	protected int getOutputBufferSize() {
		return outputBufferSize;
	}
	
}
