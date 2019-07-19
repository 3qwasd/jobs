package jobs.toolkit.support.zookeeper.impl;


import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import jobs.toolkit.support.zookeeper.ZK;
import jobs.toolkit.support.zookeeper.ZKSupporter;
import jobs.toolkit.support.zookeeper.holder.ZKParamHolder;
import jobs.toolkit.support.zookeeper.holder.ZKSessionEventFunHolder;

public class ZKConnectorImplTest {

	ZKSupporter supporter;

	@Before
	public void setUp() throws Exception {
		supporter = new ZKSupportImpl();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws Exception {
		ZKConnectorImpl connector = new ZKConnectorImpl();
		connector.reset();
		Assert.assertTrue(connector.sessionEventFunHolder.get() != null);
		Assert.assertTrue(connector.paramHolder.get() != null);
		final ZKSessionEventFunHolder[] eventFunHolders = new ZKSessionEventFunHolder[10];
		final ZKParamHolder[] paramHolders = new ZKParamHolder[10];
		final CyclicBarrier barrier = new CyclicBarrier(11);
		final AtomicInteger checkValue = new AtomicInteger(0);
		for(int i=0;i<10;i++){
			final int index = i + 1;
			new Thread(()->{
				try {
					barrier.await();
					connector.reset().
					authFailedFun(e->checkValue.set(index*11)).
					disconnedFun(e->checkValue.set(index*12)).
					errorEventFun(e->checkValue.set(index*13)).
					expiredFun(e->checkValue.set(index*14)).
					saslAuthedFun(e->checkValue.set(index*15)).
					syncConnedFun(e->checkValue.set(index*16)).
					sessionTimeout(index * 1000).
					retry(false).retryInterval(index * 2000).retryLimit(index * 10);
					eventFunHolders[index - 1] = connector.sessionEventFunHolder.get();
					paramHolders[index - 1] = connector.paramHolder.get();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					try {
						barrier.await();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();

		}
		barrier.await();
		barrier.await();

		for(int i=0;i<10;i++){
			checkValue.set(-1);
			eventFunHolders[i].getAuthFailedFun().process(null);Assert.assertTrue(checkValue.get() == (i + 1)*11);checkValue.set(-1);
			eventFunHolders[i].getDisconnedFun().process(null);Assert.assertTrue(checkValue.get() == (i + 1)*12);checkValue.set(-1);
			eventFunHolders[i].getErrorEventFun().process(null);Assert.assertTrue(checkValue.get() == (i + 1)*13);checkValue.set(-1);
			eventFunHolders[i].getExpiredFun().process(null);Assert.assertTrue(checkValue.get() == (i + 1)*14);checkValue.set(-1);
			eventFunHolders[i].getSaslAuthedFun().process(null);Assert.assertTrue(checkValue.get() == (i + 1)*15);checkValue.set(-1);
			eventFunHolders[i].getSyncConnedFun().process(null);Assert.assertTrue(checkValue.get() == (i + 1)*16);checkValue.set(-1);
			Assert.assertTrue(paramHolders[i].getRetryInterval() == (i + 1) * 2000);
			Assert.assertTrue(paramHolders[i].getRetryLimit() == (i + 1) * 10);
			Assert.assertTrue(paramHolders[i].getSessionTimeout() == (i + 1) * 1000);
		}
		
		connector.reset().
		authFailedFun(e->System.out.println(e)).
		disconnedFun(e->System.out.println(e)).
		errorEventFun(e->System.out.println(e)).
		expiredFun(e->System.out.println(e)).
		saslAuthedFun(e->System.out.println(e)).
		syncConnedFun(e->System.out.println(e)).
		sessionTimeout(101).
		retry(false).retryInterval(102).retryLimit(103);
		Assert.assertTrue(connector.sessionEventFunHolder.get().getAuthFailedFun() != ZK.LOG_DEBUG_EVENT_FUN);
		Assert.assertTrue(connector.sessionEventFunHolder.get().getDisconnedFun() != ZK.LOG_DEBUG_EVENT_FUN);
		Assert.assertTrue(connector.sessionEventFunHolder.get().getErrorEventFun() != ZK.LOG_DEBUG_EVENT_FUN);
		Assert.assertTrue(connector.sessionEventFunHolder.get().getExpiredFun() != ZK.LOG_DEBUG_EVENT_FUN);
		Assert.assertTrue(connector.sessionEventFunHolder.get().getSaslAuthedFun() != ZK.LOG_DEBUG_EVENT_FUN);
		Assert.assertTrue(connector.sessionEventFunHolder.get().getSyncConnedFun() != ZK.LOG_DEBUG_EVENT_FUN);
		Assert.assertTrue(connector.paramHolder.get().getSessionTimeout() == 101);
		Assert.assertTrue(connector.paramHolder.get().getRetryLimit() == 103);
		Assert.assertTrue(connector.paramHolder.get().getRetryInterval() == 102);
		connector.reset();
		Assert.assertTrue(connector.sessionEventFunHolder.get().getDisconnedFun() == ZK.LOG_DEBUG_EVENT_FUN);
		Assert.assertTrue(connector.sessionEventFunHolder.get().getErrorEventFun() == ZK.LOG_DEBUG_EVENT_FUN);
		Assert.assertTrue(connector.sessionEventFunHolder.get().getExpiredFun() == ZK.LOG_DEBUG_EVENT_FUN);
		Assert.assertTrue(connector.sessionEventFunHolder.get().getSaslAuthedFun() == ZK.LOG_DEBUG_EVENT_FUN);
		Assert.assertTrue(connector.sessionEventFunHolder.get().getSyncConnedFun() == ZK.LOG_DEBUG_EVENT_FUN);
		Assert.assertTrue(connector.paramHolder.get().getSessionTimeout() == ZK.SESSION_TIME_OUT);
		Assert.assertTrue(connector.paramHolder.get().getRetryLimit() == ZK.RETRY_LIMIT);
		Assert.assertTrue(connector.paramHolder.get().getRetryInterval() == ZK.RETRY_INTERVAL);
	}

}
