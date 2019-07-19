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
public class New3ULCKMean extends New2ULCKMean{

	/**
	 * @param network
	 * @param usCalculater
	 */
	public New3ULCKMean(Network network, USCalculater usCalculater) {
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
			if(blong == null&&alternative.size()==1){
				blong = tBlong;
			}
			if(blong!=null){
				blong.addNode(node);
				continue;
			}
			
			if(tBlong!=null&&cBlong!=null&&tBlong!=cBlong){
				double mus = 0;
				for(Community community:alternative){
					double ts = tsc.calculate(network, node, community.getSeed());
					double cs = csc.calculate(network, node, community.getSeed());
					double us = this.usCalculater.calculaterUs(ts, cs);
					if(us>mus){
						mus = us;
						blong = community;
					}
				}
			}
			if(blong == null){
				int index = (int) (Math.random()*communities.size());
				blong = communities.get(index);
			}
				
			blong.addNode(node);
		}
	}
}
