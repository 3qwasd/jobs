package jobs.toolkit.distributed.zk;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.ZooDefs;

import jobs.toolkit.distributed.Master;
import jobs.toolkit.distributed.NodeService;
import jobs.toolkit.logging.Log;
import jobs.toolkit.logging.LogFactory;

public abstract class ZKMaster extends ZKCommon implements Master {
	
	private final static Log LOG = LogFactory.getLog(ZKMaster.class);
	
	private final String zkMasterPath;
	
	private volatile boolean isLeader = false;
	
	private final String zkWorkersPath;
	
	public ZKMaster(String serviceName) {
		super(serviceName, NodeService.NodeType.MASTER);
		this.zkMasterPath = this.zkEnsemblePath.concat("/").concat(NodeService.NodeType.MASTER.toString());
		this.zkWorkersPath = this.zkEnsemblePath.concat("/").concat(CFG.DEFAULT_ZK_WORKER_ROOT);
	}

	@Override
	protected void nodeStart() {
		
	}
	
	protected void bootstrap(){
		this.supporter.prepareCreate().
		persistent().
		acl(ZooDefs.Ids.OPEN_ACL_UNSAFE).
		createAsync(this.zkWorkersPath);
	}
	protected void runFoLeader() {
		this.supporter.prepareCreate().
		ephemeral().
		retry(false).
		acl(ZooDefs.Ids.READ_ACL_UNSAFE).
		data(this.zkNodePath.getBytes()).
		connLossFun(s -> this.checkLeader()).
		okFun(s -> this.takeLeadship()).
		nodeExistFun(s -> this.takeCandidateship()).
		commonFun((s, rc, ctx) -> {
			LOG.error("Unknow exception when run for leader.", KeeperException.create(Code.get(rc)));
			this.takeCandidateship();
		}).
		createAsync(this.zkMasterPath);
	}
	
	protected void takeLeadship(){
		this.isLeader = true;
	}
	protected void takeCandidateship(){
		this.isLeader = false;
		this.checkLeader();
	}
}
