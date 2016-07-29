/**
 * @QiaoJian
 */
package cn.edu.bjtu.qj.graph.algorithms;

import cn.edu.bjtu.qj.graph.algorithms.datastrut.TriplesTable;
import cn.edu.bjtu.qj.graph.component.Graph;
import cn.edu.bjtu.qj.graph.component.MGraph;

/**
 * @author QiaoJian
 *
 */
public class SignalPropagate extends Algorithms{
	
	private int t = 3;
	private MGraph graph;
	public SignalPropagate(Graph graph) {
		super();
		this.graph = (MGraph) graph;
	}
	public SignalPropagate(Graph graph,int t) {
		super();
		this.graph = (MGraph) graph;
		this.t=t;
	}
	
	@Override
	public void attachGraph(Graph graph) {
		// TODO Auto-generated method stub
		super.attachGraph(graph);
		this.graph = (MGraph) graph;
	}


	/**
	 * 
	 */
	public SignalPropagate() {
		// TODO Auto-generated constructor stub
		
		
	}
	
	public void execute(){
		
		MatrixMultiply multiply = new MatrixMultiply();
		TriplesTable<Integer> N = graph.getTriplesTable();
		TriplesTable<Integer> M = graph.getTriplesTable();
		for(int iter=0;iter<t;iter++){
			System.out.println("Iterator num:"+iter);
			M = multiply.IntMultSparse(M, N);	
		}
		graph.setSignalTriplesTable(M);
		
		N = null;
	}
}
