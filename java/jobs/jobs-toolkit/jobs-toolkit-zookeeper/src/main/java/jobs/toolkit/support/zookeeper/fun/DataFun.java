package jobs.toolkit.support.zookeeper.fun;

import org.apache.zookeeper.data.Stat;

@FunctionalInterface
public interface DataFun {
	
	public void process(byte[] data, Stat stat);
}
