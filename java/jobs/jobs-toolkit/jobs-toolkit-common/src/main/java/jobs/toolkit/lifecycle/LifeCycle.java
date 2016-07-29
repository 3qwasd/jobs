package jobs.toolkit.lifecycle;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import jobs.toolkit.config.Configuration;
import jobs.toolkit.lifecycle.LifeCycleStateModel.STATE;
/**
 * 生命周期组件接口
 * @author jobs
 *
 */
public interface LifeCycle extends Closeable{
	/**
	 * 初始化生命周期组件
	 * @param configuration
	 */
	void init(Configuration configuration);
	/**
	 * 启动生命周期组件
	 */
	void start();
	/**
	 *停止生命周期组件
	 */
	void stop();
	
	/**
	 * 关闭生命周期组件
	 */
	void close() throws IOException;
	
	/**
	 * 注册状态监听器
	 * @param listener
	 */
	void registerStateListener(LifeCycleStateListener listener);
	/**
	 * 移除状态监听器
	 * @param listener
	 */
	void unRegisterStateListener(LifeCycleStateListener listener);
	
	/**
	 * 获取组件的启动时间
	 * @return
	 */
	long getStartTime();
	
	/**
	 * 获取引起组件异常的原因
	 * @return
	 */
	Throwable getFailureCause();
	/**
	 * 获取组件发生异常的状态
	 * @return
	 */
	STATE getFailureState();
	/**
	 * 等待组件停止
	 * 
	 * 该方法由等待服务停止的线程调用, 如果服务正在执行停止操作, 但还未完全停止, 调用该方法的线程
	 * 会休眠等待, 一旦服务完成停止操作, 会唤醒因调用此方法而休眠的线程
	 * @param timeout 每次休眠的最大时长
	 * @return
	 */
	boolean waitToStop(long timeout);
	/**
	 * 获取组件名称
	 * @return
	 */
	String getName();
	/**
	 * 获取生命周期事件历史记录
	 * @return
	 */
	List<LifeCycleStateTransferEvent> getLifecycleHistory();
	/**
	 * 判断组件是否已经启动
	 * @return
	 */
	boolean isStarted();
	/**
	 * 判断组件是否已经初始化
	 * @return
	 */
	boolean isInited();
	/**
	 * 判断组件是否已经停止
	 * @return
	 */
	boolean isStopped();
}
