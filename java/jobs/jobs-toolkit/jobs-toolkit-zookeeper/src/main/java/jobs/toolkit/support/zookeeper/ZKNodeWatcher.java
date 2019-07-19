package jobs.toolkit.support.zookeeper;

import jobs.toolkit.support.zookeeper.fun.EventFun;

public interface ZKNodeWatcher<T, Fun> extends ZKCallback<T, Fun>{
	/**
	 * 设置是否监控目标节点
	 * @param isWatch
	 * @return
	 */
	public T isWatch(boolean isWatch);
	/**
	 * 监控目标节点, 如果发生NodeChildrenChanged事件时的回调方法
	 * @param fun
	 * @return
	 */
	public T childChangedFun(EventFun fun);
	/**
	 * 监控目标节点, 如果发生NodeCreated事件时的回调方法
	 * @param fun
	 * @return
	 */
	public T nodeCreatedFun(EventFun fun);
	/**
	 * 监控目标节点, 如果发生NodeDeleted事件时的回调方法
	 * @param fun
	 * @return
	 */
	public T nodeDeletedFun(EventFun fun);
	/**
	 * 监控目标节点, 如果发生NodeDataChanged事件时的回调方法
	 * @param fun
	 * @return
	 */
	public T dataChangedFun(EventFun fun);
	/**
	 * 监控目标节点, 如果发生未知错误事件时的回调方法
	 * @param fun
	 * @return
	 */
	public T errorEventFun(EventFun fun);
}
