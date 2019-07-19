/**
 * @QiaoJian
 */
package cn.edu.bjtu.qj.graph.algorithms;

import java.util.List;

import cn.edu.bjtu.qj.graph.algorithms.datastrut.MQueue;
import cn.edu.bjtu.qj.graph.component.AdjListEdge;
import cn.edu.bjtu.qj.graph.component.AdjListNode;
import cn.edu.bjtu.qj.graph.component.Graph;
import cn.edu.bjtu.qj.graph.component.MGraph;
import cn.edu.bjtu.qj.graph.component.Node;

/**
 * @author QiaoJian
 *
 */
public class AverageShortPath extends Algorithms{

	public static final String VISTOR_MARK = "isVistor";
	
	MGraph graph;

	/**
	 * 
	 */
	public AverageShortPath() {
		// TODO Auto-generated constructor stub
	}
	public AverageShortPath(MGraph graph) {
		// TODO Auto-generated constructor stub
		this.graph = graph;
	}
	@Override
	public void attachGraph(Graph graph) {
		// TODO Auto-generated method stub
		super.attachGraph(graph);
		this.graph = (MGraph) graph;
	}
	/**
	 * 计算有向无权图的平均最短路径,平均路径长度
	 */
	public void duwGraph(){
		List<Node> nodes = graph.getNodes();
		int[][] distances = new int[graph.getNodeNum()][graph.getNodeNum()];
		long sumDis = 0;
		MQueue<AdjListNode> queue = new MQueue<>();
		for(int i=0;i<graph.getNodeNum();i++){
			boolean[] isVistor = new boolean[graph.getNodeNum()];
			queue.offer((AdjListNode) nodes.get(i));
			while(!queue.isEmpty()){
				AdjListNode node = queue.poll();
				
				if(isVistor[node.getId()-1]){
					continue;
				}
				List<AdjListEdge> edges = null;
				if(graph.isDirected()){
					edges = node.getOutEdges();
				}else{
					edges = node.getUndirectEdges();
				}
				if(edges != null&&edges.size()>0){
					for(int j=0;j<edges.size();j++){
						AdjListEdge edge = edges.get(j);
						AdjListNode targetNode = (AdjListNode) edge.getTargetNode();
						if(queue.contains(targetNode)){
							continue;
						}
						if(isVistor[targetNode.getId()-1]){
							continue;
						}
						AdjListNode sourceNode = (AdjListNode) edge.getSourceNode();
						distances[i][targetNode.getId()-1] = 
								distances[i][sourceNode.getId()-1]+1;
						sumDis+=distances[i][targetNode.getId()-1];
						queue.offer(targetNode);
					}
				}
				isVistor[node.getId()-1] = true;
			}
		}		
		double aveagePath = sumDis/(0.5*graph.getNodeNum()*(graph.getNodeNum()-1));
		System.out.println(sumDis);
		System.out.println(aveagePath);
		graph.putAttribute("averagepath", aveagePath);
	}
}
