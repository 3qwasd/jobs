package jobs.toolkit.event;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import jobs.toolkit.service.BaseService;
/**
 * 事件调度器的一个基本实现, 事件调度器是一个服务, 实现Dispatcher接口
 * @author jobs
 *
 * @param <T>
 */
public abstract class BaseDispatcher<T extends Event> extends BaseService implements Dispatcher<T> {
		
	/*事件处理器的Map, 不同类型的事件对应不同的事件处理器*/
	private final Map<EventType, EventHandler<? extends T>> eventDispatchers;
	
	public BaseDispatcher(String name) {
		super(name);
		this.eventDispatchers = new ConcurrentHashMap<EventType, EventHandler<? extends T>>();
	}
	
	/**
	 * 注册某一种类型事件的事件处理器
	 */
	@Override
	public void register(EventType eventType, EventHandler<? extends T> eventHandler) {
		if(this.eventDispatchers.putIfAbsent(eventType, eventHandler) == null){
			LOG.info(String.format("Registering event type [%1$s] for handler [%2$s].", eventType, eventHandler.getClass().getName()));
		}
	}
	
	@Override
	public void unRegister(EventType eventType) {
		// TODO Auto-generated method stub
		if(this.eventDispatchers.remove(eventType) != null){
			LOG.info(String.format("Un registering handler of event type [%1$s] handler.", eventType));
		}
	}

	@Override
	public void dispatch(T event) {
		// TODO Auto-generated method stub
		this.doDispatch(event);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	void doDispatch(T event){
		LOG.info(String.format("Dispatching the event [%1$s]", event.toString()));
		EventType eventType = event.getEventType();
		EventHandler handler = this.eventDispatchers.get(eventType);
		try{
			if(handler != null){
				handler.doHandle(event);
			}else{
				throw new NullPointerException(String.format("No handler for registered for evnet type [%1$s]", eventType.toString()));
			}
		}catch(Throwable e){
			if(handler != null){
				handler.doExcepion(event, e);
			}else{
				this.dispacthFailed(event, e);
			}
		}
	}
	
	/**
	 * 派发失败时的处理函数的默认实现
	 * @param event
	 */
	protected void dispacthFailed(Event event, Throwable e){
		LOG.error(String.format("Error in dispatching event [%1$s]", event), e);
		//讲异常抛给调用者
		throw new IllegalStateException(e);
	}
	/**
	 * 获取eventHandler只对测试可见
	 * @param type
	 * @return
	 */
	protected EventHandler<? extends Event> getEventHandler(EventType type) {
		return this.eventDispatchers.get(type);
	}
	
}
