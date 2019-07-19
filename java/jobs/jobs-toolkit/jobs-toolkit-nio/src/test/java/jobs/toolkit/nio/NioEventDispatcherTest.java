package jobs.toolkit.nio;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import jobs.toolkit.config.MapConfiguration;
import jobs.toolkit.event.EventHandler;
import jobs.toolkit.event.EventType;
import jobs.toolkit.nio.event.NioEvent;
import jobs.toolkit.nio.event.NioEventDispatcher;

public class NioEventDispatcherTest {
	
	NioEventDispatcher dispatcher;
	
	boolean falg = false;
	
	@Before
	public void setUp() throws Exception {
		this.dispatcher = NioEventDispatcher.getInstance();
		this.dispatcher.init(new MapConfiguration());
		
	}



	@Test
	public void testInitialize() {
		Assert.assertTrue(this.dispatcher.isInited());
	}

	@Test
	public void testDoDispatch() {
		try{
			dispatcher.dispatch(new TestNioEvent());
		}catch(Throwable _){
			_.printStackTrace();
			Assert.assertTrue(_.getCause() instanceof NullPointerException);
			Assert.assertTrue(_ instanceof IllegalStateException);
		}
		dispatcher.register(EventType.of(TestNioEvent.TYPE_NAME), new TestNioEventHandler());
		TestNioEvent event = new TestNioEvent();
		dispatcher.dispatch(event);
		Assert.assertTrue(event.count == 1);
		Assert.assertTrue(event.exeception == 1);
		Assert.assertTrue(event.e instanceof IllegalArgumentException);
		
	}
	@After
	public void tearDown() throws Exception {
		
	}
	
	public static class TestNioEvent extends NioEvent{
		
		public static final String TYPE_NAME = "TESTNIOEVENT";
		
		public  int count = 0;
		
		public  int exeception = 0;
		
		public  Throwable e;
		
		public TestNioEvent() {
			super(EventType.of(TYPE_NAME), null);
		}
		
	}
	public static class TestNioEventHandler implements EventHandler<TestNioEvent>{

		@Override
		public void doHandle(TestNioEvent event) throws Throwable {
			// TODO Auto-generated method stub
			event.count++;
			throw new IllegalArgumentException();
		}

		@Override
		public void doExcepion(TestNioEvent event, Throwable e) {
			// TODO Auto-generated method stub
			event.exeception++;
			event.e = e;
		}
		
	}
}
