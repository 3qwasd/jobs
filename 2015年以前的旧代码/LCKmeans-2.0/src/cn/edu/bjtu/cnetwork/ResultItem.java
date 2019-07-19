/**
*
**/
package cn.edu.bjtu.cnetwork;

/**
 * @author QiaoJian
 *
 */
public class ResultItem implements Comparable<ResultItem> {
	
	public static int sortType = 1;
	public String networkName;
	public double mu;
	public double kmeanAlpha;
	public double mergeAlpha;
	public int n;
	public double acc;
	public double nmi;
	public double pwf;
	public double modu;
	public double time;
	public double iter;
	public double entr;
	public String remark;
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(ResultItem o) {
		// TODO Auto-generated method stub
		if(sortType == 0) return complare(o);
		else return -complare(o);
	}
	public int complare(ResultItem o ){
		if(acc<o.acc)
			return -1;
		if(acc>o.acc)
			return 1;
		return 0;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String str = networkName+"\t"+remark+"\t"+"mu:"+mu+"\t"+"kmeanAlpha:"+kmeanAlpha+"\t"+"mergeAlpha:"+mergeAlpha+"\t"+"n:"+n+"\t"+
				"acc:"+acc+"\t"+"NMI:"+nmi+"\t"+"PWF:"+pwf+"\t"+"Modu:"+modu+"\t"+"ENTR:"+entr+"\t"+"time:"+time;
		return str;
	}
	
}
