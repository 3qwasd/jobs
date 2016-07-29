/**
 * @QiaoJian
 */
package cn.edu.bjtu.qj.graph.algorithms;
import java.util.ArrayList;
import java.util.List;

import cn.edu.bjtu.qj.graph.algorithms.datastrut.IndexValue;
import cn.edu.bjtu.qj.graph.component.AdjListNode;
import cn.edu.bjtu.qj.graph.component.Community;
import cn.edu.bjtu.qj.graph.component.Graph;
import cn.edu.bjtu.qj.graph.component.MGraph;

/**
 * @author QiaoJian
 *	
 */
@Deprecated
public class KMean extends Algorithms{

	MGraph graph;
	List<AdjListNode> seeds = new ArrayList<>();
	/* seed num */
	int k = 10;
	/* μ */
	double mu = 0;
	
	List<Community> communities;
	
	double[] euclideanDistances;
	
	QKSort qkSort;
	
	/**
	 * 
	 */
	public KMean() {
		// TODO Auto-generated constructor stub
	}

	public KMean(MGraph graph) {
		this.graph = graph;
		euclideanDistances = graph.getEuclideanDistances();
	}

	public void execute(){
		qkSort = new QKSort();
		calculateMu();
		selectBeginSeeds();
		initializeCommunities();
		
		for(int t=0;t<10;t++){
			if(t!=0)
				afreshSeeds();
			cluster();
		}
		
	}
	public void execute(List<AdjListNode> seeds){
		qkSort = new QKSort();
		this.seeds = seeds;
		initializeCommunities();
		
		for(int t=0;t<10;t++){
			if(t!=0)
				afreshSeeds();
			cluster();
		}
	}
	private void cluster(){
		
		Community blong = null;
		double minEucDis = 10.0d;
		for(int i=0;i<graph.getNodeNum();i++){
			AdjListNode node = (AdjListNode) graph.getNode(i);
			if(seeds.contains(node))
				continue;
			for(Community community:communities){
				double euclideanDistance = graph.getEuclideanDistance(community.getCenterNode().getId()-1,node.getId()-1);
				if(euclideanDistance<minEucDis){
					minEucDis = euclideanDistance;
					blong = community;
				}
			}
			blong.addNode(node);
		}
	}
	public void calculateMu(){
		double maxSim = (double) graph.getAttribute("max_similarity");
		double minSim = (double) graph.getAttribute("min_similarity");
		mu = (maxSim - minSim)/2;
	}
	/**
	 * 重新选择种子
	 */
	private void afreshSeeds(){
		seeds.clear();
		for(Community community : communities){
			
			IndexValue<Double>[] pageRanks = new IndexValue[community.getPageRanks().size()];
			pageRanks = community.getPageRanks().toArray(pageRanks);
			pageRanks = qkSort.execute(pageRanks, 1);
			AdjListNode newSeed = (AdjListNode) graph.getNode(pageRanks[0].id-1);
			seeds.add(newSeed);
			community.initialize();
			community.setCenterNode(newSeed);
			System.out.println(newSeed.getName());
		}
		System.out.println("-----------------------------------------");
	}
	public List<AdjListNode> selectBeginSeeds(double mu){
		this.mu = mu;
		this.selectBeginSeeds();
		return this.getSeeds();
	}
	/**
	 * 选择初始种子
	 */
	private void selectBeginSeeds(){
		this.communities = new ArrayList<>();
		IndexValue<Double>[] pageRanks = graph.getPageRanks();
		int index = 0;
		while(seeds.size()<10){
			AdjListNode newSeed = (AdjListNode) graph.getNode(pageRanks[index].id-1);
			boolean flag = true;
			for(int i=0;i<seeds.size();i++){
				AdjListNode seed = seeds.get(i);
				
				double euclideanDistance = graph.getEuclideanDistance(newSeed.getId()-1, seed.getId()-1);
				if(euclideanDistance<mu){
					flag = false;
					break;
				}
			}
			if(flag){
				seeds.add(newSeed);
			}
			index++;
		}
	}
	private void initializeCommunities(){
		for(AdjListNode node:seeds){
			Community community = new Community(node);
			communities.add(community);
		}
	}
	@Override
	public void attachGraph(Graph graph) {
		// TODO Auto-generated method stub
		this.graph = (MGraph) graph;
		euclideanDistances = this.graph.getEuclideanDistances();
	}

	public List<AdjListNode> getSeeds() {
		return seeds;
	}

	public List<Community> getCommunities() {
		return communities;
	}
}
