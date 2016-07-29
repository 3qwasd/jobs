package jobs.toolkit.lifecycle;

import jobs.toolkit.lifecycle.LifeCycleStateModel.STATE;

/**
 * 生命周期组件状态转移事件
 * @author jobs
 *
 */
public class LifeCycleStateTransferEvent {
	
	private long time;
	
	private STATE from;
	
	private STATE to;
	
	public LifeCycleStateTransferEvent(){}
	
	public LifeCycleStateTransferEvent(long time, STATE from, STATE to) {
		super();
		this.time = time;
		this.from = from;
		this.to = to;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public STATE getFrom() {
		return from;
	}

	public void setFrom(STATE from) {
		this.from = from;
	}

	public STATE getTo() {
		return to;
	}

	public void setTo(STATE to) {
		this.to = to;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("Time: %1$s; from: %2$s; to: %3$s", this.time, this.from, this.to);
	}
	
}
