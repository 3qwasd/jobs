/**
*
**/
package cn.edu.bjtu.cnetwork.algorithms;

import java.util.ArrayList;
import java.util.List;

import cn.edu.bjtu.cnetwork.Community;
import cn.edu.bjtu.cnetwork.Network;


/**
 * @author QiaoJian
 *
 */
public class Merge extends NetworkAlgorithms{
	
	public int k;
	
	
	/**
	 * @param network
	 */
	public Merge(Network network) {
		super(network);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.algorithms.NetworkAlgorithms#execute()
	 */
	@Override
	void execute() {
		// TODO Auto-generated method stub
		List<Community> communities = network.getCommunities();
		List<Community> seeds = new ArrayList<>();
		
		double alpha = 0.1;
		int index = 0;
		while(seeds.size()<k){
			Community selected = communities.get(index++);
			for(Community seed:seeds){
				double similarity = network.getSimilarity(seed.getSeed().getId()-1, selected.getSeed().getId()-1);
				if(similarity>alpha){
					selected = null;
					break;
				}
			}
			if(selected != null){
				seeds.add(selected);
				communities.remove(selected);
				index--;
			}
			if(index == communities.size()){
				index = 0;
				alpha = (alpha+0.1)<1?alpha+0.1:0.9;
			}
		}
		while(communities.size()>0){
			Community community = communities.remove(0);
			Community belong = seeds.get(0);
			for(Community seed:seeds){
				double x = network.getSimilarity(community.getSeed().getId()-1, seed.getSeed().getId()-1);
				double y = network.getSimilarity(community.getSeed().getId()-1, belong.getSeed().getId()-1);
				if(x>y){
					belong = seed;
				}
			}
			
			belong.addAll(community);
			
		}
		network.setCommunities(seeds);
	}
	
}
