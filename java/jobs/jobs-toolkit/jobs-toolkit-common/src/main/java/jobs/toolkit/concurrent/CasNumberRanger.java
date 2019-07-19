package jobs.toolkit.concurrent;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 使用CAS实现的数值区间
 * 可以使用AtomicReference类以及不可变对象来实现自定义的原子变量
 * @author jobs
 *
 */
public final class CasNumberRanger {
	
	private static class IntPair{
		
		final int lower;
		
		final int upper;
		
		IntPair(int lower, int upper) {
			this.lower = lower;
			this.upper = upper;
		}
	}
	
	
	
	private final AtomicReference<IntPair> numRanger;
	
	public CasNumberRanger(int lower, int upper) {
		if(lower > upper) throw new IllegalArgumentException("The lower can't be bigger than upper!");
		this.numRanger = new AtomicReference<IntPair>(new IntPair(lower, upper));
	}
	
	public int[] getRanger(){
		IntPair pair = this.numRanger.get();
		return new int[]{pair.lower, pair.upper};
	}
	
	public void setLower(int l){
		while(true){
			IntPair oldVal = this.numRanger.get();
			if(l > oldVal.upper) 
				throw new IllegalArgumentException("The lower must not bigger than upper!");
			if(this.numRanger.compareAndSet(oldVal, new IntPair(l, oldVal.upper)))
				return;
		}
	}
	public void setUpper(int u){
		while(true){
			//首先先获取旧值
			IntPair oldVal = this.numRanger.get();
			if(u < oldVal.lower)
				throw new IllegalArgumentException("The upper must bigger than lower!");
			if(this.numRanger.compareAndSet(oldVal, new IntPair(oldVal.lower, u)))
				return;
		}
	}
}
