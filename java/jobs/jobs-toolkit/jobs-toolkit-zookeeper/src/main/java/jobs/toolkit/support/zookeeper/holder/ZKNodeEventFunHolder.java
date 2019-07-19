package jobs.toolkit.support.zookeeper.holder;

import jobs.toolkit.support.zookeeper.ZK;
import jobs.toolkit.support.zookeeper.fun.EventFun;

public class ZKNodeEventFunHolder {

	private volatile EventFun childChangedFun =  ZK.LOG_DEBUG_EVENT_FUN;

	private volatile EventFun nodeCreatedFun = ZK.LOG_DEBUG_EVENT_FUN;

	private volatile EventFun nodeDeletedFun = ZK.LOG_DEBUG_EVENT_FUN;

	private volatile EventFun dataChangedFun = ZK.LOG_DEBUG_EVENT_FUN;

	private volatile EventFun errorEventFun = ZK.LOG_DEBUG_EVENT_FUN;

	public void reset(){
		childChangedFun =  ZK.LOG_DEBUG_EVENT_FUN;
		nodeCreatedFun = ZK.LOG_DEBUG_EVENT_FUN;
		nodeDeletedFun = ZK.LOG_DEBUG_EVENT_FUN;
		dataChangedFun = ZK.LOG_DEBUG_EVENT_FUN;
		errorEventFun = ZK.LOG_DEBUG_EVENT_FUN;
	}

	public EventFun getChildChangedFun() {
		return childChangedFun;
	}

	public void setChildChangedFun(EventFun childChangedFun) {
		this.childChangedFun = childChangedFun;
	}

	public EventFun getNodeCreatedFun() {
		return nodeCreatedFun;
	}

	public void setNodeCreatedFun(EventFun nodeCreatedFun) {
		this.nodeCreatedFun = nodeCreatedFun;
	}

	public EventFun getNodeDeletedFun() {
		return nodeDeletedFun;
	}

	public void setNodeDeletedFun(EventFun nodeDeletedFun) {
		this.nodeDeletedFun = nodeDeletedFun;
	}

	public EventFun getDataChangedFun() {
		return dataChangedFun;
	}

	public void setDataChangedFun(EventFun dataChangedFun) {
		this.dataChangedFun = dataChangedFun;
	}

	public EventFun getErrorEventFun() {
		return errorEventFun;
	}

	public void setErrorEventFun(EventFun errorEventFun) {
		this.errorEventFun = errorEventFun;
	}
}
