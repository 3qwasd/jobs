package jobs.toolkit.container;

import java.util.HashMap;
import java.util.Map;

/**
 * 本地线程容器, 封闭在线程内的Map
 * @author jobs
 *
 */
public abstract class ThreadLocalMapHolder<K, V> {
	
	private final ThreadLocal<Map<K, V>> cache = new ThreadLocal<Map<K, V>>();
	
	protected void init(){
		Map<K, V> map = this.cache.get();
		if(map == null){
			map = new HashMap<K, V>();
			this.cache.set(map);
		}else{
			map.clear();
		}
	}
	
	protected void put(K key, V value){
		if(this.cache.get() == null){
			this.init();
		}
		this.cache.get().put(key, value);
	}
	
	@SuppressWarnings("unchecked")
	protected <T extends V> T get(K key, T defaultValue){
		return (T) this.cache.get().getOrDefault(key, defaultValue);
	}
	@SuppressWarnings("unchecked")
	protected <T extends V> T get(K key){
		return (T) this.cache.get().get(key);
	}
}
