package jobs.toolkit.support.zookeeper.impl;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.zookeeper.ZooKeeper;

import jobs.toolkit.logging.Log;
import jobs.toolkit.logging.LogFactory;
import jobs.toolkit.support.zookeeper.ZKConnector;
import jobs.toolkit.support.zookeeper.ZKDataPuller;
import jobs.toolkit.support.zookeeper.ZKDataPusher;
import jobs.toolkit.support.zookeeper.ZKNodeChecker;
import jobs.toolkit.support.zookeeper.ZKNodeCreater;
import jobs.toolkit.support.zookeeper.ZKSupporter;

public class ZKSupportImpl implements ZKSupporter{
	
	protected final Log LOG = LogFactory.getLog(this.getClass());
	
	private volatile ZooKeeper zk;
	
	private final Map<String, AtomicInteger> versionCache = new ConcurrentHashMap<String, AtomicInteger>();
	
	private final Lock versionCacheLock = new ReentrantLock();
	
	ZKConnector connector;
	
	ZKNodeCreater creater;
	
	ZKNodeChecker checker;
	
	ZKDataPusher pusher;
	
	public int getVersion(String path){
		AtomicInteger versionValue = this.versionCache.get(path);
		if(versionValue != null) return versionValue.get();
		
		try{
			this.versionCacheLock.lock();
			if(!versionCache.containsKey(path)){
				return 0;
			}else{
				return this.versionCache.get(path).get();
			}
		}finally{
			this.versionCacheLock.unlock();
		}
	}
	
	protected void setZk(ZooKeeper newZk) {
		ZooKeeper old = this.zk;
		this.zk = newZk;
		new Thread(() -> {
			try{ old.close(); } catch (Exception e){}
		}).start();
	}
	protected ZooKeeper getZk(){
		return this.zk;
	}
	@Override
	public ZKConnector prepareConnect() {
		if(this.connector == null) this.connector = new ZKConnectorImpl().setSupport(this);
		return this.connector.reset();
	}

	@Override
	public ZKNodeCreater prepareCreate() {
		if(this.creater == null) this.creater = new ZKNodeCreaterImpl().setSupport(this);
		return this.creater.reset().okFun(s -> LOG.info("Node %1$s created.", s)).nodeExistFun(s -> LOG.info("Node %1$s already exists.", s));
	}

	@Override
	public ZKNodeChecker prepareCheck() {
		if(this.checker == null) this.checker = new ZKNodeCheckerImpl().setSupport(this);
		return this.checker.reset();
	}

	@Override
	public ZKDataPuller preparePull() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ZKDataPusher preparePush() {
		if(this.pusher == null) this.pusher = new ZKDataPusherImpl().setSupport(this);
		return this.pusher.reset().okFun(s -> LOG.info("Set data ok, now version=%1$s", s.getVersion()));
	}
	@Override
	public void close() throws IOException {
		try{ 
			this.zk.close(); 
		} catch (Exception e){
			LOG.error("Close ZooKeeper error.", e);
		}
	}
	@Override
	public boolean isValid() {
		return this.zk != null && this.zk.getState().isAlive();
	}

}
