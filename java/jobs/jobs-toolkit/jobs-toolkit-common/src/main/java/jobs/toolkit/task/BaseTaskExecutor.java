package jobs.toolkit.task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import jobs.toolkit.logging.Log;
import jobs.toolkit.logging.LogFactory;

/**
 * 基本的任务执行器实现类, 继承自ThreadPoolExecutor, 主要拓展了日志、记时等功能
 * 提供默认的任务饱和策略等
 * @author jobs
 *
 * @param <T>
 * @param <V>
 */
public abstract class BaseTaskExecutor extends ThreadPoolExecutor{
	
	private final String name;
	
	/*该日志用于记录Executor自身的信息*/
	private static final Log LOG = LogFactory.getLog(BaseTaskExecutor.class);
	
	/*该日志用于记录已经提交的作业的相关信息, 子类可以在构造器里面重新实例化
	 *作业执行的一些信息, 例如开始时间, 结束时间, 用时, 异常信息等可以委托Executor使用
	 *taskLog进行记录, 作业执行的细节信息由作业的实现者记录*/
	private volatile TaskLog taskLog;
	
	/*执行完成的任务数量*/
	private static final AtomicLong executedNumTasks = new AtomicLong();
	
	private static final AtomicLong totalTime = new AtomicLong();
	
	private final ThreadLocal<Long> startTime = new ThreadLocal<Long>();
	
	public BaseTaskExecutor(String name, int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		this.name = name;
		this.setRejectedExecutionHandler(new DiscardAndLogRejectedTaskPolicy());
		taskLog = new DefaultTaskLog();
	}
	
	
	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		// TODO Auto-generated method stub
		super.beforeExecute(t, r);
		this.startTime.set(System.nanoTime());
		this.taskLog.traceStart(r, this.startTime.get());
	}

	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		// TODO Auto-generated method stub
		try{
			long endTime = System.nanoTime();
			long taskTime = endTime - this.startTime.get();
			this.executedNumTasks.incrementAndGet();
			this.totalTime.addAndGet(taskTime);
			if(t != null){
				this.taskLog.traceException(r, t);
			}
			this.taskLog.traceEnd(r, taskTime, taskTime);
		}finally{
			super.afterExecute(r, t);
		}
		
	}

	@Override
	protected void terminated() {
		// TODO Auto-generated method stub
		super.terminated();
	}




	/**
	 * 
	 * 默认的任务饱和策略,丢弃被拒绝的任务, 同时日志记录任务的信息
	 * @author jobs
	 *
	 */
	private class DiscardAndLogRejectedTaskPolicy implements RejectedExecutionHandler{

		@Override
		public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
			/*任务日志记录被拒绝的任务信息*/
			taskLog.traceReject(r, name);
			/*Executor日志记录executor拒绝了task, 主要记录此刻Executor的状态*/
			LOG.info(String.format("task executor %1$s reject task, executor'status is %2$s now. ", name, e.toString()));
		}
		
	}
	private class LogableFuturTask<V> extends FutureTask<V>{
		
		private Callable<V> callableTask = null;
		
		private Runnable runnableTask = null;
		
		public LogableFuturTask(Callable<V> callable) {
			super(callable);
			this.callableTask = callable;
		}

		public LogableFuturTask(Runnable runnable, V result) {
			super(runnable, result);
			this.runnableTask = runnable;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return callableTask != null ? callableTask.toString() : runnableTask.toString();
		}
	}
	@Override
	protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
		// TODO Auto-generated method stub
		return new LogableFuturTask<T>(runnable, value);
	}

	@Override
	protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
		// TODO Auto-generated method stub
		return new LogableFuturTask<>(callable);
	}
	
}
