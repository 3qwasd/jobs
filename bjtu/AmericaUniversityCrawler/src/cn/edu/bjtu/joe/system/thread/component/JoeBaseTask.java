/**
 * @QiaoJian
 */
package cn.edu.bjtu.joe.system.thread.component;

import java.util.Date;

/**
 * @author QiaoJian
 *	任务抽象基类
 */
public abstract class JoeBaseTask implements Runnable{
	
	/*任务名称*/
	private String taskName;
	/*任务开始时输出的信息*/
	private String taskStartMessage;
	/*任务结束时输出的信息*/
	private String taskCompleteMessage;
	/*任务创建的时间*/
	private Date taskCreateTime;
	/*任务提交的时间*/
	private Date taskSubmitTime;
	/*任务运行的时间*/
	private Date taskStartTime;
	/*任务结束的时间*/
	private Date taskCompleteTime;
	/*任务执行的时间*/
	private Long runTime;
	
	public JoeBaseTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 构造函数
	 * @param taskName
	 */
	public JoeBaseTask(String taskName) {
		super();
		this.taskName = taskName;
		this.taskCreateTime = new Date();
	}
	
	public void setTaskSubmitTime() {
		this.taskSubmitTime = new Date();
	}

	public void setTaskStartTime() {
		this.taskStartTime = new Date();
	}
	public void setTaskCompleteTime() {
		this.taskCompleteTime = new Date();
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskStartMessage() {
		return taskStartMessage;
	}
	public void setTaskStartMessage(String taskStartMessage) {
		this.taskStartMessage = taskStartMessage;
	}
	
	public String getTaskCompleteMessage() {
		return taskCompleteMessage;
	}

	public void setTaskCompleteMessage(String taskCompleteMessage) {
		this.taskCompleteMessage = taskCompleteMessage;
	}

	public Date getTaskCompleteTime() {
		return taskCompleteTime;
	}

	

	public Date getTaskCreateTime() {
		return taskCreateTime;
	}
	public void setTaskCreateTime(Date taskCreateTime) {
		this.taskCreateTime = taskCreateTime;
	}
	public Date getTaskSubmitTime() {
		return taskSubmitTime;
	}
	
	public Date getTaskStartTime() {
		return taskStartTime;
	}

	public Long getRunTime() {
		return runTime;
	}
	/*计算任务执行的时间*/
	public void setRunTime(Long startTime,Long endTime) {
		this.runTime = endTime-startTime;
	}
	
	
	
}
