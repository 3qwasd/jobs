package jobs.toolkit.lifecycle;

import java.util.concurrent.atomic.AtomicInteger;

import jobs.toolkit.lifecycle.LifeCycleComponent;
import jobs.toolkit.logging.Log;
import jobs.toolkit.logging.LogFactory;

public class MyLifeCycle extends LifeCycleComponent {
	
	private static final Log LOG = LogFactory.getLog(MyLifeCycle.class);
	
	AtomicInteger initCount = new AtomicInteger(0);
	
	AtomicInteger startCount = new AtomicInteger(0);
	
	AtomicInteger stopCount = new AtomicInteger(0);
	
	
	public MyLifeCycle(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void doInit() {
		// TODO Auto-generated method stub
		this.initCount.incrementAndGet();
		for(int i=0; i<Integer.MAX_VALUE; i++){
			int j = i * i;
		}
	}

	@Override
	public void doStart() {
		// TODO Auto-generated method stub
		this.startCount.incrementAndGet();
		for(int i=0; i<Integer.MAX_VALUE; i++){
			int j = i * i;
		}
	}

	@Override
	public void doStop() {
		// TODO Auto-generated method stub
		this.stopCount.incrementAndGet();
		for(int i=0; i<Integer.MAX_VALUE; i++){
			int j = i * i;
		}
	}	
}
