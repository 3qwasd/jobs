package jobs.toolkit.support.zookeeper.impl;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import jobs.toolkit.support.zookeeper.ZKSessionWatcher;
import jobs.toolkit.support.zookeeper.fun.EventFun;
import jobs.toolkit.support.zookeeper.holder.ZKSessionEventFunHolder;

abstract class ZKSessionWatcherImpl<T> extends ZKHelperImpl<T> implements ZKSessionWatcher<T> {
	
	ThreadLocal<ZKSessionEventFunHolder> sessionEventFunHolder = new ThreadLocal<ZKSessionEventFunHolder>();
	@SuppressWarnings("unchecked")
	@Override
	public T errorEventFun(EventFun fun) {
		// TODO Auto-generated method stub
		this.sessionEventFunHolder.get().setErrorEventFun(fun);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T authFailedFun(EventFun fun) {
		// TODO Auto-generated method stub
		this.sessionEventFunHolder.get().setAuthFailedFun(fun);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T disconnedFun(EventFun fun) {
		// TODO Auto-generated method stub
		this.sessionEventFunHolder.get().setDisconnedFun(fun);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T expiredFun(EventFun fun) {
		// TODO Auto-generated method stub
		this.sessionEventFunHolder.get().setExpiredFun(fun);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T saslAuthedFun(EventFun fun) {
		// TODO Auto-generated method stub
		this.sessionEventFunHolder.get().setSaslAuthedFun(fun);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T syncConnedFun(EventFun fun) {
		// TODO Auto-generated method stub
		this.sessionEventFunHolder.get().setSyncConnedFun(fun);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	T resetFunHolder() {
		ZKSessionEventFunHolder efh = this.sessionEventFunHolder.get();
		if(efh == null){
			efh = new ZKSessionEventFunHolder();
			this.sessionEventFunHolder.set(efh);
		}else{
			efh.reset();
		}
		return (T) this;
	}
	
	protected Watcher createWatcher(){
		final EventFun errorEventFun = this.sessionEventFunHolder.get().getErrorEventFun();
		final EventFun authFailedFun = this.sessionEventFunHolder.get().getAuthFailedFun();
		final EventFun disconnedFun = this.sessionEventFunHolder.get().getDisconnedFun();
		final EventFun expiredFun = this.sessionEventFunHolder.get().getExpiredFun();
		final EventFun saslAuthedFun = this.sessionEventFunHolder.get().getSaslAuthedFun();
		final EventFun syncConnedFun = this.sessionEventFunHolder.get().getSyncConnedFun();
		Watcher watcher = new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				switch (event.getState()) {
				case SyncConnected:
					if(syncConnedFun != null) syncConnedFun.process(event);
					break;
				case AuthFailed:
					if(authFailedFun != null) authFailedFun.process(event);
					break;
				case Disconnected:
					if(disconnedFun != null) disconnedFun.process(event);
					break;
				case Expired:
					if(expiredFun != null) expiredFun.process(event);
					break;
				case SaslAuthenticated:
					if(saslAuthedFun != null) saslAuthedFun.process(event);
					break;
				default:
					if(errorEventFun != null) errorEventFun.process(event);
					break;
				}
			}
		};
		return watcher;
	}
}
