package jobs.toolkit.control;

import java.util.concurrent.atomic.AtomicInteger;

public class FixFreqQPSController extends QPSController {
	
	private final AtomicInteger maxQps;
	
	
	public FixFreqQPSController(int maxQps) {
		this.maxQps = new AtomicInteger(maxQps);
	}
	public FixFreqQPSController() {
		this(CFG.MAX_QPS);
	}
	
	@Override
	public long delay() {
		return 1000/this.maxQps.get();
	}

}
