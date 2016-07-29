/**
 * @QiaoJian
 */
package cn.edu.bjtu.qj.graph.component;

import java.util.ArrayList;
import java.util.Map;
/**
 * @author QiaoJian
 *
 */
public class AdjListGraph extends Graph {
	
	
	
	public AdjListGraph(){
		super();
	}
	
	public AdjListGraph(int nodeNum,int edgeNum){
		super(nodeNum, edgeNum);
		init();
	}
	
	public void init(){
		if(nodes == null){
			nodes = new ArrayList<>();
		}
		
		if(edges == null){
			edges = new ArrayList<>();
		}
		
		
	}
	/* (non-Javadoc)
	 * @see cn.edu.bjtu.qj.graph.component.Graph#createNode(int, java.lang.String, java.util.Map)
	 */
	@Override
	public Node createNode(int id, String name, Map<String, Object> attributes) {
		// TODO Auto-generated method stub
		AdjListNode node = new AdjListNode(id,name);
		node.setAttributes(attributes);
		this.addNode(node);
		return node;
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.qj.graph.component.Graph#createEdge(cn.edu.bjtu.qj.graph.component.Node, cn.edu.bjtu.qj.graph.component.Node, boolean)
	 */
	@Override
	public Edge createEdge(Node sourceNode, Node targetNode, boolean isDirected) {
		// TODO Auto-generated method stub
		AdjListEdge edge = new AdjListEdge((AdjListNode)sourceNode, (AdjListNode)targetNode, isDirected);
		this.addEdge(edge);
		return edge;
	}
	
	@Override
	public void addNode(Node node) {
		// TODO Auto-generated method stub
		super.addNode(node);
	}

	@Override
	public void addEdge(Edge edge) {
		// TODO Auto-generated method stub
		super.addEdge(edge);
	}

	
	
}
