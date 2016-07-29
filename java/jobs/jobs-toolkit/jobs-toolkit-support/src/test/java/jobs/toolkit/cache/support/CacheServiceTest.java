package jobs.toolkit.cache.support;


import org.junit.Before;
import org.junit.Test;

import jobs.toolkit.cache.Cache;
import jobs.toolkit.cache.CacheCurator;
import jobs.toolkit.support.yaml.YamlConfiguration;
import jobs.toolkit.utils.ConfigFileUtils;;


public class CacheServiceTest {
	
	CacheCurator cacheCurator;
	
	@Before
	public void init() throws Exception{
		cacheCurator = CacheCurator.getInstance();
		YamlConfiguration configuration = new YamlConfiguration();
		
		configuration.parse(ConfigFileUtils.loadConfigFile("jobs/toolkit/resources/cache.yaml"));
		cacheCurator.init(configuration);
		Cache<String, String> cache = cacheCurator.getCache("resource");
		System.out.println(cache.get("system"));
		Cache<Integer, Double> doubleCache = cacheCurator.getCache("doubleCache");
		if(doubleCache != null){
			System.out.println(doubleCache.get(1000));
		}
		
	}
	
	
	@Test
	public void guavaApiTest(){
		
	}
}
