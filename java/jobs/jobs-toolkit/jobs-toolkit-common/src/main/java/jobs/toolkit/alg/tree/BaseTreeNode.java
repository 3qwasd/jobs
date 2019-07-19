package jobs.toolkit.alg.tree;

public abstract class BaseTreeNode<T extends BaseTreeNode<T>> {
	
	private volatile T p = null;
	
	private volatile T l = null;
	
	private volatile T r = null;
	
	private volatile Object k = null;
	
	@SuppressWarnings("unchecked")
	public final T setParent(T p){
		this.p = p;
		return (T) this;
	}
	public final T parent(){
		return this.p;
	}
	@SuppressWarnings("unchecked")
	public final T setLeftChild(T l){
		this.l = l;
		return (T) this;
	}
	public final T left(){
		return this.l;
	}
	@SuppressWarnings("unchecked")
	public final T setRightChild(T r){
		this.r = r;
		return (T) this;
	}
	public final T right(){
		return this.r;
	}
	@SuppressWarnings("unchecked")
	public final <V extends Comparable<V>> T setKey(V v){
		this.k = v;
		return (T) this;
	}
	@SuppressWarnings("unchecked")
	public final <V extends Comparable<V>> V key(){
		return (V) this.k;
	}
}
