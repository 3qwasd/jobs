package jobs.toolkit.support.zookeeper.impl;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.data.Stat;

import jobs.toolkit.support.zookeeper.ZKNodeChecker;
import jobs.toolkit.support.zookeeper.fun.RetryStatCallback;
import jobs.toolkit.support.zookeeper.fun.StatFun;

public class ZKNodeCheckerImpl extends ZKNodeWatcherImpl<ZKNodeChecker, StatFun> implements ZKNodeChecker {
		
	protected ZKNodeCheckerImpl() {}

	@Override
	public Stat checkSync(String path) throws KeeperException, Exception{
		validZK();
		final boolean isWatch = this.paramHolder.get().isWatch();
		final boolean isRetry = this.paramHolder.get().isRetry();
		final int retryLimit = this.paramHolder.get().getRetryLimit();
		final int retryInterval = this.paramHolder.get().getRetryInterval();
		int retryCount = 0;
		do{
			try{
				if(isWatch)
					return this.support.getZk().exists(path, this.createWatcher());
				else
					return this.support.getZk().exists(path, false);
			}catch(KeeperException exp){
				if(!isRetry || exp.code() != Code.CONNECTIONLOSS) throw exp;
				if(retryLimit > retryCount){
					LOG.info("ZooKeeper disconnected, the check operation will be retry after several seconds.", exp);
					Thread.sleep(retryInterval);
				}else{
					throw new IllegalStateException(String.format("Check node[path=%1$s]  failed. Retry count reach the limit[%2$s]", path, retryLimit) ,exp);
				}
			}
			
		}while(isRetry && retryLimit > retryCount++);
		
		throw new IllegalStateException(String.format("Check node[path=%1$s]  failed.", path));
	}
	
	@Override
	public void checkAsync(String path) {
		validZK();
		final boolean isWatch = this.paramHolder.get().isWatch();
		final Object ctx = this.paramHolder.get().getCtx();
		final boolean isRetry = this.paramHolder.get().isRetry();
		final RetryStatCallback callback = this.createStatCallback();;
		final StatFun checkFun = (Stat stat) -> {
			if(isWatch)
				this.support.getZk().exists(path, this.createWatcher(), this.createStatCallback(), ctx);
			else
				this.support.getZk().exists(path, false, this.createStatCallback(), ctx);
		};
		if(isRetry) callback.setRetryFun((Stat stat) -> {
			LOG.info("Connection loss when exists node[path=" + path + "]. Will be retry");
			checkFun.process(stat);}
		);
		checkFun.process(null);
	}

	@Override
	public ZKNodeChecker ctx(Object ctx) {
		this.paramHolder.get().setCtx(ctx);
		return this;
	}

	@Override
	public ZKNodeChecker isWatch(boolean isWatch) {
		this.paramHolder.get().setWatch(isWatch);
		return this;
	}
}
