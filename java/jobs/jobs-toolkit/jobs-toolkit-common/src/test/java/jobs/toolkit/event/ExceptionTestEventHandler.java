package jobs.toolkit.event;

import jobs.toolkit.event.EventHandler;

public class ExceptionTestEventHandler implements EventHandler<ExceptionTestEvent>{
	
	public int sum;
	public int count = 0;
	@Override
	public void doHandle(ExceptionTestEvent event) {
		// TODO Auto-generated method stub
		sum += event.value;
		//throw new NullPointerException("ExceptionTestEventHandler Throws NullPointerException!");
		count++;
	}
	@Override
	public void doExcepion(ExceptionTestEvent event, Throwable e) {
		// TODO Auto-generated method stub
		
	}

}
