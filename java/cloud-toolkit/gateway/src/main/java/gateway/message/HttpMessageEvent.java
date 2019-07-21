package gateway.message;

import jobs.toolkit.event.BaseEvent;
import jobs.toolkit.event.EventType;

public abstract class HttpMessageEvent extends BaseEvent{
	
	public HttpMessageEvent(EventType eventType) {
		super(eventType);
	}
	
	public abstract String getContextPath();
	
	public abstract String getServiceVersion();
}
