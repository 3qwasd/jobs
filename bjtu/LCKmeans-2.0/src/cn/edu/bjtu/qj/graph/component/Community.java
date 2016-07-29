/**
 * @QiaoJian
 */
package cn.edu.bjtu.qj.graph.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.edu.bjtu.qj.graph.algorithms.datastrut.IndexValue;

/**
 * @author QiaoJian
 *
 */
public class Community extends Graph{
	
	AdjListNode centerNode;
	List<IndexValue<Double>> pageRanks;
	
	/**
	 * 
	 */
	public Community() {
		// TODO Auto-generated constructor stub
		super();
	}
	public Community(AdjListNode centerNode) {
		// TODO Auto-generated constructor stub
		super();
		this.pageRanks = new ArrayList<>();
		this.centerNode = centerNode;
		this.addNode(centerNode);
		
	}
	
	public void initialize(){
		
		this.edges.clear();
		this.nodes.clear();
		this.pageRanks.clear();
		if(this.attributes!=null){
			this.attributes.clear();
		}
		centerNode = null;
		this.nodeNum = 0;
		this.edgeNum = 0;
	}

	public AdjListNode getCenterNode() {
		return centerNode;
	}

	public void setCenterNode(AdjListNode centerNode) {
		this.centerNode = centerNode;
		this.addNode(centerNode);
	}
	
	
	@Override
	public void addNode(Node node) {
		// TODO Auto-generated method stub
		super.addNode(node);
		pageRanks.add((IndexValue<Double>) node.getAttribute("pagerank"));
	}
	/* (non-Javadoc)
	 * @see cn.edu.bjtu.qj.graph.component.Graph#createNode(int, java.lang.String, java.util.Map)
	 */
	@Override
	public Node createNode(int id, String name, Map<String, Object> attribute) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.qj.graph.component.Graph#createEdge(cn.edu.bjtu.qj.graph.component.Node, cn.edu.bjtu.qj.graph.component.Node, boolean)
	 */
	@Override
	public Edge createEdge(Node sourceNode, Node targetNode, boolean isDirected) {
		//  Auto-generated method stub
		return null;
	}
	public List<IndexValue<Double>> getPageRanks() {
		return pageRanks;
	}
	
}
