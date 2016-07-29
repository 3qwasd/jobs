package jobs.toolkit.control;

import jobs.toolkit.concurrent.CasNumberRanger;
import jobs.toolkit.utils.RandomUtils;

public class RandomFreqQPSController extends QPSController{
	
	private final CasNumberRanger delayRanger;
		
	public RandomFreqQPSController(int lower, int upper) {
		this.delayRanger = new CasNumberRanger(lower, upper);
	}

	@Override
	public long delay() {
		int[] ranger = delayRanger.getRanger();
		long delay = RandomUtils.uniform(ranger[0], ranger[1] + 1);
		return delay;
	}

}
