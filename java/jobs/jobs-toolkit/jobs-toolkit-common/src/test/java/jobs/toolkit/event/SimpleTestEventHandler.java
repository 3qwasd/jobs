package jobs.toolkit.event;

import jobs.toolkit.event.EventHandler;

public class SimpleTestEventHandler implements EventHandler<SimpleTestEvent> {
	public int sum;
	public int count = 0;
	@Override
	public void doHandle(SimpleTestEvent event) {
		// TODO Auto-generated method stub
		sum += event.value;
		count++;
	}
	@Override
	public void doExcepion(SimpleTestEvent event, Throwable e) {
		// TODO Auto-generated method stub
		
	}

}
