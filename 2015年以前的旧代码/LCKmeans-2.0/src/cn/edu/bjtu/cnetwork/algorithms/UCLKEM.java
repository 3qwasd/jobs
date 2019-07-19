/**
 *
 **/
package cn.edu.bjtu.cnetwork.algorithms;

import java.util.List;

import cn.edu.bjtu.cnetwork.Community;
import cn.edu.bjtu.cnetwork.Network;

/**
 * @author QiaoJian
 *
 */
public class UCLKEM extends NetworkAlgorithms{

	public double[] params_j = {0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9};
	public double[] params_i = {0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9};
	public int[] offset = {-2,-1,0,1,2};
	public int n; 
	double sampleVal[][][][];
	public double resKMeanAlpha;
	public double resMergeAlpha;
	public int resN;
	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.algorithms.NetworkAlgorithms#beforeExecute()
	 */
	@Override
	void beforeExecute() {
		// TODO Auto-generated method stub
		super.beforeExecute();
		sampleVal = new double[params_i.length][params_j.length][offset.length][network.topicNum];
	}

	/**
	 * @param network
	 */
	public UCLKEM(Network network) {
		super(network);
		//n = network.topicNum;
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.algorithms.NetworkAlgorithms#execute()
	 */
	@Override
	void execute() {
		// TODO Auto-generated method stub
		eStep();
		mStep();
	}

	void eStep(){
		for(int i=0;i<params_i.length;i++){
			for(int j=0;j<params_j.length;j++){
				for(int n = 0;n<offset.length;n++){
					ULCKMean ulckMean = new ULCKMean(network);
					ulckMean.k = network.topicNum*(network.topicNum+offset[n]);
					ulckMean.alpha = params_i[i];
					ulckMean.run();
					ULCMerge merge = new ULCMerge(network);
					merge.alpha = params_j[j];
					merge.run();
					List<Community> communities = network.getCommunities();
					for(int k = 0;k<network.topicNum;k++){
						sampleVal[i][j][n][k] = communities.get(k).getNodeNum()/(double)network.nodeNum;
					}
				}
			}
		}
	}
	void mStep(){
		int kmean_pindex = 0;
		int merge_pindex = 0;
		int offset_pindex = 0;
		double max = -999;
		for(int i=0;i<params_i.length;i++){
			for(int j=0;j<params_j.length;j++){
				for(int n=0;n<offset.length;n++){
					double logBase = 1;
					for(int k=0;k<network.topicNum;k++){
						logBase = logBase*sampleVal[i][j][n][k];
					}
					double logVal = Math.log(logBase);
					if(this.valueHandler!=null)
						this.valueHandler.handlerValue(new Object[]{params_i[i],params_j[j],network.topicNum+offset[n],logVal});
					if(logVal>max){
						max = logVal;
						kmean_pindex = i;
						merge_pindex = j;
						offset_pindex = n;
					}
				}
			}
		}
		System.out.println(network.networkName+":n="+n+"\tkmeanAlpha="+params_i[kmean_pindex]+"\tmergeAlpha="+params_j[merge_pindex]+"\toffset="+offset[offset_pindex]);
		resKMeanAlpha = params_i[kmean_pindex];
		resMergeAlpha = params_j[merge_pindex];
		resN = network.topicNum+offset[offset_pindex];
	}
}
