/**
 * @QiaoJian
 */
package cn.edu.bjtu.qj.graph.algorithms;

import cn.edu.bjtu.qj.graph.component.MGraph;


/**
 * @author QiaoJian
 * 信号传播算法,利用该算法计算图中各结点的影响度向量
 */
@Deprecated
public class SignalPropagation extends Algorithms {
	
	int t = 10;
	private MGraph graph;
	int[][] signalMatrix;
	int[][] similarityMatrix;
	int nodeNum;
	/**
	 * 
	 */
	public SignalPropagation(MGraph graph) {
		// TODO Auto-generated constructor stub
		this.graph = graph;
		
	}
	/**
	 * 初始化计算矩阵
	 */
	private void init(){
		nodeNum = graph.getNodeNum();
		signalMatrix = new int[nodeNum][nodeNum];
		for(int i=0;i<signalMatrix.length;i++){
			for(int j=0;j<signalMatrix[i].length;j++){
				if(i==j){
					signalMatrix[i][j] = 1;
				}else{
					signalMatrix[i][j] = graph.getAdjMatrix()[i][j];
				}
			}
		}
	}
	/**
	 * 计算信号传播相似矩阵
	 */
	private void calcuteSimilarityMatrix(){
		int[][] tempMatrix = signalMatrix;
		//循环t次,计算信号传播影响矩阵
		long loopNum = 1;
		for(int iter=1;iter<=1;iter++){
			System.out.println(iter);
			similarityMatrix = new int[nodeNum][nodeNum];
			for(int i=0;i<nodeNum;i++){
				int sum = 0;
				for(int j=0;j<nodeNum;j++){
					int res = 0;
					for(int count=0;count<nodeNum;count++){
						res += tempMatrix[i][count]*signalMatrix[count][j];
						System.out.println(loopNum);
						loopNum++;
					}
					similarityMatrix[i][j] = res;
					sum += res;
				}
				graph.getNode(i).putAttribute("sum", sum);
			}
			tempMatrix = similarityMatrix;
		}
	}
	private void standardized(){
		for(int i=0;i<similarityMatrix.length;i++){
			int[] vector = similarityMatrix[i];
			double[] standardSinalVector = new double[nodeNum];
			double sum = (double) graph.getNode(i).getAttribute("sum");
			for(int count = 0;count<nodeNum;count++){
				standardSinalVector[count] = ((double)vector[count])/sum;
			}
			System.out.println(graph.getNode(i).getName());
			System.out.println(standardSinalVector);
			graph.getNode(i).putAttribute("sinal_vector", standardSinalVector);
		}
	}
	public void execute(){
		//初始化计算矩阵
		init();
		//计算s相似矩阵
		calcuteSimilarityMatrix();
		//标准化
		standardized();
		//释放资源
		release();
	}
	/**
	 * 释放资源
	 */
	private void release(){
		this.graph = null;
		this.signalMatrix = null;
		this.similarityMatrix = null;
	}
}
