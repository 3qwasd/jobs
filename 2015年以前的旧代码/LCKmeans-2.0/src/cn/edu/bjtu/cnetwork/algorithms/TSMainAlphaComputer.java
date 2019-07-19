/**
*
**/
package cn.edu.bjtu.cnetwork.algorithms;

/**
 * @author QiaoJian
 *
 */
public class TSMainAlphaComputer implements AlphaComputer{

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.algorithms.AlphaComputer#computeAlpha(double, double)
	 */
	@Override
	public double computeAlpha(double ts, double cs) {
		if(ts == 0&&cs == 0)
			return 0;
		if(ts>=cs){
			return ts/(ts+cs);
		}else{
			return cs/(ts+cs);
		}
	}
	
}
