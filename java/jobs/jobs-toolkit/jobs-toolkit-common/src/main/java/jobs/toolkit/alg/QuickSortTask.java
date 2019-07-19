package jobs.toolkit.alg;


public class QuickSortTask<T extends Comparable<T>> extends BaseSortTask<T> {

	private static final long serialVersionUID = 1L;
	
	public QuickSortTask(T[] array) {
		super(array);
	}

	public QuickSortTask(T[] array, int start, int end) {
		super(array, start, end);
	}


	@Override
	protected void compute() {
		if((this.end - this.start) <= SortAssistant.THRESHOLD) {
			SortAssistant.insertSort(this.array, this.start, this.end);
			return;
		}
		
		int mid = SortAssistant.partition(this.array, this.start, this.end);

		QuickSortTask<T> leftTask = new QuickSortTask<T>(array, this.start, mid - 1);
		QuickSortTask<T> rightTask = new QuickSortTask<>(array, mid + 1, this.end);
		
		leftTask.fork();
		rightTask.fork();
		
		leftTask.join();
		rightTask.join();
	}

}
