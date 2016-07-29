/**
 * @QiaoJian
 */
package cn.edu.bjtu.qj.graph.algorithms;

import cn.edu.bjtu.qj.graph.algorithms.datastrut.TriplesTable;
import cn.edu.bjtu.qj.graph.component.Graph;
import cn.edu.bjtu.qj.graph.component.IResultHandler;
import cn.edu.bjtu.qj.graph.component.IValueHandler;
import cn.edu.bjtu.qj.graph.component.MGraph;
import cn.edu.bjtu.qj.graph.component.Node;

/**
 * @author QiaoJian
 *
 */
public class SinalSimilarityCalculate implements ISimilarityCalculate,IResultHandler{
	
	IValueHandler valueHandler;
	
	MGraph graph;
	
	/**
	 * 
	 */
	public SinalSimilarityCalculate(MGraph graph,IValueHandler valueHandler) {
		// TODO Auto-generated constructor stub
		this.graph = graph;
		this.valueHandler = valueHandler;
	}

	/**
	 * 
	 */
	public SinalSimilarityCalculate(MGraph graph) {
		// TODO Auto-generated constructor stub
		this.graph = graph;
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.qj.graph.component.IResultHandler#handleResult()
	 */
	@Override
	public void handleResult() {
		// TODO Auto-generated method stub
		if(valueHandler!=null)
			valueHandler.befor();
		
		calSSMatrix();
		
		if(valueHandler!=null)
			valueHandler.finish();
	}
	
	
	
	/* (non-Javadoc)
	 * @see cn.edu.bjtu.qj.graph.algorithms.ISimilarityCalculate#calculate(cn.edu.bjtu.qj.graph.component.Graph, cn.edu.bjtu.qj.graph.component.Node, cn.edu.bjtu.qj.graph.component.Node)
	 */
	@Override
	public double calculate(Graph graph, Node node_x, Node node_y) {
		// TODO Auto-generated method stub
		double[] sing_x = singalStandardize(node_x.getId());
		double[] sing_y = singalStandardize(node_y.getId());
		
		double sum = 0;
		
		for(int i=0;i<sing_x.length;i++){
			if(sing_x[i]==0&&sing_y[i] == 0){
				continue;
			}
			sum += (sing_x[i]-sing_y[i])*(sing_x[i]-sing_y[i]);
		}
		double similarity = Math.sqrt(sum);
 		return similarity;
	}
	/*
	 * 计算图的信号相似矩阵
	 */
	private void calSSMatrix(){
		int num = (graph.getNodeNum()-1)*graph.getNodeNum()/2;
		double[] similarites = new double[num];
		graph.setEuclideanDistances(similarites);
		double max = 0;
		double min = 2;
		for(int i=0;i<graph.getNodeNum();i++){
			for(int j=i+1;j<graph.getNodeNum();j++){
				
				double similarity = calculate(graph, graph.getNode(i), graph.getNode(j));
				if(similarity>max)
					max = similarity;
				if(similarity<min)
					min = similarity;
				//k = 0,1,2,...n(n-1)/2 存储上三角,不存储对角线
				int k = i*(graph.getNodeNum()-i-1)+i*(i+1)/2+(j-i-1);
				similarites[k] = similarity;
				if(valueHandler!=null)
					valueHandler.execute(similarity);
			}
		}
		graph.putAttribute("max_similarity", max);
		graph.putAttribute("min_similarity", min);
	}
	/*
	 * 信号向量标准化
	 */
	private double[] singalStandardize(int nodeId){
		double[] standardizeSingal = new double[graph.getNodeNum()];
		TriplesTable<Integer> signalTriples = graph.getSignalTriplesTable();
		int rowPos = signalTriples.postions[nodeId-1];
		int nextRowPos = nodeId==signalTriples.rn?signalTriples.length:signalTriples.postions[nodeId];
		double sum = 0;
		for(int pos = rowPos;pos<nextRowPos;pos++){
			sum+=signalTriples.datas[pos].getWeight();
		}
		for(int pos = rowPos;pos<nextRowPos;pos++){
			standardizeSingal[signalTriples.datas[pos].col] = signalTriples.datas[pos].getWeight()/sum;
		}
		return standardizeSingal;
	}
	public void setValueHandler(IValueHandler valueHandler) {
		this.valueHandler = valueHandler;
	}
	
}
