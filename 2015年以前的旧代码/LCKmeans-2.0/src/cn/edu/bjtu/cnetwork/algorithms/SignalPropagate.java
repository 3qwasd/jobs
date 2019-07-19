/**
 * @QiaoJian
 */
package cn.edu.bjtu.cnetwork.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.qj.graph.algorithms.MatrixMultiply;
import cn.edu.bjtu.qj.graph.algorithms.datastrut.Triple;
import cn.edu.bjtu.qj.graph.algorithms.datastrut.Triple.SortType;
import cn.edu.bjtu.qj.graph.algorithms.datastrut.TriplesTable;

/**
 * @author QiaoJian
 *
 */
public class SignalPropagate extends NetworkAlgorithms {
	
	/*迭代次数*/
	private int iterNum = 5;
	
	private MatrixMultiply matrixMultiply;
	/**
	 * @param network
	 */
	public SignalPropagate(Network network) {
		super(network);
		// TODO Auto-generated constructor stub
	}
	
	public SignalPropagate(Network network, int iterNum) {
		super(network);
		this.iterNum = iterNum;
	}



	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.algorithms.NetworkAlgorithms#execute()
	 */
	@Override
	void execute() {
		beforeExecute();
		// TODO Auto-generated method stub
		TriplesTable<Double> N = network.signalTriplesTable;
		TriplesTable<Double> M = network.signalTriplesTable;
		for(int iter=0;iter<iterNum;iter++){
			M = matrixMultiply.douMultSparse(M, N);	
		}
		network.signalTriplesTable = M;
		N = null;
	}
	void transposeLowM(TriplesTable<Double> maritx){
		List<Triple> items = new ArrayList<Triple>();
		for(int i=0;i<maritx.length;i++){
			Triple<Double> item = maritx.datas[i];
			int row = item.col;
			int col = item.row;
			item.row = row;
			item.col = col;
			items.add(maritx.datas[i]);
		}
		Collections.sort(items);
		int index = -1;
		for(int i=0;i<maritx.length;i++){
			Triple<Double> item = items.get(i);
			maritx.datas[i] = item;
			if(item.row !=index){
				index++;
				maritx.postions[index] = i;
			}
		}
	}
	void transpose(TriplesTable<Double> maritx){
		double[][] t = new double[maritx.cn][maritx.rn];
		for(int i=0;i<maritx.length;i++){
			Triple<Double> item = maritx.datas[i];
			t[item.col][item.row] = item.getWeight();
		}
		int index = 0;
		for(int i=0;i<t.length;i++){
			maritx.postions[i] = index;
			for(int j=0;j<t.length;j++){
				if(t[i][j]!=0){
					maritx.datas[index].row = i;
					maritx.datas[index].col = j;
					maritx.datas[index].setWeight(t[i][j]);
					index++;
				}
			}
		}
	}
	@Override
	void beforeExecute() {
		// TODO Auto-generated method stub
		network.createSignalTriplesTable();
		super.beforeExecute();
		matrixMultiply = new MatrixMultiply();
	}
	
	@Override
	void afterExecute() {
		// TODO Auto-generated method stub
		super.afterExecute();
		transposeLowM(network.signalTriplesTable);
	}
	public void setIterNum(int iterNum) {
		this.iterNum = iterNum;
	}
	
	
}
