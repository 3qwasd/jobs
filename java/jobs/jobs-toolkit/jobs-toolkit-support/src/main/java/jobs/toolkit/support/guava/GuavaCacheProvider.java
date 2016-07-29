package jobs.toolkit.support.guava;

import java.util.Map;

import jobs.toolkit.cache.CFG;
import jobs.toolkit.cache.Cache;
import jobs.toolkit.cache.AutoLoadingCache;
import jobs.toolkit.cache.CacheLoader;
import jobs.toolkit.cache.CacheProvider;

public class GuavaCacheProvider implements CacheProvider{
	
	private final com.google.common.cache.CacheBuilder<Object, Object> defaultBuilder;
	
	
	public GuavaCacheProvider() {
		this.defaultBuilder =  com.google.common.cache.CacheBuilder.newBuilder()
				.maximumSize(CFG.DEFAULT_CACHE_MAXSIZ)
				.expireAfterAccess(CFG.DEFAULT_EXPIRE_DURATION, CFG.DEFAULT_EXPIRE_TIMEUNIT);
	}

	@Override
	public <K, V> AutoLoadingCache<K, V> provide(CacheLoader<K, V> loader) {
		// TODO Auto-generated method stub
		com.google.common.cache.LoadingCache<K, V> loadingCache = this.defaultBuilder.build(new com.google.common.cache.CacheLoader<K, V>() {
			@Override
			public V load(K key) throws Exception {
				// TODO Auto-generated method stub
				return loader.load(key);
			}
		});
		return new GuavaAutoLoadingCache<>(loadingCache);
	}
	
	@Override
	public <K, V> Cache<K, V> provide() {
		com.google.common.cache.Cache<K, V> cache = this.defaultBuilder.build();
		return new GuavaCache<>(cache);
	}
	
	@Override
	public <K, V> AutoLoadingCache<K, V> provide(CacheLoader<K, V> loader, Map<String, ?> config) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public <K, V> Cache<K, V> provide(Map<String, ?> config) {
		// TODO Auto-generated method stub
		return null;
	}
}
