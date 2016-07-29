package jobs.toolkit.lifecycle;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jobs.toolkit.lifecycle.LifeCycle;
import jobs.toolkit.lifecycle.LifeCycleStateListener;
import jobs.toolkit.lifecycle.LifeCycleStateTransferEvent;
import jobs.toolkit.lifecycle.LifeCycleStateModel.STATE;
import jobs.toolkit.config.SimpleConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LifeCycleComponentTest {

	MyLifeCycle lifeCycle = new MyLifeCycle("MyTestLifeCycleComponent");
	MyLifeCycle iifeCycle_1 = new MyLifeCycle("MyTestLifeCycleComponent");
	ExceptionLifeCycle exceptionLifeCycle = new ExceptionLifeCycle("MyTestExceptionLifeCycleComponent");
	private static final ExecutorService pool = Executors.newCachedThreadPool();

	int paireSum = 20;
	final CyclicBarrier barrier = new CyclicBarrier(3 * this.paireSum + 1);
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testInit() {
		try{
			lifeCycle.init(null);
			fail();
		}catch(Exception e){
		}

		try{
			lifeCycle.init(new SimpleConfig());
			assertTrue(lifeCycle.isInState(STATE.INITED));
		}catch(Exception e){
			fail();
		}

		try{
			lifeCycle.init(new SimpleConfig());
			assertEquals(lifeCycle.initCount.get(), 1);
			assertTrue(lifeCycle.isInState(STATE.INITED));
		}catch(Exception e){
			fail();
		}
		try{
			lifeCycle.start();
			lifeCycle.init(new SimpleConfig());
			fail();
		}catch(Exception e){
			e.printStackTrace();
			assertTrue(lifeCycle.isInState(STATE.STARTED));
		}
		lifeCycle = new ExceptionLifeCycle("MyExcepionLifeCycle");

		try{
			lifeCycle.init(new SimpleConfig());
			fail();
		}catch(Exception e){
			e.printStackTrace();
		}
		assertEquals(lifeCycle.stopCount.get(), 1);
		assertTrue(lifeCycle.isInState(STATE.STOPPED));

	}
	@Test
	public void testStart(){

		try{
			lifeCycle.init(new SimpleConfig());
			assertTrue(lifeCycle.isInState(STATE.INITED));
		}catch(Exception e){
			fail();
		}
		try{
			lifeCycle.start();
			assertEquals(lifeCycle.startCount.get(), 1);
			assertTrue(lifeCycle.isInState(STATE.STARTED));
		}catch(Exception e){
			fail();
		}
		try{
			lifeCycle.start();
			assertEquals(lifeCycle.startCount.get(), 1);
			assertTrue(lifeCycle.isInState(STATE.STARTED));
		}catch(Exception e){
			fail();
		}
		try{
			lifeCycle.stop();
			lifeCycle.start();
			fail();
		}catch(Exception e){
			e.printStackTrace();
		}
		assertTrue(lifeCycle.isInState(STATE.STOPPED));
		lifeCycle = new ExceptionLifeCycle("MyExcepionLifeCycle");

		try{
			lifeCycle.init(new SimpleConfig());
			lifeCycle.start();
			fail();
		}catch(Exception e){
			e.printStackTrace();
		}
		assertEquals(lifeCycle.stopCount.get(), 1);
		assertFalse(lifeCycle.isInState(STATE.INITED));
		assertTrue(lifeCycle.isInState(STATE.STOPPED));
	}
	@Test
	public void testStop(){
		try{
			lifeCycle.init(new SimpleConfig());
			assertTrue(lifeCycle.isInState(STATE.INITED));
		}catch(Exception e){
			fail();
		}
		try{
			lifeCycle.start();
			assertEquals(lifeCycle.startCount.get(), 1);
			assertTrue(lifeCycle.isInState(STATE.STARTED));
		}catch(Exception e){
			fail();
		}
		try{
			lifeCycle.stop();
			assertEquals(lifeCycle.stopCount.get(), 1);
			assertTrue(lifeCycle.isInState(STATE.STOPPED));
		}catch(Exception e){
			fail();
		}
		try{
			lifeCycle.stop();
			assertEquals(lifeCycle.stopCount.get(), 1);
			assertTrue(lifeCycle.isInState(STATE.STOPPED));
			lifeCycle.start();
			fail();
		}catch(Exception e){
			e.printStackTrace();
			assertFalse(lifeCycle.isInState(STATE.STARTED));
		}
		lifeCycle = new ExceptionLifeCycle("MyExcepionLifeCycle");

		try{
			lifeCycle.init(new SimpleConfig());
			lifeCycle.start();
			lifeCycle.stop();
			fail();
		}catch(Exception e){
			e.printStackTrace();
		}
		assertEquals(lifeCycle.stopCount.get(), 1);
		assertTrue(lifeCycle.isInState(STATE.STOPPED));
	}

	@Test
	public void testInitAtConcurrent() throws Exception{
		for(int i=0;i<3*paireSum;i++){
			this.pool.execute(new initTask());
		}
		barrier.await();
		barrier.await();
		assertEquals(lifeCycle.initCount.get(), 1);
		assertTrue(lifeCycle.isInState(STATE.INITED));
		assertFalse(lifeCycle.isInState(STATE.NOTINITED));
	}
	@Test
	public void testStartAtConcurrent() throws Exception{
		lifeCycle.init(new SimpleConfig());
		for(int i=0;i<3*paireSum;i++){
			this.pool.execute(new startTask());
		}
		barrier.await();
		barrier.await();
		assertEquals(lifeCycle.startCount.get(), 1);
		assertTrue(lifeCycle.isInState(STATE.STARTED));
	}
	@Test
	public void testStopAtConcurrent() throws Exception{
		lifeCycle.init(new SimpleConfig());
		lifeCycle.start();
		for(int i=0;i<3*paireSum;i++){
			this.pool.execute(new stopTask());
		}
		barrier.await();
		barrier.await();
		assertEquals(lifeCycle.stopCount.get(), 1);
		assertTrue(lifeCycle.isInState(STATE.STOPPED));
	}
	@Test
	public void testInitStartStopAtConcurrent() throws Exception{
		for(int i = 0 ; i < paireSum; i++){
			this.pool.execute(new initTask());
			this.pool.execute(new startTask());
			this.pool.execute(new stopTask());
		}
		barrier.await();
		barrier.await();
		System.out.println(lifeCycle.initCount);
		System.out.println(lifeCycle.startCount);
		System.out.println(lifeCycle.stopCount);
		assertTrue(lifeCycle.initCount.get()<= 1);
		assertTrue(lifeCycle.startCount.get()<=1);
		assertTrue(lifeCycle.stopCount.get()<=1);
		assertTrue(lifeCycle.isInState(STATE.STOPPED));
		this.pool.shutdown();
	}
	@Test
	public void testNotifyFailure(){
		try{
			exceptionLifeCycle.init(new SimpleConfig());
			fail();
		}catch(Exception e){
			e.printStackTrace();
		}
		assertTrue(exceptionLifeCycle.getFailureCause() instanceof NullPointerException);
		assertEquals(STATE.INITED,exceptionLifeCycle.getFailureState());
	}
	@Test
	public void testNotifyFailureConcurrent() throws Exception{
		final CyclicBarrier cb = new CyclicBarrier(41);

		for(int i = 0; i < 10; i++){
			final int j = i;
			pool.execute(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try{
						cb.await();
						System.out.print("IllegalAccessExceptionIllegalAccessExceptionIllegalAccessExceptionIllegalAccessException");
						lifeCycle.notifyFailure(new IllegalAccessException());
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						try {
							cb.await();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}
				}
			});
			pool.execute(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try{
						cb.await();
						System.out.print("NullPointerExceptionNullPointerExceptionNullPointerExceptionNullPointerExceptionNullPointerException");
						lifeCycle.notifyFailure(new NullPointerException());
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						try {
							cb.await();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}
				}
			});
			pool.execute(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try{
						cb.await();
						lifeCycle.getFailureCause();
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						try {
							cb.await();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}
				}
			});
			pool.execute(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try{
						cb.await();
						lifeCycle.getFailureState();
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						try {
							cb.await();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}
				}
			});
		}
		cb.await();
		cb.await();
		System.out.println(lifeCycle.getFailureCause());
		assertEquals(lifeCycle.getState(), STATE.NOTINITED);
	}
	@Test
	public void waitToStopTest() throws Exception{
		final CyclicBarrier cb = new CyclicBarrier(12);
		final List<String> flag = new ArrayList<>();
		lifeCycle = new MyLifeCycle("TestWaitToStopLifeCycle"){

			@Override
			public void doStop() {
				// TODO Auto-generated method stub
				for(int i=0;i<100000;i++){
					int j = i*i;
				}
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				flag.add("lifeCycle");
			}
			
		};
		lifeCycle.init(new SimpleConfig());
		lifeCycle.start();
		pool.execute(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					cb.await();
					lifeCycle.stop();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					try {
						cb.await();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		for(int i = 0 ;i < 10; i++){
			pool.execute(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						cb.await();
						flag.add("startWaitToStop");
						lifeCycle.waitToStop(20000);
						flag.add("stopwaitToStop");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						try {
							cb.await();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (BrokenBarrierException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
		}
		cb.await();
		cb.await();
		System.out.println(flag);
	}
	@Test
	public void testRecordLifecycleEventHistory(){
		lifeCycle.init(new SimpleConfig());
		lifeCycle.start();
		lifeCycle.stop();
		List<LifeCycleStateTransferEvent> history = lifeCycle.getLifecycleHistory();
		for(int i = 0; i < history.size(); i++){
			LifeCycleStateTransferEvent event = history.get(i);
			System.out.println(event);
		}
		try{
			exceptionLifeCycle.init(new SimpleConfig());
			exceptionLifeCycle.start();
			exceptionLifeCycle.stop();
		}catch(Exception e){
			
		}
		history = exceptionLifeCycle.getLifecycleHistory();
		for(int i = 0; i < history.size(); i++){
			LifeCycleStateTransferEvent event = history.get(i);
			System.out.println(event);
		}
	}
	@Test
	public void testNotifyListener(){

		for(int i = 0; i<10; i++){
			final int j = i;
			lifeCycle.registerStateListener(new LifeCycleStateListener() {

				@Override
				public void stateChanged(LifeCycle component) {
					// TODO Auto-generated method stub
					//System.out.println(component.getState().toString() + j);
				}
			});
		}
		lifeCycle.init(new SimpleConfig());
		lifeCycle.start();
		lifeCycle.stop();
	}
	@Test
	public void testNotifyLisenerConcurrent() throws Exception{
		final List<LifeCycleStateListener> backup = new ArrayList<>();
		final List<LifeCycleStateListener> global = new ArrayList<>();
		for(int i = 0; i < 10000; i++){
			final int j = i;
			LifeCycleStateListener listener = new LifeCycleStateListener() {
				String name = "listener_" + j;
				@Override
				public void stateChanged(LifeCycle component) {
					// TODO Auto-generated method stub
					System.out.println("Notify Listener:" + name);
				}
			};
			backup.add(listener);
			global.add(new LifeCycleStateListener() {
				String name = "global_listener_" + j;
				@Override
				public void stateChanged(LifeCycle component) {
					// TODO Auto-generated method stub
					System.out.println("Notify Global Listener:" + name);
				}
			});
		}
		final CyclicBarrier cb = new CyclicBarrier(61);

		for(int i = 0; i < 20; i++){
			pool.execute(new Runnable() {

				@Override
				public void run() {
					try {
						cb.await();
						for(int i = 0; i < backup.size(); i++){
							lifeCycle.registerStateListener(backup.get(i));
							MyLifeCycle.registerGlobalListener(global.get(i));
						}
					} catch (Exception e){
						e.printStackTrace();
					} finally {
						try {
							cb.await();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			});
			pool.execute(new Runnable() {

				@Override
				public void run() {
					try {
						cb.await();
						for(int i = backup.size() - 1; i >= 0; i--){
							lifeCycle.unRegisterStateListener(backup.get(i));
							MyLifeCycle.unRegisterGlobalListener(global.get(i));
						}
					} catch (Exception e){
						e.printStackTrace();
					} finally {
						try {
							cb.await();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			});
			pool.execute(new Runnable() {

				@Override
				public void run() {
					try {
						cb.await();
						lifeCycle.init(new SimpleConfig());
						lifeCycle.start();
						lifeCycle.stop();
					} catch (Exception e){
						e.printStackTrace();
					} finally {
						try {
							cb.await();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			});
		}
		cb.await();
		cb.await();
	}
	public class TestListener implements LifeCycleStateListener{

		@Override
		public void stateChanged(LifeCycle component) {
			// TODO Auto-generated method stub
			//System.out.println(component.getState());
		}

	}
	public class TestExceptionListener implements LifeCycleStateListener{

		@Override
		public void stateChanged(LifeCycle component) {
			// TODO Auto-generated method stub
			//System.out.println(component.getState());
			throw new NullPointerException("生命周期 出错");
		}

	}
	public class initTask implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				barrier.await();
				lifeCycle.init(new SimpleConfig());

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally{
				try {
					barrier.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
	public class startTask implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				barrier.await();
				lifeCycle.start();
				barrier.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally{
				try {
					barrier.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
	public class stopTask implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				barrier.await();
				lifeCycle.stop();
				barrier.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally{
				try {
					barrier.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}
