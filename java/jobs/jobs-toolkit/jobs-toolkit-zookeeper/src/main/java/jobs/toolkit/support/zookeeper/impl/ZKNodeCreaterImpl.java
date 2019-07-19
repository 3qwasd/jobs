package jobs.toolkit.support.zookeeper.impl;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import jobs.toolkit.support.zookeeper.ZKNodeCreater;
import jobs.toolkit.support.zookeeper.fun.RetryStringCallback;
import jobs.toolkit.support.zookeeper.fun.StringFun;

final class ZKNodeCreaterImpl extends ZKCallbackImpl<ZKNodeCreater, StringFun> implements ZKNodeCreater {
		

	protected ZKNodeCreaterImpl() {}

	@Override
	public String createSync(String path) throws  KeeperException, Exception {
		validZK();
		final byte[] data = this.paramHolder.get().getData();
		final List<ACL> acl = this.paramHolder.get().getAcl();
		final CreateMode mode = this.paramHolder.get().getMode();
		final Stat stat = this.paramHolder.get().getStat();
		final boolean isRetry = this.paramHolder.get().isRetry();
		final int retryLimit = this.paramHolder.get().getRetryLimit();
		final int retryInterval = this.paramHolder.get().getRetryInterval();
		final StringFun nodeExistsFun = this.funHolder.get().getNodeExistFun();
		int retryCount = 0;
		do{
			try{
				if(stat != null)
					return this.support.getZk().create(path, data, acl, mode, stat);
				else
					return this.support.getZk().create(path, data, acl, mode);
			} catch (KeeperException exp){
				if(exp.code() == Code.NODEEXISTS) {
					if(nodeExistsFun != null)
						nodeExistsFun.process(path);
					return path;
				}
				if(!isRetry || exp.code() != Code.CONNECTIONLOSS) throw exp;
				if(retryLimit > retryCount){
					LOG.info("ZooKeeper disconnected, the create operation will be retry after several seconds.", exp);
					Thread.sleep(retryInterval);
				}else{
					throw new IllegalStateException(String.format("Create node[path=%1$s]  failed. Retry count reach the limit[%2$s]", path, retryLimit) ,exp);
				}
			}
		}while(isRetry && retryLimit > retryCount++);
		
		throw new IllegalStateException(String.format("Create node[path=%1$s]  failed.", path));
	}

	@Override
	public void createAsync(String path) {
		validZK();
		final byte[] data = this.paramHolder.get().getData();
		final List<ACL> acl = this.paramHolder.get().getAcl();
		final CreateMode mode = this.paramHolder.get().getMode();
		final Object ctx = this.paramHolder.get().getCtx();
		final boolean isRetry = this.paramHolder.get().isRetry();
		final RetryStringCallback callback = this.createStringCallback();;
		final StringFun createFun = (String name) -> this.support.getZk().create(path, data, acl, mode, callback, ctx);
		if(isRetry)
			callback.setRetryFun((String name) -> {
				LOG.info("Connection loss when create node[path=" + path + "]. Will be retry");
				createFun.process(path);
			});
		createFun.process(path);
	
	}

	@Override
	public ZKNodeCreater acl(List<ACL> acl) {
		this.paramHolder.get().setAcl(acl);
		return this;
	}
	
	@Override
	public ZKNodeCreater createMode(CreateMode mode){
		this.paramHolder.get().setMode(mode);
		return this;
	}
	
	@Override
	public ZKNodeCreater ctx(Object ctx) {
		this.paramHolder.get().setCtx(ctx);
		return this;
	}

	@Override
	public ZKNodeCreater ephemeral() {
		this.paramHolder.get().setMode(CreateMode.EPHEMERAL);
		return this;
	}

	@Override
	public ZKNodeCreater ephemeralSequential() {
		this.paramHolder.get().setMode(CreateMode.EPHEMERAL_SEQUENTIAL);
		return this;
	}

	@Override
	public ZKNodeCreater persistent() {
		this.paramHolder.get().setMode(CreateMode.PERSISTENT);
		return this;
	}

	@Override
	public ZKNodeCreater persistentSequential() {
		this.paramHolder.get().setMode(CreateMode.PERSISTENT_SEQUENTIAL);
		return this;
	}

	@Override
	public ZKNodeCreater stat(Stat stat) {
		this.paramHolder.get().setStat(stat);
		return this;
	}

	
}
