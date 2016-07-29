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
public class New3ULCMerge extends New2ULCMerge{

	/**
	 * @param network
	 * @param usCalculater
	 */
	public New3ULCMerge(Network network, USCalculater usCalculater) {
		super(network, usCalculater);
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
			double mts = 0;
			double mcs = 0;
			Community tBlong = null;
			Community cBlong = null;
			double mtssum = 0;
			double mcssum = 0;
			int num = 0;
			List<Community> alternative = new ArrayList<Community>();
			for(Community seed:seeds){
				double ts = network.getTopologySimilarities(community.getSeed().getId()-1, seed.getSeed().getId()-1);
				double cs = network.getContentSimilarities(community.getSeed().getId()-1, seed.getSeed().getId()-1);
				mtssum+=ts;
				mcssum+=cs;
				if(ts>0){
					alternative.add(seed);
					num++;
				}if(ts>mts){
					mts = ts;
					tBlong = seed;
				}
				if(cs>mcs){
					mcs = cs;
					cBlong = seed;
				}
			}

			if(tBlong!=null&&cBlong!=null&&tBlong==cBlong)
				belong = tBlong;
			else if(tBlong!=null&&cBlong==null)
				belong = tBlong;
			else if(cBlong!=null&&tBlong==null)
				belong = cBlong;
			if(belong == null&&alternative.size()==1)
				belong = tBlong;

			if(belong!=null){
				belong.addAll(community);
				continue;
			}
			
			if(tBlong!=null&&cBlong!=null&&tBlong!=cBlong){
				double mus = 0;
				for(Community alter:alternative){
					double ts = network.getTopologySimilarities(community.getSeed().getId()-1, alter.getSeed().getId()-1);
					double cs = network.getContentSimilarities(community.getSeed().getId()-1, alter.getSeed().getId()-1);
					double us = usCalculater.calculaterUs(ts, cs);
					if(us>mus){
						mus = us;
						belong = alter;
					}
				}
			}
			if(belong == null){
				int uIndex = (int) (Math.random()*seeds.size());
				belong = seeds.get(uIndex);
			}

			belong.addAll(community);
		}
		network.setCommunities(seeds);
	}
}
