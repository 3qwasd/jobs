package jobs.toolkit.lifecycle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import jobs.toolkit.config.Configuration;
import jobs.toolkit.lifecycle.LifeCycleStateModel.STATE;
import jobs.toolkit.logging.Log;
import jobs.toolkit.logging.LogFactory;

/**
 * 生命周期组件的抽象实现
 * @author jobs
 *
 */
public abstract class LifeCycleComponent implements LifeCycle{
	
	protected final Log LOG = LogFactory.getLog(this.getClass());
	
	/*生命周期组件的配置*/
	private volatile Configuration configuration;
	/*生命周期组件的状态模型*/
	private final LifeCycleStateModel stateModel;
	/*生命周期组件的名字*/
	private final String name;
	/*生命周期组件状态模型的锁*/
	private final Lock stateModelLock = new ReentrantLock(); 
	/*组件启动时间*/
	private long startTime;
	/*生命周期组件监听器*/
	private final LifeCycleListenerManager listenerManager = new LifeCycleListenerManager();
	/*全局的生命周期组件监听器*/
	private static final LifeCycleListenerManager globalListenerManger = new LifeCycleListenerManager();
	/*组件出现异常的原因*/
	private Throwable failureCause;
	/*组件发生异常时所处的状态*/
	private STATE failureState;
	/*负责异常处理的锁*/
	private final Lock failureLock = new ReentrantLock();
	/*生命周期状态转移历史记录*/
	private final List<LifeCycleStateTransferEvent> stateTransferHistory = new ArrayList<LifeCycleStateTransferEvent>(5);
	/*用于waitForServiceToStop方法中, 在服务停止过程中, 多个线程之间的协调*/
	private final AtomicBoolean terminationNotification = new AtomicBoolean(false);
	
	public LifeCycleComponent(String name) {
		super();
		this.name = name;
		this.stateModel = new LifeCycleStateModel();
	}

	@Override
	public final void init(Configuration configuration) {
		if(configuration == null){
			throw new LifeCycleStateException(String.format("Can't initialize life cycle component [%1$s] : null configuration", this.name));
		}
		/*如果生命周期组件的状态是已经初始化, 则直接返回*/
		/*此处无需加锁, 因为在后续stateTransfer方法执行完后, 还有一次判断,
		 *可以保证在多线程环境下, 不会进行多次初始化*/
		if(this.isInState(STATE.INITED)){
			return;
		}
		stateModelLock.lock();
		try{
			/*若没有异常抛出并且原状态不等于新状态表示状态转换成功, 
			 *如果没有异常抛出, 但原状态等于新状态, 说明已经初始化完成, 无需在执行具体的初始化动作*/
			if(this.stateTransfer(STATE.INITED) != STATE.INITED){
				/*设置配置*/
				this.setConfiguration(configuration);
				try{
					/*初始化组件, 执行具体的组件初始化操作*/
					this.doInit();
					/*通知监听器, 组件初始化操作完成*/
					this.notifyStateListener();
				}catch(Throwable e){
					/*通知组件初始化失败*/
					this.notifyFailure(e);
					/*停止当前组件*/
					this.stop();
					/*抛出运行时异常, 供上层进行处理*/
					throw new LifeCycleStateException(String.format("Life cycle component [%1$s] init failed!", this.name), e);
				}
				/*通知监听器*/
			}
		}finally{
			stateModelLock.unlock();
		}
	}
	protected abstract void doInit() throws Exception;
	
	@Override
	public final void start() {
		/*如果生命周期组件的状态是已经启动, 则直接返回*/
		if(this.isInState(STATE.STARTED)){
			return;
		}
		
		this.stateModelLock.lock();
		
		try{
			if(this.stateTransfer(STATE.STARTED) != STATE.STARTED){
				/*记录组件启动时间*/
				this.startTime = System.currentTimeMillis();
				try{
					/*执行组件启动操作*/
					this.doStart();
					/*通知监听器组件启动*/
					this.notifyStateListener();
				}catch(Throwable e){
					/*通知组件启动出错*/
					this.notifyFailure(e);
					/*停止组件*/
					this.stop();
					throw new LifeCycleStateException(String.format("Life cycle component [%1$s] start failed!", this.name), e);
				}
			}
		}finally{
			this.stateModelLock.unlock();
		}
	}
	protected abstract void doStart() throws Exception;

	@Override
	public final void stop() {
		/*如果组件已经停止, 直接返回*/
		if(this.isInState(STATE.STOPPED)){
			return;
		}
		this.stateModelLock.lock();
		
		try{
			if(this.stateTransfer(STATE.STOPPED) != STATE.STOPPED){
				try{
					/*执行具体的组件停止工作*/
					this.doStop();
				}catch(Throwable e){
					/*停止出错, 通知异常*/
					this.notifyFailure(e);
					throw new LifeCycleStateException(String.format("Life cycle component [%1$s] stop failed!", this.name), e);
				}finally{
					/*无论停止操作是否执行成功, 都视为组件已停止,  要通知监听器组件的状态改变 */
					this.notifyStateListener();
					/*执行最后的操作*/
					this.terminate();
				}
			}
		}finally{
			this.stateModelLock.unlock();
		}
	}
	protected abstract void doStop() throws Exception;
	
	@Override
	public final void close(){
		// TODO Auto-generated method stub
		this.stop();
	}

	private STATE stateTransfer(STATE newState) {
		STATE old = this.stateModel.stateTransfer(newState);
		if(old != newState){
			this.recordLifecycleEvent(old, newState);
		}
		return old;
	}
	
	private final void recordLifecycleEvent(STATE from, STATE to) {
		stateTransferHistory.add(new LifeCycleStateTransferEvent(System.currentTimeMillis(), from, to));
	}
	
	
	@Override
	public final List<LifeCycleStateTransferEvent> getLifecycleHistory() {
		// TODO Auto-generated method stub
		return this.stateTransferHistory;
	}

	/**
	 * 服务停止的一些收尾工作
	 */
	private void terminate(){
		/*设置服务完全停止的标记为true*/
		this.terminationNotification.set(true);
		/*唤醒由于等待服务完全停止而休眠的线程*/
		synchronized (this.terminationNotification) {
			this.terminationNotification.notifyAll();
		}
	}
	
	/**
	 * 该方法由等待服务停止的线程调用, 如果服务没有停止, 调用该方法的线程
	 * 会休眠等待, 一旦服务完成停止操作, 会唤醒因调用此方法而休眠的线程
	 */
	@Override
	public boolean waitToStop(long timeout) {
		/*获取服务是否完全停止的*/
		boolean completed = terminationNotification.get();
		while(!completed){
			try{
				/*如果服务还没有停止, 那么等待服务停止的线程在此处休眠*/
				synchronized (this.terminationNotification) {
					this.terminationNotification.wait(timeout);
				}
				/*被唤醒, 说明服务已经停止*/
				completed = true;
			}catch(InterruptedException e){
				/*如果休眠被打断, 继续判断服务是否完全终止*/
				completed = this.terminationNotification.get();
			}
		}
		/*服务完全停止, 返回*/
		return this.terminationNotification.get();
	}
	@Override
	public void registerStateListener(LifeCycleStateListener listener) {
		this.listenerManager.addListener(listener);
	}

	@Override
	public void unRegisterStateListener(LifeCycleStateListener listener) {
		this.listenerManager.removeListener(listener);
	}
	
	protected void notifyStateListener() {
		try{
			this.listenerManager.notifyListeners(this);
			globalListenerManger.notifyListeners(this);
		}catch(Throwable e){
			LOG.info(String.format("Exception while notifying listeners of life cycle component [%1$s]!", this.name), e);
		}
	}
	
	protected final void notifyFailure(Throwable e) {
		if(e == null) return;
		failureLock.lock();
		try{
			if(this.failureCause == null){
				this.failureCause = e;
				this.failureState = this.getState();
				LOG.info(String.format("Life cycle component [%1$s] failed in state [%2$s]!", this.name, this.failureState), e);
			}
		}finally{
			failureLock.unlock();
		}
	}

	@Override
	public final Throwable getFailureCause() {
		failureLock.lock();
		try{
			return this.failureCause;
		}finally{
			failureLock.unlock();
		}
	}

	@Override
	public final STATE getFailureState() {
		failureLock.lock();
		try{
			return this.failureState;
		}finally{
			failureLock.unlock();
		}
	}
	
	@Override
	public final long getStartTime() {
		return this.startTime;
	}
	
	
	@Override
	public String getName() {
		return this.name;
	}
	
	/**
	 * 获取生命周期组件当前状态, 内部使用, 不做并发处理
	 * @return
	 */
	final STATE getState() {
		return this.stateModel.getState();
	}
	/**
	 * 内部使用的, 不做并发并发处理
	 * @param proposed
	 * @return
	 */
	final boolean isInState(STATE proposed) {
		return this.stateModel.isInState(proposed);
	}
	
	public final Configuration getConfiguration() {
		return configuration;
	}

	final void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
	/**
	 * 注册全局监听器
	 * @param listener
	 */
	public static void registerGlobalListener(LifeCycleStateListener listener){
		globalListenerManger.addListener(listener);
	}
	/**
	 * 解除全局监听器
	 * @param listener
	 */
	public static void unRegisterGlobalListener(LifeCycleStateListener listener){
		globalListenerManger.removeListener(listener);
	}
	
	/**
	 * 该方法用于单元测试
	 */
	protected void resetListener(){
		this.listenerManager.reset();
		globalListenerManger.reset();
	}

	@Override
	public boolean isStarted() {
		this.stateModelLock.lock();
		try{
			return this.stateModel.isInState(STATE.STARTED);
		}finally{
			this.stateModelLock.unlock();
		}
	}

	@Override
	public boolean isInited() {
		this.stateModelLock.lock();
		try{
			return this.stateModel.isInState(STATE.INITED);
		}finally{
			this.stateModelLock.unlock();
		}
	}

	@Override
	public boolean isStopped() {
		this.stateModelLock.lock();
		try{
			return this.stateModel.isInState(STATE.STOPPED);
		}finally{
			this.stateModelLock.unlock();
		}
	}
	
}
