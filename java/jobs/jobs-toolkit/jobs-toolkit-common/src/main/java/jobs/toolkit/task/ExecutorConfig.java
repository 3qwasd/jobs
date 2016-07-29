package jobs.toolkit.task;

import jobs.toolkit.config.XmlConfiguration;

public class ExecutorConfig extends XmlConfiguration {
	
	/*该属性表示Executor线程池的基本大小, 也就是说, 在没有任务执行时, 线程池中存活的线程数*/
	public static final String EXECUTOR_BASE_THREAD_NUM = "baseThreadNum";
	/*该属性表示Executor线程池中的最大线程数, 也就是线程池中可以同时活跃的线程数量的上限*/
	public static final String EXECUTOR_MAX_THREAD_NUM = "maxTheadNum";
	/*任务队列的大小*/
	public static final String EXECUTOR_TASK_QUEUE_SIZE = "taskQueueSize";
	/*空闲线程的存活时间, 超过该时间的线程将被回收, 单位是毫秒*/
	public static final String EXECUTOR_THREAD_KEEP_ALIVE = "keepAliveTime";
}
