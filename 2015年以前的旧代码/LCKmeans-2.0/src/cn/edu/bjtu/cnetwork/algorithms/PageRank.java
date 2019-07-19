/**
*
**/
package cn.edu.bjtu.cnetwork.algorithms;

import java.util.Iterator;

import cn.edu.bjtu.cnetwork.ComparableValue;
import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.cnetwork.NetworkEdge;
import cn.edu.bjtu.cnetwork.NetworkNode;
import cn.edu.bjtu.qj.graph.component.Edge;

/**
 * @author QiaoJian
 *
 */
@Deprecated
public class PageRank extends NetworkAlgorithms{
	
	double c = 0.85;
	double[] x;
	double[] r;
	/**
	 * @param network
	 */
	public PageRank(Network network) {
		super(network);
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.algorithms.NetworkAlgorithms#beforeExecute()
	 */
	@Override
	void beforeExecute() {
		// TODO Auto-generated method stub
		super.beforeExecute();
		x = new double[network.nodeNum];
		r = new double[network.nodeNum];
		network.initPageRankValues();
		for(int i=0;i<network.nodeNum;i++){
			x[i] = 1D/(double)network.nodeNum;
			network.pageRankValues.add(i,new ComparableValue(i+1, 0.0d)) ;
		}
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.algorithms.NetworkAlgorithms#execute()
	 */
	@Override
	void execute() {
		// TODO Auto-generated method stub
		double convergence = 0.001;
		double residual = 1;
		while(residual > convergence){
			Iterator<Edge> edges = network.getEdges().iterator();
			while(edges.hasNext()){
				NetworkEdge edge = (NetworkEdge) edges.next();
				NetworkNode sourceNode = (NetworkNode) edge.getSourceNode();
				NetworkNode targetNode = (NetworkNode) edge.getTargetNode();
				r[targetNode.getId()-1] +=
						x[sourceNode.getId()-1]/sourceNode.getOutEdges().size();
			}
			double residualSum = 0;
			for(int i=0;i<network.nodeNum;i++){
				r[i] = c*r[i]+(1-c)/network.nodeNum;
				double residualItem = x[i]-r[i];
				residualSum += residualItem*residualItem;
				x[i] = r[i];
				network.pageRankValues.get(i).value = x[i];
				
				network.getNode(i).putAttribute("pagerank", network.pageRankValues.get(i));
				
				r[i] = 0;
			}
			residual = Math.sqrt(residualSum);
		}
	}

}
