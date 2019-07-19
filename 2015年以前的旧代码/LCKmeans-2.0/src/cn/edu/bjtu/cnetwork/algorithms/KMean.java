/**
 *
 **/
package cn.edu.bjtu.cnetwork.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import cn.edu.bjtu.cnetwork.Community;
import cn.edu.bjtu.cnetwork.ComparableValue;
import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.cnetwork.NetworkNode;

/**
 * @author QiaoJian
 *
 */
public class KMean extends NetworkAlgorithms{
	/*class num*/
	public int k;
	public double mu = 0.6;
	public int iterNum;
	List<Community> communities; 
	Random random = new Random();
	/*选择初始种子时遍历的结点个数*/
	public int stopNodeNum;
	/**
	 * @param network
	 */
	public KMean(Network network) {
		super(network);
		// TODO Auto-generated constructor stub
		k = network.topicNum;
		communities = new ArrayList<>();
		stopNodeNum = network.nodeNum;
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.algorithms.NetworkAlgorithms#execute()
	 */
	@Override
	void execute() {
		// TODO Auto-generated method stub
		selectBeginSeed();
		for(int i=0;i<iterNum-1;i++){
			cluster();
			selectSeeds();
		}
		cluster();
		network.setCommunities(communities);
	}
	void cluster(){
		for(int i=0;i<network.nodeNum;i++){
			Community blong = null;
			NetworkNode node = (NetworkNode) network.getNode(i);
			double maxSimilarity = 0.0;
			for(Community community:communities){
				if(node == community.getSeed()){
					blong = community;
					break;
				}
				double similarity = getSimilarity(node, community.getSeed());
				if(similarity == 1){
					blong = community;
					break;
				}
				if(similarity>maxSimilarity){
					maxSimilarity = similarity;
					blong = community;
				}
			}
			if(blong == null){
				int index = random.nextInt(communities.size());

				if(index == communities.size())
					index--;
				blong = communities.get(index);

			}
			blong.addNode(node);
		}
	}
	double getSimilarity(NetworkNode x,NetworkNode y){
		return network.getSimilarity(x.getId()-1,y.getId()-1);
	}
	void selectSeeds(){
		ComparableValue.SORT_TYPE = 1;
		for(Community community:communities){
			Collections.sort(community.pageRankValues);
			NetworkNode newSeed = (NetworkNode) network.getNode(community.pageRankValues.get(0).id-1);
			community.pageRankValues.clear();
			community.getNodes().clear();
			community.setSeed(newSeed);
		}
	}
	void selectBeginSeed(){
		if(stopNodeNum>network.nodeNum)
			stopNodeNum = network.nodeNum;
		ComparableValue.SORT_TYPE = 1;
		Collections.sort(network.pageRankValues);

		List<NetworkNode> seeds = new ArrayList<>();
		int index = 0;
		while(communities.size()<k){
			if(index>=stopNodeNum){
				index = 0;
				mu = (mu+0.1)<=1?mu+0.1:0.9;
			}
			boolean selected = true; 
			ComparableValue pageRank = network.pageRankValues.get(index++);
			NetworkNode node = (NetworkNode) network.getNode(pageRank.id-1);
			if(seeds.contains(node)) continue;
			for(int i=0;i<communities.size();i++){
				double similarity = network.getSimilarity(node.getId()-1,communities.get(i).getSeed().getId()-1);
				if(similarity>mu){
					selected = false;
					break;
				}
			}
			if(selected){
				Community community = new Community();
				community.id = communities.size();
				community.setSeed(node);
				communities.add(community);
				seeds.add(node);
			}
			if(index == stopNodeNum){
				index = 0;
				mu = (mu+0.1)<=1?mu+0.1:0.9;
			}
		}
	}

	/**
	 * @return the communities
	 */
	public List<Community> getCommunities() {
		return communities;
	}

}
