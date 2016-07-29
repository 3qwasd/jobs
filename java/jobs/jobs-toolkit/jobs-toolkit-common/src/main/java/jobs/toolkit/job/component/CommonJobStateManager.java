package jobs.toolkit.job.component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jobs.toolkit.job.Job;
import jobs.toolkit.job.JobStateMachine;
import jobs.toolkit.job.JobStateManager;

/**
 * 通用作业状态管理器
 * @author jobs
 *
 */
public class CommonJobStateManager implements JobStateManager{
	
	private final Map<String, JobStateMachine> stateMap = new ConcurrentHashMap<String, JobStateMachine>();
	
	public void register(Job job){
		stateMap.put(job.getJobId(), job.getJobStateMachine());
	}
	public void deregister(String jobId){
		stateMap.remove(jobId);
	}
}
