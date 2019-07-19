/**
 * @QiaoJian
 */
package cn.edu.bjtu.joe.system.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.edu.bjtu.joe.system.thread.component.JoeTaskThreadFactory;
import cn.edu.bjtu.joe.system.thread.component.JoeThreadPoolExecutor;
import cn.edu.bjtu.joe.system.utils.SystemUtils;

/**
 * @author QiaoJian
 *	线程服务类，提供多线程任务的服务
 */
public class JoeThreadService{
	
	/*threadService 实例*/
	private static JoeThreadService threadService;
	
	private final  Semaphore semaphore;
	
	private JoeThreadPoolExecutor taskExecutor;
	
	private JoeThreadService(){
		semaphore = new Semaphore(SystemUtils.getSystemProcessorNumber()+1);
	}
	
	
	/**
	 * 创建线程池
	 */
	private void createJoeThreadPoolExecutor(){
		int processNum = SystemUtils.getSystemProcessorNumber();
		long keepAliveTime = 1000;
		TimeUnit unit = TimeUnit.MILLISECONDS;
		ThreadFactory threadFactory = new JoeTaskThreadFactory();
		BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(processNum+1);
		taskExecutor = new JoeThreadPoolExecutor(processNum, processNum+1, keepAliveTime, unit, workQueue,threadFactory);
		taskExecutor.setSemaphore(semaphore);
	}
	/**
	 * 重置线程池
	 */
	public  void reset(){
		this.taskExecutor.shutdownNow();
		threadService = new JoeThreadService();
		threadService.createJoeThreadPoolExecutor();
	}
	/**
	 * 获取JoeThreadService 实例;
	 * @return
	 */
	public static JoeThreadService getThreadService(){
		if(threadService == null){
			threadService = new JoeThreadService();
			threadService.createJoeThreadPoolExecutor();
		}
		return threadService;
	}
	/**
	 * 提交要执行的任务
	 * @param task
	 * @throws Exception 
	 */
	public void submitTask(Runnable task){
	
			
			this.taskExecutor.execute(task);
		
	}
	/**
	 * 服务是否已经终止
	 * @return
	 */
	public boolean isTerminated(){
		return this.taskExecutor.isTerminated();
	}
	/**
	 * 获取线程池中正在执行的任务
	 * @return
	 */
	public int getActiveCount(){
		return this.taskExecutor.getActiveCount();
	}
	/**
	 * 获取线程池曾经提交的任务数
	 * @return
	 */
	public long getTaskCount(){
		return this.taskExecutor.getTaskCount();
	}
	/**
	 * 获取信号量的可用资源
	 * @return
	 */
	public int getSemaphore(){
		return this.semaphore.availablePermits();
	}
	/**
	 * 重新启动所有线程
	 */
	public void prestartAllCoreThread(){
		this.taskExecutor.prestartAllCoreThreads();
	}
	/**
	 * 获取已经运行结束的任务数量
	 * @return
	 */
	public long getCompleteTaskCount(){
		return taskExecutor.getCompletedTaskCount();
	}
	/**
	 * 停止服务
	 */
	public void shutdownService(){
		this.taskExecutor.shutdown();
	}
}
