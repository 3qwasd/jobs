package jobs.toolkit.nio.event;

import jobs.toolkit.event.BaseEvent;
import jobs.toolkit.event.EventType;

public abstract class NioEvent extends BaseEvent {

	public NioEvent(EventType eventType, String eventId) {
		super(eventType, eventId);
	}
	public NioEvent() {
		super();
	}
}
