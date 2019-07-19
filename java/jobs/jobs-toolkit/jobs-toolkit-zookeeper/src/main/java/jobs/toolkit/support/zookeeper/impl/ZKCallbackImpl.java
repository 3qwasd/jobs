package jobs.toolkit.support.zookeeper.impl;

import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.data.Stat;

import jobs.toolkit.support.zookeeper.ZK;
import jobs.toolkit.support.zookeeper.ZKCallback;
import jobs.toolkit.support.zookeeper.fun.CommonFun;
import jobs.toolkit.support.zookeeper.fun.RetryStatCallback;
import jobs.toolkit.support.zookeeper.fun.RetryStringCallback;
import jobs.toolkit.support.zookeeper.fun.StatFun;
import jobs.toolkit.support.zookeeper.fun.StringFun;
import jobs.toolkit.support.zookeeper.holder.ZKFunHolder;

 class ZKCallbackImpl<T, Fun> extends ZKHelperImpl<T> implements ZKCallback<T, Fun>{
	
	ThreadLocal<ZKFunHolder<Fun>> funHolder = new ThreadLocal<ZKFunHolder<Fun>>();
	
	@SuppressWarnings("unchecked")
	@Override
	public T authFailedFun(Fun fun){
		this.funHolder.get().setAuthFailedFun(fun);
		return (T)this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T okFun(Fun fun){
		this.funHolder.get().setOkFun(fun);
		return (T)this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T nodeExistFun(Fun fun){
		this.funHolder.get().setNodeExistFun(fun);
		return (T)this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T noNodeFun(Fun fun){
		this.funHolder.get().setNoNodeFun(fun);
		return (T)this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T sessionExpiredFun(Fun fun){
		this.funHolder.get().setSessionExpiredFun(fun);
		return (T)this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T badVersionFun(Fun fun){
		this.funHolder.get().setBadVersionFun(fun);
		return (T)this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T connLossFun(Fun fun){
		this.funHolder.get().setConnLossFun(fun);
		return (T)this;
	}
	@SuppressWarnings("unchecked")
	@Override
	public T commonFun(CommonFun fun){
		this.funHolder.get().setCommonFun(fun);
		return (T)this;
	}

	@SuppressWarnings("unchecked")
	@Override
	T resetFunHolder() {
		ZKFunHolder<Fun> fh = this.funHolder.get();
		if(fh == null){
			fh = new ZKFunHolder<Fun>();
			this.funHolder.set(fh);
		}else{
			fh.reset();
		}
		return (T) this;
	}

	RetryStringCallback createStringCallback(){
		final ZKFunHolder<Fun> statFuns = this.funHolder.get();
		final CommonFun commonFun = statFuns.getCommonFun();
		final StringFun connLossFun = (StringFun) statFuns.getConnLossFun();
		final StringFun okFun = (StringFun) statFuns.getOkFun();
		final StringFun nodeExistFun = (StringFun) statFuns.getNodeExistFun();
		final StringFun noNodeFun = (StringFun) statFuns.getNoNodeFun();
		return new RetryStringCallback() {
			@Override
			public void processResult(int rc, String path, Object ctx, String name) {
				switch (Code.get(rc)) {
				case CONNECTIONLOSS:
					if(connLossFun != null) connLossFun.process(name);
					if(this.retryFun != null) this.retryFun.process(name);
					return;
				case OK:
					if(okFun != null) okFun.process(name);
					return;
				case NODEEXISTS:
					if(nodeExistFun != null) nodeExistFun.process(name);
					else ZK.LOG_INFO_NODE_EXIST_FUN.process(path);
					return;
				case NONODE:
					if(noNodeFun != null) noNodeFun.process(name);
					else ZK.LOG_INFO_NO_NODE_FUN.process(path);
					return;
				default:
					commonFun.process(path, rc, ctx);
					return;
				}
			}
		};
	}
	RetryStatCallback createStatCallback(){
		final ZKFunHolder<Fun> statFuns = this.funHolder.get();
		final CommonFun commonFun = statFuns.getCommonFun();
		final StatFun connLossFun = (StatFun) statFuns.getConnLossFun();
		final StatFun okFun = (StatFun) statFuns.getOkFun();
		final StatFun nodeExistFun = (StatFun) statFuns.getNodeExistFun();
		final StatFun noNodeFun = (StatFun) statFuns.getNoNodeFun();
		return new RetryStatCallback() {
			@Override
			public void processResult(int rc, String path, Object ctx, Stat stat) {
				switch (Code.get(rc)) {
				case CONNECTIONLOSS:
					if(connLossFun != null) connLossFun.process(stat);
					if(this.retryFun != null) this.retryFun.process(stat);
					return;
				case OK:
					if(okFun != null) okFun.process(stat);
					return;
				case NODEEXISTS:
					if(nodeExistFun != null) nodeExistFun.process(stat);
					else ZK.LOG_INFO_NODE_EXIST_FUN.process(path);
					return;
				case NONODE:
					if(noNodeFun != null) noNodeFun.process(stat);
					else ZK.LOG_INFO_NO_NODE_FUN.process(path);
					return;
				default:
					commonFun.process(path, rc, ctx);
					return;
				}
			}
		};
	}
}
