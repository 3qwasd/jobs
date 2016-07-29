/**
 *
 **/
package cn.edu.bjtu.cnetwork.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import cn.edu.bjtu.cnetwork.Community;
import cn.edu.bjtu.cnetwork.CommunityDetector;
import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.cnetwork.ResultItem;
import cn.edu.bjtu.cnetwork.CommunityDetector.ATTR_TYPE;
import cn.edu.bjtu.cnetwork.algorithms.ClusteringQualityAssessment;
import cn.edu.bjtu.cnetwork.algorithms.ContentSimilarityCalculater;
import cn.edu.bjtu.cnetwork.algorithms.PageRank;
import cn.edu.bjtu.cnetwork.algorithms.SignalPropagate;
import cn.edu.bjtu.cnetwork.algorithms.TopicDisSimilarityCalculater;
import cn.edu.bjtu.cnetwork.algorithms.TopologySimilarityCalculater;
import cn.edu.bjtu.cnetwork.algorithms.ULCKMean;
import cn.edu.bjtu.cnetwork.algorithms.ULCMerge;
import cn.edu.bjtu.cnetwork.handler.SimilarityHandler.MathType;
import cn.edu.bjtu.cnetwork.io.NetwokWriter;
import cn.edu.bjtu.cnetwork.io.NetworkReader;
import cn.edu.bjtu.qj.graph.component.Node;
import cn.edu.bjtu.utils.MathUtils;

/**
 * @author QiaoJian
 *
 */
public class SinaWeiboTest {

	@Test
	public void testKRLC() throws Throwable{
		CommunityDetector detector = new CommunityDetector();
		//String[] names = new String[]{"citeseer","cora","cornell","texas","washington","wisconsin"};
		String[] names = new String[]{"sina_weibo"};
		TopicDisSimilarityCalculater.mathType = MathType.cosin;
		TopologySimilarityCalculater.mathType = MathType.cosin;
		for(int i=0;i<names.length;i++){
			Network network = detector.executeOne(names[i], ATTR_TYPE.USE_TOPIC_MODEL_ONLY,10,true,"",null,70);
			int k=0;
			for(Community community:network.getCommunities()){
				BufferedWriter writer = new BufferedWriter(new FileWriter(new File("I:\\weibo_result\\"+k+".txt")));
				writer.write("类标："+community.getClassFlag()+"\n");
				writer.write("中心点："+community.getSeed().getName()+" "+community.getSeed().getAttribute("class_flag"));
				Map<String,Integer> map = this.countTopics(community);
				Map<String,String> map1 = this.countTopicsP(community);
				writer.write(community.getNodes().size()+"\n");
				writer.write(map1.toString()+"\n");
				writer.write(map.toString()+"\n");
				for(Node node : community.getNodes()){
					writer.write(node.getId()+"\t"+node.getName()+"\t"+
							node.getAttribute("class_flag")+"\t"+node.getAttribute("class_flag_new")+"\n");
				}
				writer.close();
				k++;
			}
			detector.processRes(names[i]);
		}
	}

	@Test
	public void testKRL() throws Throwable{
		String networkName = "sina_weibo";
		Network network = null;
		NetworkReader networkReader = new NetworkReader();
		network = networkReader.readNodeWithTopics(networkName, true);

		TopologySimilarityCalculater tsc = null;
		long start = System.currentTimeMillis();
		SignalPropagate signalPropagate = new SignalPropagate(network, 3);
		signalPropagate.run();
		tsc = new TopologySimilarityCalculater();
		ContentSimilarityCalculater csc = new TopicDisSimilarityCalculater();

		if(network.pageRankValues == null)
			network.initPageRankValues();
		PageRank pageRank = new PageRank(network);
		pageRank.run();
		network.initDoubleArray(-1);
		ULCKMean ulckMean = new ULCKMean(network);
		ulckMean.setTsc(tsc);;
		ulckMean.setCsc(csc);
		ulckMean.alpha = 1;
		ulckMean.k = network.topicNum;
		ulckMean.run();
		ULCMerge merge = new ULCMerge(network);

		merge.alpha = 1;
		merge.run();

		long end = System.currentTimeMillis();
		double time = ((double)(end-start))/1000.0d;
		ResultItem item = new ResultItem();
		item.n = 1;
		item.kmeanAlpha = 0;
		item.mergeAlpha = 0;
		item.time = time;
		item.networkName = networkName;
		item.remark = "Link";
		network.assignClassFlag();
		ClusteringQualityAssessment assessment = new ClusteringQualityAssessment(network,item,true);
		assessment.run();
		System.out.println(item.toString());
		int k=0;
		for(Community community:network.getCommunities()){
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("I:\\weibo_result\\"+k+".txt")));
			writer.write("类标："+community.getClassFlag()+"\n");
			writer.write("中心点："+community.getSeed().getName()+" "+community.getSeed().getAttribute("class_flag"));
			Map<String,Integer> map = this.countTopics(community);
			writer.write(map.toString()+"\n");
			for(Node node : community.getNodes()){
				writer.write(node.getId()+"\t"+node.getName()+"\t"+
						node.getAttribute("class_flag")+"\t"+node.getAttribute("class_flag_new")+"\n");
			}
			writer.close();
			k++;
		}
	}
	@Test
	public void testKRC() throws Throwable{
		int LDAK = 28;
		String networkName = "sina_weibo";
		Network network = null;
		NetworkReader networkReader = new NetworkReader();
		network = networkReader.readNodeWithTopics(networkName, true,LDAK);

		TopologySimilarityCalculater tsc = null;
		long start = System.currentTimeMillis();
		SignalPropagate signalPropagate = new SignalPropagate(network, 3);
		signalPropagate.run();
		tsc = new TopologySimilarityCalculater();
		ContentSimilarityCalculater csc = new TopicDisSimilarityCalculater();

		if(network.pageRankValues == null)
			network.initPageRankValues();
		PageRank pageRank = new PageRank(network);
		pageRank.run();
		network.initDoubleArray(-1);
		ULCKMean ulckMean = new ULCKMean(network);
		ulckMean.setTsc(tsc);;
		ulckMean.setCsc(csc);
		ulckMean.alpha = 0;
		ulckMean.k = network.topicNum;
		ulckMean.run();
		ULCMerge merge = new ULCMerge(network);

		merge.alpha = 0;
		merge.run();

		long end = System.currentTimeMillis();
		double time = ((double)(end-start))/1000.0d;
		ResultItem item = new ResultItem();
		item.n = 1;
		item.kmeanAlpha = 0;
		item.mergeAlpha = 0;
		item.time = time;
		item.networkName = networkName;
		item.remark = "Link";
		network.assignClassFlag();
		ClusteringQualityAssessment assessment = new ClusteringQualityAssessment(network,item,true);
		assessment.run();
		System.out.println(item.toString());
		int k=0;
		for(Community community:network.getCommunities()){
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("I:\\weibo_result\\"+k+".txt")));
			writer.write("类标："+community.getClassFlag()+"\n");
			writer.write("中心点："+community.getSeed().getName()+" "+community.getSeed().getAttribute("class_flag"));
			Map<String,Integer> map = this.countTopics(community);
			writer.write(map.toString()+"\n");
			for(Node node : community.getNodes()){
				writer.write(node.getId()+"\t"+node.getName()+"\t"+
						node.getAttribute("class_flag")+"\t"+node.getAttribute("class_flag_new")+"\n");
			}
			writer.close();
			k++;
		}
	}
	public Map<String,String> countTopicsP(Network network){
		Map<String,String> res = new HashMap<String,String>();
		Map<String,Integer> count = new HashMap<String, Integer>();
		String[] topicsNames={
				"财经",
				"文艺",
				"时尚",
				"时政",
				"体育",
				"科技",
				"生活",
				"娱乐",
				"亲子",
				"公益"
		};

		for(int i=0;i<topicsNames.length;i++){
			count.put(topicsNames[i], 0);
		}
		double sum = 0;
		for(Node node : network.getNodes()){
			String key = (String) node.getAttribute("class_flag");
			int num = count.get(key)+1;
			count.put(key, num);
			sum ++;
		}
		for(String key:count.keySet()){
			String val = MathUtils.cutDouble(count.get(key)/sum,3)*100+"%";
			res.put(key, val);
		}
		return res;
	}
	public Map<String,Integer> countTopics(Network network){
		Map<String,Integer> count = new HashMap<String, Integer>();
		String[] topicsNames={
				"财经",
				"文艺",
				"时尚",
				"时政",
				"体育",
				"科技",
				"生活",
				"娱乐",
				"亲子",
				"公益"
		}; 
		for(int i=0;i<topicsNames.length;i++){
			count.put(topicsNames[i], 0);
		}

		for(Node node : network.getNodes()){
			String key = (String) node.getAttribute("class_flag");
			int num = count.get(key)+1;
			count.put(key, num);
		}
		return count;
	}
	@Test
	public void compute(){
		int[] array = new int[]{7,
				22,
				16,
				19,
				1,
				7,
				1,
				78,
				3,
				4,
		};
		double sum = 0;
		for(int i=0;i<array.length;i++){
			sum+=array[i];
		}
		for(int i=0;i<array.length;i++){
			System.out.println(MathUtils.cutDouble(array[i]/sum, 4)*100+"%");
		}
	}
	@Test
	public void countTopicDis() throws Throwable{
		Network network = null;
		NetworkReader networkReader = new NetworkReader();
		network = networkReader.readNodeWithTopics("sina_weibo", true,10);
		Map<String,Integer> count = new HashMap<String, Integer>();
		String[] topicsNames={
				"财经",
				"文艺",
				"时尚",
				"时政",
				"体育",
				"科技",
				"生活",
				"娱乐",
				"亲子",
				"公益"
		};

		for(int i=0;i<topicsNames.length;i++){
			count.put(topicsNames[i], 0);
		}

		for(Node node : network.getNodes()){
			String key = (String) node.getAttribute("class_flag");
			int num = count.get(key)+1;
			count.put(key, num);
		}
		double sum = 0;
		for(String key:count.keySet()){
			sum += count.get(key);
		}
		System.out.println(sum);
		for(String key:count.keySet()){

			System.out.println(key+"\t"+MathUtils.cutDouble(count.get(key)/sum,4)*100+"%");
		}
		System.out.println(count);
	}
}
