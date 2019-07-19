package jobs.toolkit.nio;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import jobs.toolkit.nio.NioCoder;
import jobs.toolkit.nio.NioConfig;
import jobs.toolkit.nio.NioConnector;
import jobs.toolkit.nio.NioManager;
import jobs.toolkit.nio.event.NioMessageEvent;
import jobs.toolkit.utils.NioUtils;

public class NormalNioManager extends NioManager{

	public NormalNioManager(String name) {
		super(name);
		// TODO Auto-generated constructor stub
		
	}
	
	@Override
	protected NioConnector initTestConnector() {
		// TODO Auto-generated method stub
		return new NioConnector(this, "") {
			
			@Override
			public int getInputBufferSize() {
				// TODO Auto-generated method stub
				return NioConfig.DEFAULT_OUTPUT_BUFFER_SIZE;
			}

			@Override
			public int getOutputBufferSize() {
				// TODO Auto-generated method stub
				return NioConfig.DEFAULT_OUTPUT_BUFFER_SIZE;
			}

			@Override
			public void onConnectAbort(SocketChannel channel, Throwable cause) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean isKeepOutputBuffer() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isKeepInputBuffer() {
				// TODO Auto-generated method stub
				return false;
			}
			
			
		};
	}

	@Override
	protected NioCoder initTestCoder() {
		// TODO Auto-generated method stub
		return new NioCoder() {
			
			@Override
			public ByteBuffer encode(NioMessageEvent msgEvent) {
				// TODO Auto-generated method stub
				TestNioMessageEvent event = (TestNioMessageEvent) msgEvent;
				
				return (ByteBuffer) ByteBuffer.allocate(event.byteSize() + 4).putInt(event.byteSize()).put(event.marshall()).flip();
			}
			
			@Override
			public NioMessageEvent decode(ByteBuffer byteBuffer) {
				// TODO Auto-generated method stub
				int len = byteBuffer.getInt();
				//System.out.println(len);
				ByteBuffer eventBuffer = (ByteBuffer) byteBuffer.slice().limit(len);
				TestNioMessageEvent event = new TestNioMessageEvent();
				event.unMarshall(eventBuffer);
				byteBuffer.position(byteBuffer.position() + len);
				//byteBuffer.compact();
				return event;
			}
			
			@Override
			public boolean checkEncode(NioMessageEvent msgEvent) {
				// TODO Auto-generated method stub
				return true;
			}
			
			@Override
			public boolean checkDecode(ByteBuffer byteBuffer) {
				// TODO Auto-generated method stub
				if(!byteBuffer.hasRemaining()) return false;
				int i = byteBuffer.asIntBuffer().get();
				if(i != 404)
					System.out.println("package len=" + i);
				return byteBuffer.remaining() >= (i + 4);
			}
		};
	}
	
	
	
}
