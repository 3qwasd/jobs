package jobs.toolkit.nio;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jobs.toolkit.config.MapConfiguration;
import jobs.toolkit.nio.Nio;
import jobs.toolkit.nio.NioConfig;
import jobs.toolkit.nio.NioEngine;
import jobs.toolkit.nio.NioManager;
import jobs.toolkit.nio.NioServer;
import jobs.toolkit.nio.event.NioEventDispatcher;

public class NioManagerTest {
	
	NioManager manager;
	
	NioServer server;
	
	NioEventDispatcher dispatcher;
	
	Set<Nio> backupNioSet;
	
	
	@Before
	public void setUp() throws Exception {
		String configFile = NioManagerTest.class.getResource("..").getPath().concat("resource/nio_server.xml");
		NioConfig nioConfig = new NioConfig();
		nioConfig.parse(configFile);
		NioEngine.getInstance().init(nioConfig);
		NioEngine.getInstance().start();
		this.dispatcher = NioEventDispatcher.getInstance();
		this.dispatcher.init(new MapConfiguration());
		this.dispatcher.start();
		this.manager = new NormalNioManager("TestNioManager");
		manager.init(nioConfig);
	}
	@Test
	public void testInitialize() {
		assertTrue(manager.isInited());
		assertTrue(server.isInited());
	}

	@Test
	public void testStartup() {
		this.manager.start();
		assertTrue(manager.isStarted());
		assertTrue(server.isStarted());
	}

	@Test
	public void testShutdown() {
		this.manager.stop();
	}

	@Test
	public void testCreateNewNio() {
		fail("Not yet implemented");
	}

	@Test
	public void testCloseNio() {
		fail("Not yet implemented");
	}
	@After
	public void tearDown() throws Exception {
	}

}
