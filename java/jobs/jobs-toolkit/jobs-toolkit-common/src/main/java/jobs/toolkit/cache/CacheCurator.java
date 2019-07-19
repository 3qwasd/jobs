package jobs.toolkit.cache;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


import jobs.toolkit.config.Configuration;
import jobs.toolkit.service.BaseService;
import jobs.toolkit.utils.ReflectUtils;

public final class CacheCurator extends BaseService{

	private final Map<String, Cache<?, ?>> caches = new ConcurrentHashMap<String, Cache<?, ?>>();

	private static final CacheCurator instance = new CacheCurator();

	private volatile CacheProvider cacheProvider;

	private CacheCurator() {
		super("CacheCurator");
	}

	public static CacheCurator getInstance(){
		return instance;
	}
	public static <K, V> Cache<K, V> get(String uniqueName){
		return getInstance().getCache(uniqueName);
	}
	public static <K, V> Cache<K, V> getWithCreate(String uniqueName){
		return getInstance().getCacheWithCreate(uniqueName);
	}
	@Override
	protected void initialize() throws Exception {
		Configuration config = this.getConfiguration();
		try{
			this.cacheProvider = ReflectUtils.newInstanceByClassName(config.getString(CFG.NAME_CACHE_PROVIDER));
		}catch(Exception _e){
			throw new RuntimeException("Create cache provider occur some error. Please check the configuration file.", _e);
		}
		Collection<Configuration> cachesConfig = config.getSubConfigs(CFG.NAME_CACHES);
		if(cachesConfig == null || cachesConfig.isEmpty()) return;
		for(Configuration cacheConfig : cachesConfig){
			Cache<?, ?> cache = null;
			if(cacheConfig.containsKey(CFG.NAME_CACHELOADER)){
				try{
					String cacheLoaderClassName = cacheConfig.getString(CFG.NAME_CACHELOADER);
					CacheLoader<?, ?> cacheLoader = ReflectUtils.newInstanceByClassName(cacheLoaderClassName);
					cache = this.cacheProvider.provide(cacheLoader);
				}catch(Exception _e){
					throw new RuntimeException("Create cache of name[" + cacheConfig.getString(CFG.NAME_UNIQUENAME) + "] occur some error.", _e);
				}
			}else{
				cache = this.cacheProvider.provide();
			}
			if(cache != null)
				this.caches.put(cacheConfig.getString(CFG.NAME_UNIQUENAME), cache);
		}
	}
	/**
	 * 从CacheCurator中获取缓存, 如果没有则返回null
	 * @param uniqueName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <K, V> Cache<K, V> getCache(String uniqueName){
		return (Cache<K, V>) this.caches.getOrDefault(uniqueName, null);
	}
	@SuppressWarnings("unchecked")
	public <K, V> Cache<K, V> getCacheWithCreate(String uniqueName){
		if(this.caches.containsKey(uniqueName)){
			return (Cache<K, V>) this.caches.get(uniqueName);
		}
		synchronized (this.caches) {
			if(!this.caches.containsKey(uniqueName)){
				Cache<K, V> cache = this.cacheProvider.provide();
				this.caches.put(uniqueName, cache);
			}
		}
		return (Cache<K, V>) this.caches.get(uniqueName);
	}
}
