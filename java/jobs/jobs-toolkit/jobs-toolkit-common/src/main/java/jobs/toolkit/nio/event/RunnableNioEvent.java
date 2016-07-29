package jobs.toolkit.nio.event;

import jobs.toolkit.event.EventType;

public abstract class RunnableNioEvent extends NioEvent implements Runnable {
	
	public static final String TYPE_NAME = "nio.runnable.event";
	
	private final long delay;
	
	public RunnableNioEvent() {
		this(0);
	}
	public RunnableNioEvent(long delay){
		super(EventType.of(TYPE_NAME), null);
		this.delay = delay;
	}
	public long getDelay() {
		return delay;
	}
}
