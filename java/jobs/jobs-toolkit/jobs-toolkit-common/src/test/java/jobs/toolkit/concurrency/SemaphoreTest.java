package jobs.toolkit.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {
	
	public static void main(String[] args) throws InterruptedException {
		Semaphore semaphore = new Semaphore(1);
		
		semaphore.acquire();
		System.out.println(semaphore.availablePermits());
	}
}
