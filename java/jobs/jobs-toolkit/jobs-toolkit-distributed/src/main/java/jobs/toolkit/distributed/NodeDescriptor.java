package jobs.toolkit.distributed;

import java.net.InetAddress;

import jobs.toolkit.distributed.NodeService.NodeType;
import jobs.toolkit.marshal.BinaryMarshaller;
import jobs.toolkit.marshal.JsonMarshaller;

public class NodeDescriptor implements JsonMarshaller, BinaryMarshaller{
	
	private volatile String nodeName = "";
	
	private volatile String nodeId = "";
	
	private volatile NodeType nodeTyp = NodeType.COMMON;
	
	private volatile InetAddress nodeAddress;
	
	public String getNodeName() {
		return nodeName;
	}

	public NodeDescriptor setNodeName(String nodeName) {
		this.nodeName = nodeName;
		return this;
	}

	public String getNodeId() {
		return nodeId;
	}

	public NodeDescriptor setNodeId(String nodeId) {
		this.nodeId = nodeId;
		return this;
	}

	public NodeType getNodeTyp() {
		return nodeTyp;
	}

	public NodeDescriptor setNodeTyp(NodeType nodeTyp) {
		this.nodeTyp = nodeTyp;
		return this;
	}

	public InetAddress getNodeAddress() {
		return nodeAddress;
	}

	public NodeDescriptor setNodeAddress(InetAddress nodeAddress) {
		this.nodeAddress = nodeAddress;
		return this;
	}

	@Override
	public byte[] toBinary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void fromBinary(byte[] binary) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void fromJson(String json) {
		// TODO Auto-generated method stub
		
	}

}
