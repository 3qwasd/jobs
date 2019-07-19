package jobs.toolkit.utils;

import java.util.Arrays;

/**
 * 数组相关的工具
 * @author jobs
 *
 */
public class ArrayUtils {
	
	/**
	 * 将多个数组合并成一个
	 * @param first
	 * @param other
	 * @return
	 */
	public  static <T> T[] concatArray(T[] first, T[] ... others){
		
		if(first == null) 
			return null;
		
		if(others == null || others.length < 1)
			return Arrays.copyOf(first, first.length);
		
		int totalLength = first.length;
		
		for(T[] array : others){
			totalLength += array.length;
		}
		
		T[] result = Arrays.copyOf(first, totalLength);
		int offset = first.length;
		for(T[] array : others){
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		
		return result;
	}
}
