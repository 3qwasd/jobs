package jobs.toolkit.nio.event;

import jobs.toolkit.event.EventType;

public class NioMessageEvent extends NioEvent {
	
	public static final String TYPE_NAME = "nio.message.event";
	
	public NioMessageEvent(String eventId) {
		super(EventType.of(TYPE_NAME), eventId);
		// TODO Auto-generated constructor stub
	}

}
