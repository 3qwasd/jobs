package jobs.toolkit.event;

import java.util.UUID;

import jobs.toolkit.event.BaseEvent;
import jobs.toolkit.event.EventType;

public class SimpleTestEvent extends BaseEvent{

	public int value;
	public SimpleTestEvent(int value) {
		super(EventType.of(AsyncDispatcherTest.SIMPLETESTEVENT));
		this.value = value;
		// TODO Auto-generated constructor stub
	}

}
