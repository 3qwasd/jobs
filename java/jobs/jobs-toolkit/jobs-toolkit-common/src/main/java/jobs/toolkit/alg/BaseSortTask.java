package jobs.toolkit.alg;

import java.util.concurrent.RecursiveAction;

public abstract class BaseSortTask<T extends Comparable<T>> extends RecursiveAction{

	private static final long serialVersionUID = 1L;

	protected final T[] array;

	protected final int start;

	protected final int end;

	public BaseSortTask(T[] array){
		this(array, 0, array == null || array.length < 1 ? 0 : array.length - 1);
	}
	public BaseSortTask(T[] array, int start, int end) {
		this.array = array;
		this.start = start;
		this.end = end;
	}
}
