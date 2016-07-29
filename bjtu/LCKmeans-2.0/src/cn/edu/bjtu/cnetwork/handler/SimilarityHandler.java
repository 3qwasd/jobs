/**
 * @QiaoJian
 */
package cn.edu.bjtu.cnetwork.handler;

import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.cnetwork.NetworkNode;

/**
 * @author QiaoJian
 *
 */
public interface SimilarityHandler {
	
	public enum MathType{
		euclidean,cosin, 
	}
	public double calculate(Network network,NetworkNode x,NetworkNode y);

}
