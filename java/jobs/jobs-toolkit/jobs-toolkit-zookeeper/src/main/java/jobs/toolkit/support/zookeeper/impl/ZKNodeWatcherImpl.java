package jobs.toolkit.support.zookeeper.impl;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import jobs.toolkit.support.zookeeper.ZKNodeWatcher;
import jobs.toolkit.support.zookeeper.fun.EventFun;
import jobs.toolkit.support.zookeeper.holder.ZKNodeEventFunHolder;

abstract class ZKNodeWatcherImpl<T, Fun> extends ZKCallbackImpl<T, Fun> implements ZKNodeWatcher<T, Fun>{

	ThreadLocal<ZKNodeEventFunHolder> eventFunHolder = new ThreadLocal<ZKNodeEventFunHolder>();

	@SuppressWarnings("unchecked")
	@Override
	T resetFunHolder() {
		super.resetFunHolder();
		ZKNodeEventFunHolder nefh = this.eventFunHolder.get();
		if(nefh == null){
			nefh = new ZKNodeEventFunHolder();
			this.eventFunHolder.set(nefh);
		}else{
			nefh.reset();
		}
		return (T) this;
	}
	@SuppressWarnings("unchecked")
	@Override
	public T childChangedFun(EventFun fun){
		this.eventFunHolder.get().setChildChangedFun(fun);
		return (T)this;
	}
	@SuppressWarnings("unchecked")
	@Override
	public T nodeCreatedFun(EventFun fun){
		this.eventFunHolder.get().setNodeCreatedFun(fun);
		return (T)this;
	}
	@SuppressWarnings("unchecked")
	@Override
	public T nodeDeletedFun(EventFun fun){
		this.eventFunHolder.get().setNodeDeletedFun(fun);
		return (T)this;
	}
	@SuppressWarnings("unchecked")
	@Override
	public T dataChangedFun(EventFun fun){
		this.eventFunHolder.get().setDataChangedFun(fun);
		return (T)this;
	}
	@SuppressWarnings("unchecked")
	@Override
	public T errorEventFun(EventFun fun){
		this.eventFunHolder.get().setErrorEventFun(fun);
		return (T)this;
	}

	Watcher createWatcher(){

		final ZKNodeEventFunHolder eventFuns = this.eventFunHolder.get();
		final EventFun nodeCreatedFun = eventFuns.getNodeCreatedFun();
		final EventFun nodeDeletedFun = eventFuns.getNodeDeletedFun();
		final EventFun dataChangedFun = eventFuns.getDataChangedFun();
		final EventFun childChangedFun = eventFuns.getChildChangedFun();
		final EventFun errorEventFun = eventFuns.getErrorEventFun();

		return new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				switch (event.getType()) {
				case NodeCreated:
					if(nodeCreatedFun != null) nodeCreatedFun.process(event);
					break;
				case NodeDeleted:
					if(nodeDeletedFun != null) nodeDeletedFun.process(event);
					break;
				case NodeDataChanged:
					if(dataChangedFun != null) dataChangedFun.process(event);
					break;
				case NodeChildrenChanged:
					if(childChangedFun != null) childChangedFun.process(event);
					break;
				default:
					if(errorEventFun != null) errorEventFun.process(event);
					break;
				}
			}
		};
	}

}
