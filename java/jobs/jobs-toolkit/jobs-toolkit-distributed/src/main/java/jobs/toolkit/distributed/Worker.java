package jobs.toolkit.distributed;

/**
 * work类型节点
 * @author jobs
 *
 */
public interface Worker extends Common{
	
	public void registerWorker();
	
	public <T> T receiveTask();
	
	public <T> void executeTask(T task);
}
