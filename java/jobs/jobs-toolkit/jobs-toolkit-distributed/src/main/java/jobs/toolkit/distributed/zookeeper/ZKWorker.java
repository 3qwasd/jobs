package jobs.toolkit.distributed.zookeeper;

import jobs.toolkit.distributed.Worker;
import jobs.toolkit.node.NodeType;
import jobs.toolkit.support.zookeeper.ZK;

public abstract class ZKWorker extends ZKNode implements Worker {
	
	protected volatile String workerDirNodePath;
	
	protected volatile String workerNodePath;
		
	public ZKWorker(String name) {
		super(name, NodeType.WORKER);
	}

	@Override
	protected void nodeInit() throws Exception {
		super.nodeInit();
		String workerDirName = this.getConfiguration().getString(CFG.NAME_ZK_WORKER_ROOT, CFG.DEFAULT_ZK_WORKER_ROOT);
		this.workerDirNodePath = ZK.JOIN_PATH(this.clusterNodePath, workerDirName);
		this.workerNodePath = ZK.JOIN_PATH(this.workerDirNodePath, this.getNodeId());
	}

	@Override
	protected void nodeStart() throws Exception {
		super.nodeStart();
		this.supporter.prepareCheck().
		nodeExistFun(s -> this.registerWorker()).
		nodeCreatedFun(e -> this.registerWorker()).
		checkAsync(this.workerDirNodePath);
	}

	@Override
	protected void nodeStop() throws Exception {
		super.nodeStop();
	}

	@Override
	public void registerWorker() {
		
	}
}
