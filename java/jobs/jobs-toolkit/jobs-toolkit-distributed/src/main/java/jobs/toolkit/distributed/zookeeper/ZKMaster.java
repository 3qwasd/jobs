package jobs.toolkit.distributed.zookeeper;

import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.Code;

import jobs.toolkit.distributed.DistributedServiceDescriptor;
import jobs.toolkit.distributed.Master;
import jobs.toolkit.node.NodeType;
import jobs.toolkit.support.zookeeper.ZK;

public abstract class ZKMaster extends ZKNode implements Master{

	protected volatile String masterNodePath;
	
	protected volatile String workerDirNodePath;
	
	protected volatile boolean isLeader = false;
	
	protected final List<DistributedServiceDescriptor> workList = new ArrayList<DistributedServiceDescriptor>();
	
	public ZKMaster(String name) {
		super(name, NodeType.MASTER);
	}

	@Override
	protected void nodeInit() throws Exception {
		super.nodeInit();
		String masterNodeName = this.getConfiguration().getString(CFG.NAME_ZK_MASTER_NAME, NodeType.MASTER.name());
		this.masterNodePath = ZK.JOIN_PATH(this.clusterNodePath, masterNodeName);
		String workerDirName = this.getConfiguration().getString(CFG.NAME_ZK_WORKER_ROOT, CFG.DEFAULT_ZK_WORKER_ROOT);
		this.workerDirNodePath = ZK.JOIN_PATH(this.clusterNodePath, workerDirName);
	}

	@Override
	protected void nodeStart() throws Exception {
		super.nodeStart();
		this.supporter.prepareCreate().persistent().createAsync(this.workerDirNodePath);
		this.runFoLeader();
	}

	@Override
	protected void nodeStop() throws Exception {
		super.nodeStop();
	}

	protected void runFoLeader() {
		
		this.supporter.prepareCreate().
		ephemeral().
		retry(false).
		data(this.thisNodePath.getBytes()).
		connLossFun( s -> this.checkLeader()).
		okFun(s -> this.takeLeadship()).
		nodeExistFun(s -> this.takeCandidateship()).
		commonFun((s, rc, ctx) -> {
			LOG.error("Unknow exception when run for leader.", KeeperException.create(Code.get(rc)));
			this.takeCandidateship();
		}).
		createAsync(this.masterNodePath);
	}
	 public void checkLeader(){
		this.supporter.prepareCheck().
		okFun( s -> this.takeCandidateship()).
		noNodeFun(s -> this.runFoLeader()).
		checkAsync(this.masterNodePath);
	}
	protected void takeLeadship(){
		this.isLeader = true;
	}
	protected void takeCandidateship(){
		this.isLeader = false;
		this.supporter.prepareCheck().
		nodeDeletedFun(e -> this.runFoLeader()).
		noNodeFun(s -> this.runFoLeader()).
		checkAsync(this.masterNodePath);
	}

	@Override
	public void runForLeader() {
		
	}

	@Override
	public void takeLeadShip() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void takeCandidateShip() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void watchLeader() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleUnKnowError() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void watchWorkers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<DistributedServiceDescriptor> getWorkerList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void refreshLocalWorkerList() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void initLocalWorkerList() {
		// TODO Auto-generated method stub
		
	}
}
