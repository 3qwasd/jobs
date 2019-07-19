package jobs.toolkit.support.zookeeper;

import jobs.toolkit.support.zookeeper.fun.EventFun;

public interface ZKSessionWatcher<T> extends ZKHelper<T>{
	/**
	 * 设置zookeeper会话发生未知错误时的处理函数
	 * @param fun
	 * @return
	 */
	public T errorEventFun(EventFun fun);
	/**
	 * 设置zookeeper会话发生验证错误时的处理函数
	 * @param fun
	 * @return
	 */
	public T authFailedFun(EventFun fun);
	/**
	 * 设置zookeeper会话发生连接丢失时的处理函数, 注意zookeeper客户端本身具有重连机制
	 * @param fun
	 * @return
	 */
	public T disconnedFun(EventFun fun);
	/**
	 * 设置zookeeper会话过期时的回调函数
	 * @param fun
	 * @return
	 */
	public T expiredFun(EventFun fun);
	/**
	 * 设置zookeeper会话发生SaslAuthenticated时的回调函数
	 * @param fun
	 * @return
	 */
	public T saslAuthedFun(EventFun fun);
	/**
	 * 设置zookeeper会话连接成功时的回调函数
	 * @param fun
	 * @return
	 */
	public T syncConnedFun(EventFun fun);
}
