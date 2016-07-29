package jobs.toolkit.event;

/**
 * 事件处理器接口, 每一种实现只能处理一种事件
 * @author jobs
 *
 * @param <T> 该事件处理器能够处理的事件类型
 */
public interface EventHandler<T extends Event> {
	
	public void  doHandle(T event) throws Throwable;
	
	public void doExcepion(T event, Throwable e);
}
