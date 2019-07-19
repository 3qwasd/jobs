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
public class New2ULCMerge extends NewULCMerge {

	/**
	 * @param network
	 * @param usCalculater
	 */
	public New2ULCMerge(Network network, USCalculater usCalculater) {
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
			for(Community seed:seeds){
				double ts = network.getTopologySimilarities(community.getSeed().getId()-1, seed.getSeed().getId()-1);
				double cs = network.getContentSimilarities(community.getSeed().getId()-1, seed.getSeed().getId()-1);
				mtssum+=ts;
				mcssum+=cs;
				if(ts>0)
					num++;
				if(ts>mts){
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
			if(belong == null&&mts/mtssum==1)
				belong = tBlong;

			if(belong!=null){
				belong.addAll(community);
				continue;
			}
			int[] freqBlong = new int[seeds.size()];
			int uIndex = -1;
			if(tBlong!=null&&cBlong!=null&&tBlong!=cBlong){
			
				double top = 11;
				double low = 0;
				int avage = (int) Math.floor(mtssum/num);
				if(avage>6) {
					low = 7;
					top = 11;
				}else if(avage<4){
					low = 0;
					top = 4;
				}else{
					low = 4;
					top = 7;
				}
				for(double d=low;d<top;d++){
					double alpha = d/10;
					double mus = 0;
					int uBlong = -1;
					for(int i=0;i<seeds.size();i++){
						Community seed = seeds.get(i);
						double ts = network.getTopologySimilarities(community.getSeed().getId()-1, seed.getSeed().getId()-1);
						double cs = network.getContentSimilarities(community.getSeed().getId()-1, seed.getSeed().getId()-1);
						double us = ts*alpha+cs*(1-alpha);
	
						if(us>mus){
							mus = us;
							uBlong = i;
						}
					}
					if(uBlong>-1) freqBlong[uBlong]++;
				}
				int max = 0;
				for(int i=0;i<seeds.size();i++){
					if(freqBlong[i]>max){
						max = freqBlong[i];
						uIndex = i;
					}
				}
			}
			if(uIndex < 0) uIndex = (int) (Math.random()*seeds.size());

			belong = seeds.get(uIndex);
			belong.addAll(community);
		}
		network.setCommunities(seeds);
	}
}
