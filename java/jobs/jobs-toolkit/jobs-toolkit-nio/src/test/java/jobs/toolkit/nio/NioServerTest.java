/**
 * 
 */
package jobs.toolkit.nio;

import static org.junit.Assert.*;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import jobs.toolkit.nio.NioConfig;
import jobs.toolkit.nio.NioEngine;
import jobs.toolkit.nio.NioManager;
import jobs.toolkit.nio.NioServer;
import jobs.toolkit.config.MapConfiguration;
import jobs.toolkit.event.EventType;
import jobs.toolkit.nio.event.NioEventDispatcher;
import jobs.toolkit.nio.event.RunnableNioEvent;

/**
 * @author jobs
 *
 */
public class NioServerTest {


	NioManager manager;

	NioServer server;

	NioEventDispatcher dispatcher;

	NioConfig nioConfig;
	@Before
	public void setUp() throws Exception {
		//启动engine和dispatcher
		String configFile = NioServerTest.class.getResource("..").getPath().concat("resource/nio.xml");
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
		this.testInitialize();
		this.testStartup();
	}

	public void testInitialize() {
		Assert.assertTrue(this.manager.isInited());
		Assert.assertTrue(this.server.isInited());
		Assert.assertEquals(this.server.getInputBufferSize(), NioConfig.DEFAULT_INPUT_BUFFER_SIZE);
		Assert.assertEquals(this.server.getOutputBufferSize(), NioConfig.DEFAULT_OUTPUT_BUFFER_SIZE);
		Assert.assertEquals(this.server.getReceiveBufferSize(), NioConfig.DEFAULT_RECEIVE_BUFFER_SIZE);
		Assert.assertEquals(this.server.getSendBufferSize(), NioConfig.DEFAULT_SEND_BUFFER_SIZE);
		Assert.assertEquals(this.server.isKeepInputBuffer(), NioConfig.DEFAULT_KEEP_INPUT_BUFFER);
		Assert.assertEquals(this.server.isKeepOutputBuffer(), NioConfig.DEFAULT_KEEP_OUTPUT_BUFFER);
		Assert.assertEquals(this.server.isTcpNoDelay(), NioConfig.DEFAULT_TCP_NO_DELAY);
		Assert.assertEquals(this.server.getName(), "TestNioServer");
		Assert.assertEquals(this.server.getAddress(), new InetSocketAddress(8888));
		Assert.assertEquals(this.server.getBacklog(), NioConfig.DEFAULT_BACK_LOG);
	}

	public void testStartup() {
		this.manager.start();
		Assert.assertTrue(this.manager.isStarted());
		Assert.assertTrue(this.server.isStarted());
		Assert.assertTrue(this.server.getServerKey().isValid());
		Assert.assertTrue(this.server.getServer().isOpen());
		Assert.assertTrue(this.server.getServer().isRegistered());
		Assert.assertTrue(this.server.getServerKey().channel() == this.server.getServer());
		Assert.assertTrue(this.server.getServerKey().attachment() == this.server);
		Assert.assertTrue((this.server.getServerKey().interestOps() & SelectionKey.OP_ACCEPT) > 0);
	}


	public void testShutdown() {
		this.manager.stop();
		Assert.assertTrue(this.manager.isStopped());
		Assert.assertTrue(this.server.isStopped());
		Assert.assertFalse(this.server.getServerKey().isValid());
		Assert.assertFalse(this.server.getServer().isOpen());
	}
	@Test
	public void test() throws Throwable{
		this.testOnConnectSuccess();
		this.testOnConnectAbort();
		this.testOnClose();
	}

	public void testOnConnectSuccess(){
		
		try {
			this.server.onConnectSucess(null);
		}catch (Throwable _) {
			fail();
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try{
			this.dispatcher.unRegister(EventType.of(RunnableNioEvent.TYPE_NAME));
			this.server.onConnectSucess(null);
			fail();
		}catch(Throwable e){
			e.printStackTrace();
			assertTrue(e instanceof IllegalStateException);
		}
	}


	
	public void testOnConnectAbort() {
		this.server.onConnectAbort(null, new NullPointerException());
	}


	public void testOnClose() {
		try {
			this.server.onClose(null, null);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@After
	public void tearDown() throws Exception {
		this.testShutdown();
	}

}
