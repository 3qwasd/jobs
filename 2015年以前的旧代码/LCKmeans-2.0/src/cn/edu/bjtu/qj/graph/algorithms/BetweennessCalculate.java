/**
 * @QiaoJian
 */
package cn.edu.bjtu.qj.graph.algorithms;

import java.util.Collection;
import java.util.List;

import cn.edu.bjtu.qj.graph.component.AdjListGraph;
import cn.edu.bjtu.qj.graph.component.Edge;
import cn.edu.bjtu.qj.graph.component.Graph;
import cn.edu.bjtu.qj.graph.component.Node;
import cn.edu.bjtu.qj.graph.component.SubGraphsNodeProcesser;

/**
 * @author QiaoJian
 *
 */
public class BetweennessCalculate extends Algorithms{
	public final static String BETWEENNESS = "betweenness";
	public void initBetweenness(){
		for(Node node:graph.getNodes()){
			node.putAttribute(BETWEENNESS, 0);
		}
		for(Edge edge:graph.getEdges()){
			edge.putAttribute(BETWEENNESS, 0);
		}
	}
	
	public void calculateBetweenness(){
		BreadthFirstSearch bfs = new BreadthFirstSearch();
		bfs.attachGraph(graph);
		SubGraphsNodeProcesser subGraphsNodeProcesser = new SubGraphsNodeProcesser();
		bfs.execute(subGraphsNodeProcesser);
		List<Graph> subGraphs = subGraphsNodeProcesser.getSubGraphs(false);
		Collection<Node> subGraphNodes = null;
        Collection<Edge> subGraphEdges = null;
        
        for(Graph subGraph : subGraphs){
        	subGraphNodes = subGraph.getNodes();
        	subGraphEdges = subGraph.getEdges();
        	
        }
	}
}
