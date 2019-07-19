package jobs.toolkit.distributed.zk;

import org.apache.zookeeper.ZooDefs;

import jobs.toolkit.distributed.Worker;
import jobs.toolkit.logging.Log;
import jobs.toolkit.logging.LogFactory;

public abstract class ZKWorker extends ZKCommon implements Worker{

	private final static Log LOG = LogFactory.getLog(ZKMaster.class);

	private final String zkWorkersPath;
	
	private final String zkWorkerPath;
	
	public ZKWorker(String serviceName) {
		super(serviceName, NodeType.WORKER);
		this.zkWorkersPath = this.zkEnsemblePath.concat("/").concat(CFG.DEFAULT_ZK_WORKER_ROOT);
		this.zkWorkerPath = this.zkWorkersPath.concat("/").concat(this.getNodeId());
	}
	
	@Override
	protected void nodeStart() {
		
	}

	@Override
	public void register() throws Exception {
		this.supporter.prepareCreate().
		ephemeral().
		acl(ZooDefs.Ids.READ_ACL_UNSAFE).
		data(this.getServiceDescriptor().toBinary()).
		createAsync(this.zkWorkerPath);
	}
	
	@Override
	public void reregister() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deregister() throws Exception {
		
	}
	
	
}
