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
public class New6ULCKMean extends NewULCKMean{

	/**
	 * @param network
	 * @param usCalculater
	 */
	public New6ULCKMean(Network network, USCalculater usCalculater) {
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
			/*求tBelong和cBelong*/
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
			//处理种子结点
			if(blong!=null){
				blong.addNode(node);
				continue;
			} 
			//求uBelong
			Community uBlong = null;
			int[] freqBlong = new int[communities.size()];
			double top = 11;
			double low = 0;
			for(double d=low;d<top;d++){
				double alpha = d/10;
				double mus = -1;
				int uBlongIndex = -1;
				for(int index=0;index<communities.size();index++){
					Community community = communities.get(index);
					double ts = tsc.calculate(network, node, community.getSeed());
					double cs = csc.calculate(network, node, community.getSeed());
					double us = ts*alpha+cs*(1-alpha);

					if(us>mus){
						mus = us;
						uBlongIndex = index;
					}
				}
				if(uBlongIndex>=0) freqBlong[uBlongIndex]++;
			}
			int max = 0;
			for(int index=0;index<communities.size();index++){
				if(freqBlong[index]>max){
					max = freqBlong[index];
					uBlong = communities.get(index);
				}
			}
			if(tBlong!=null&&cBlong!=null&&tBlong==cBlong)
				blong = tBlong;
			else if(tBlong!=null&&uBlong!=null&&tBlong==uBlong)
				blong = tBlong;
			else if(cBlong!=null&&uBlong!=null&&uBlong==cBlong)
				blong = cBlong;
			else blong = uBlong;
			
			if(blong == null){
				int index = (int) (Math.random()*communities.size());
				blong = communities.get(index);
			}
			
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
		if(ts>mu)return true;
		return false;
	}


}
