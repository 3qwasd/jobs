/**
 *
 **/
package cn.edu.bjtu.cnetwork.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.edu.bjtu.cnetwork.Community;
import cn.edu.bjtu.cnetwork.Network;

/**
 * @author QiaoJian
 *
 */
public class New4ULCMerge extends NewULCMerge {

	/**
	 * @param network
	 * @param usCalculater
	 */
	public New4ULCMerge(Network network, USCalculater usCalculater) {
		super(network, usCalculater);
		K = network.topicNum;
		// TODO Auto-generated constructor stub
	}
	private List<Community> selectedSeeds(){
		List<Community> seeds = new ArrayList<Community>();
		List<Community> communities = network.getCommunities();
		Collections.sort(communities);
		int index = 0;
		double mu = 0.1;
		//选择种子
		while(seeds.size()<K){
			Community selected = communities.get(index++);
			for(Community seed:seeds){
				double ts = network.getTopologySimilarities(selected.getSeed().getId()-1,seed.getSeed().getId()-1);
				double cs = network.getContentSimilarities(selected.getSeed().getId()-1,seed.getSeed().getId()-1);
				//double us = ts*0.6+cs*0.4;
				if(ts>mu){
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
		return seeds;
	}
	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.algorithms.NetworkAlgorithms#execute()
	 */
	@Override
	void execute() {
		// TODO Auto-generated method stub
		List<Community> communities = network.getCommunities();
		List<Community> seeds = selectedSeeds();
		while(communities.size()>0){
			Community community = communities.remove(0);
			Community belong = null;
			//求tBlong,cBlong;
			double mts = -1;
			double mcs = -1;
			Community tBlong = null;
			Community cBlong = null;
			for(Community seed:seeds){
				double ts = network.getTopologySimilarities(community.getSeed().getId()-1, seed.getSeed().getId()-1);
				double cs = network.getContentSimilarities(community.getSeed().getId()-1, seed.getSeed().getId()-1);
				if(ts>mts){
					mts = ts;
					tBlong = seed;
				}
				if(cs>mcs){
					mcs = cs;
					cBlong = seed;
				}
			}
			//求ublong
			Community uBlong = null;
			int[] freqBlong = new int[seeds.size()];
			double top = 11;
			double low = 0;
			for(double d=low;d<top;d++){
				double alpha = d/10;
				double mus = -1;
				int uBlongIndex = -1;
				for(int i=0;i<seeds.size();i++){
					Community seed = seeds.get(i);
					double ts = network.getTopologySimilarities(community.getSeed().getId()-1, seed.getSeed().getId()-1);
					double cs = network.getContentSimilarities(community.getSeed().getId()-1, seed.getSeed().getId()-1);
					double us = ts*alpha+cs*(1-alpha);

					if(us>mus){
						mus = us;
						uBlongIndex = i;
					}
				}
				if(uBlongIndex>-1) freqBlong[uBlongIndex]++;
			}
			int max = 0;
			for(int i=0;i<seeds.size();i++){
				if(freqBlong[i]>max){
					max = freqBlong[i];
					uBlong = seeds.get(i);
				}
			}
			if(tBlong!=null&&cBlong!=null&&tBlong==cBlong)
				belong = tBlong;
			else if(tBlong!=null&&uBlong!=null&&tBlong==uBlong)
				belong = tBlong;
			else if(cBlong!=null&&uBlong!=null&&uBlong==cBlong)
				belong = cBlong;
			else belong = uBlong;
			
			
			if(belong == null){
				int index = (int) (Math.random()*communities.size());
				belong = seeds.get(index);
			}
			
			belong.addAll(community);
		}
		network.setCommunities(seeds);
	}
}
