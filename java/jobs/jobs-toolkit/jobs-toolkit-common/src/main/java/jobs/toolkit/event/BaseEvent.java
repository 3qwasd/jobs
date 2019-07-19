package jobs.toolkit.event;


/**
 * 基本的事件实现类
 * @author jobs
 *
 */
public abstract class BaseEvent implements Event{
	
	private volatile EventType eventType;
	
	private final long occurTime;
	
	private final String id;
		
	public BaseEvent(EventType eventType, String id){
		this.eventType = eventType;
		this.id = id;
		this.occurTime = this.generateOccurTime();
	}
	public BaseEvent(EventType eventType){
		this.eventType = eventType;
		this.id = this.generateEventId();
		this.occurTime = this.generateOccurTime();
	}
	public BaseEvent(){
		this(null);
	}
	/**
	 * 如果需要生成occurTime, 可以在子类中重写该方法, 默认不生成occurTime
	 * @return
	 */
	public long generateOccurTime(){
		return -1;
	}
	/**
	 * 如果需要生成eventId, 可以在子类中重写该方法, 默认不生成id
	 * @return
	 */
	public String generateEventId(){
		return null;
	}
	@Override
	public final EventType getEventType() {
		// TODO Auto-generated method stub
		return this.eventType;
	}
	@Override
	public final long getOccurtime() {
		// TODO Auto-generated method stub
		return this.occurTime;
	}
	@Override
	public final String getId() {
		// TODO Auto-generated method stub
		return this.id;
	}
	public final void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
	/**
	 * 默认情况下, 事件不会生成结果, 如果是有结果的事件, 应该重写该方法
	 */
	@Override
	public Object getOutcome() {
		// TODO Auto-generated method stub
		throw new NullPointerException(String.format("This event of type %1$s has not outcome!", this.getEventType().getName()));
	}
}
