/**
 * @QiaoJian
 */
package cn.edu.bjtu.joe.system.thread.component;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import cn.edu.bjtu.joe.system.thread.log.JoeLoggerFactory;
import cn.edu.bjtu.joe.system.thread.log.JoeThreadLogger;
import cn.edu.bjtu.joe.system.thread.log.JoeThreadLogger.TaskLogType;
import cn.edu.bjtu.joe.system.thread.log.JoeThreadLogger.ThreadPoolLogType;


/**
 * @author QiaoJian
 *	自定义线程池，继承自{@linkplain}ThreadPoolExecutor
 */
public class JoeThreadPoolExecutor extends ThreadPoolExecutor{
	
	private final ThreadLocal<Long> startTime = new ThreadLocal<Long>();
	
	private final JoeThreadLogger logger = JoeLoggerFactory.getJoeThreadLogger();
	
	private Semaphore semaphore;
	
	private final AtomicLong taskNumber = new AtomicLong();
	private final AtomicLong totoalTime = new AtomicLong();
	
	/**
	 * 
	 * @param corePoolSize
	 * @param maximumPoolSize
	 * @param keepAliveTime
	 * @param unit
	 * @param workQueue
	 * @param handler
	 */
	public JoeThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
		// TODO Auto-generated constructor stub
		logger.recordThreadPoolLog(this,ThreadPoolLogType.CREATE);
	}
	/**
	 * 
	 * @param corePoolSize
	 * @param maximumPoolSize
	 * @param keepAliveTime
	 * @param unit
	 * @param workQueue
	 * @param threadFactory
	 * @param handler
	 */
	public JoeThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
			RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
				threadFactory, handler);
		logger.recordThreadPoolLog(this,ThreadPoolLogType.CREATE);
		// TODO Auto-generated constructor stub
	}

	public JoeThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
				threadFactory);
		logger.recordThreadPoolLog(this,ThreadPoolLogType.CREATE);
		// TODO Auto-generated constructor stub
	}

	public JoeThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		// TODO Auto-generated constructor stub
		logger.recordThreadPoolLog(this,ThreadPoolLogType.CREATE);
	}
	
	public void setSemaphore(Semaphore semaphore){
		this.semaphore = semaphore;
	}
	
	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		// TODO Auto-generated method stub
		try{
			JoeBaseTask task = (JoeBaseTask) r;
			startTime.set(System.nanoTime());
			task.setTaskStartTime();
			logger.recordTaskLog(task, TaskLogType.START);
		}finally{
			super.beforeExecute(t, r);
		}
	}
	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		// TODO Auto-generated method stub
		try{
			
			JoeBaseTask task = (JoeBaseTask) r;
			task.setTaskCompleteTime();
			long endTime = System.nanoTime();
			long startTime = this.startTime.get();
			task.setRunTime(startTime, endTime);
			logger.recordTaskLog(task, TaskLogType.COMPLETE);
			
		}finally{
			
			super.afterExecute(r, t);
			semaphore.release();
		}
	}
	
	@Override
	public void execute(Runnable command) {
		// TODO Auto-generated method stub
		try {
			semaphore.acquire();
			if(command instanceof JoeBaseTask){
				JoeBaseTask task = (JoeBaseTask) command;
				task.setTaskSubmitTime();
				logger.recordTaskLog(task, TaskLogType.SUBMIT);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			semaphore.release();
		}
		super.execute(command);
		
	}
	@Override
	protected void terminated() {
		// TODO Auto-generated method stub
		super.terminated();
		logger.recordThreadPoolLog(this, ThreadPoolLogType.TERMINATE);
	}
	/**
	 * 获取执行任务的总时间
	 * @return
	 */
	public Long getTotoalTime(){
		return this.totoalTime.get();
	}
	/**
	 * 获取执行成的任务数量
	 * @return
	 */
	public Long getTaskNumber(){
		return this.taskNumber.get();
	}
}
