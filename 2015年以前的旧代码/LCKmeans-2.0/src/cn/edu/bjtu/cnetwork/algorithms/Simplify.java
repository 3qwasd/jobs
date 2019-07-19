/**
*
**/
package cn.edu.bjtu.cnetwork.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import cn.edu.bjtu.cnetwork.ComparableValue;
import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.cnetwork.NetworkEdge;
import cn.edu.bjtu.cnetwork.NetworkNode;
import cn.edu.bjtu.qj.graph.component.Edge;

/**
 * @author QiaoJian
 *
 */
public class Simplify extends NetworkAlgorithms {
	
	JaccardSimilarityCalculater jaccardSC;
	
	Network simplifyNetwork;
	/**
	 * @param network
	 */
	public Simplify(Network network) {
		super(network);
		network.initDoubleArray();
		jaccardSC = new JaccardSimilarityCalculater();
		// TODO Auto-generated constructor stub
		simplifyNetwork = new Network(network.networkName, network.isDirected());
		simplifyNetwork.nodeNum = network.nodeNum;
		simplifyNetwork.topicNum = network.topicNum;
		simplifyNetwork.init();
		
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.algorithms.NetworkAlgorithms#execute()
	 */
	@Override
	void execute() {
		ComparableValue.SORT_TYPE = 1;
		// TODO Auto-generated method stub
		for(int i=0;i<network.nodeNum;i++){
			NetworkNode node = (NetworkNode) network.getNode(i);
			List<ComparableValue> sims= new ArrayList<>();
			for(NetworkNode neighbour:node.getNeighbours()){
				double jaccard = jaccardSC.calculate(network, node, neighbour);
				ComparableValue value = new ComparableValue(neighbour.getId(), jaccard);
				sims.add(value);
			}
			Collections.sort(sims);
			int num = (int) Math.ceil(Math.sqrt(sims.size()));
			for(int j = 0;j<num;j++){
				int id = sims.get(j).id;
				NetworkNode target = (NetworkNode) network.getNode(id-1);
				simplifyNetwork.createEdge(node, target);
			}
			simplifyNetwork.addNode(node);
		}
		simplifyNetwork.edgeNum = simplifyNetwork.getEdges().size();
		Iterator<Edge> edges = simplifyNetwork.getEdges().iterator();
		while(edges.hasNext()){
			NetworkEdge edge = (NetworkEdge) edges.next();
			((NetworkNode)edge.getSourceNode()).getOutEdges().add(edge);
			((NetworkNode)edge.getTargetNode()).getInEdges().add(edge);
		}
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.algorithms.NetworkAlgorithms#beforeExecute()
	 */
	@Override
	void beforeExecute() {
		// TODO Auto-generated method stub
		super.beforeExecute();
		for(int i=0;i<network.nodeNum;i++){
			NetworkNode node = (NetworkNode) network.getNode(i);
			for(int j=0;j<node.getOutEdges().size();j++){
				node.addNeighbours((NetworkNode) node.getOutEdges().get(j).getTargetNode());
			}
			node.getInEdges().clear();
			node.getOutEdges().clear();
			node.getUndirectEdges().clear();
		}
	}

	/**
	 * @return the simplifyNetwork
	 */
	public Network getSimplifyNetwork() {
		return simplifyNetwork;
	}
}
