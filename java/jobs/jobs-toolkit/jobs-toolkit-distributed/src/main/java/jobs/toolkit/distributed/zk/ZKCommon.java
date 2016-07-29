package jobs.toolkit.distributed.zk;

import org.apache.zookeeper.ZooDefs;
import jobs.toolkit.distributed.Common;

public abstract class ZKCommon extends ZKNodeService implements Common {
	
	protected final String zkNodePath;
		
	public ZKCommon(String serviceName) {
		this(serviceName, NodeType.COMMON);
	}
	
	public ZKCommon(String serviceName, NodeType nodeType) {
		super(serviceName, nodeType);
		zkNodePath = this.zkEnsemblePath.concat("/").concat(this.getNodeId());
	}
	
	protected void nodeRunning(){
		
	}

	@Override
	protected void nodeStart() throws Exception{
		this.supporter.prepareCreate().
		acl(ZooDefs.Ids.CREATOR_ALL_ACL).
		ephemeral().
		okFun( name -> this.nodeRunning()).
		data(this.getNodeDescriptor().toBinary()).
		createAsync(this.zkNodePath);
	}

}
