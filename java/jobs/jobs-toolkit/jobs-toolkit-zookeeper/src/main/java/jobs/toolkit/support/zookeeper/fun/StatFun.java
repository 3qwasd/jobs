package jobs.toolkit.support.zookeeper.fun;

import org.apache.zookeeper.data.Stat;

@FunctionalInterface
public interface StatFun {
	
	public void process(Stat stat);
}
