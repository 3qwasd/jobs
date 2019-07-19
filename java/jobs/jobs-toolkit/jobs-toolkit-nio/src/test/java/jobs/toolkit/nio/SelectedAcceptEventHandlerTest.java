package jobs.toolkit.nio;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.CountDownLatch;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import jobs.toolkit.config.MapConfiguration;
import jobs.toolkit.nio.Nio;
import jobs.toolkit.nio.NioConfig;
import jobs.toolkit.nio.NioEngine;
import jobs.toolkit.nio.NioManager;
import jobs.toolkit.nio.NioServer;
import jobs.toolkit.nio.event.AcceptEventHandler;
import jobs.toolkit.nio.event.NioEventDispatcher;
import jobs.toolkit.nio.event.SelectedEvent;

public class SelectedAcceptEventHandlerTest {
	
	NioManager manager;

	NioServer server;
	
	Selector selector;
	
	NioEventDispatcher dispatcher;

	NioConfig nioConfig;
	
	AcceptEventHandler handler;
	
	final CountDownLatch latch = new CountDownLatch(1);
	final CountDownLatch latch1 = new CountDownLatch(1);
	Socket so;
	@Before
	public void setUp() throws Exception {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					latch.await();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				so = new Socket();
		
				try {
					so.connect(new InetSocketAddress(8888), 2000);
					System.out.println("xxxxxxxxx");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				latch1.countDown();
			}
		}).start();
		handler = new AcceptEventHandler();
		this.selector = Selector.open();
		String configFile = NioServerTest.class.getResource("..").getPath().concat("resource/nio_server.xml");
		 nioConfig = new NioConfig();
		nioConfig.parse(configFile);
		NioEngine.getInstance().init(nioConfig);
		NioEngine.getInstance().start();
		this.dispatcher = NioEventDispatcher.getInstance();
		this.dispatcher.init(new MapConfiguration());
		this.dispatcher.start();
		this.manager = new NormalNioManager("TestNioManager");
		manager.init(nioConfig);
		this.server = this.manager.getNioServer();
		this.manager.start();
		this.server.getServerKey().cancel();

		this.server.getServer().register(selector, SelectionKey.OP_ACCEPT, this.server);
	}

	

	@Test
	public void testSuccess() throws IOException {
		latch.countDown();
		try {
			latch1.await();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		selector.select(2000);
		
		
		
		java.util.Set<SelectionKey> selected = this.selector.selectedKeys();
		System.out.println(selected.size());
		for(SelectionKey key : selected){
			if(!key.isValid()) continue;
			if(key.isAcceptable()){
				SelectedEvent event = SelectedEvent.newEvent(SelectedEvent.NIO_SELECTED_ACCEPTE_EVENT,key);
				try {
					this.handler.doHandle(event);
				} catch (Throwable e) {
					this.handler.doExcepion(event, e);
				}
			}
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Assert.assertTrue(this.manager.getNioSet().size() == 1);
		Nio nio = this.manager.getNioSet().iterator().next();
		Assert.assertTrue(nio.getChannel().isOpen());
		Assert.assertTrue(nio.getChannel().isConnected());
		Assert.assertTrue(nio.getChannel().isRegistered());
		Assert.assertTrue(nio.getSelectionKey().isValid());
		Assert.assertTrue(nio.getSelectionKey().attachment() == nio);
		Assert.assertTrue((nio.getSelectionKey().interestOps() & SelectionKey.OP_READ) > 0);
		Assert.assertTrue(so.isConnected());
		System.out.println(nio.getChannel().getRemoteAddress().toString());
	}
	@Test
	public void testException() throws IOException, Exception{
		latch.countDown();
		try {
			latch1.await();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		assertTrue(so.isConnected());
		
		selector.select(2000);
		
		
		
		java.util.Set<SelectionKey> selected = this.selector.selectedKeys();
		System.out.println(selected.size());
		for(SelectionKey key : selected){
			if(!key.isValid()) continue;
			if(key.isAcceptable()){
				SelectedEvent event = SelectedEvent.newEvent(SelectedEvent.NIO_SELECTED_ACCEPTE_EVENT,key);
				key.attach(null);
				this.dispatcher.dispatch(event);
			}
		}
		
		try {
			Thread.sleep( 2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Assert.assertTrue(this.manager.getNioSet().isEmpty());
		Assert.assertTrue(this.manager.getNioSet().size() == 1);
		Nio nio = this.manager.getNioSet().iterator().next();
		nio.close();
		Thread.sleep(2000);
		assertFalse(nio.getChannel().isOpen());
		this.manager.stop();
		try{
			byte[] bs = new byte[1000];
			System.out.println(so.getInputStream().read(bs));
			System.out.println(bs);
			so.getOutputStream().write("asdfasdfsadfasdfsadf".getBytes());
			
			//so.getOutputStream().write("asdfsadfasfasdfasdfasdfjaslkdfjasjdfaklsdjfklsadfjaskldfjasl;dfjas;dfjas;dfjasdf;jsadjfas;dfjsaldfjasdfasl;dfjasl;dfasd;lfjas;ldfsaldf".getBytes());
		}catch(Throwable e){
			e.printStackTrace();
		}
		Assert.assertTrue(so.isConnected());
		
	}
	
	@After
	public void tearDown() throws Exception {
	}
}
