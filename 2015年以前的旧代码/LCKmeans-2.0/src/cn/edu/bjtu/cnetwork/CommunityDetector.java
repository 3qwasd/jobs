/**
 *
 **/
package cn.edu.bjtu.cnetwork;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import cn.edu.bjtu.cnetwork.algorithms.ClusteringQualityAssessment;
import cn.edu.bjtu.cnetwork.algorithms.ContentSimilarityCalculater;
import cn.edu.bjtu.cnetwork.algorithms.LDATopicModel;
import cn.edu.bjtu.cnetwork.algorithms.New2ULCKMean;
import cn.edu.bjtu.cnetwork.algorithms.New2ULCMerge;
import cn.edu.bjtu.cnetwork.algorithms.New3ULCKMean;
import cn.edu.bjtu.cnetwork.algorithms.New3ULCMerge;
import cn.edu.bjtu.cnetwork.algorithms.New4ULCKMean;
import cn.edu.bjtu.cnetwork.algorithms.New4ULCMerge;
import cn.edu.bjtu.cnetwork.algorithms.New5ULCKMean;
import cn.edu.bjtu.cnetwork.algorithms.New5ULCMerge;
import cn.edu.bjtu.cnetwork.algorithms.New6ULCKMean;
import cn.edu.bjtu.cnetwork.algorithms.New6ULCMerge;
import cn.edu.bjtu.cnetwork.algorithms.PageRank;
import cn.edu.bjtu.cnetwork.algorithms.SignalPropagate;
import cn.edu.bjtu.cnetwork.algorithms.TopicDisSimilarityCalculater;
import cn.edu.bjtu.cnetwork.algorithms.TopologySimilarityCalculater;
import cn.edu.bjtu.cnetwork.algorithms.ULCKMean;
import cn.edu.bjtu.cnetwork.algorithms.ULCMerge;
import cn.edu.bjtu.cnetwork.algorithms.USCalculater;
import cn.edu.bjtu.cnetwork.handler.SimilarityHandler.MathType;
import cn.edu.bjtu.cnetwork.io.NetworkReader;

/**
 * @author QiaoJian
 *
 */
public class CommunityDetector {

	public enum ATTR_TYPE{
		RUN_LDA,USE_TOPIC_MODEL,USE_ATTR_SRC,USE_TOPIC_MODEL_ONLY
	}
	private enum LINK_TYPE{
		JACCARD,SIGNAL
	}
	private enum CONTENT_TYPE{
		LDA,SRC
	}
	public static void main(String[] args) throws Throwable{
		CommunityDetector detector = new CommunityDetector();
		//String[] names = new String[]{"citeseer","cora","cornell","texas","washington","wisconsin"};
		String[] names = new String[]{"sina_weibo"};
		TopicDisSimilarityCalculater.mathType = MathType.cosin;
		TopologySimilarityCalculater.mathType = MathType.cosin;
		for(int i=0;i<names.length;i++){
			detector.execute(names[i], ATTR_TYPE.USE_TOPIC_MODEL_ONLY,28,true,"",null);
		}
	}
	public  Network executeOne(String networkName,ATTR_TYPE attType,int LDAK,boolean hasGrountTruth,String remark,USCalculater usc,int n) throws Throwable {

		Network network = null;
		NetworkReader reader = new NetworkReader();
		ContentSimilarityCalculater csc = null;
		switch (attType) {
		case RUN_LDA:
			network = reader.readNodeWithContentAndLDAData(networkName, true);
			LDATopicModel model = new LDATopicModel(network);
			if(LDAK > 0)
				model.K = LDAK;
			model.run();
			csc = new TopicDisSimilarityCalculater();
			break;
		case USE_TOPIC_MODEL:
			network = reader.readNodeWithContentAndTopics(networkName, true);
			csc = new TopicDisSimilarityCalculater();
			break;
		case USE_ATTR_SRC:
			network = reader.readNodeWithContent(networkName, true);
			csc = new ContentSimilarityCalculater();
			break;
		case USE_TOPIC_MODEL_ONLY:
			network = reader.readNodeWithTopics(networkName, true,LDAK);
			csc = new TopicDisSimilarityCalculater();
			break;
		}


		SignalPropagate signalPropagate = new SignalPropagate(network, 3);
		signalPropagate.run();
		if(network.pageRankValues == null)
			network.initPageRankValues();
		PageRank pageRank = new PageRank(network);
		pageRank.run();
		network.initDoubleArray(-1);
//		for(int n=8;n<=8;n++){
		int max = Math.min(network.nodeNum/network.topicNum, 100);
		
			long start = System.currentTimeMillis();
			New6ULCKMean ulckMean = new New6ULCKMean(network,null);
			ulckMean.setCsc(csc);
			//ulckMean.alpha = 0.2;
			ulckMean.k = network.topicNum*n;
			ulckMean.run();
			New6ULCMerge merge = new New6ULCMerge(network,null);
			merge.run();
			if(hasGrountTruth)
				network.assignClassFlag();
//			for(Community community:network.getCommunities()){
//				System.out.println(community.toString());
//			}
			long end = System.currentTimeMillis();
			double time = ((double)(end-start))/1000.0d;
			ResultItem item = new ResultItem();
			item.networkName = networkName;
			item.remark = remark;
			item.n = n;
			item.time = time;
			ClusteringQualityAssessment assessment = new ClusteringQualityAssessment(network,item,hasGrountTruth);
			assessment.run();
		
		//processRes(networkName);
		
		return network;
	}
	public  Network execute(String networkName,ATTR_TYPE attType,int LDAK,boolean hasGrountTruth,String remark,USCalculater usc) throws Throwable {

		Network network = null;
		NetworkReader reader = new NetworkReader();
		ContentSimilarityCalculater csc = null;
		switch (attType) {
		case RUN_LDA:
			network = reader.readNodeWithContentAndLDAData(networkName, true);
			LDATopicModel model = new LDATopicModel(network);
			if(LDAK > 0)
				model.K = LDAK;
			model.run();
			csc = new TopicDisSimilarityCalculater();
			break;
		case USE_TOPIC_MODEL:
			network = reader.readNodeWithContentAndTopics(networkName, true);
			csc = new TopicDisSimilarityCalculater();
			break;
		case USE_ATTR_SRC:
			network = reader.readNodeWithContent(networkName, true);
			csc = new ContentSimilarityCalculater();
			break;
		case USE_TOPIC_MODEL_ONLY:
			network = reader.readNodeWithTopics(networkName, true,LDAK);
			csc = new TopicDisSimilarityCalculater();
			break;
		}


		SignalPropagate signalPropagate = new SignalPropagate(network, 3);
		signalPropagate.run();
		if(network.pageRankValues == null)
			network.initPageRankValues();
		PageRank pageRank = new PageRank(network);
		pageRank.run();
		network.initDoubleArray(-1);
//		for(int n=8;n<=8;n++){
		int max = Math.min(network.nodeNum/network.topicNum, 100);
		for(int n=1;n<=max;n++){
			long start = System.currentTimeMillis();
			New6ULCKMean ulckMean = new New6ULCKMean(network,null);
			ulckMean.setCsc(csc);
			//ulckMean.alpha = 0.2;
			ulckMean.k = network.topicNum*n;
			ulckMean.run();
			New6ULCMerge merge = new New6ULCMerge(network,null);
			merge.run();
			if(hasGrountTruth)
				network.assignClassFlag();
//			for(Community community:network.getCommunities()){
//				System.out.println(community.toString());
//			}
			long end = System.currentTimeMillis();
			double time = ((double)(end-start))/1000.0d;
			ResultItem item = new ResultItem();
			item.networkName = networkName;
			item.remark = remark;
			item.n = n;
			item.time = time;
			ClusteringQualityAssessment assessment = new ClusteringQualityAssessment(network,item,hasGrountTruth);
			assessment.run();
		}
		processRes(networkName);
		
		return network;
	}
	public void processRes(String networkName) throws Exception{
		BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("I:\\network\\result\\"+networkName+".txt")));
		String content = null;
		List<ResultItem> items = new ArrayList<>();
		while((content = bufferedReader.readLine())!=null){
			String[] strs = content.split(" |\t|\n",12);
			ResultItem item = new ResultItem();
			item.networkName = strs[0];
			item.remark = strs[1];
			item.mu = Double.valueOf(strs[2].split(":",2)[1]);
			item.kmeanAlpha = Double.valueOf(strs[3].split(":",2)[1]);
			item.mergeAlpha = Double.valueOf(strs[4].split(":",2)[1]);
			item.n = Integer.valueOf(strs[5].split(":",2)[1]);
			item.acc = Double.valueOf(strs[6].split(":",2)[1]);
			item.nmi = Double.valueOf(strs[7].split(":",2)[1]);
			item.pwf = Double.valueOf(strs[8].split(":",2)[1]);
			item.modu = Double.valueOf(strs[9].split(":",2)[1]);
			item.entr = Double.valueOf(strs[10].split(":",2)[1]);
			item.time = Double.valueOf(strs[11].split(":",2)[1]);
			items.add(item);
		}
		ResultItem.sortType = 1;
		Collections.sort(items);
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHssmm");
		BufferedWriter bufferedWriter = new BufferedWriter
				(new FileWriter(new File("I:\\network\\result\\"+networkName+"_acc_"+dateFormat.format(date)+".txt"),false));

		for(int i=0;i<items.size();i++){
			//System.out.println(items.get(i).toString());
			bufferedWriter.write(items.get(i).toString()+"\n");

		}
		bufferedWriter.close();
	}
}
