package jobs.toolkit.protocol;

import jobs.toolkit.event.BaseEvent;
import jobs.toolkit.event.EventType;

/**
 * 远程消息事件
 * @author jobs
 *
 */
public abstract class MessageEvent extends BaseEvent{
	
	public static final String TYPE_NAME_PREFIX = "MSG-";
	
	private volatile short code; 
	
	public MessageEvent(short code) {
		super(EventType.of(TYPE_NAME_PREFIX.concat(Integer.toHexString(code))));
		this.code = code;
	}
	public MessageEvent(){
		super();
	}
	public void setCode(short code){
		this.code = code;
		this.setEventType(EventType.of(TYPE_NAME_PREFIX.concat(Integer.toHexString(code))));
	}
	public short getCode() {
		return code;
	}
	
	public static EventType getEventTypeByCode(short code){
		return EventType.of(TYPE_NAME_PREFIX.concat(Integer.toHexString(code)));
	}
	
	public abstract void transfer();
}
