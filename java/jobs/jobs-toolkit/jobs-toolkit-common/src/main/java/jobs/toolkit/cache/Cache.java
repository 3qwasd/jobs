package jobs.toolkit.cache;

import java.util.Map;

/**
 * 缓存
 * @author jobs
 *
 */
public interface Cache<K, V> {
	
	/**
	 * 通过key查找值
	 * @param k
	 * @return
	 * @throws Exception
	 */
	public V get(K k);
	/**
	 * 通过keys查找值的集合，只会返回值存在的项的集合
	 * @param keys
	 * @return
	 */
	public Map<K, V> getAllPresent(Iterable<K> keys);
	/**
	 * 写入值到缓存中
	 * @param k
	 * @param v
	 */
	public void put(K k, V v);
	/**
	 * 清空缓存
	 */
	public void cleanUp();
	/**
	 * 返回缓存的size
	 * @return
	 */
	public int size();
}
