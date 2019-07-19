package jobs.toolkit.leetcode;

/**
 * 线性结构相关工具
 * @author jobs
 *
 */
public class Liner {
	
	/**
	 * 移除有序数组中的重复元素
	 * @param <T>
	 * @param array
	 * return 新的数组长度
	 */
	public static <T extends Comparable<T>> int removeDuplicates(T[] array){
		if(array == null || array.length < 1) return 0;
		
		int index = 0;
		
		for(int i = 1; i < array.length; i++){
			if(array[index].compareTo(array[i]) != 0)
				array[++index] = array[i];
		}
		return index + 1;
	}
}
