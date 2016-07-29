package jobs.toolkit.concurrency;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentTestCase {
	
	public final ExecutorService pool = Executors.newCachedThreadPool();
	
	public final Random random = new Random(System.currentTimeMillis());
	
	public final int RANDOM_VALUE_MAX = 1000;
}
