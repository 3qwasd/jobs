package jobs.toolkit.alg;

public class StatisticAssistant {
	
	/**
	 * 挑选数组a中排在第k位的元素
	 * @param a
	 * @param k
	 */
	public static <T extends Comparable<T>> T select(T[] a, int k){
		if(a == null || a.length < 1) return null;
		return select(a, k, 0, a.length - 1);
	}
	protected static <T extends Comparable<T>> T select(T[] a, int k, int start, int end){
		if(start == end) return a[start];
		int mid = SortAssistant.partition(a, start, end);
		int leftLen = mid - start + 1;
		if(leftLen == k) return a[mid];
		else if(leftLen < k) return select(a, k - leftLen, mid + 1, end);
		else return select(a, k, start, mid - 1);
	}
}
