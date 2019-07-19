package jobs.toolkit.event;

/**
 * 事件分派, 调度器接口
 * @author jobs
 *
 */
public  interface Dispatcher<T extends Event> {
	/**
	 * 注册对应类型的事件处理器
	 * @param eventType
	 * @param eventHandler
	 */
	void register(EventType eventType,  EventHandler<? extends T> eventHandler);
	/**
	 * 解除注册的对应类型的事件处理器
	 * @param eventType
	 */
	void unRegister(EventType eventType);
	/**
	 * 派遣调度
	 */
	void dispatch(T event);
}
