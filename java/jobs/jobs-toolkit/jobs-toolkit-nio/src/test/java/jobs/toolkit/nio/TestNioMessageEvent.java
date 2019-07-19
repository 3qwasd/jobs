package jobs.toolkit.nio;

import java.nio.ByteBuffer;

import jobs.toolkit.nio.event.NioMessageEvent;
import jobs.toolkit.utils.RandomUtils;

public class TestNioMessageEvent extends NioMessageEvent{
	
	
	int[] values;
	
	public TestNioMessageEvent(){
		super("");
	}
	public TestNioMessageEvent init(int cap){
		values = new int[cap];
		for(int i=0;i<values.length;i++){
			values[i] = RandomUtils.uniform(100, 1000000);
		}
		return this;
	}
	public ByteBuffer marshall(){
		ByteBuffer buffer = ByteBuffer.allocate(this.byteSize());
		buffer.putInt(values.length);
		for(int i = 0; i < values.length; i++){
			buffer.putInt(values[i]);
		}
		return (ByteBuffer) buffer.flip();
	}
	public TestNioMessageEvent unMarshall(ByteBuffer buffer){
		int cap = buffer.getInt();
		//System.out.println(cap);
		values = new int[cap];
		for(int i=0;i<values.length;i++){
			values[i]=buffer.getInt();
		}
		return this;
	}
	public int byteSize(){
		return 4 + values.length*4;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(! (obj instanceof TestNioMessageEvent)) return false;
		TestNioMessageEvent event = (TestNioMessageEvent) obj;
		if(this.values.length != event.values.length) return false;
		for(int i=0;i<values.length;i++){
			if(values[i]!=event.values[i]) return false;
		}
		return true;
	}
	
}
