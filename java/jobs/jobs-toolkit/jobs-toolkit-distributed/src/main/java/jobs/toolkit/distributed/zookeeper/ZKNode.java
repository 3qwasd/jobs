package jobs.toolkit.distributed.zookeeper;

import jobs.toolkit.config.Configuration;
import jobs.toolkit.node.Node;
import jobs.toolkit.node.NodeDescriptor;
import jobs.toolkit.node.NodeType;
import jobs.toolkit.support.zookeeper.ZK;
import jobs.toolkit.support.zookeeper.ZKSupporter;

public class ZKNode extends Node {
	
	protected volatile ZKSupporter supporter;

	protected volatile String clusterNodePath;
	
	protected volatile String thisNodePath;
	
	private volatile String hostRi;

	private volatile int seesionTimeout;
	
	public ZKNode(String name, NodeType type) {
		super(name, type);
	}

	@Override
	protected void nodeInit() throws Exception {
		this.supporter = ZK.newSupporter();
		Configuration config = this.getConfiguration();
		this.hostRi = config.getString(CFG.NAME_ZK_CONNECTRI);
		this.seesionTimeout = config.getInt(CFG.NAME_ZK_SESSION_TIMEOUT, ZK.SESSION_TIME_OUT);
		this.clusterNodePath = ZK.JOIN_PATH(this.getClusterName());
		this.thisNodePath = ZK.JOIN_PATH(this.getClusterName(), this.getNodeName());
	}

	@Override
	protected void nodeStart() throws Exception {
		this.supporter.prepareConnect().sessionTimeout(this.seesionTimeout).connectSync(this.hostRi);
		this.supporter.prepareCreate().persistent().createSync(this.clusterNodePath);
	}

	@Override
	protected void nodeStop() throws Exception {
		// TODO Auto-generated method stub
		this.supporter.close();
	}

	@Override
	protected void register() throws Exception {
		this.supporter.prepareCreate().
		ephemeral().
		data(this.getNodeDescriptor().toBinary()).
		createSync(this.thisNodePath);
	}

	@Override
	protected void deregister() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void reregister() throws Exception {
		
	}

	@Override
	protected NodeDescriptor initNodeDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}


}
