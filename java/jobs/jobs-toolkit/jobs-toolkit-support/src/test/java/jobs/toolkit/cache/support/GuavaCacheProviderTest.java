package jobs.toolkit.cache.support;

import org.junit.Test;

import jobs.toolkit.cache.Cache;
import jobs.toolkit.cache.CacheLoader;
import jobs.toolkit.cache.CacheProvider;
import jobs.toolkit.support.guava.GuavaCacheProvider;
import junit.framework.Assert;

public class GuavaCacheProviderTest {
	
	@Test
	public void test(){
		
		CacheProvider cacheProvider = new GuavaCacheProvider();
		
		Cache<String, String> cache = cacheProvider.provide(new CacheLoader<String, String>() {

			@Override
			public String load(String key) {
				// TODO Auto-generated method stub
				return key;
			}
		});
		
		try {
			String v = cache.get("x");
			System.out.println(v);
			Assert.assertTrue(v == "x");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
