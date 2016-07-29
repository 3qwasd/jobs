package jobs.toolkit.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class TestChannel implements GatheringByteChannel, ScatteringByteChannel, ByteChannel{

	ByteBuffer buffer;


	public TestChannel(int cap) {
		buffer = ByteBuffer.allocate(cap);
	}

	@Override
	public synchronized int write(ByteBuffer src) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public synchronized boolean isOpen() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public synchronized void close() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public synchronized long write(ByteBuffer[] srcs, int offset, int length) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public synchronized long write(ByteBuffer[] srcs) throws IOException {
		// TODO Auto-generated method stub
		Deque<ByteBuffer> queue = new ArrayDeque<ByteBuffer>(Arrays.asList(srcs));
		ByteBuffer curr = queue.pollFirst();
		int size = 0;
		while(this.buffer.hasRemaining()){
			if(!curr.hasRemaining()) curr = queue.pollFirst();
			if(curr!=null){
				int len = this.buffer.remaining() > curr.remaining() ? curr.remaining() : this.buffer.remaining();
				this.buffer.put((ByteBuffer) curr.slice().limit(len));
				size += len;
				curr.position(curr.position() + len);
			}else{
				return size;
			}
		}
		return size;
	}

	@Override
	public synchronized int read(ByteBuffer dst) throws IOException {
		// TODO Auto-generated method stub
		int len = Math.min(buffer.remaining(), dst.remaining());
		dst.put((ByteBuffer) buffer.slice().limit(len));
		buffer.position(buffer.position() + len);
		buffer.compact();
		return len;
	}

	@Override
	public synchronized long read(ByteBuffer[] dsts, int offset, int length) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public synchronized long read(ByteBuffer[] dsts) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

}
