package jobs.toolkit.node;

import jobs.toolkit.service.BaseService;
import jobs.toolkit.utils.RandomUtils;

public abstract class Node extends BaseService implements Peer{
	
	private volatile String clusterName;
	
	private volatile String nodeId;
	
	private volatile String nodeName;
	
	private final NodeType nodeType;
	
	private volatile NodeDescriptor nodeDescriptor;
	
	public Node(String name, NodeType type) {
		super(name);
		this.nodeType = type;
	}
	
	@Override
	protected final void initialize() throws Exception {
		
		this.clusterName = this.getConfiguration().getString(CFG.NAME_CLUSTER_NAME, this.getName());
		this.nodeId = this.generateNodeId();
		this.nodeName = this.clusterName.concat("-").concat(this.nodeType.name()).concat("-").concat(nodeId);
		this.nodeDescriptor = this.initNodeDescriptor();
		if(this.nodeDescriptor == null) throw new NullPointerException("Node descriptor must not null.");
		this.nodeInit();
	}
	@Override
	protected final void startup() throws Exception {
		this.nodeStart();
		this.register();
	}

	@Override
	protected final void shutdown() throws Exception {
		this.deregister();
		this.nodeStop();		
	}
	
	protected String generateNodeId(){
		return Integer.toHexString(RandomUtils.uniform(Integer.MAX_VALUE));
	}
	
	protected abstract void nodeInit() throws Exception;
	protected abstract void nodeStart() throws Exception;
	protected abstract void nodeStop() throws Exception;
	/**
	 * 注册节点
	 * @throws Exception
	 */
	protected abstract void register() throws Exception;
	/**
	 * 取消注册节点
	 * @throws Exception
	 */
	protected abstract void deregister() throws Exception;
	/**
	 * 重新注册节点
	 * @throws Exception
	 */
	protected abstract void reregister() throws Exception;
	/**
	 * 初始化节点描述
	 * @return
	 */
	protected abstract NodeDescriptor initNodeDescriptor();

	public final NodeDescriptor getNodeDescriptor() {
		// TODO Auto-generated method stub
		return this.nodeDescriptor;
	}

	public final String getNodeName() {
		// TODO Auto-generated method stub
		return this.nodeName;
	}

	public final String getNodeId() {
		// TODO Auto-generated method stub
		return this.nodeName;
	}
	
	public final String getClusterName(){
		return this.clusterName;
	}
}
