/**
 *
 **/
package cn.edu.bjtu.cnetwork.algorithms;
import java.util.ArrayList;
import java.util.List;

import cn.edu.bjtu.cnetwork.Community;
import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.cnetwork.NetworkNode;

/**
 * @author QiaoJian
 *
 */
public class New2ULCKMean extends NewULCKMean{

	/**
	 * @param network
	 * @param usCalculater
	 */
	public New2ULCKMean(Network network, USCalculater usCalculater) {
		super(network, usCalculater);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.algorithms.NewULCKMean#cluster()
	 */
	@Override
	void cluster() {
		for(int i=0;i<network.nodeNum;i++){
			Community blong = null;
			NetworkNode node = (NetworkNode) network.getNode(i);
			double mts = 0;
			double mcs = 0;
			Community tBlong = null;
			Community cBlong = null;
			double mtssum = 0;
			double mcssum = 0;
			int num = 0;
			List<Community> alternative = new ArrayList<>();
			for(Community community:communities){
				if(node == community.getSeed()){
					blong = community;
					tBlong = null;
					cBlong = null;
					break;
				}
				double ts = tsc.calculate(network, node, community.getSeed());
				double cs = csc.calculate(network, node, community.getSeed());
				if(ts>0) {
					num++;
					mtssum+=ts;
					alternative.add(community);
					if(ts>mts){
						mts = ts;
						tBlong = community;
					}
				}
				if(cs>0){
					mcssum+=cs;
					if(cs>mcs){
						mcs = cs;
						cBlong = community;
					}
				}
			}
			if(blong==null){
				if(tBlong!=null&&cBlong!=null&&tBlong==cBlong)
					blong = tBlong;
				else if(tBlong!=null&&cBlong==null)
					blong = tBlong;
				else if(cBlong!=null&&tBlong==null)
					blong = cBlong;
			}
			if(blong == null&&mts/mtssum==1){
				blong = tBlong;
			}
			if(blong!=null){
				blong.addNode(node);
				continue;
			}
			
			int uIndex = -1;
			if(tBlong!=null&&cBlong!=null&&tBlong!=cBlong){
				//System.out.println(alternative.size());
				int[] freqBlong = new int[communities.size()];
				double top = 11;
				double low = 0;
				
				for(double d=low;d<top;d++){
					double alpha = d/10;
					double mus = 0;
					int uBlong = -1;
					for(int index=0;index<communities.size();index++){
						Community community = communities.get(index);
						double ts = tsc.calculate(network, node, community.getSeed());
						double cs = csc.calculate(network, node, community.getSeed());
						double us = ts*alpha+cs*(1-alpha);
						
						if(us>mus){
							mus = us;
							uBlong = index;
						}
					}
					if(uBlong>=0) freqBlong[uBlong]++;
				}
				int max = 0;
				for(int index=0;index<communities.size();index++){
					if(freqBlong[index]>max){
						max = freqBlong[index];
						uIndex = index;
					}
				}
			}
			if(uIndex < 0) uIndex = (int) (Math.random()*communities.size());

			blong = communities.get(uIndex);
			blong.addNode(node);
		}
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.algorithms.NewULCKMean#observeSimilarity(cn.edu.bjtu.cnetwork.NetworkNode, cn.edu.bjtu.cnetwork.NetworkNode)
	 */
	@Override
	boolean observeSimilarity(NetworkNode x, NetworkNode y) {
		double ts = tsc.calculate(network, x, y);
		double cs = csc.calculate(network, x, y);
		//		double us = ts*0.5+cs*0.5;
		//		if(us>mu)return true;
		if(ts>mu||cs>mu)return true;
		return false;
	}


}
