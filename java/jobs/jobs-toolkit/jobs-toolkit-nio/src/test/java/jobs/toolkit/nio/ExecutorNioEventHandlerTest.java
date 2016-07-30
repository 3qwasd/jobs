package jobs.toolkit.nio;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import jobs.toolkit.nio.event.ExecutorNioEventHandler;
import jobs.toolkit.nio.event.RunnableNioEvent;

public class ExecutorNioEventHandlerTest {
	
	ExecutorNioEventHandler handler;
	@Before
	public void setUp() throws Exception {
		this.handler = new ExecutorNioEventHandler();
	}


	@Test
	public void testDoHandle() throws Exception {
		final AtomicInteger count = new AtomicInteger(0);
		final CountDownLatch latch = new CountDownLatch(3);
		final long current = System.currentTimeMillis();
		this.handler.doHandle(new RunnableNioEvent(){

			@Override
			public void run() {
				count.incrementAndGet();
				latch.countDown();
			}
			
		});
		this.handler.doHandle(new RunnableNioEvent(1000) {
			
			@Override
			public void run() {
				count.incrementAndGet();
				latch.countDown();
			}
		});
		this.handler.doHandle(new RunnableNioEvent(2000) {
			
			@Override
			public void run() {
				count.incrementAndGet();
				latch.countDown();
				throw new IllegalArgumentException();
			}
		});
		latch.await();
		Thread.sleep(1000);
		Assert.assertTrue(count.get() == 3);
		
	}

	@Test
	public void testDoExcepion() {
		
	}

	@Test
	public void testAfterExecuteRunnableThrowable() {
		
	}
	

	@After
	public void tearDown() throws Exception {
	}
}
