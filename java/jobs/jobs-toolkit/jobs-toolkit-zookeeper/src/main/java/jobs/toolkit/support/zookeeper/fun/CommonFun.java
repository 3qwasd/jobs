package jobs.toolkit.support.zookeeper.fun;

@FunctionalInterface
public interface CommonFun {
	public void process(String path, int rc, Object ctx);
}
