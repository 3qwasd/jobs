/**
*
**/
package cn.edu.bjtu.cnetwork.algorithms;


import java.util.Iterator;

import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.cnetwork.NetworkEdge;
import cn.edu.bjtu.cnetwork.NetworkNode;
import cn.edu.bjtu.qj.graph.component.Edge;

/**
 * @author QiaoJian
 *
 */
public class SparseNetwork extends NetworkAlgorithms {
	
	Network sparseNetwork;
	/**
	 * @param network
	 */
	public SparseNetwork(Network network) {
		super(network);
		// TODO Auto-generated constructor stub
		sparseNetwork = new Network(network.networkName,network.isDirected());
		sparseNetwork.nodeNum = network.nodeNum;
		sparseNetwork.topicNum = network.topicNum;
		sparseNetwork.init();
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.algorithms.NetworkAlgorithms#execute()
	 */
	@Override
	void execute() {
		// TODO Auto-generated method stub
		for(int i=0;i<network.nodeNum;i++){
			NetworkNode node = (NetworkNode) network.getNode(i);
			sparseNetwork.addNode(node);
			if(node.getOutEdges().size() <= 2){
				continue;
			}
			int num = (int) Math.ceil(Math.sqrt(node.getOutEdges().size()));
			NetworkEdge[] sampleEdges = new NetworkEdge[num];
			int index = 0;
			for(int j=0;j<node.getOutEdges().size();j++){
				NetworkEdge edge = (NetworkEdge) node.getOutEdges().get(j);
				NetworkNode targetNode = (NetworkNode) edge.getTargetNode();
				if(j == 0) { sampleEdges[0] = edge; index++; continue; }
				for(int k=0;k<index+1;k++){
					if(k == index) {
						if(index<num)
							sampleEdges[k] = edge;
						break;
						
					} 
					int inDegree = ((NetworkNode)sampleEdges[k].getTargetNode()).getInEdges().size();
					if(targetNode.getInEdges().size()>inDegree){
						NetworkEdge temp[] = new NetworkEdge[index-k];
						System.arraycopy(sampleEdges, k, temp, 0, index-k);
						sampleEdges[k] = edge;
						for(int n=0;n<temp.length;n++){
							if(k+n+1<num)
								sampleEdges[k+n+1] = temp[n];
						}
						break;
					}
				}
				if(index<num) index++;
			}
			for(int j=0;j<num;j++){
				sparseNetwork.addEdge(sampleEdges[j]);
			}
			
			node.getOutEdges().clear();
			node.getInEdges().clear();
		}
		sparseNetwork.edgeNum = sparseNetwork.getEdges().size();
		Iterator<Edge> edges = sparseNetwork.getEdges().iterator();
		while(edges.hasNext()){
			NetworkEdge edge = (NetworkEdge) edges.next();
			((NetworkNode)edge.getSourceNode()).getOutEdges().add(edge);
			((NetworkNode)edge.getTargetNode()).getInEdges().add(edge);
		}
	}

	/**
	 * @return the sparseNetwork
	 */
	public Network getSparseNetwork() {
		return sparseNetwork;
	}
	
}
