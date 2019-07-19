package jobs.toolkit.distributed.zk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;

import jobs.toolkit.config.Configuration;
import jobs.toolkit.distributed.NodeService;
import jobs.toolkit.logging.Log;
import jobs.toolkit.logging.LogFactory;
import jobs.toolkit.service.ServiceDescriptor;
import jobs.toolkit.support.zookeeper.ZK;
import jobs.toolkit.support.zookeeper.ZKSupporter;

/**
 * 分布式节点的zookeeper实现
 * 
 * @author jobs
 *
 */
public abstract class ZKNodeService extends NodeService{

	private static final Log LOG = LogFactory.getLog(ZKNodeService.class);

	protected final ZKSupporter supporter;

	protected final String zkEnsemblePath;

	protected Map<String, List<ACL>> aclMap = new HashMap<String, List<ACL>>();

	private volatile String connectRi;

	private volatile int seesionTimeout;
	
	protected volatile String zkServiceRootPath;
	
	protected volatile String zkServicePath;
	
	public ZKNodeService(String name, NodeType nodeType) {
		super(name, nodeType);
		this.zkEnsemblePath = "/".concat(name);
		this.supporter = ZK.newSupporter();
	}

	public ZKNodeService(String name){
		this(name, null);
	}

	@Override
	protected void serviceInit() throws Exception{
		Configuration config = this.getConfiguration();
		this.connectRi = config.getString(CFG.NAME_ZK_CONNECTRI);
		this.seesionTimeout = config.getInt(CFG.NAME_ZK_SESSION_TIMEOUT, ZK.SESSION_TIME_OUT);
		this.zkServiceRootPath = "/".concat(config.getString(CFG.NAME_ZK_SERVICE_ROOT, CFG.DEFAULT_ZK_SERVICE_ROOT));
		this.zkServicePath = this.zkServiceRootPath.concat("/").concat(this.getName());
		super.serviceInit();
	}	

	@Override
	protected final void serviceStart() throws Exception {
		//连接到zookeeper server
		this.supporter.prepareConnect().sessionTimeout(this.seesionTimeout).connectSync(this.connectRi);
		//判断当前zkNode的所在的根节点是否存在
		if(this.supporter.prepareCheck().checkSync(this.zkEnsemblePath) == null){
			//根节点不存在则创建
			this.supporter.prepareCreate().
			persistent().
			acl(this.getAclByPath(this.zkEnsemblePath)).
			nodeExistFun((String name) -> LOG.info("Ensemble node[path=%1$s] already exists.", this.zkEnsemblePath)).
			createSync(this.zkEnsemblePath);
		}
		//启动节点
		this.nodeStart();
	}

	protected abstract void nodeStart() throws Exception;
	
	
	@Override
	public void register() throws Exception {
		this.supporter.prepareCreate().
		acl(ZooDefs.Ids.READ_ACL_UNSAFE).
		ephemeral().
		data(this.getServiceDescriptor().toBinary()).
		createAsync(this.zkServicePath);
	}
	
	@Override
	public void reregister() throws Exception {
		final byte[] data = this.getServiceDescriptor().toBinary();
		final int version = this.getServiceDescriptor().getAndIncrementVersion();
		this.supporter.preparePush().
		version(version).
		data(data).
		pushAsync(this.zkServicePath);
	}
	
	@Override
	public void deregister() throws Exception {
		
	}
	@Override
	public ServiceDescriptor discovery(String serviceName) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected void serviceStop() throws Exception {
		this.supporter.close();
	}
	
	private List<ACL> getAclByPath(String path){
		return this.aclMap.getOrDefault(path, ZooDefs.Ids.OPEN_ACL_UNSAFE);
	}
}
