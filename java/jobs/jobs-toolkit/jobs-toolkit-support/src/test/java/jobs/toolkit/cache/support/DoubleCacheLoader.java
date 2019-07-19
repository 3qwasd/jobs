package jobs.toolkit.cache.support;

import jobs.toolkit.cache.CacheLoader;

public class DoubleCacheLoader implements CacheLoader<Integer, Double> {

	@Override
	public Double load(Integer key) {
		// TODO Auto-generated method stub
		return Math.log10(key);
	}

}
