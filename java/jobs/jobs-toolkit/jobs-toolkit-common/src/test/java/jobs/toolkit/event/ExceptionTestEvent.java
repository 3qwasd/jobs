package jobs.toolkit.event;

import java.util.UUID;

import jobs.toolkit.event.BaseEvent;
import jobs.toolkit.event.EventType;

public class ExceptionTestEvent extends BaseEvent {
	
	public int value;
	
	public ExceptionTestEvent(int value) {
		super(EventType.of(AsyncDispatcherTest.EXCEPTIONTESTEVENT));
		// TODO Auto-generated constructor stub
		this.value = value;
	}

}
