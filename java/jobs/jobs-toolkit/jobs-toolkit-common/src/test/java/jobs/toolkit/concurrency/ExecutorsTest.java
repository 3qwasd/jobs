package jobs.toolkit.concurrency;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;

public class ExecutorsTest {
	
	@Test
	public void testForkJoin(){
		ForkJoinPool forkJoinPool = new ForkJoinPool();
	}
	
	@Test
	public void testPoolExpansion() throws InterruptedException{
		int MAX_SIZE = 10;
		TestThreadFactory factory = new TestThreadFactory();
		ExecutorService pool = Executors.newFixedThreadPool(MAX_SIZE, factory);
		
		for(int i = 0; i < 10 * MAX_SIZE; i++){
			pool.execute(new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep(Long.MAX_VALUE);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						Thread.currentThread().interrupt();
					}
				}
			});
		}
		for(int i = 0; i < 20 && factory.numCreated.get() < MAX_SIZE; i++){
			Thread.sleep(100);
		}
		Assert.assertEquals(factory.numCreated.get(), MAX_SIZE);
		pool.shutdownNow();
	}
	
	class TestThreadFactory implements ThreadFactory{
		public final AtomicInteger numCreated = new AtomicInteger(0);
		
		private final ThreadFactory factory = Executors.defaultThreadFactory();
		@Override
		public Thread newThread(Runnable r) {
			// TODO Auto-generated method stub
			numCreated.incrementAndGet();
			return factory.newThread(r);
		}
		
	}
}
