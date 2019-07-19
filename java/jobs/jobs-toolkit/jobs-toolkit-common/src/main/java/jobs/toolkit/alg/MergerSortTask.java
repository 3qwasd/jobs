package jobs.toolkit.alg;


public class MergerSortTask<T extends Comparable<T>> extends BaseSortTask<T> {

	private static final long serialVersionUID = 1L;

	public MergerSortTask(T[] array) {
		super(array);
	}
	
	public MergerSortTask(T[] array, int start, int end) {
		super(array, start, end);
	}

	@Override
	protected void compute() {
		// TODO Auto-generated method stub
		if((end - start) <= SortAssistant.THRESHOLD) {
			SortAssistant.insertSort(array, start, end);
			return;
		}
		
		int mid = (start + end) >> 1;
		
		MergerSortTask<T> leftTask = new MergerSortTask<>(array, start, mid);
		MergerSortTask<T> rightTask = new MergerSortTask<>(array, mid + 1, end);
		
		leftTask.fork();
		rightTask.fork();
		
		leftTask.join();
		rightTask.join();

		SortAssistant.merger(array, start, mid, end);
	}

}
