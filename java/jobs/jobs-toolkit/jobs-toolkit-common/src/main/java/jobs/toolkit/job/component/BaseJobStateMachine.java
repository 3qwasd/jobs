package jobs.toolkit.job.component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import jobs.toolkit.job.JobState;
import jobs.toolkit.job.JobStateMachine;

public abstract class BaseJobStateMachine implements JobStateMachine {
	
	private volatile JobState state;
	
	private final Lock stateLock = new ReentrantLock();
	
	public void nextState(){
		
	}
}
