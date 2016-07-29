/**
 * @QiaoJian
 */
package cn.edu.bjtu.cnetwork;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import jgibblda.LDADataset;
import cn.edu.bjtu.qj.graph.algorithms.datastrut.IndexValue;
import cn.edu.bjtu.qj.graph.algorithms.datastrut.Triple;
import cn.edu.bjtu.qj.graph.algorithms.datastrut.TriplesTable;
import cn.edu.bjtu.qj.graph.component.AdjListGraph;
import cn.edu.bjtu.qj.graph.component.Edge;
import cn.edu.bjtu.qj.graph.component.Node;


/**
 * @author QiaoJian
 *
 */
public class Network extends AdjListGraph{

	public String networkName;

	public List<String> classFlags = new ArrayList<String>();

	public int attrSize;

	public int topicNum;
	/*page rank 值*/
	public List<ComparableValue> pageRankValues;

	private List<Community> communities;
	/*邻接矩阵*/
	public int[][] adjMatrix;
	/*信号量的三元组表*/
	public TriplesTable<Double> signalTriplesTable;

	/*节点相似度矩阵,存储上三角*/
	public double[] similarities;

	public double[] topologySimilarities;

	//节点局部相似度
	public double[] jaccardSimilarities;

	public double[] contentSimilarities;


	public double[] uniteSimilarities;

	public LDADataset ldaDataset;

	private List<NetworkEdge> sampleEdges ;
	/*topic name*/
	String[] topicNames;
	/**
	 * 
	 */
	public Network(String networkName,boolean isDirected) {
		// TODO Auto-generated constructor stub
		this.networkName = networkName;
		this.setDirected(isDirected);
	}
	public Network(){

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
		adjMatrix = new int[nodeNum][nodeNum];
	}
	public void initDoubleArray(){
		int num = (nodeNum-1)*nodeNum/2;
		this.contentSimilarities = new double[num];
		this.topologySimilarities = new double[num];
		this.uniteSimilarities = new double[num];
		this.jaccardSimilarities = new double[num];
		for(int i=0;i<num;i++){
			jaccardSimilarities[i] = -1;
		}
	}
	public void initDoubleArray(double initValue){
		int num = (nodeNum-1)*nodeNum/2;
		this.contentSimilarities = new double[num];
		this.topologySimilarities = new double[num];
		for(int i=0;i<num;i++){
			contentSimilarities[i] = initValue;
			topologySimilarities[i] = initValue;
		}
	}
	/*
	 * 信号向量标准化
	 */
	public double[] singalStandardize(int nodeId){
		double[] standardizeSingal = new double[nodeNum];
		int rowPos = signalTriplesTable.postions[nodeId-1];
		int nextRowPos = nodeId==signalTriplesTable.rn?signalTriplesTable.length:signalTriplesTable.postions[nodeId];
		double sum = 0;
		for(int pos = rowPos;pos<nextRowPos;pos++){
			sum+=signalTriplesTable.datas[pos].getWeight();
		}
		for(int pos = rowPos;pos<nextRowPos;pos++){
			standardizeSingal[signalTriplesTable.datas[pos].col] = signalTriplesTable.datas[pos].getWeight()/sum;
		}
		return standardizeSingal;
	}
	public void initPageRankValues(){
		pageRankValues = new ArrayList<ComparableValue>();
	}
	public void createSignalTriplesTable(){
		signalTriplesTable = new TriplesTable<>();
		signalTriplesTable.rn = nodeNum;
		signalTriplesTable.cn = nodeNum;
		signalTriplesTable.postions = new int[signalTriplesTable.rn];
		List<Triple<Double>> temp = new ArrayList<>();
		for(int row=0;row<this.nodeNum;row++){
			signalTriplesTable.postions[row] = temp.size();
			for(int col=0;col<this.nodeNum;col++){
				if(row==col){
					Triple<Double> triple = new Triple<Double>(row, col, (double) (adjMatrix[row][col]+1));
					temp.add(triple);
				}else{
					if(adjMatrix[row][col]!=0){
						Triple<Double> triple = new Triple<Double>(row, col, (double) adjMatrix[row][col]);
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
	public double getUniteSimilarities(int i,int j){
		int k=0;
		if(i==j)
			return 1;
		if(i<j){
			k = i*(this.nodeNum-i-1)+i*(i+1)/2+(j-i-1);
		}else{
			k = j*(this.nodeNum-j-1)+j*(j+1)/2+(i-j-1);
		}
		return uniteSimilarities[k];
	}
	public int getK(int i,int j){
		int k=0;
		if(i<j){
			k = i*(this.nodeNum-i-1)+i*(i+1)/2+(j-i-1);
		}else{
			k = j*(this.nodeNum-j-1)+j*(j+1)/2+(i-j-1);
		}
		return k;
	}
	/**
	 * 从上三角矩阵中取值
	 * @param row
	 * @param col
	 * @return
	 */
	public double getTopologySimilarities(int i,int j){
		int k=0;
		if(i==j)
			return 1;
		if(i<j){
			k = i*(this.nodeNum-i-1)+i*(i+1)/2+(j-i-1);
		}else{
			k = j*(this.nodeNum-j-1)+j*(j+1)/2+(i-j-1);
		}
		return topologySimilarities[k];
	}
	/**
	 * 从上三角矩阵中取值
	 * @param row
	 * @param col
	 * @return
	 */
	public double getContentSimilarities(int i,int j){
		int k=0;
		if(i==j)
			return 1;
		if(i<j){
			k = i*(this.nodeNum-i-1)+i*(i+1)/2+(j-i-1);
		}else{
			k = j*(this.nodeNum-j-1)+j*(j+1)/2+(i-j-1);
		}
		return contentSimilarities[k];
	}
	/**
	 * 从上三角矩阵中取值
	 * @param row
	 * @param col
	 * @return
	 */
	public double getJaccardSimilarities(int i,int j){
		int k=0;
		if(i==j)
			return 1;
		if(i<j){
			k = i*(this.nodeNum-i-1)+i*(i+1)/2+(j-i-1);
		}else{
			k = j*(this.nodeNum-j-1)+j*(j+1)/2+(i-j-1);
		}
		return jaccardSimilarities[k];
	}
	/**
	 * 从上三角矩阵中取值
	 * @param row
	 * @param col
	 * @return
	 */
	public double getSimilarity(int i,int j){
		int k=0;
		if(i==j)
			return 1;
		if(i<j){
			k = i*(this.nodeNum-i-1)+i*(i+1)/2+(j-i-1);
		}else{
			k = j*(this.nodeNum-j-1)+j*(j+1)/2+(i-j-1);
		}
		return similarities[k];
	}
	@Override
	public NetworkNode createNode(int id, String name, Map<String, Object> attributes) {
		// TODO Auto-generated method stub
		NetworkNode networkNode = new NetworkNode(id, name);
		networkNode.setAttributes(attributes);
		this.addNode(networkNode);
		return networkNode;
	}
	public NetworkEdge createEdge(Node sourceNode, Node targetNode) {
		// TODO Auto-generated method stub
		NetworkEdge edge = new NetworkEdge();
		edge.setDirected(isDirected());
		edge.setSourceNode(sourceNode);
		edge.setTargetNode(targetNode);
		this.addEdge(edge);
		return edge;
	}
	@Override
	public void addNode(Node node) {
		// TODO Auto-generated method stub
		super.addNode(node);
	}
	@Override
	public void addEdge(Edge edge) {
		// TODO Auto-generated method stub
		this.getEdges().add(edge);
		this.addAdjMatrix(edge.getSourceNode().getId()-1, edge.getTargetNode().getId()-1);
	}
	private void addAdjMatrix(int i,int j){
		adjMatrix[i][j] = 1;
		if(!isDirected()){
			adjMatrix[j][i] = 1;
		}
	}
	/**
	 * @return the communities
	 */
	public List<Community> getCommunities() {
		return communities;
	}
	/**
	 * @param communities the communities to set
	 */
	public void setCommunities(List<Community> communities) {
		this.communities = communities;
	}
	/**
	 * @return the sampleEdges
	 */
	public List<NetworkEdge> getSampleEdges() {
		return sampleEdges;
	}
	/**
	 * @param sampleEdges the sampleEdges to set
	 */
	public void setSampleEdges(List<NetworkEdge> sampleEdges) {
		this.sampleEdges = sampleEdges;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public void createNodeNgbr(){
		for(int i=0;i<this.nodeNum;i++){
			NetworkNode node = (NetworkNode) this.getNode(i);
			for(int j=0;j<node.getOutEdges().size();j++){
				node.addNeighbours((NetworkNode) node.getOutEdges().get(j).getTargetNode());
			}
			//			for(int j=0;j<node.getInEdges().size();j++){
			//				node.addNeighbours((NetworkNode) node.getInEdges().get(j).getSourceNode());
			//			}
		}
	}
	public void addClassFlag(String classFlag){
		if(!this.classFlags.contains(classFlag))
			this.classFlags.add(classFlag);
	}
	public void assignClassFlag(){
		for(int i=0;i<topicNum;i++){
			Community community = this.getCommunities().get(i);
			community.id = i;
			community.computeClassFlag();
		}
	}
}
