package jobs.toolkit.nio;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jobs.toolkit.config.MapConfiguration;
import jobs.toolkit.nio.NioEngine;
import jobs.toolkit.nio.NioSelectorThread;

public class NioEngineTest {
	
	NioEngine nioEngine = NioEngine.getInstance();
	
	@Before
	public void setUp() throws Exception {
		MapConfiguration configuration = new MapConfiguration();
		//configuration(NioConfig.NIO_ENGINE_THREAD_NUM, 4);
		nioEngine.init(configuration);

	}

	@After
	public void tearDown() throws Exception {
		nioEngine.stop();
		assertTrue(nioEngine.isStopped());
		assertTrue(nioEngine.threadsIsInState(Thread.State.TERMINATED));
		try{
			NioEngine.verify();
			fail();
		}catch(IllegalStateException e){
			e.printStackTrace();
		}
	}

	@Test
	public void test() {
		nioEngine.start();
		assertTrue(nioEngine.threadsIsInState(Thread.State.RUNNABLE));
		assertTrue(nioEngine.isStarted());
		NioEngine.verify();
		NioSelectorThread thread = NioEngine.selector();
		assertTrue(thread.getState() == Thread.State.RUNNABLE);
	}

}
