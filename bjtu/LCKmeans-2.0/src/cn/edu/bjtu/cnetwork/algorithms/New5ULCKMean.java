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
public class New5ULCKMean extends NetworkAlgorithms{

	/*class num*/
	public int k;
	public double mu = 0.0;
	public int iterNum = 0;
	List<Community> communities; 
	public int stopNodeNum;
	TopologySimilarityCalculater tsc;
	ContentSimilarityCalculater csc;
	List<NetworkNode> currentSeeds;
	List<NetworkNode> lastSeeds;
	/**
	 * @param network
	 */
	public New5ULCKMean(Network network) {
		super(network);
		// TODO Auto-generated constructor stub
		k = network.topicNum;
		communities = new ArrayList<>();
		stopNodeNum = network.nodeNum;
		tsc = new TopologySimilarityCalculater();
		//tsc = new JTopologySimilarityCalculater();
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

			double mts = -1;
			double mcs = -1;
			Community tBlong = null;
			Community cBlong = null;
			
			for(Community community:communities){
				if(node == community.getSeed()){
					blong = community;
					tBlong = null;
					cBlong = null;
					break;
				}
				double ts = tsc.calculate(network, node, community.getSeed());
				double cs = csc.calculate(network, node, community.getSeed());
				if(ts>mts){
					mts = ts;
					tBlong = community;
				}
				if(cs>mcs){
					mcs = cs;
					cBlong = community;
				}
			}
			if(blong!=null) {
				blong.addNode(node);
				continue;
			}
			Community uBlong = null;
			int[] freq = new int[communities.size()];
			for(double d=2;d<3;d++){
				double mus = -1;
				double alpha = d/10;
				int index = -1;
				
				for(int c=0;c<communities.size();c++){
					Community community = communities.get(c);
					double ts = tsc.calculate(network, node, community.getSeed());
					double cs = csc.calculate(network, node, community.getSeed());
					double us = ts*alpha+(1-alpha)*cs;
					if(us>mus){
						mus = us;
						index = c;
					}
				}
				if(index>=0) freq[index]++;
			}
			int max = 0;
			for(int index=0;index<communities.size();index++){
				if(freq[index]>max){
					max = freq[index];
					uBlong = communities.get(index);
				}
			}
			if(tBlong!=null&&cBlong!=null&&uBlong!=null){
				if(tBlong == cBlong) 
					blong = tBlong;
				else{
					blong = uBlong;
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
			community.classDistribute.clear();
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
		//if(us>mu)
		if(ts>mu||cs>mu)
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
		//network.initDoubleArray(-1);
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

	/**
	 * @param tsc the tsc to set
	 */
	public void setTsc(TopologySimilarityCalculater tsc) {
		this.tsc = tsc;
	}

	/**
	 * @param csc the csc to set
	 */
	public void setCsc(ContentSimilarityCalculater csc) {
		this.csc = csc;
	}
	
	
}
