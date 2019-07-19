package jobs.toolkit.distributed;

import jobs.toolkit.service.DistributedService;
import jobs.toolkit.utils.RandomUtils;


/**
 * 分布式集群节点
 * @author jobs
 *
 */
public abstract class NodeService extends DistributedService{
		
	/**
	 * 节点类型
	 * @author jobs
	 *
	 */
	public enum NodeType{

		MASTER(0, "master"), WORKER(1, "worker"), COMMON(2, "common");

		private int code;

		private String name;

		private NodeType(int code, String name){
			this.code = code;
			this.name = name;
		}
		public int getCode(){
			return this.code;
		}
		@Override
		public String toString() {
			return this.name;
		}
	}
	
	private final String nodeId;
	
	private final String nodeName;
	
	private volatile NodeType nodeType = NodeType.COMMON;
	
	private volatile NodeDescriptor nodeDescriptor;
	
	public NodeService(String name, NodeType nodeType) {
		super(name);
		// TODO Auto-generated constructor stub
		this.nodeId = generateNodeId();
		if(nodeType != null) this.nodeType = nodeType;
		this.nodeName = this.getName().concat("-").concat(this.nodeType.name()).concat("-").concat(nodeId);
	}
	public NodeService(String name) {
		this(name, null);
	}
	
	public NodeDescriptor initNodeDescriptor(){
		return new NodeDescriptor().setNodeName(this.nodeName).setNodeId(this.nodeId).setNodeTyp(this.nodeType);
	}
	
	@Override
	protected void serviceInit() throws Exception {
		this.nodeDescriptor = this.initNodeDescriptor();
	}
	public String generateNodeId(){
		return Integer.toHexString(RandomUtils.uniform(Integer.MAX_VALUE));
	}
	
	public final String getNodeId() {
		return nodeId;
	}
	public final String getNodeName() {
		return nodeName;
	}
	public final NodeType getNodeType() {
		return nodeType;
	}
	public final NodeDescriptor getNodeDescriptor(){
		return this.nodeDescriptor;
	}
}
