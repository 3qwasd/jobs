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
public class NewULCMerge extends NetworkAlgorithms{
	
	public int K;
	USCalculater usCalculater;
	/**
	 * @param network
	 */
	public NewULCMerge(Network network,USCalculater usCalculater) {
		super(network);
		// TODO Auto-generated constructor stub
		this.usCalculater = usCalculater;
		
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
			Community uBelong = null;
			double mts = 0;
			double mcs = 0;
			double mus = 0;
			for(Community seed:seeds){
				double ts = network.getTopologySimilarities(community.getSeed().getId()-1, seed.getSeed().getId()-1);
				double cs = network.getContentSimilarities(community.getSeed().getId()-1, seed.getSeed().getId()-1);
				double us = usCalculater.calculaterUs(ts, cs);
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
			if(tBelong!=null&&cBelong!=null&&uBelong!=null){
				if(tBelong == cBelong) 
					belong = tBelong;
				else{
					belong = uBelong;
				}
			}
			if(belong == null){
				if(tBelong==null&&cBelong!=null)
					belong = cBelong;
				if(tBelong!=null&&cBelong==null)
					belong = tBelong;
			} 
			if(belong == null){
				int i = (int) Math.floor(Math.random()*K);
				belong = seeds.get(i);
			}
			belong.addAll(community);
		}
		network.setCommunities(seeds);
	}
}
