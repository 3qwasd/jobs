package jobs.toolkit.distributed.zk;

public class CFG {
	public static final String NAME_ZK_CONNECTRI = "zk.connect.RI";
	public static final String NAME_ZK_SESSION_TIMEOUT = "zk.session.timeout";
	public static final String NAME_ZK_SERVICE_ROOT = "zk.service.root";
	public static final String NAME_ZK_WORKER_ROOT = "zk.node.worker.root";
	public static final String DEFAULT_ZK_SERVICE_ROOT = "jobs-services";
	public static final String DEFAULT_ZK_WORKER_ROOT = "works";
	public static final int DEFAULT_SESSION_TIMEOUT = 15 * 1000;
}
