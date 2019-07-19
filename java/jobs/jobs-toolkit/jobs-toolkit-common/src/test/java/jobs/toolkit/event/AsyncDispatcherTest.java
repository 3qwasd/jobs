package jobs.toolkit.event;

import static org.junit.Assert.*;

import java.lang.Thread.State;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import jobs.toolkit.config.Configuration;
import jobs.toolkit.event.AsyncDispatcher;
import jobs.toolkit.event.Event;
import jobs.toolkit.event.EventType;
import jobs.toolkit.concurrency.ConcurrentTestCase;
import jobs.toolkit.config.SimpleConfig;

import org.junit.Before;
import org.junit.Test;

public class AsyncDispatcherTest extends ConcurrentTestCase{

	protected static final String SIMPLETESTEVENT = "SimpleTestEvent";

	protected static final String EXCEPTIONTESTEVENT = "ExceptionTestEvent";

	protected static final String COMPUTETESTEVENT = "ComputeTestEvent";

	protected static final EventType SIMPLE_EVENT_TYPE = EventType.of(SIMPLETESTEVENT);

	protected static final EventType EXCEPTION_EVENT_TYPE = EventType.of(EXCEPTIONTESTEVENT);

	protected static final EventType COMPUTE_EVENT_TYPE = EventType.of(COMPUTETESTEVENT);

	AsyncDispatcher<Event> dispatcher = new AsyncDispatcher<Event>("", new LinkedBlockingQueue<Event>());

	Configuration config = new SimpleConfig();

	ComputeTestEventHandler computeTestEventHandler;

	ExceptionTestEventHandler exceptionTestEventHandler;

	SimpleTestEventHandler simpleTestEventHandler;

	@Before
	public void setUp() throws Exception {
		computeTestEventHandler = new ComputeTestEventHandler();
		exceptionTestEventHandler = new ExceptionTestEventHandler();
		simpleTestEventHandler = new SimpleTestEventHandler();
		dispatcher.init(new SimpleConfig());
		//dispatcher.startup();
	}
	@Test
	public void testDispacherShutDown() throws Exception{
		dispatcher.start();
		Thread.sleep(1000);
		assertEquals(dispatcher.dispatcherThreadState(), State.WAITING);
		final AtomicInteger checkSum = new AtomicInteger(0);
		this.dispatcher.register(SIMPLE_EVENT_TYPE, simpleTestEventHandler);
		this.dispatcher.register(EXCEPTION_EVENT_TYPE, exceptionTestEventHandler);
		this.dispatcher.register(COMPUTE_EVENT_TYPE, computeTestEventHandler);
		final AtomicInteger eventCount = new AtomicInteger(0);
		final AtomicBoolean flag = new AtomicBoolean(true);
		for(int i=0;i<20;i++){
			this.pool.execute(new Runnable() {

				@Override
				public void run() {
					while (flag.get()) {
						int value = random.nextInt(RANDOM_VALUE_MAX);
						checkSum.addAndGet(value);
						eventCount.incrementAndGet();
						dispatcher.dispatch(getRandomEvent(value));
					}
				}
			});
		}
		Thread.sleep(1000);
		assertEquals(dispatcher.dispatcherThreadState(), State.RUNNABLE);
		Thread shutDownThread = new Thread(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				dispatcher.stop();
			}
			
		};
		shutDownThread.start();
		Thread.sleep(1000);
		assertEquals(shutDownThread.getState(), State.TIMED_WAITING);
		shutDownThread.join();
		assertEquals(dispatcher.dispatcherThreadState(), State.TERMINATED);
		flag.set(false);
		pool.shutdown();
		while(!pool.isTerminated()){
			Thread.sleep(1000);
		}
		System.out.println(eventCount.get());
		System.out.println(dispatcher.rejectEventCount.get());
		System.out.println(dispatcher.eventQueue().size());
		System.out.println(simpleTestEventHandler.count + computeTestEventHandler.count + exceptionTestEventHandler.count);
		int sum = dispatcher.rejectEventCount.get() + 
				dispatcher.eventQueue().size() + 
				simpleTestEventHandler.count + computeTestEventHandler.count + exceptionTestEventHandler.count;
		assertTrue((eventCount.get() - sum) == 0);
	}
	@Test
	public void testDispacherThreadBlocking() throws Exception{
		this.dispatcher.register(SIMPLE_EVENT_TYPE, simpleTestEventHandler);
		this.dispatcher.register(EXCEPTION_EVENT_TYPE, exceptionTestEventHandler);
		this.dispatcher.register(COMPUTE_EVENT_TYPE, computeTestEventHandler);
		dispatcher.start();
		Thread.sleep(1000);
		assertEquals(dispatcher.dispatcherThreadState(), State.WAITING);
		final AtomicInteger checkSum = new AtomicInteger(0);
		Thread thread = new Thread(){

			@Override
			public void run() {
				while(!this.isInterrupted()){
					int value = random.nextInt(1000);
					dispatcher.dispatch(getRandomEvent(value));
					checkSum.addAndGet(value);
				}
			}
			
		};
		thread.start();
		Thread.sleep(1000);
		assertEquals(dispatcher.dispatcherThreadState(), State.RUNNABLE);
		thread.interrupt();
		thread.join();
		Thread.sleep(1000);
		assertEquals(dispatcher.dispatcherThreadState(), State.WAITING);
		dispatcher.dispatcherThread().interrupt();
		try{
			dispatcher.dispatcherThread().join(1000);
		}catch(Exception e){
			fail();
		}
		assertEquals(dispatcher.dispatcherThreadState(), State.TERMINATED);
		assertTrue((checkSum.get() - simpleTestEventHandler.sum - exceptionTestEventHandler.sum - computeTestEventHandler.sum) == 0);
	}
	@Test
	public void testDispacherThread() throws Exception{
		dispatcher.start();
		Thread.sleep(1000);
		assertEquals(dispatcher.dispatcherThreadState(), State.WAITING);
		final CyclicBarrier barrier = new CyclicBarrier(21);
		final AtomicInteger checkSum = new AtomicInteger(0);
		this.dispatcher.register(SIMPLE_EVENT_TYPE, simpleTestEventHandler);
		this.dispatcher.register(EXCEPTION_EVENT_TYPE, exceptionTestEventHandler);
		this.dispatcher.register(COMPUTE_EVENT_TYPE, computeTestEventHandler);
		final AtomicInteger eventCount = new AtomicInteger(0);
		for(int i=0;i<20;i++){
			this.pool.execute(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						barrier.await();
						for(int i=0;i<100;i++){
							int value = random.nextInt(RANDOM_VALUE_MAX);
							checkSum.addAndGet(value);
							eventCount.incrementAndGet();
							dispatcher.dispatch(getRandomEvent(value));
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						try {
							barrier.await();
						} catch (InterruptedException | BrokenBarrierException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
		}
		barrier.await();
		barrier.await();
		assertEquals(dispatcher.dispatcherThreadState(), State.RUNNABLE);
		dispatcher.dispatcherThread().interrupt();
		Thread.sleep(1000);
		assertEquals(dispatcher.dispatcherThreadState(), State.TERMINATED);
		assertTrue((checkSum.get() - (simpleTestEventHandler.sum + computeTestEventHandler.sum + exceptionTestEventHandler.sum))>0);
		while (!dispatcher.eventQueue().isEmpty()) {
			Event event = dispatcher.eventQueue().take();
			dispatcher.dispatch(event);
		}
		assertTrue((checkSum.get() - (simpleTestEventHandler.sum + computeTestEventHandler.sum + exceptionTestEventHandler.sum)) == 0);
		assertTrue((eventCount.get() - (simpleTestEventHandler.count + computeTestEventHandler.count + exceptionTestEventHandler.count)) == 0);
	}
	@Test
	public void testInitialize() throws Exception {
		final CyclicBarrier barrier = new CyclicBarrier(11);
		final AtomicInteger initCount = new AtomicInteger(0);
		final AsyncDispatcher testAsyncDispatcher = new AsyncDispatcher(""){

			@Override
			protected void initialize() throws Exception {
				// TODO Auto-generated method stub
				super.initialize();
				System.out.println("初始化组件：" + this.getName());
				initCount.addAndGet(1);
			}
		};
		for(int i=0;i<10;i++){
			this.pool.execute(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						barrier.await();
						testAsyncDispatcher.init(config);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						try {
							barrier.await();
						} catch (InterruptedException | BrokenBarrierException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
		}
		barrier.await();
		barrier.await();
		assertEquals(initCount.get(), 1);
		assertTrue(testAsyncDispatcher.isInited());
	}
	@Test
	public void testInitializeException() throws Exception {
		final CyclicBarrier barrier = new CyclicBarrier(11);
		final AtomicInteger initCount = new AtomicInteger(0);
		final AtomicInteger shutDownCount = new AtomicInteger(0);
		final AsyncDispatcher testAsyncDispatcher = new AsyncDispatcher(""){

			@Override
			protected void initialize() throws Exception {
				// TODO Auto-generated method stub
				super.initialize();
				System.out.println("初始化组件：" + this.getName());
				initCount.addAndGet(1);
				throw new Exception();
			}

			@Override
			protected void shutdown() throws Exception {
				// TODO Auto-generated method stub
				super.shutdown();
				shutDownCount.addAndGet(1);
			}

		};
		for(int i=0;i<10;i++){
			final int j = i;
			this.pool.execute(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						barrier.await();
						testAsyncDispatcher.init(config);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println(j);
						e.printStackTrace();
						System.out.println(this.toString());
					}finally{
						try {
							barrier.await();
						} catch (InterruptedException | BrokenBarrierException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
		}
		barrier.await();
		barrier.await();
		assertEquals(initCount.get(), 1);
		assertTrue(testAsyncDispatcher.isStopped());
		assertEquals(shutDownCount.get(), 1);
	}
	@Test
	public void testStartup() throws Exception {

		AsyncDispatcher testDispatcher = new AsyncDispatcher(""){

			@Override
			protected void startup() throws Exception {
				// TODO Auto-generated method stub
				super.startup();
			}

		};
		testDispatcher.init(new SimpleConfig());
		testDispatcher.start();
		assertEquals(State.RUNNABLE, dispatcher.dispatcherThreadState());
		Thread.sleep(1000);
		assertEquals(State.WAITING, dispatcher.dispatcherThreadState());
	}

	@Test
	public void testShutdown() {

	}

	@Test
	public void testHandle() throws InterruptedException {
		//this.dispatcher.init(new SimpleConfig());
		for(int i=0; i<1000;i++){
			dispatcher.dispatch(getRandomEvent(1000));
		}
		final Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				dispatcher.dispatch(getRandomEvent(1000));
				
				while (!Thread.currentThread().isInterrupted()) {
					System.out.println("123456789");
				}
			}
		});
		
		thread.start();
		Thread.sleep(1000);
		assertEquals(State.WAITING, thread.getState());
		dispatcher.eventQueue().take();
		Thread.sleep(1000);
		assertEquals(State.RUNNABLE,  thread.getState());
		thread.interrupt();
		thread.join();
	}
	@Test
	public void testHandlerInterrupt() throws InterruptedException{
		for(int i=0; i<1000;i++){
			dispatcher.dispatch(getRandomEvent(1000));
		}
		final Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try{
					dispatcher.dispatch(getRandomEvent(1000));
					fail("失败");
				}catch(RuntimeException e){
					e.printStackTrace();
				}
				
			}
		});
		
		thread.start();
		Thread.sleep(1000);
		assertEquals(State.WAITING, thread.getState());		
		thread.interrupt();
		thread.join();
	}
	@Test
	public void testRegister() throws InterruptedException, Exception {
		this.dispatcher.init(config);
		this.dispatcher.start();
		this.dispatcher.register(SIMPLE_EVENT_TYPE, simpleTestEventHandler);
		this.dispatcher.register(EXCEPTION_EVENT_TYPE, exceptionTestEventHandler);
		this.dispatcher.register(COMPUTE_EVENT_TYPE, computeTestEventHandler);
		final CyclicBarrier barrier = new CyclicBarrier(41);
		final AtomicLong checkSum = new AtomicLong(0);
		for(int i=0;i<20;i++){
			this.pool.execute(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						barrier.await();
						for(int i=0;i<100;i++){
							int value = random.nextInt(RANDOM_VALUE_MAX);
							checkSum.addAndGet(value);
							dispatcher.dispatch(getRandomEvent(value));
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						try {
							barrier.await();
						} catch (InterruptedException | BrokenBarrierException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
			this.pool.execute(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						barrier.await();
						dispatcher.register(SIMPLE_EVENT_TYPE, new SimpleTestEventHandler());
						dispatcher.register(EXCEPTION_EVENT_TYPE, new ExceptionTestEventHandler());
						dispatcher.register(COMPUTE_EVENT_TYPE, new ComputeTestEventHandler());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						try {
							barrier.await();
						} catch (InterruptedException | BrokenBarrierException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}
			});
		}
		barrier.await();
		barrier.await();
		this.dispatcher.stop();
		assertEquals(this.simpleTestEventHandler, this.dispatcher.getEventHandler(SIMPLE_EVENT_TYPE));
		assertEquals(this.exceptionTestEventHandler, this.dispatcher.getEventHandler(EXCEPTION_EVENT_TYPE));
		assertEquals(this.computeTestEventHandler, this.dispatcher.getEventHandler(COMPUTE_EVENT_TYPE));
		assertTrue((checkSum.get() - simpleTestEventHandler.sum - exceptionTestEventHandler.sum - computeTestEventHandler.sum) == 0);
		assertTrue(this.dispatcher.eventQueue().isEmpty());
		assertEquals(State.TERMINATED, this.dispatcher.dispatcherThreadState());
		assertTrue(this.dispatcher.isStopped());
		
	}

	@Test
	public void testDispatchWithDispatchFailed() {
		
		this.dispatcher = new AsyncDispatcher(""){
			@Override
			protected void dispacthFailed(Event event, Throwable e){
				super.dispacthFailed(event, e);
				throw new RuntimeException(e);
			}
		};
		this.dispatcher.init(config);
		this.dispatcher.register(SIMPLE_EVENT_TYPE, simpleTestEventHandler);
		this.dispatcher.register(EXCEPTION_EVENT_TYPE, exceptionTestEventHandler);
		/*测试正常的事件*/
		this.dispatcher.dispatch(new SimpleTestEvent(1000));
		assertEquals(simpleTestEventHandler.sum, 1000);
		/*测试发生异常的事件*/
		try{
			this.dispatcher.dispatch(new ExceptionTestEvent(9999));
			fail();
		}catch(Exception e){
			e.printStackTrace();
			assertEquals(exceptionTestEventHandler.sum, 9999);
		}
		/*测试无法处理的事件*/
		try{
			this.dispatcher.dispatch(new ComputeTestEvent(8888));
			fail();
		}catch(Exception e){
			e.printStackTrace();
			assertEquals(computeTestEventHandler.sum, 0);
		}
	}


	private Event getRandomEvent(int value){
		int type = random.nextInt(3);
		switch (type) {
		case 1:
			return new ExceptionTestEvent(value);
		case 2:
			return new ComputeTestEvent(value);
		default:
			return new SimpleTestEvent(value);
		}
	}
}
