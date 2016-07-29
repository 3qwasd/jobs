package jobs.toolkit.cache;

public interface CacheLoader<K, V>{
	
	public V load(K key);
}
