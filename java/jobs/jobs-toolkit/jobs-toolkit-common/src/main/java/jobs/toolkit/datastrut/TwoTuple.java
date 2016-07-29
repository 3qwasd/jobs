package jobs.toolkit.datastrut;

/**
 * 2元组
 * @author jobs
 *
 */
public class TwoTuple<A, B> {
	
	private final A first;
	
	private final B second;
	
	public TwoTuple(A first, B second) {
		super();
		this.first = first;
		this.second = second;
	}
	
	public A first(){
		return this.first;
	}
	public B second(){
		return this.second;
	}
}
