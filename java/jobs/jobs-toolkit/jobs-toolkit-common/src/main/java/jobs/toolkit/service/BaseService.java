package jobs.toolkit.service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import jobs.toolkit.lifecycle.LifeCycleComponent;

/**
 * 服务的抽象实现, 服务的生命周期由LifeCycleComponent来控制, 该类只需要添加服务的基础功能即可
 * @author jobs
 *
 */
public abstract class BaseService extends LifeCycleComponent implements Service{

	/* 服务状态，服务只有两种状态，可用和不可用，
	 * 服务状态与LifeCycleComponent的生命周期状态没有必然联系，
	 * 服务是否可用的逻辑由实现类提供 */
	private volatile Status status = Status.INVALID;

	private final Lock statusLock = new ReentrantLock();

	private volatile boolean isSyncWaiting = false;

	private final Object syncWaitMonitor = new Object();

	public BaseService(String name) {
		super(name);
	}

	@Override
	protected final void doInit() throws Exception {
		this.initialize();
	}

	@Override
	protected final void doStart() throws Exception {
		this.startup();
	}

	@Override
	protected final void doStop() throws Exception {
		this.inValid();
		this.isSyncWaiting = false;
		synchronized (this.syncWaitMonitor) {
			this.syncWaitMonitor.notifyAll();
		}
		this.shutdown();
	}

	protected void initialize() throws Exception {}

	protected void startup() throws Exception {}

	protected void shutdown() throws Exception {}

	@Override
	public void syncWait() throws InterruptedException{
		this.isSyncWaiting = true;
		while (!Thread.currentThread().isInterrupted() && this.isSyncWaiting) {
			try {
				synchronized (this.syncWaitMonitor) {
					this.syncWaitMonitor.wait();
				}
			} catch (InterruptedException e) {
				this.isSyncWaiting = false;
				throw new InterruptedException(String.format("Service [name=%1$s] syncWait has been interrupted.", this.getName()));
			} catch (Throwable __) {
				LOG.error(String.format("Service[name=%1$s] will stop because it's occur some error.", this.getName()), __);
				this.stop();
			}
		}
	}
	@Override
	public final boolean isValid() {
		this.statusLock.lock();
		try{
			return this.status == Status.VALID;
		}finally{
			this.statusLock.unlock();
		}
	}

	protected Status getServiceStatus() {
		return this.status;
	}
	@Override
	public final void valid() throws Exception{
		this.statusLock.lock();
		try{
			this.serviceValid();
			this.status = Status.VALID;
		} catch(Throwable e){
			throw new Exception(String.format("Service[%1$s] execute valid() occur some error.", this.getName()), e);
		} finally {
			this.statusLock.unlock();
		}
	}
	/**
	 * 服务状态转为可用需要执行的操作，需要子类重写
	 */
	protected void serviceValid(){};
	@Override
	public final void inValid() throws Exception {
		this.statusLock.lock();
		try{
			this.status = Status.INVALID;
			this.serviceInValid();
		} catch(Throwable e){
			throw new Exception(String.format("Service[%1$s] execute inValid() occur some error.", this.getName()), e);
		} finally {
			this.statusLock.unlock();
		}
	}
	/**
	 * 服务状态转为可用需要执行的操作， 需要子类重写
	 */
	protected void serviceInValid(){};
}
