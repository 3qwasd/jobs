/**
 * @QiaoJian
 */
package cn.edu.bjtu.qj.graph.component;


import java.util.ArrayList;
import java.util.List;

import cn.edu.bjtu.qj.graph.algorithms.datastrut.IndexValue;
import cn.edu.bjtu.qj.graph.algorithms.datastrut.Triple;
import cn.edu.bjtu.qj.graph.algorithms.datastrut.TriplesTable;

/**
 * @author QiaoJian
 *
 */
public class MGraph extends AdjListGraph{
	
	private int topicNum;
	
	private int[][] adjMatrix;
	
	/*PageRank值*/
	private IndexValue<Double>[] pageRanks;
	/*信号量的三元组表*/
	private TriplesTable<Integer> signalTriplesTable;
	
	
	/*欧氏距离存储上三角*/
	double[] euclideanDistances = null;
	
	List<String> topicNames;
	/**
	 * 
	 */
	public MGraph() {
		// TODO Auto-generated constructor stub
	}
	
	public MGraph(int nodeNum, int edgeNum) {
		super(nodeNum, edgeNum);
		// TODO Auto-generated constructor stub
		init();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
		adjMatrix = new int[nodeNum][nodeNum];
		signalTriplesTable = new TriplesTable<>();
		signalTriplesTable.rn = nodeNum;
		signalTriplesTable.cn = nodeNum;
		signalTriplesTable.postions = new int[signalTriplesTable.rn];
	}
	public void createSignalTriplesTable(){
		signalTriplesTable = new TriplesTable<>();
		signalTriplesTable.rn = nodeNum;
		signalTriplesTable.cn = nodeNum;
		signalTriplesTable.postions = new int[signalTriplesTable.rn];
		List<Triple<Integer>> temp = new ArrayList<>();
		for(int row=0;row<this.nodeNum;row++){
			signalTriplesTable.postions[row] = temp.size();
			for(int col=0;col<this.nodeNum;col++){
				if(row==col){
					Triple<Integer> triple = new Triple<Integer>(row, col, adjMatrix[row][col]+1);
					temp.add(triple);
				}else{
					if(adjMatrix[row][col]!=0){
						Triple<Integer> triple = new Triple<Integer>(row, col, adjMatrix[row][col]);
						temp.add(triple);
					}
				} 
			}
		}
		signalTriplesTable.length = temp.size();
		signalTriplesTable.datas = new Triple[temp.size()];
		temp.toArray(signalTriplesTable.datas);
		temp = null;
		
	}
	
	/**
	 * 从上三角矩阵中取值
	 * @param row
	 * @param col
	 * @return
	 */
	public double getEuclideanDistance(int i,int j){
		
		int row = i;
		int col = j;
		int k=0;
		if(i==j)
			return 0;
		if(i<j){
			k = i*(this.nodeNum-i-1)+i*(i+1)/2+(j-i-1);
		}else{
			k = j*(this.nodeNum-j-1)+j*(j+1)/2+(i-j-1);
		}
		double euclideanDistance = euclideanDistances[k];
		return euclideanDistance;
	}
	private void addAdjMatrix(int i,int j,boolean isDirected){
		adjMatrix[i][j] = 1;
		if(!isDirected){
			adjMatrix[j][i] = 1;
		}
	}


	@Override
	public Edge createEdge(Node sourceNode, Node targetNode, boolean isDirected) {
		// TODO Auto-generated method stub
		Edge edge = super.createEdge(sourceNode, targetNode, isDirected);
		addAdjMatrix(sourceNode.getId()-1,targetNode.getId()-1,isDirected);
		return edge;
	}


	@Override
	public void addEdge(Edge edge) {
		// TODO Auto-generated method stub
		super.addEdge(edge);
		addAdjMatrix(edge.getSourceNode().getId()-1,edge.getTargetNode().getId()-1,isDirected);
	}

	public int[][] getAdjMatrix() {
		return adjMatrix;
	}

	public IndexValue<Double>[] getPageRanks() {
		return pageRanks;
	}

	
	public void setPageRanks(IndexValue<Double>[] pageRanks) {
		this.pageRanks = pageRanks;
	}

	public TriplesTable<Integer> getTriplesTable() {
		return signalTriplesTable;
	}
	public TriplesTable<Integer> getSignalTriplesTable() {
		return signalTriplesTable;
	}

	public void setSignalTriplesTable(TriplesTable<Integer> signalTriplesTable) {
		this.signalTriplesTable = signalTriplesTable;
	}

	public double[] getEuclideanDistances() {
		return euclideanDistances;
	}

	public void setEuclideanDistances(double[] euclideanDistances) {
		this.euclideanDistances = euclideanDistances;
	}

	public int getTopicNum() {
		return topicNum;
	}

	public void setTopicNum(int topicNum) {
		this.topicNum = topicNum;
	}

	public List<String> getTopicNames() {
		return topicNames;
	}

	public void setTopicNames(List<String> topicNames) {
		this.topicNames = topicNames;
	}
	
}
