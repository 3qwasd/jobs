package jobs.toolkit.cache;

import java.util.Map;

/**
 * 缓存提供接口
 * @author jobs
 *
 */
public interface CacheProvider {
	
	public <K, V> AutoLoadingCache<K, V> provide(CacheLoader<K, V> loader, Map<String, ?> config);
	
	public <K, V> AutoLoadingCache<K, V> provide(CacheLoader<K, V> loader);
	
	public <K, V> Cache<K, V> provide(Map<String, ?> config);
	
	public <K, V> Cache<K, V> provide();
	
}
