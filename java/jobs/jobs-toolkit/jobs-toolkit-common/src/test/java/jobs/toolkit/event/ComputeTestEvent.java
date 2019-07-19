package jobs.toolkit.event;

import java.util.UUID;

import jobs.toolkit.event.BaseEvent;
import jobs.toolkit.event.EventType;

public class ComputeTestEvent extends BaseEvent{
	
	public int value;
	
	public ComputeTestEvent(int value) {
		super(EventType.of(AsyncDispatcherTest.COMPUTETESTEVENT));
		this.value = value;
	}
	
}
