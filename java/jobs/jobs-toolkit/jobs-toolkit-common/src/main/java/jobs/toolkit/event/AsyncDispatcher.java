package jobs.toolkit.event;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 异步的事件调度器, 由一个线程负责进行事件派发
 * @author jobs
 *
 */
public class AsyncDispatcher<T extends Event> extends BaseDispatcher<T>{

	/*服务停止时, 等待事件队列被清空时等待的默认最大时间, 单位是毫秒*/
	private static final long DEFAULT_DISPATCHER_WAITE_EVENTQUEUE_EMPTY_TIMEOUT = 1000*10;
	/*事件队列*/
	private final BlockingQueue<T> eventQueue;
	/*用来标记调度线程是否停止*/
	private volatile boolean stopped = false;
	/*执行事件调度工作的线程*/
	private Thread dispatcherThread;
	/*拒绝处理新的事件, 在该调度器执行关闭动作的时候, 需要将该值置为true*/
	private volatile boolean rejectNewEvent = false;
	/*表示, 在服务停止的时候, 是否处理队列中剩余的事件, 默认为处理*/
	private volatile boolean handlerRemainingEventsOnStop = true;
	/*服务停止时, 等待事件队列被清空时的锁*/
	private final Object waitForEventQueueEmpty = new Object();
	/*服务停止时, 等待事件队列被清空时等待的最大时间*/
	private long waitTimeout = DEFAULT_DISPATCHER_WAITE_EVENTQUEUE_EMPTY_TIMEOUT;
	
	final AtomicInteger rejectEventCount = new AtomicInteger(0);
	
	public AsyncDispatcher(String name) {
		this(name, new LinkedBlockingQueue<T>());
	}
	public AsyncDispatcher(String name, BlockingQueue<T> eventQueue) {
		super(name);
		this.eventQueue = eventQueue;
	}
	@Override
	public void dispatch(T event) {
		/*如果当前服务拒绝接受新事件, 则返回*/
		if(this.rejectNewEvent){
			rejectEventCount.incrementAndGet();
			LOG.info(String.format("AsyncDispatcher %1$s reject event %2$s", this.getName(), event.toString()));
			return;
		}
		try {
			this.eventQueue.put(event);
		} catch (InterruptedException e) {
			if (!stopped) {
				LOG.error("AsyncDispatcher thread interrupted", e);
			}
			throw new RuntimeException(e);
		}

	}
	@Override
	protected void initialize() throws Exception {
		super.initialize();
	}
	@Override
	protected void startup() throws Exception {
		super.startup();
		this.dispatcherThread = new Thread(new DispatcherThread());
		this.dispatcherThread.setName("AsyncEventDispatcherThread");
		this.dispatcherThread.start();
	}
	@Override
	protected void shutdown() throws Exception {
		/*如果需要, 等待处理完事件队列或者超过最大等待时间*/
		if(this.handlerRemainingEventsOnStop){
			/*在停止的时候先拒绝接受任何新的事件*/
			this.rejectNewEvent = true;
			LOG.info("AsyncDispatcher is draing to stop, igonring any new events.");
			long endTime = System.currentTimeMillis() + this.waitTimeout;
			/*等待派发线程清空队列*/
			synchronized (this.waitForEventQueueEmpty) {
				while( !this.eventQueue.isEmpty() //队列不为空
						&& this.dispatcherThread != null//派发线程已经创建
						&& this.dispatcherThread.isAlive()//派发线程未停止
						&& System.currentTimeMillis() < endTime){//未超过最大的等待时间
					this.waitForEventQueueEmpty.wait(1000);//执行shutdown方法的线程进入休眠
				}
			}
		}
		/*执行服务停止操作*/
		this.stopped = true;
		/*由于服务很有可能在初始化或者启动时报错停止, 这个时候派发线程有可能为实例化, 因此最好判断一下派发线程是否为空*/
		if(this.dispatcherThread != null){
			/*通过打断的方式中断派发线程*/
			this.dispatcherThread.interrupt();
			try{
				/*等待派发线程终止*/
				this.dispatcherThread.join();
			}catch(InterruptedException e){
				LOG.error("Interrupted Exception while stopping", e);
			}
		}
		super.shutdown();
	}
	/**
	 * 负责执行调度工作的线程
	 * @author jobs
	 *
	 */
	private class DispatcherThread implements Runnable{

		@Override
		public void run() {
			/*如果服务没有停止或者派发的线程未被打断*/
			while( !stopped && !Thread.currentThread().isInterrupted()){
				/*服务停止时的操作*/
				if(rejectNewEvent){//如果服务拒绝接受新的事件, 说明服务正在执行停止操作
					synchronized (waitForEventQueueEmpty) {
						if(eventQueue.isEmpty()){//判断当前事件队列是否为空
							waitForEventQueueEmpty.notify();//如果事件队列已经为空, 则唤醒等待线程继续执行停止操作
						}
					}
				}
				/*执行事件派发操作*/
				T event;
				try{
					event = eventQueue.take();
				}catch(InterruptedException e){
					if(!stopped){
						LOG.info("AsyncDispatcher thread interrupted", e);
					}
					return;
				}
				if(event != null){
					/*派发事件, 事件派发过程中的异常由doDispatch方法负责, 该线程不应该由于dispatch方法发生错误而退出*/
					doDispatch(event);
				}
			}
		}

	}
	
	/**
	 * 获取dispatcher线程的状态, 该方法只用单元测试
	 * @return
	 */
	protected java.lang.Thread.State  dispatcherThreadState(){
		return this.dispatcherThread.getState();
	}
	/**
	 * 获取事件队列, 该方法只对单元测试类可见
	 * @return
	 */
	protected BlockingQueue<T> eventQueue(){
		return this.eventQueue;
	}
	/**
	 * 获取调度线程, 该方法只用于单元测试
	 * @return
	 */
	protected Thread dispatcherThread(){
		return this.dispatcherThread;
	}
	
}
