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
public class New5ULCMerge extends NetworkAlgorithms{
	
	int K;
	/**
	 * @param network
	 */
	public New5ULCMerge(Network network) {
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
				if(ts>mu||cs>mu){
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
			double mts = -1;
			double mcs = -1;
			for(Community seed:seeds){
				double ts = network.getTopologySimilarities(community.getSeed().getId()-1, seed.getSeed().getId()-1);
				double cs = network.getContentSimilarities(community.getSeed().getId()-1, seed.getSeed().getId()-1);
				if(ts>mts){
					mts = ts;
					tBelong = seed;
				}
				if(cs>mcs){
					mcs = cs;
					cBelong = seed;
				}
			}
			Community uBelong = null;
			int[] freq = new int[seeds.size()];
			for(double d=0;d<11;d++){
				double alpha = d/10;
				double mus = -1;
				int ubindex = -1;
				for(int c=0;c<seeds.size();c++){
					Community seed = seeds.get(c);
					double ts = network.getTopologySimilarities(community.getSeed().getId()-1, seed.getSeed().getId()-1);
					double cs = network.getContentSimilarities(community.getSeed().getId()-1, seed.getSeed().getId()-1);
					double us = ts*alpha+(1-alpha)*cs;
					if(us>mus){
						mus = us;
						ubindex = c;
					}
				}
				if(ubindex>=0) freq[ubindex]++;
			}
			int max = 0;
			for(int i=0;i<seeds.size();i++){
				if(freq[i]>max){
					max = freq[i];
					uBelong = seeds.get(i);
				}
			}
			if(tBelong == cBelong) belong = tBelong;
			else belong = uBelong;
			belong.addAll(community);
		}
		network.setCommunities(seeds);
	}

}
