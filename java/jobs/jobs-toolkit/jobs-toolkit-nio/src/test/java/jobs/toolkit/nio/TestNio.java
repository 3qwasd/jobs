package jobs.toolkit.nio;

import java.io.IOException;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.Pipe;
import java.nio.channels.ScatteringByteChannel;
import java.util.concurrent.CopyOnWriteArrayList;

import jobs.toolkit.nio.Nio;
import jobs.toolkit.nio.NioManager;
import jobs.toolkit.nio.event.NioMessageEvent;

public class TestNio extends Nio {
	
	CopyOnWriteArrayList<TestNioMessageEvent> reciveList = new CopyOnWriteArrayList<>();
	CopyOnWriteArrayList<TestNioMessageEvent> sendList = new CopyOnWriteArrayList<>();
	@Override
	ScatteringByteChannel getReadableChannel() {
		// TODO Auto-generated method stub
		try {
			this.channel.source().configureBlocking(false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.channel.source();
	}

	@Override
	GatheringByteChannel getWriteableChannel() {
		// TODO Auto-generated method stub
		try {
			this.channel.sink().configureBlocking(false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.channel.sink();
	}
	public Pipe getTestChannel(){
		return this.channel;
	}
	private volatile Pipe channel;
	
	TestNio(int cap) {
		super();
		try {
			channel = Pipe.open();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected NioManager createTestNioManager() {
		// TODO Auto-generated method stub
		return new NormalNioManager("").initTestComponent();
	}

	@Override
	public void recive(NioMessageEvent event) {
		// TODO Auto-generated method stub
		//System.out.println(event);
		this.reciveList.add((TestNioMessageEvent) event);
	}

	@Override
	public void send(NioMessageEvent event) {
		// TODO Auto-generated method stub
		synchronized (this) {
			super.send(event);
			this.sendList.add((TestNioMessageEvent) event);
		}
		
	}
	
}
