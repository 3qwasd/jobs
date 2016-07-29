/**
*
**/
package cn.edu.bjtu.cnetwork.algorithms;

import cn.edu.bjtu.cnetwork.Network;

/**
 * @author QiaoJian
 *
 */
public class PPLDCModel extends PPLDModel {

	/**
	 * @param network
	 */
	public PPLDCModel(Network network) {
		super(network);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.PPLDModel#init()
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
		double sum = 0;
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){
				s[i][j] = this.network.adjMatrix[i][j]+network.getSimilarity(i, j);
				sum+=s[i][j];
			}
		}
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){
				s[i][j] = s[i][j]/sum;
			}
		}
	}
	
	
}
