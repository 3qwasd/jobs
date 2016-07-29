package jobs.toolkit.cache.support;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class GuavaCacheTest {
	
	@Test
	public void LoadingCacheTest(){
		LoadingCache<String, String> cache = CacheBuilder.newBuilder()
				.maximumSize(1000)
				.expireAfterAccess(1000, TimeUnit.MINUTES)
				.build(new CacheLoader<String, String>(){
					@Override
					public String load(String key) throws Exception {
						// TODO Auto-generated method stub
						return null;
					}
					
				});

	}
}
