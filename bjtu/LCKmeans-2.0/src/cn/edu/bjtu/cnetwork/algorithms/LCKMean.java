/**
 *
 **/
package cn.edu.bjtu.cnetwork.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import cn.edu.bjtu.cnetwork.Community;
import cn.edu.bjtu.cnetwork.ComparableValue;
import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.cnetwork.NetworkNode;

/**
 * @author QiaoJian
 *
 */
public class LCKMean extends NetworkAlgorithms{

	/*class num*/
	public int k;
	public double mu = 0.6;
	public int iterNum = 0;
	List<Community> communities; 
	public int stopNodeNum;
	public double alpha = 0.5;
	TopologySimilarityCalculater tsc;
	ContentSimilarityCalculater csc;
	List<NetworkNode> currentSeeds;
	List<NetworkNode> lastSeeds;
	/**
	 * @param network
	 */
	public LCKMean(Network network) {
		super(network);
		// TODO Auto-generated constructor stub
		k = network.topicNum;
		communities = new ArrayList<>();
		stopNodeNum = network.nodeNum;
		tsc = new TopologySimilarityCalculater();
		csc = new ContentSimilarityCalculater();

	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.algorithms.NetworkAlgorithms#execute()
	 */
	@Override
	void execute() {
		// TODO Auto-generated method stub
		while(seedChange()){
			//System.out.println("iterNum:"+iterNum);
			if(iterNum == 0) selectBeginSeed();
			else selectSeeds();
			//System.out.println(currentSeeds);
			cluster();

			iterNum++;
		}
		network.setCommunities(communities);
	}
	void cluster(){
		for(int i=0;i<network.nodeNum;i++){
			Community blong = null;
			NetworkNode node = (NetworkNode) network.getNode(i);
			double mus = -1;

			for(Community community:communities){
				if(node == community.getSeed()){
					blong = community;
					break;
				}
				double ts = tsc.calculate(network, node, community.getSeed());
				double cs = csc.calculate(network, node, community.getSeed());
				double us = ts*alpha+(1-alpha)*cs;
	
				if(us>mus){
					mus = us;
					blong = community;
				}
			}
			blong.addNode(node);
		}

	}
	void selectSeeds(){
		lastSeeds = currentSeeds;
		currentSeeds = new ArrayList<>();
		ComparableValue.SORT_TYPE = 1;
		for(Community community:communities){
			Collections.sort(community.pageRankValues);
			NetworkNode newSeed = (NetworkNode) network.getNode(community.pageRankValues.get(0).id-1);
			community.pageRankValues.clear();
			community.getNodes().clear();
			community.setSeed(newSeed);
			currentSeeds.add(newSeed);
		}
	}
	void selectBeginSeed(){
		ComparableValue.SORT_TYPE = 1;
		Collections.sort(network.pageRankValues);

		currentSeeds = new ArrayList<>();
		int index = 0;
		while(communities.size()<k){
			if(index>=stopNodeNum){
				index = 0;
				mu = (mu+0.1)<=1?mu+0.1:0.9;
			}
			boolean selected = true; 
			ComparableValue pageRank = network.pageRankValues.get(index++);
			NetworkNode node = (NetworkNode) network.getNode(pageRank.id-1);
			if(currentSeeds.contains(node)) continue;
			for(int i=0;i<communities.size();i++){
				if(observeSimilarity(node, communities.get(i).getSeed())){
					selected = false;
					break;
				}
			}
			if(selected){
				Community community = new Community();
				community.id = communities.size();
				community.setSeed(node);
				communities.add(community);
				currentSeeds.add(node);
			}
			if(index == stopNodeNum){
				index = 0;
				mu = (mu+0.1)<=1?mu+0.1:0.9;
			}
		}
	}
	boolean observeSimilarity(NetworkNode x,NetworkNode y){
		double ts = tsc.calculate(network, x, y);
		double cs = csc.calculate(network, x, y);
		double us = ts*alpha+cs*(1-alpha);
		if(us>mu)
			return true;
		return false;
	}
	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.algorithms.NetworkAlgorithms#beforeExecute()
	 */
	@Override
	void beforeExecute() {
		// TODO Auto-generated method stub
		super.beforeExecute();
		network.initDoubleArray(-1);
	}
	boolean seedChange(){
		if(lastSeeds == null)
			return true;
		lastSeeds.retainAll(currentSeeds);
		if(lastSeeds.size()<k){
			return true;
		}
		else
			return false;
	}

}

