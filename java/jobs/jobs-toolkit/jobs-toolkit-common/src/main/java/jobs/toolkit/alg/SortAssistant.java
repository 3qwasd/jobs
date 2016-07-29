package jobs.toolkit.alg;

import java.util.Arrays;

import jobs.toolkit.utils.RandomUtils;

public class SortAssistant {
	
	public static final int THRESHOLD = 20;
	
	public static <T> void quickSort(Comparable<T>[] array){
		quickSort(array, 0, array == null || array.length < 1 ? 0 : array.length - 1);
	}
	/**
	 * 快速排序
	 * @param array
	 * @param start
	 * @param end
	 */
	protected static <T> void quickSort(Comparable<T>[] array, int start, int end){
		if(start >= end || array == null || array.length <= 1) return;
		if((end - start) <= THRESHOLD) {
			insertSort(array, start, end);
			return;
		}
		int mid = partition(array, start, end);
		quickSort(array, start, mid - 1);
		quickSort(array, mid + 1, end);
	}
	protected static <T> int partition(Comparable<T>[] array, int start, int end){
		int keyIndex = RandomUtils.uniform(start, end + 1);
		Comparable<T> key = array[keyIndex];
		array[keyIndex] = array[end];
		int less = start - 1;
		for(int i = start; i < end; i++){
			//i表示当前检查的元素下标
			if(array[i].compareTo((T) key) <= 0){
				Comparable<T> temp = array[++less];
				array[less] = array[i];
				array[i] = temp;
			}
		}
		
		array[end] = array[less + 1];
		array[less +1] = key;
		return less + 1;
	}
	/**
	 * 大顶堆排序
	 * @param array
	 */
	public static <T> void maxHeapSort(Comparable<T>[] array){
		if(array == null || array.length <= 1) return;
		buildMaxHeap(array);
		int n = array.length;
		int heapSize = array.length;
		for(int i = n - 1; i > 0; i--){
			Comparable<T> temp = array[0];
			array[0] = array[i];
			array[i] = temp;
			maxHeapify(array, 0, --heapSize);
 		}
	}
	private static <T> void buildMaxHeap(Comparable<T>[] array){
		int n = array.length;
		//找到堆中最后一个分支节点的下标
		int lastBranch = n >> 1 - 1;
		for(int i = lastBranch; i >= 0; i--){
			maxHeapify(array, i, n);
		}
	}
	@SuppressWarnings("unchecked")
	private static <T> void maxHeapify(Comparable<T>[] array, int i, int heapSize){
		//找出左右孩子的下标索引
		int r = (i + 1) << 1;
		int l = r - 1;
		int max = i;
		//找出节点i以及其左右孩子(如果都存在)三个节点中的最大的节点
		if(l < heapSize && array[l].compareTo((T) array[i]) > 0)
			max = l;
		if(r < heapSize && array[r].compareTo((T) array[max]) > 0)
			max = r;
		if(max != i){
			Comparable<T> temp = array[i];
			array[i] = array[max];
			array[max] = temp;
			maxHeapify(array, max, heapSize);
		}
	}
	/**
	 * 通过原址排列算法随机排列数组array中的元素, 产生均匀随机排列
	 * @param array
	 */
	public static <T> void randomInReplace(T[] array){
		if(array == null || array.length <= 1) return;
		
		int len = array.length;
		
		for(int i = 0; i < len; i++){
			int randomIndex = RandomUtils.uniform(i, len);
			T tmp = array[i];
			array[i] = array[randomIndex];
			array[randomIndex] = tmp;
		}
	}
	/**
	 * 归并排序递归实现
	 * @param array
	 */
	public static <T> void mergerSortRecursion(Comparable<T>[] array){
		mergerSortRecursion(array, 0, (array == null || array.length < 1) ? 0 : array.length - 1);
	}
	static <T> void mergerSortRecursion(Comparable<T>[] array, int start, int end){
		if(start >= end || array == null || array.length < 1) return; 
		if((end - start) <= THRESHOLD) {
			insertSort(array, start, end);
			return;
		}
		int mid = (start + end) >> 1;
		mergerSortRecursion(array, start, mid);
		mergerSortRecursion(array, mid + 1, end);
		merger(array, start, mid, end);
	}
	@SuppressWarnings("unchecked")
	static <T> void merger(Comparable<T>[] array, int start, int mid, int end){
		int lenLeft = mid - start + 1;
		int lenRight = end - mid;
		Comparable<T>[] arrayLeft = Arrays.copyOfRange(array, start, mid + 1);
		Comparable<T>[] arrayRight = Arrays.copyOfRange(array, mid + 1, end + 1);
		int i, j, k;
		for(i = 0, j = 0, k = start; i < lenLeft && j < lenRight; k++){
			if(arrayLeft[i].compareTo((T) arrayRight[j]) <= 0){
				array[k] = arrayLeft[i++];
			}else{
				array[k] = arrayRight[j++];
			}
		}
		if(i < lenLeft) while(i < lenLeft) array[k++] = arrayLeft[i++];
		if(j < lenRight) while(j < lenRight) array[k++] = arrayRight[j++];
	}
	
	public static <T> void insertSort(Comparable<T>[] array){
		insertSort(array, 0, array == null || array.length < 1 ? 0 : array.length - 1);
	}
	/**
	 * 直接插入排序
	 * @param array
	 * @param start
	 * @param end
	 */
	@SuppressWarnings("unchecked")
	public static <T> void insertSort(Comparable<T>[] array, int start, int end){
		if(start >= end || array == null || array.length < 1) return;
		for(int i = start + 1; i <= end; i++){
			Comparable<T> curr = array[i];
			int j = i - 1;
			while(j >= start && array[j].compareTo((T) curr) > 0){
				array[j + 1] = array[j];
				j--;
			}
			array[j + 1] = curr;
		}
	}
}
