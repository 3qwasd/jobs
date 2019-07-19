package jobs.toolkit.support.guava;

import java.util.concurrent.ExecutionException;

import jobs.toolkit.cache.AutoLoadingCache;
import jobs.toolkit.logging.Log;
import jobs.toolkit.logging.LogFactory;

/**
 * cache的实现类, 使用Guava的cache实现
 * @author jobs
 *
 */
public class GuavaAutoLoadingCache<K, V> extends GuavaCache<K, V> implements AutoLoadingCache<K, V>{
	
	protected Log LOG = LogFactory.getLog(this.getClass());
	
	private final com.google.common.cache.LoadingCache<K, V> loadingCache;
	
	public GuavaAutoLoadingCache(com.google.common.cache.LoadingCache<K, V> cache) {
		super(cache);
		this.loadingCache = cache;
	}
	
	
	
	@Override
	public V get(K k) {
		try {
			return this.loadingCache.get(k);
		} catch (ExecutionException _e) {
			LOG.error(String.format("AutoLoadingCache loading value of key[%1$s] failed because of some error.", k), _e);
		}	
		return null;
	}



	@Override
	public void refresh(K k) {
		this.loadingCache.refresh(k);
	}

}
