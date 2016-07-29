package jobs.toolkit.support.guava;

import java.util.Map;

import jobs.toolkit.cache.Cache;

public class GuavaCache<K, V> implements Cache<K, V> {
	
	private final com.google.common.cache.Cache<K, V> cache;
	
	
	public GuavaCache(com.google.common.cache.Cache<K, V> cache) {
		this.cache = cache;
	}
	
	@Override
	public V get(K k){
		// TODO Auto-generated method stub
		return this.cache.getIfPresent(k);
	}

	@Override
	public Map<K, V> getAllPresent(Iterable<K> keys) {
		// TODO Auto-generated method stub
		return this.cache.getAllPresent(keys);
	}

	@Override
	public void put(K k, V v) {
		// TODO Auto-generated method stub
		this.cache.put(k, v);
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
		this.cache.cleanUp();
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return (int) this.cache.size();
	}

}
