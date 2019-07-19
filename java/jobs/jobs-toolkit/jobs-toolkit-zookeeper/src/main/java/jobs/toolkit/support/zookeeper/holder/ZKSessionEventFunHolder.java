package jobs.toolkit.support.zookeeper.holder;

import jobs.toolkit.support.zookeeper.ZK;
import jobs.toolkit.support.zookeeper.fun.EventFun;

public class ZKSessionEventFunHolder {

	private volatile EventFun errorEventFun = ZK.LOG_DEBUG_EVENT_FUN;

	private volatile EventFun authFailedFun = ZK.LOG_DEBUG_EVENT_FUN;

	private volatile EventFun disconnedFun = ZK.LOG_DEBUG_EVENT_FUN;

	private volatile EventFun expiredFun = ZK.LOG_DEBUG_EVENT_FUN;

	private volatile EventFun saslAuthedFun = ZK.LOG_DEBUG_EVENT_FUN;

	private volatile EventFun syncConnedFun = ZK.LOG_DEBUG_EVENT_FUN;

	public void reset(){
		errorEventFun = ZK.LOG_DEBUG_EVENT_FUN;
		authFailedFun = ZK.LOG_DEBUG_EVENT_FUN;
		disconnedFun = ZK.LOG_DEBUG_EVENT_FUN;
		expiredFun = ZK.LOG_DEBUG_EVENT_FUN;
		saslAuthedFun = ZK.LOG_DEBUG_EVENT_FUN;
		syncConnedFun = ZK.LOG_DEBUG_EVENT_FUN;
	}

	public EventFun getErrorEventFun() {
		return errorEventFun;
	}

	public void setErrorEventFun(EventFun errorEventFun) {
		this.errorEventFun = errorEventFun;
	}

	public EventFun getAuthFailedFun() {
		return authFailedFun;
	}

	public void setAuthFailedFun(EventFun authFailedFun) {
		this.authFailedFun = authFailedFun;
	}

	public EventFun getDisconnedFun() {
		return disconnedFun;
	}

	public void setDisconnedFun(EventFun disconnedFun) {
		this.disconnedFun = disconnedFun;
	}

	public EventFun getExpiredFun() {
		return expiredFun;
	}

	public void setExpiredFun(EventFun expiredFun) {
		this.expiredFun = expiredFun;
	}

	public EventFun getSaslAuthedFun() {
		return saslAuthedFun;
	}

	public void setSaslAuthedFun(EventFun saslAuthedFun) {
		this.saslAuthedFun = saslAuthedFun;
	}

	public EventFun getSyncConnedFun() {
		return syncConnedFun;
	}

	public void setSyncConnedFun(EventFun syncConnedFun) {
		this.syncConnedFun = syncConnedFun;
	}
}
