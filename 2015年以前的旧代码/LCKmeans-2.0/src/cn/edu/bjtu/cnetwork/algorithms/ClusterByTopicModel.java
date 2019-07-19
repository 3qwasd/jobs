/**
*
**/
package cn.edu.bjtu.cnetwork.algorithms;

import java.util.ArrayList;
import java.util.List;

import cn.edu.bjtu.cnetwork.Community;
import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.cnetwork.NetworkNode;

/**
 * @author QiaoJian
 *
 */
public class ClusterByTopicModel extends NetworkAlgorithms{

	/**
	 * @param network
	 */
	public ClusterByTopicModel(Network network) {
		super(network);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.algorithms.NetworkAlgorithms#execute()
	 */
	@Override
	void execute() {
		// TODO Auto-generated method stub
		List<Community> communities = new ArrayList<Community>();
		for(int k=0;k<network.topicNum;k++){
			Community community = new Community();
			community.id = k;
			communities.add(community);
		}
		for(int i = 0;i<network.nodeNum;i++){
			NetworkNode node = (NetworkNode) network.getNode(i);
			double max_p = 0;
			int index = 0;
			for(int j=0;j<node.nodeContentDouble.length;j++){
				if(node.nodeContentDouble[j]>max_p){
					max_p = node.nodeContentDouble[j];
					index = j;
				}
			}
			communities.get(index).addNode(node);
		}
		network.setCommunities(communities);
	}

}
