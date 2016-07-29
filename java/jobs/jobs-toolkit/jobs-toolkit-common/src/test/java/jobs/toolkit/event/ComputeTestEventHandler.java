package jobs.toolkit.event;

import jobs.toolkit.event.EventHandler;

public class ComputeTestEventHandler implements EventHandler<ComputeTestEvent> {
	
	int sum = 0;
	public int count = 0;
	@Override
	public void doHandle(ComputeTestEvent event) {
		// TODO Auto-generated method stub
		sum += event.value;
		count++;
	}
	@Override
	public void doExcepion(ComputeTestEvent event, Throwable e) {
		// TODO Auto-generated method stub
		
	}

}
