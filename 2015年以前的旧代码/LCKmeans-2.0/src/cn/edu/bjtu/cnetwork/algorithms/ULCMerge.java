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
public class ULCMerge extends NetworkAlgorithms{
	
	int K;
	public double alpha;
	/**
	 * @param network
	 */
	public ULCMerge(Network network) {
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
		K = network.topicNum;
		int index = 0;
		double mu = 0.1;
		while(seeds.size()<K){
			Community selected = communities.get(index++);
			for(Community seed:seeds){
				double ts = network.getTopologySimilarities(selected.getSeed().getId()-1,seed.getSeed().getId()-1);
				double cs = network.getContentSimilarities(selected.getSeed().getId()-1,seed.getSeed().getId()-1);
				double us = ts*alpha+(1-alpha)*cs;
				if(us>mu){
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
				mu = (mu+0.1)<1?mu+0.1:0.9;
			}
		}
		while(communities.size()>0){
			Community community = communities.remove(0);
			Community belong = null;
			Community tBelong = null;
			Community cBelong = null;
			Community uBelong = null;
			double mts = -1;
			double mcs = -1;
			double mus = -1;
			for(Community seed:seeds){
				double ts = network.getTopologySimilarities(community.getSeed().getId()-1, seed.getSeed().getId()-1);
				double cs = network.getContentSimilarities(community.getSeed().getId()-1, seed.getSeed().getId()-1);
				double us = ts*alpha+(1-alpha)*cs;
				if(ts>mts){
					mts = ts;
					tBelong = seed;
				}
				if(cs>mcs){
					mcs = cs;
					cBelong = seed;
				}
				if(us>mus){
					mus = us;
					uBelong = seed;
				}
			}
			if(tBelong == cBelong) belong = tBelong;
			else belong = uBelong;
			belong.addAll(community);
		}
		network.setCommunities(seeds);
	}

}
