package jobs.toolkit.task;

import java.util.concurrent.Callable;

public interface TaskLog {
	
	public static final String SUBMIT = "submit";
	public static final String START = "start";
	public static final String END = "end";
	public static final String EXCEPTION = "exception";
	
	void traceSubmit(Callable<?> task, long submitTime);
	
	void traceSubmit(Runnable task, long submitTime);
	
	void traceReject(Runnable task, String executorName);
	
	void traceReject(Callable<?> task, String executorName);
	
	void traceStart(Runnable task, long startTime);
	
	void traceEnd(Runnable task, long endTime, long spendTime);
	
	void traceException(Runnable task, Throwable th);
	
}
