package jobs.toolkit.task;

import jobs.toolkit.logging.Log;
import jobs.toolkit.logging.LogFactory;

/**
 * TaskExecutor的默认线程, 线程的主循环Runnable由ThreadPoolExecutor的Worker类提供
 * @author jobs
 *
 */
final class Worker extends Thread {
	
	private static final Log LOG = LogFactory.getLog(Worker.class);
	
	private final String name;

	public Worker(String executorName, Runnable r) {
		super(r);
		this.setDaemon(true);
		this.name = String.format("executor_%1$s.worker.%2$s", executorName, this.getId());
	}

	@Override
	public void run() {
		try{
			super.run();
		}catch(Throwable e){
			LOG.error(String.format("Work %1$s catch Exception", this.name), e);
		}
	}
	
}
