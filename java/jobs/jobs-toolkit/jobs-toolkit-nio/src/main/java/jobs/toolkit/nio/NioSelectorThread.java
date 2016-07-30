package jobs.toolkit.nio;

import java.io.Closeable;
import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import jobs.toolkit.logging.Log;
import jobs.toolkit.logging.LogFactory;
import jobs.toolkit.nio.event.SelectedEvent;

/**
 * Selector线程, 负责Selector事件管理和派发
 * @author jobs
 *
 */
public class NioSelectorThread extends Thread implements Closeable{

	protected final Log LOG = LogFactory.getLog(this.getClass());

	/*表示线程状态的布尔变量*/
	private volatile boolean running = true;
	/*java nio Selector*/
	private final Selector selector;
	/*由该线程执行的任务列表, 主要执行的是将SelectableChannel注册当前线程维护的selector上的任务, 
	 *也就是说对selector的各种操作均由当前线程来执行, 不允许当前线程以外的线程直接操作selector, 不允许将selector发布出去*/
	private volatile List<Runnable> tasks = new ArrayList<Runnable>();
	/*任务列表的锁*/
	private final Lock taskLock = new ReentrantLock();

	public NioSelectorThread(String threadName) {
		super(threadName);
		this.setDaemon(true);
		try {
			selector = Selector.open();
		} catch (java.io.IOException e) {
			throw new java.io.IOError(e);
		}
	}
	/**
	 * 提交注册任务, 将一个channel通道注册到当前线程的selector上
	 * @param channel
	 * @param optionCode
	 * @param eventHandler
	 */
	public FutureTask<SelectionKey> submitRegsiterTask(final SelectableChannel channel, final int optionCode, final Object attement){
		this.taskLock.lock();
		try{
			FutureTask<SelectionKey> regsiterTask = new FutureTask<SelectionKey>(new Callable<SelectionKey>() {

				@Override
				public SelectionKey call() throws Exception {
					// TODO Auto-generated method stub
					return NioSelectorThread.this.resgister(channel, optionCode, attement);
				}
			});
			this.tasks.add(regsiterTask);
			/*唤醒selector*/
			this.selector.wakeup();
			
			return regsiterTask;
		}finally{
			this.taskLock.unlock();
		}
	}
	/**
	 * 注册一个通道到本线程的selector上
	 * 该方法只能由本线程执行调用, 如果是非本线程之外的线程调用会抛出RuntimeException
	 * @param channel
	 * @param optionCode
	 * @param eventHandler
	 * @return
	 */
	private SelectionKey resgister(SelectableChannel channel, int optionCode, Object attement){
		try {
			if(Thread.currentThread() != this){
				throw new RuntimeException("Resgister must execute by the thread who hold the selector!");
			}
			channel.configureBlocking(false);
			return channel.register(this.selector, optionCode, attement);
		} catch (IOException e) {
			throw new java.io.IOError(e);
		} 
	}

	/**
	 * 执行任务列表中的注册任务
	 */
	private void executeNow() {
		List<Runnable> rt = null;
		this.taskLock.lock();
		try {
			if (this.tasks.isEmpty())
				return; // 没有任务。
			rt = tasks;
			tasks = new ArrayList<Runnable>();
		} finally {
			this.taskLock.unlock();
		}
		for (Runnable task : rt) {
			try {
				task.run();
			} catch (Throwable e) {
				LOG.error(String.format("SelectorThread [%1$s] execut task occurs exception!", this.getName()), e);
			}
		}
	}


	@Override
	public void run() {
		while(this.running){
			try{
				/*执行任务列表中的任务*/
				this.executeNow();
				/*执行selector上的相关事件*/
				if(this.selector.select() <= 0)continue;
				/*获取有事件发生的selectionKey*/
				java.util.Set<SelectionKey> selected = this.selector.selectedKeys();
				for(SelectionKey key : selected){
					if(!key.isValid()) continue;
					SelectedEvent.occur(key);
				}
				/*清空selected set*/
				selected.clear();
			}catch(Throwable e){
				LOG.error(String.format("SelectorThread [%1$s] has occur exception ! ", this.getName()), e);
			}
		}
	}

	@Override
	public void close(){
		this.running = false;
		this.selector.wakeup();

		while(true){
			try {
				this.join();
				break;
			} catch (Throwable e) {
				LOG.error(String.format("SelectorThread [%1$s] occur exception in closing!", this.getName()), e);
			}
		}

		try {
			this.selector.close();
		} catch (Throwable e) {
			LOG.error(String.format("SelectorThread [%1$s] selector occur exception in closing!", this.getName()), e);
		}

	}

}
