package jobs.toolkit.support.zookeeper.impl;

import java.net.ConnectException;
import org.apache.zookeeper.ZooKeeper;
import jobs.toolkit.support.zookeeper.ZKConnector;

/**
 * 
 * @author jobs
 *
 */
class ZKConnectorImpl extends ZKSessionWatcherImpl<ZKConnector> implements ZKConnector{
	
	protected ZKConnectorImpl() {}
	
	@Override
	public void connectSync(String hostRi) throws Exception {
		final boolean isRetry = this.paramHolder.get().isRetry();
		final int retryLimit = this.paramHolder.get().getRetryLimit();
		final int retryInterval = this.paramHolder.get().getRetryInterval();
		final int sessionTimeout = this.paramHolder.get().getSessionTimeout();
		ZooKeeper newZk = new ZooKeeper(hostRi, sessionTimeout, this.createWatcher());
		//此处等待连接成功
		int retryCount = 0;
		while(!newZk.getState().isConnected() && isRetry && retryLimit > retryCount++){
			LOG.info("Not yet connected to zookeeper server %1$s, will be retry after seconds.", hostRi);
			Thread.sleep(retryInterval);
		}
		if(!newZk.getState().isConnected())	throw new ConnectException("Can't connect to ZooKeeper server." + hostRi);
		
		this.support.setZk(newZk);
	}
	
	
	
	@Override
	public ZKConnector sessionTimeout(int sessionTimeout) {
		// TODO Auto-generated method stub
		this.paramHolder.get().setSessionTimeout(sessionTimeout);
		return this;
	}

}
