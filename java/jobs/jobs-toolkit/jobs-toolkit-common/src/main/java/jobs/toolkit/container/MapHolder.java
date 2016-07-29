package jobs.toolkit.container;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class MapHolder<K, V> {
	
	private final Map<K, V> cache = new LinkedHashMap<K, V>();
	
	protected void init(){
		cache.clear();
	}
	
	protected void put(K key, V value){
		this.cache.put(key, value);
	}
	
	@SuppressWarnings("unchecked")
	protected <T extends V> T get(K key, T defaultValue){
		return (T) this.cache.getOrDefault(key, defaultValue);
	}
	@SuppressWarnings("unchecked")
	protected <T extends V> T get(K key){
		return (T) this.cache.get(key);
	}
}
