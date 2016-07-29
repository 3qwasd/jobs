package jobs.toolkit.support.zookeeper.holder;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import jobs.toolkit.support.zookeeper.ZK;

public class ZKParamHolder {
	
	private volatile byte[] data = new byte[0];

	private volatile List<ACL> acl = ZooDefs.Ids.READ_ACL_UNSAFE;

	private volatile CreateMode mode = CreateMode.EPHEMERAL;

	private volatile Object ctx = null;

	private volatile Stat stat = null;

	private volatile boolean isRetry = ZK.RETRY;
	
	private volatile int retryLimit = ZK.RETRY_LIMIT;
	
	private volatile boolean isWatch = false;

	private volatile int sessionTimeout = ZK.SESSION_TIME_OUT;
	
	private volatile int retryInterval = ZK.RETRY_INTERVAL;
	
	private volatile int version = -1;
	
	public void reset(){
		data = new byte[0];
		acl = ZooDefs.Ids.READ_ACL_UNSAFE;
		mode = CreateMode.EPHEMERAL;
		ctx = null;
		stat = null;
		isRetry = ZK.RETRY;
		isWatch = false;
		sessionTimeout = ZK.SESSION_TIME_OUT;
		retryLimit = ZK.RETRY_LIMIT;
		retryInterval = ZK.RETRY_INTERVAL;
		version = -1;
	}
	
	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public List<ACL> getAcl() {
		return acl;
	}

	public void setAcl(List<ACL> acl) {
		this.acl = acl;
	}
	
	public int getRetryInterval() {
		return retryInterval;
	}

	public void setRetryInterval(int retryInterval) {
		this.retryInterval = retryInterval;
	}

	public CreateMode getMode() {
		return mode;
	}

	public void setMode(CreateMode mode) {
		this.mode = mode;
	}

	public Object getCtx() {
		return ctx;
	}

	public void setCtx(Object ctx) {
		this.ctx = ctx;
	}

	public Stat getStat() {
		return stat;
	}

	public void setStat(Stat stat) {
		this.stat = stat;
	}

	public boolean isRetry() {
		return isRetry;
	}

	public void setRetry(boolean isRetry) {
		this.isRetry = isRetry;
	}

	public boolean isWatch() {
		return isWatch;
	}

	public void setWatch(boolean isWatch) {
		this.isWatch = isWatch;
	}

	public int getSessionTimeout() {
		return sessionTimeout;
	}

	public void setSessionTimeout(int sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}

	public int getRetryLimit() {
		return retryLimit;
	}

	public void setRetryLimit(int retryLimit) {
		this.retryLimit = retryLimit;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}
