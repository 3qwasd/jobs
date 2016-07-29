package jobs.toolkit.cache;

import java.util.concurrent.TimeUnit;

public class CFG {
	
	public static final String NAME_CACHE_PROVIDER = "cache.provider";
	
	public static final String NAME_UNIQUENAME = "uniqueName";
	
	public static final String NAME_CACHES = "caches";
	
	public static final String NAME_CACHELOADER = "cacheLoader";
	
	public static final int DEFAULT_CACHE_MAXSIZ = 1000;
	
	public static final long DEFAULT_EXPIRE_DURATION = 10;
	
	public static final TimeUnit DEFAULT_EXPIRE_TIMEUNIT = TimeUnit.MINUTES;
}
