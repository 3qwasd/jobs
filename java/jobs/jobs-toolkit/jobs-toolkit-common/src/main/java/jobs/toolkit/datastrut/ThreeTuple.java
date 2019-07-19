package jobs.toolkit.datastrut;

public class ThreeTuple<A, B, C> extends TwoTuple<A, B>{
	
	private final C third;
	
	public ThreeTuple(A first, B second, C third) {
		super(first, second);
		this.third = third;
	}
	
	public C third(){
		return this.third;
	}
}
