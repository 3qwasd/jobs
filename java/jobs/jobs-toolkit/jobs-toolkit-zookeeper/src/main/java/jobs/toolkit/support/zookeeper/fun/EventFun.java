package jobs.toolkit.support.zookeeper.fun;

import org.apache.zookeeper.WatchedEvent;

@FunctionalInterface
public interface EventFun {
	
	public void process(WatchedEvent e);
}
