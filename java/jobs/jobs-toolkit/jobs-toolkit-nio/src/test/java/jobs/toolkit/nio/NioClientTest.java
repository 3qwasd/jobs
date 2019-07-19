package jobs.toolkit.nio;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import jobs.toolkit.config.MapConfiguration;
import jobs.toolkit.nio.Nio;
import jobs.toolkit.nio.NioClient;
import jobs.toolkit.nio.NioConfig;
import jobs.toolkit.nio.NioEngine;
import jobs.toolkit.nio.NioManager;
import jobs.toolkit.nio.event.NioEventDispatcher;

public class NioClientTest {
	
	
	NioManager serverManager;
	
	NioManager clientManager;
	
	NioClient client;
	
	NioEventDispatcher dispatcher;
	
	NioConfig nioConfig;
	
	Nio clientNio;
	
	@Before
	public void setUp() throws Exception {
		String serverConfigFile = NioClientTest.class.getResource("..").getPath().concat("resource/nio_server.xml");
		NioConfig serverConfig = new NioConfig();
		serverConfig.parse(serverConfigFile);
		NioEngine.getInstance().init(serverConfig);
		NioEngine.getInstance().start();
		this.dispatcher = NioEventDispatcher.getInstance();
		this.dispatcher.init(new MapConfiguration());
		this.dispatcher.start();
		this.serverManager = new NormalNioManager("TestNioManager");
		serverManager.init(serverConfig);
		serverManager.start();
		
		String clientConfigFile = NioClientTest.class.getResource("..").getPath().concat("resource/nio_client.xml");
		this.nioConfig = new NioConfig();
		this.nioConfig.parse(clientConfigFile);
		this.clientManager = new NormalNioManager("TestClientManager");
		this.testInitialize();
		this.testStartup();
	}
	
	

	public void testInitialize() {
		this.clientManager.init(this.nioConfig);
		this.client = this.clientManager.getNioClient();
		assertTrue(this.clientManager.isInited());
		assertTrue(this.client.isInited());
	}

	public void testStartup() {
		this.clientManager.start();
		assertTrue(this.clientManager.isStarted());
		assertTrue(this.client.isStarted());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.clientNio = this.clientManager.getNioSet().iterator().next();
		Assert.assertTrue(this.clientNio.getChannel().isConnected());
		Assert.assertTrue(this.clientNio.getChannel().isOpen());
		Assert.assertTrue(this.clientNio.getChannel().isRegistered());
		Assert.assertTrue(this.clientNio.getSelectionKey().channel() == this.clientNio.getChannel());
		Assert.assertTrue(this.clientNio.getSelectionKey().attachment() == this.clientNio);
		Assert.assertTrue((this.clientNio.getSelectionKey().interestOps() & SelectionKey.OP_READ) > 0);
	}
	@Test
	public void test() throws Exception{
		this.test_connect();
		//this.testOnConnectAbort();
		this.testOnClose();
	}
	
	public void test_connect() {
		this.client._connect();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Set<Nio> nios = this.clientManager.getNioSet();
		assertTrue(nios.size() == 2);
		for(Nio nio : nios){
			Assert.assertTrue(nio.getChannel().isConnected());
			Assert.assertTrue(nio.getChannel().isOpen());
			Assert.assertTrue(nio.getChannel().isRegistered());
			Assert.assertTrue(nio.getSelectionKey().channel() == nio.getChannel());
			Assert.assertTrue(nio.getSelectionKey().attachment() == nio);
			Assert.assertTrue((nio.getSelectionKey().interestOps() & SelectionKey.OP_READ) > 0);
		}
		
		try {
			this.clientNio.closeChannel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		nios.remove(this.clientNio);
		this.clientNio = nios.iterator().next();
		assertTrue(this.clientManager.nioSetSize() == 1);
	}
	
	public void testOnConnectAbort() throws Exception {
		int delay = this.client.getDelay();
		while(delay < 60){
			this.clientManager.getNioSet().remove(this.clientNio);
			this.client.onConnectAbort(this.clientNio.getChannel(), new IllegalStateException());
			Assert.assertFalse(this.clientNio.getChannel().isOpen());
			Assert.assertFalse(this.clientNio.getSelectionKey().isValid());
			if(delay == 0){
				delay = 1;
			}else{
				delay = delay * 2;
				if(delay > 60)
					delay = 60;
			}
			System.out.println(delay);
			assertTrue(delay == this.client.getDelay());
			Thread.sleep(delay * 1000 + 1000);
			this.clientNio = this.clientManager.getNioSet().iterator().next();
			Assert.assertTrue(this.clientNio.getChannel().isConnected());
			Assert.assertTrue(this.clientNio.getChannel().isOpen());
			Assert.assertTrue(this.clientNio.getChannel().isRegistered());
			Assert.assertTrue(this.clientNio.getSelectionKey().channel() == this.clientNio.getChannel());
			Assert.assertTrue(this.clientNio.getSelectionKey().attachment() == this.clientNio);
			Assert.assertTrue((this.clientNio.getSelectionKey().interestOps() & SelectionKey.OP_READ) > 0);
		}
	}

	public void testOnClose() {
		try {
			this.clientManager.closeNio(clientNio, new NullPointerException());
			//this.client.onClose(clientNio, new NullPointerException());
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertFalse(clientNio == this.clientManager.getNioSet().iterator().next());
		//assertFalse(this.clientNio.getChannel().isConnected());
		Assert.assertFalse(this.clientNio.getChannel().isOpen());
		Assert.assertFalse(this.clientNio.getSelectionKey().isValid());
		this.clientNio = this.clientManager.getNioSet().iterator().next();
		Assert.assertTrue(this.clientNio.getChannel().isConnected());
		Assert.assertTrue(this.clientNio.getChannel().isOpen());
		Assert.assertTrue(this.clientNio.getChannel().isRegistered());
		Assert.assertTrue(this.clientNio.getSelectionKey().channel() == this.clientNio.getChannel());
		Assert.assertTrue(this.clientNio.getSelectionKey().attachment() == this.clientNio);
		Assert.assertTrue((this.clientNio.getSelectionKey().interestOps() & SelectionKey.OP_READ) > 0);
	}

	
	public void testShutdown() throws InterruptedException {
		Assert.assertTrue(this.clientManager.getNioSet().size() == 1);
		this.clientManager.stop();
		Thread.sleep(1000 + 1000);
		Assert.assertTrue(this.clientManager.getNioSet().isEmpty());
		Assert.assertTrue(this.clientManager.isStopped());
		Assert.assertTrue(this.client.isStopped());
		Assert.assertFalse(this.clientNio.getChannel().isOpen());
		Assert.assertFalse(this.clientNio.getSelectionKey().isValid());
		
	}

	@After
	public void tearDown() throws Exception {
		this.testShutdown();
	}
}
