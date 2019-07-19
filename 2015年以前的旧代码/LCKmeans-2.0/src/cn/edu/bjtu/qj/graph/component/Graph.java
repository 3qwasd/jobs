/**
 * @QiaoJian
 */
package cn.edu.bjtu.qj.graph.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author QiaoJian
 *
 */
public abstract class Graph extends GraphElement{
		
	List<Node> nodes;
	
	Collection<Edge> edges;
	
	boolean isDirected;
	
	public int nodeNum;
	
	public int edgeNum;
	
	public Graph() {
		super();
		// TODO Auto-generated constructor stub
		this.nodes = new ArrayList<Node>();
		this.edges = new HashSet<Edge>();
	}
	public Graph(int nodeNum,int edgeNum) {
		super();
		// TODO Auto-generated constructor stub
		this.nodeNum = nodeNum;
		this.edgeNum = edgeNum;
		this.nodes = new ArrayList<Node>();
		this.edges = new HashSet<Edge>();
	}
	public abstract Node createNode(int id,String name,Map<String,Object> attribute);
	public abstract Edge createEdge(Node sourceNode,Node targetNode,boolean isDirected);
	public void addNode(Node node){
		this.nodes.add(node);
	}
	public void addEdge(Edge edge){
		this.edges.add(edge);
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public Collection<Edge> getEdges() {
		return edges;
	}

	public boolean isDirected() {
		return isDirected;
	}

	public void setDirected(boolean isDirected) {
		this.isDirected = isDirected;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public void setEdges(Collection<Edge> edges) {
		this.edges = edges;
	}
	public int getNodeNum() {
		return nodeNum;
	}
	public void setNodeNum(int nodeNum) {
		this.nodeNum = nodeNum;
	}
	public int getEdgeNum() {
		return edgeNum;
	}
	public void setEdgeNum(int edgeNum) {
		this.edgeNum = edgeNum;
	}
	public Node getNode(int index){
		if(index<nodes.size()&&index>=0){
			return this.nodes.get(index);
		}
		return null;
	}
}
