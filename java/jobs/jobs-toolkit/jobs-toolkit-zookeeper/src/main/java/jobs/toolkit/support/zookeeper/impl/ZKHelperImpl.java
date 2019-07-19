package jobs.toolkit.support.zookeeper.impl;

import org.apache.zookeeper.data.Stat;

import jobs.toolkit.logging.Log;
import jobs.toolkit.logging.LogFactory;
import jobs.toolkit.support.zookeeper.ZKHelper;
import jobs.toolkit.support.zookeeper.holder.ZKParamHolder;

abstract class ZKHelperImpl<T> implements ZKHelper<T>{
	
	protected final Log LOG = LogFactory.getLog(this.getClass());
	
	ThreadLocal<ZKParamHolder>  paramHolder = new ThreadLocal<ZKParamHolder>();
	
	ZKSupportImpl support;
	
	@SuppressWarnings("unchecked")
	protected T setSupport(ZKSupportImpl support){
		this.support = support;
		return (T) this;
	}
	
	@Override
	public final T reset(){
		resetParamHolder();
		return this.resetFunHolder();
	}
	final void resetParamHolder(){
		ZKParamHolder ph = this.paramHolder.get();
		if(ph == null){
			ph = new ZKParamHolder();
			this.paramHolder.set(ph);
		}else{
			ph.reset();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T retry(boolean isRetry) {
		this.paramHolder.get().setRetry(isRetry);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T retryLimit(int limit) {
		this.paramHolder.get().setRetryLimit(limit);
		return (T) this;
	}
	
	@SuppressWarnings("unchecked")
	public T retryInterval(int interval){
		this.paramHolder.get().setRetryInterval(interval);
		return (T) this;
	}
	
	@SuppressWarnings("unchecked")
	public T data(byte[] data){
		this.paramHolder.get().setData(data);
		return (T) this;
	}
	
	abstract T resetFunHolder();
	
	final void validZK(){
		if(this.support.getZk() == null)
			throw new IllegalStateException("Not yet connected to the server.");
		if(!this.support.getZk().getState().isAlive())
			throw new IllegalStateException("Zookeeper session invalid.");
	}

	@SuppressWarnings("unchecked")
	@Override
	public T ctx(Object ctx) {
		this.paramHolder.get().setCtx(ctx);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T stat(Stat stat) {
		this.paramHolder.get().setStat(stat);
		return (T) this;
	}
}
