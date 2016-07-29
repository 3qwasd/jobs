package jobs.toolkit.task;

import java.util.concurrent.Callable;

import jobs.toolkit.logging.Log;
import jobs.toolkit.logging.LogFactory;

public class DefaultTaskLog implements TaskLog{
	
	private static final Log LOG = LogFactory.getLog(DefaultTaskLog.class);

	@Override
	public void traceSubmit(Callable<?> task, long submitTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void traceSubmit(Runnable task, long submitTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void traceStart(Runnable task, long startTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void traceEnd(Runnable task, long endTime, long spendTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void traceException(Runnable task, Throwable th) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void traceReject(Runnable task, String executorName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void traceReject(Callable<?> task, String executorName) {
		// TODO Auto-generated method stub
		
	}
	
	
}
