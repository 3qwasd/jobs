/**
 * @QiaoJian
 */
package cn.edu.bjtu.qj.graph.algorithms;


import java.util.Collection;
import java.util.Iterator;
import java.util.List;






import cn.edu.bjtu.qj.graph.algorithms.datastrut.MQueue;
import cn.edu.bjtu.qj.graph.component.AdjListEdge;
import cn.edu.bjtu.qj.graph.component.AdjListNode;
import cn.edu.bjtu.qj.graph.component.Edge;
import cn.edu.bjtu.qj.graph.component.Node;
import cn.edu.bjtu.qj.graph.component.NodeProcesser;

/**
 * @author QiaoJian
 *
 */
public class BreadthFirstSearch extends Algorithms{
	public static final String VISTOR_MARK = "isVistor";
	public static final String SUBGRAPH_NUM = "SUBGRAPH_NUM";
	private boolean ignoreDirected = false;
	public void execute(NodeProcesser nodeProcesser){
		Node node = null;
		MQueue<Node> queue = new MQueue<Node>();
		int num = 0;
		for(Iterator<Node> nodes = graph.getNodes().iterator();nodes.hasNext();){
			node = nodes.next();
			boolean mark = node.getAttribute(VISTOR_MARK)!=null?(boolean)node.getAttribute(VISTOR_MARK):false;
			if(!mark){
				num++;
				node.putAttribute(VISTOR_MARK, true);
				node.putAttribute(SUBGRAPH_NUM, num);
				nodeProcesser.process(node);
				queue.addLast(node);
				while(!queue.isEmpty()){
					Node sourceNode = queue.removeFirst();
					Collection<AdjListEdge> edges = null;
					if((!graph.isDirected())||(graph.isDirected()&&ignoreDirected)){
						edges = ((AdjListNode)sourceNode).getUndirectEdges();
					}else{
						edges = ((AdjListNode)sourceNode).getOutEdges();
					}
					for(AdjListEdge edge:edges){
						Node targetNode = this.getEdgeOtherNode(sourceNode, edge);
						if(targetNode!=null){
							mark = targetNode.getAttribute(VISTOR_MARK)!=null?(boolean)node.getAttribute(VISTOR_MARK):false;
							if(!mark){
								queue.addLast(targetNode);
								targetNode.putAttribute(VISTOR_MARK, true);
								targetNode.putAttribute(SUBGRAPH_NUM, num);
								nodeProcesser.process(targetNode);
							}
						}
					}
				}
			}
		}
	}
	public Node getEdgeOtherNode(Node oneNode,Edge edge){
		Node otherNode = null;
		if(oneNode == edge.getSourceNode()){
			otherNode = edge.getTargetNode();
		}else{
			otherNode = edge.getSourceNode();
		}
		return otherNode;
	}
}
