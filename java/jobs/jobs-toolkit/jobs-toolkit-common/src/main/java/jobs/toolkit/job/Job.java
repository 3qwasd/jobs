package jobs.toolkit.job;

/**
 * 作业接口
 * @author jobs
 *
 */
public interface Job {
	
	/**
	 * 获取作业的标识, 必须是唯一的字符串
	 * @return
	 */
	public String getJobId();
	
	/**
	 * 获取作业计划表
	 * @return
	 */
	public JobPlan getJobPlan();
	
	/**
	 * 获取作业相关信息
	 * @return
	 */
	public JobRI getJobRI();
	
	/**
	 * 获取作业的可执行体
	 * @return
	 */
	public Runnable getJobExecutiveBody();
	
	public JobStateMachine getJobStateMachine();
}
