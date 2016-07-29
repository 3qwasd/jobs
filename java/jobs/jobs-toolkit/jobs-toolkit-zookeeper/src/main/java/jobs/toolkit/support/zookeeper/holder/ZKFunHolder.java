package jobs.toolkit.support.zookeeper.holder;

import jobs.toolkit.support.zookeeper.ZK;
import jobs.toolkit.support.zookeeper.fun.CommonFun;

/**
 * 回调方法持有者
 * @author jobs
 *
 * @param <Fun>
 */
public class ZKFunHolder<Fun> {
	
	private volatile Fun authFailedFun = null;
	
	private volatile Fun okFun = null;
	
	private volatile Fun nodeExistFun = null;
	
	private volatile Fun noNodeFun = null;
	
	private volatile Fun sessionExpiredFun = null;
	
	private volatile Fun badVersionFun = null;
	
	private volatile Fun connLossFun = null;
	
	private volatile CommonFun commonFun = ZK.LOG_DEBUG_COMMON_FUN;
	
	public void reset(){
		authFailedFun = null;
		okFun = null;
		nodeExistFun = null;
		noNodeFun = null;
		sessionExpiredFun = null;
		badVersionFun = null;
		connLossFun = null;
		commonFun = ZK.LOG_DEBUG_COMMON_FUN;
	}

	public Fun getAuthFailedFun() {
		return authFailedFun;
	}

	public void setAuthFailedFun(Fun authFailedFun) {
		this.authFailedFun = authFailedFun;
	}

	public Fun getOkFun() {
		return okFun;
	}

	public void setOkFun(Fun okFun) {
		this.okFun = okFun;
	}

	public Fun getNodeExistFun() {
		return nodeExistFun;
	}

	public void setNodeExistFun(Fun nodeExistFun) {
		this.nodeExistFun = nodeExistFun;
	}

	public Fun getNoNodeFun() {
		return noNodeFun;
	}

	public void setNoNodeFun(Fun noNodeFun) {
		this.noNodeFun = noNodeFun;
	}

	public Fun getSessionExpiredFun() {
		return sessionExpiredFun;
	}

	public void setSessionExpiredFun(Fun sessionExpiredFun) {
		this.sessionExpiredFun = sessionExpiredFun;
	}

	public Fun getBadVersionFun() {
		return badVersionFun;
	}

	public void setBadVersionFun(Fun badVersionFun) {
		this.badVersionFun = badVersionFun;
	}

	public Fun getConnLossFun() {
		return connLossFun;
	}

	public void setConnLossFun(Fun connLossFun) {
		this.connLossFun = connLossFun;
	}

	public CommonFun getCommonFun() {
		return commonFun;
	}

	public void setCommonFun(CommonFun commonFun) {
		this.commonFun = commonFun;
	}
}
