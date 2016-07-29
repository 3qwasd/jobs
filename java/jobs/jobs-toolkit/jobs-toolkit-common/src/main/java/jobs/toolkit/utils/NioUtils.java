package jobs.toolkit.utils;

/**
 * 
 * @author jobs
 *
 */
public class NioUtils {
	
	public static final int MAX_BUFFER_SIZE = 0x40000000; // 1G
	
	/**
	 * @param size
	 * @throws IllegalArgumentException if size < 0 || size >= 0x40000000
	 * @return 符合 2^n 并且不小于size
	 */
	public static int roudup(int size) {
		if (size < 0 || size > MAX_BUFFER_SIZE)
			throw new IllegalArgumentException("size is invalid");
		int capacity = 16;
		while ( size > capacity) capacity <<= 1;
		return capacity;
	}
}
