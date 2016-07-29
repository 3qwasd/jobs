package jobs.toolkit.control;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import jobs.toolkit.logging.Log;
import jobs.toolkit.logging.LogFactory;

class QPSControllerAssistant extends ScheduledThreadPoolExecutor{
	
	private final Log LOG = LogFactory.getLog(QPSControllerAssistant.class);
		
	private final Map<String, QPSController> cache = new ConcurrentHashMap<String, QPSController>();
	
	private static final class InstanceHolder{
		private static final QPSControllerAssistant instance = new QPSControllerAssistant();
	}
	
	private QPSControllerAssistant(){
		super(1);
	}
	
	public static QPSControllerAssistant getInstance(){
		return InstanceHolder.instance;
	}

	public void close(){
		this.shutdownNow();
		cache.forEach((k, q) ->{ try {q.stop();} catch (Exception _e) {}});
		cache.clear();
	}
}
