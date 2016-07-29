/**
 * @QiaoJian
 */
package cn.edu.bjtu.qj.graph.algorithms;

import java.util.ArrayList;
import java.util.List;

import cn.edu.bjtu.qj.graph.algorithms.datastrut.Triple;
import cn.edu.bjtu.qj.graph.algorithms.datastrut.TriplesTable;

/**
 * @author QiaoJian
 *
 */
public class MatrixMultiply{
	
	
	@SuppressWarnings("unchecked")
	public TriplesTable<Double> douMultSparse(TriplesTable<Double> M,TriplesTable<Double> N){
		TriplesTable<Double> result = new TriplesTable<Double>();
		result.rn = M.rn;
		result.cn = N.cn;
		result.postions = new int[result.rn];
		/*临时存储区*/
		List<Triple<Double>> resultList = 
				new ArrayList<Triple<Double>>();
		for(int mRow=0;mRow<M.rn;mRow++){
			double[] acc = new double[result.cn];
			int currMRowPos = M.postions[mRow];
			int nextMRowPos = mRow==(M.rn-1)?M.length:M.postions[mRow+1];
			for(int mPos = currMRowPos;mPos<nextMRowPos;mPos++){
				int nRow=M.datas[mPos].col;
				int currNrowPos = N.postions[nRow];
				int nextNRowPos = nRow==(N.rn-1)?N.length:N.postions[nRow+1];
				for(int nPos = currNrowPos;nPos<nextNRowPos;nPos++){
					int rCol = N.datas[nPos].col;
					acc[rCol] += M.datas[mPos].getWeight()*N.datas[nPos].getWeight();
				}
			}
			
			result.postions[mRow] = resultList.size();
			for(int rCol=0;rCol<N.cn;rCol++){
				
				if(acc[rCol]!=0){
					//System.out.println(acc[rCol]);
					Triple<Double> triple = new Triple<Double>(mRow,rCol,acc[rCol]);
					resultList.add(triple);
				}
			}
			
			acc = null;
			//System.gc();
		}
		result.length = resultList.size();
		result.datas = new Triple[resultList.size()];
		result.datas = resultList.toArray(result.datas);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public TriplesTable<Integer> IntMultSparse(TriplesTable<Integer> M,TriplesTable<Integer> N){
		TriplesTable<Integer> result = new TriplesTable<Integer>();
		result.rn = M.rn;
		result.cn = N.cn;
		result.postions = new int[result.rn];
		/*临时存储区*/
		List<Triple<Integer>> resultList = 
				new ArrayList<Triple<Integer>>();
		for(int mRow=0;mRow<M.rn;mRow++){
			int[] acc = new int[result.cn];
			int currMRowPos = M.postions[mRow];
			int nextMRowPos = mRow==(M.rn-1)?M.length:M.postions[mRow+1];
			for(int mPos = currMRowPos;mPos<nextMRowPos;mPos++){
				int nRow=M.datas[mPos].col;
				int currNrowPos = N.postions[nRow];
				int nextNRowPos = nRow==(N.rn-1)?N.length:N.postions[nRow+1];
				for(int nPos = currNrowPos;nPos<nextNRowPos;nPos++){
					int rCol = N.datas[nPos].col;
					acc[rCol] += M.datas[mPos].getWeight()*N.datas[nPos].getWeight();
				}
			}
			
			result.postions[mRow] = resultList.size();
			for(int rCol=0;rCol<N.cn;rCol++){
				
				if(acc[rCol]!=0){
					//System.out.println(acc[rCol]);
					Triple<Integer> triple = new Triple<Integer>(mRow,rCol,acc[rCol]);
					resultList.add(triple);
				}
			}
			
			acc = null;
			//System.gc();
		}
		result.length = resultList.size();
		result.datas = new Triple[resultList.size()];
		result.datas = resultList.toArray(result.datas);
		return result;
	}
}
