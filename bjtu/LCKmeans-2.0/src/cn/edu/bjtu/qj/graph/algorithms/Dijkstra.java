/**
 * @QiaoJian
 */
package cn.edu.bjtu.qj.graph.algorithms;

import cn.edu.bjtu.qj.graph.component.Graph;
import cn.edu.bjtu.qj.graph.component.MGraph;

/**
 * @author QiaoJian
 *
 */
public class Dijkstra extends Algorithms {
	
	MGraph graph;
	

	public Dijkstra() {
		super();
	}
	public Dijkstra(MGraph graph) {
		super();
		// TODO Auto-generated constructor stub
		this.graph = graph;
	}


	@Override
	public void attachGraph(Graph graph) {
		// TODO Auto-generated method stub
		super.attachGraph(graph);
		this.graph = (MGraph) graph;
	}
	
	public void executeStandardMethod(){
		/*有向图临接矩阵*/
		int[][] arcs = graph.getAdjMatrix();
	}
	public void executeHeapMethod(){
		
	}
}
