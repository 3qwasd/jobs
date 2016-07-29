/**
 * @QiaoJian
 */
package cn.edu.bjtu.cnetwork.algorithms;

import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.cnetwork.NetworkNode;
import cn.edu.bjtu.cnetwork.handler.SimilarityHandler;

/**
 * @author QiaoJian
 *
 */
public class UniteSimilarityCalculater implements SimilarityHandler {
	
	private SimilarityHandler xSimilarityHandler;
	private SimilarityHandler ySimilarityHandler;
	private float alpha = 0.5F;
	/**
	 * 
	 */
	public UniteSimilarityCalculater(SimilarityHandler xSimilarityHandler,SimilarityHandler ySimilarityHandler,float alpha) {
		// TODO Auto-generated constructor stub
		this.xSimilarityHandler = xSimilarityHandler;
		this.ySimilarityHandler = ySimilarityHandler;
		this.alpha = alpha;
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.handler.SimilarityHandler#calculate(cn.edu.bjtu.cnetwork.Network, cn.edu.bjtu.cnetwork.NetworkNode, cn.edu.bjtu.cnetwork.NetworkNode)
	 */
	@Override
	public double calculate(Network network, NetworkNode x, NetworkNode y) {
		// TODO Auto-generated method stub
		double xSimilarity = xSimilarityHandler.calculate(network, x, y);
		double ySimilarity = ySimilarityHandler.calculate(network, x, y);
		
		double uniteSimilarity = alpha*xSimilarity+(1-alpha)*ySimilarity;
		return uniteSimilarity;
	}
	
	public void changeAlpha(float alpha){
		this.alpha = alpha;
	}

	/**
	 * @return the xSimilarityHandler
	 */
	public SimilarityHandler getxSimilarityHandler() {
		return xSimilarityHandler;
	}

	/**
	 * @return the ySimilarityHandler
	 */
	public SimilarityHandler getySimilarityHandler() {
		return ySimilarityHandler;
	}

	/**
	 * @return the alpha
	 */
	public float getAlpha() {
		return alpha;
	}

}
