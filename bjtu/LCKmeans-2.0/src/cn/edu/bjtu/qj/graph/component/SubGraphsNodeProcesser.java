/**
 * @QiaoJian
 */
package cn.edu.bjtu.qj.graph.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.edu.bjtu.qj.graph.algorithms.BreadthFirstSearch;

/**
 * @author QiaoJian
 *
 */
public class SubGraphsNodeProcesser implements NodeProcesser{
	
	Map<Integer,List<Node>> subGraphNodes = new HashMap<Integer,List<Node>>();
	List<Graph> subGraphs;
	public static final String EDGE_MARK = "EDGE_MARK";
	/* (non-Javadoc)
	 * @see cn.edu.bjtu.qj.graph.component.NodeProcesser#process()
	 */
	@Override
	public void process(Node node) {
		// TODO Auto-generated method stub
		Integer subGraphNum = (Integer) node.getAttribute(BreadthFirstSearch.SUBGRAPH_NUM);
		List<Node> nodes =subGraphNodes.get(subGraphNum);
		if(nodes == null){
			nodes = new ArrayList<Node>();
			subGraphNodes.put(subGraphNum,nodes);
		}
		nodes.add(node);
		System.out.println("id="+node.getId()+",name="+node.getName()+","
				+ "value="+node.getAttribute("value")+",subGraphNum="
				+ node.getAttribute(BreadthFirstSearch.SUBGRAPH_NUM));
	}
	public List<Graph> getSubGraphs(boolean isDirected){
		subGraphs = new ArrayList<Graph>();
		for(Integer i:subGraphNodes.keySet()){
			List<Node> nodes = subGraphNodes.get(i);
			Graph graph = new AdjListGraph();
			graph.setNodes(nodes);
			Collection<Edge> edges = new HashSet<Edge>();
			for(Node node:nodes){
				Iterator<? extends Edge> edgeIter = null;
				if(isDirected){
					edgeIter = ((AdjListNode)node).getOutEdges().iterator();
				}else{
					edgeIter = ((AdjListNode)node).getUndirectEdges().iterator();
				}
				for(;edgeIter.hasNext();){
					AdjListEdge edge = (AdjListEdge) edgeIter.next();
					boolean mark =edge.getAttribute(EDGE_MARK)!=null?(boolean)edge.getAttribute(EDGE_MARK):false;
					if(!mark){
						edges.add(edge);
						edge.putAttribute(EDGE_MARK, true);
					}
				}
			}
			graph.setEdges(edges);
			subGraphs.add(graph);
		}
		return subGraphs;
	}
	public Map<Integer, List<Node>> getSubGraphNodes() {
		return subGraphNodes;
	}
}
