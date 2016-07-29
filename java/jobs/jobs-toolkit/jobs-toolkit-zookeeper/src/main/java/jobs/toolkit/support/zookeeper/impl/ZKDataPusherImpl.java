package jobs.toolkit.support.zookeeper.impl;


import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;

import jobs.toolkit.support.zookeeper.ZKDataPusher;
import jobs.toolkit.support.zookeeper.fun.RetryStatCallback;
import jobs.toolkit.support.zookeeper.fun.StatFun;

final class ZKDataPusherImpl extends ZKCallbackImpl<ZKDataPusher, StatFun> implements ZKDataPusher{
		
	protected ZKDataPusherImpl() {}

	@Override
	public Stat pushSync(String path) throws KeeperException, Exception {
		validZK();
		return null;
	}

	@Override
	public void pushAsync(String path) {
		validZK();
		final byte[] data = this.paramHolder.get().getData();
		final int version = this.paramHolder.get().getVersion();
		final Object ctx = this.paramHolder.get().getCtx();
		final boolean isRetry = this.paramHolder.get().isRetry();
		final RetryStatCallback cb = this.createStatCallback();
		final StatFun pushFun = (stat) -> this.support.getZk().setData(path, data, version, cb, ctx);
		if(isRetry)
			cb.setRetryFun(stat -> {
				LOG.info("Connection loss when create node[path=" + path + "]. Will be retry");
				pushFun.process(stat);
			});
		pushFun.process(null);
	}

	@Override
	public ZKDataPusher ctx(Object ctx) {
		this.paramHolder.get().setCtx(ctx);
		return this;
	}

	@Override
	public ZKDataPusher version(int version) {
		this.paramHolder.get().setVersion(version);
		return this;
	}
	
	
}
