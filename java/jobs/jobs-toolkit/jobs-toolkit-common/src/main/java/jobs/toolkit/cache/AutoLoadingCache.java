package jobs.toolkit.cache;

/**
 * 能够自动加载的cache
 * @author jobs
 *
 * @param <K>
 * @param <V>
 */
public interface AutoLoadingCache<K, V> extends Cache<K, V>{
	
	/**
	 * 加载新的值到缓存中
	 * @param k
	 */
	public void refresh(K k);
}
