/**
 * @QiaoJian
 */
package cn.edu.bjtu.cnetwork.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import cn.edu.bjtu.cnetwork.ComparableValue;
import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.cnetwork.NetworkEdge;
import cn.edu.bjtu.cnetwork.NetworkNode;
import cn.edu.bjtu.cnetwork.io.NetwokWriter;
import cn.edu.bjtu.qj.graph.component.AdjListGraph;
import cn.edu.bjtu.qj.graph.io.GMLReader;
import cn.edu.bjtu.utils.MathUtils;

/**
 * @author QiaoJian
 *
 */
public class NetworkWriteTest {

	/**
	 * 
	 */
	public NetworkWriteTest() {
		// TODO Auto-generated constructor stub
	}
	@Test
	public void newDatas() throws Exception{
		String path="I:\\network\\flickr\\";
//		BufferedReader reader = new BufferedReader(new FileReader(new File(path+"link.txt")));
		
		String content = null;
//		List<Integer> nodes = new ArrayList<Integer>();
//		int edgesNum = 0;
//		while((content=reader.readLine())!=null){
//			String[] strs = content.split(" |\t|\n",2);
//			int idx = Integer.valueOf(strs[0]);
//			int idy = Integer.valueOf(strs[1]);
//			if(!nodes.contains(idx)){
//				nodes.add(idx);
//			}
//			if(!nodes.contains(idy)){
//				nodes.add(idy);
//			}
//			edgesNum++;
//		}
//		Collections.sort(nodes);
//		reader.close();
//		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(path+"nodes.txt")));
//		//bufferedWriter.write();
//		bufferedWriter.write(16710+"\t"+edgesNum+"\t"+"400"+"\n");
//		for(int i=1;i<=16710;i++){
//			bufferedWriter.write(i+"\t"+i+"\t"+"null"+"\n");
//		}
//		bufferedWriter.close();
		List<Integer> attr = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader(new File(path+"content_src_old.txt")));
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(path+"content_src.txt")));
		bufferedWriter.write("102\n");
		int index = 1;
		String text = "";
		while((content = reader.readLine())!=null){
			System.out.println(content.trim());
		
			String strs[] = content.trim().split(" |\t|\n",2);
			int id = Integer.valueOf(strs[0]);
			int word = 0; 
			if(strs.length<2){
				word = 1;
			}else
				word = Integer.valueOf(strs[1]);
			if(!attr.contains(word)){
				attr.add(word);
			}
			if(id == index){
				text += word+"\t";
			}else{
				bufferedWriter.write(text+"\n");
				index++;
				text = "";
				text +=word+"\t";
			}
		}
		bufferedWriter.write(text+"\n");
		Collections.sort(attr);
		System.out.println(attr);
		System.out.println(attr.size());
		reader.close();
		bufferedWriter.close();
	}
	//@Test
	public void gml2Txt() throws Exception{
		GMLReader gmlReader = new GMLReader();
		AdjListGraph network = (AdjListGraph) gmlReader.read("Z:\\network\\football\\football.gml");
		network.nodeNum = network.getNodes().size();
		network.edgeNum = network.getEdges().size();
		if(network.getNode(0).getId() == 0){
			for(int i=0;i<network.nodeNum;i++){
				network.getNode(i).setId(i+1);
			}
		}
		NetwokWriter writer = new NetwokWriter();
		writer.write(network, "I:\\network\\football\\", 12);
	}
	//@Test
	public void readWeiboText() throws Exception{
		GMLReader gmlReader = new GMLReader();
		AdjListGraph network = (AdjListGraph) gmlReader.read("I:\\network\\weibo\\old\\weibo.gml");
		network.nodeNum = network.getNodes().size();
		network.edgeNum = network.getEdges().size();
		for(int i=0;i<network.nodeNum;i++){
			network.getNode(i).putAttribute("class_flag", "none");
		}
		BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("I:\\test_data\\lda\\model-final.theta")));
		String content = null;
		int nodeCount = 0;
		String[] topicsNames={"生活","财经","文化","时政","艺术","体育","时尚","行业","娱乐","科技"};
		ComparableValue.SORT_TYPE = 1;
		while((content = bufferedReader.readLine())!=null){
			String[] val = content.split(" |\t|\n",10);
			List<ComparableValue> topics = new ArrayList<>();
			for(int i=0;i<val.length;i++){
				double value = Double.valueOf(val[i]);
				ComparableValue comparableValue = new ComparableValue(i, value);
				topics.add(comparableValue);
			}
			Collections.sort(topics);
			String classFlag = topicsNames[topics.get(0).id]+" ";
			for(int k = 0;k<3;k++){
				classFlag += topicsNames[topics.get(k).id]+":"+MathUtils.cutDouble((Double) topics.get(k).value, 2)+" ";
			}
			network.getNode(nodeCount++).putAttribute("class_flag", classFlag.trim());
			if(nodeCount>=network.nodeNum)
				break;
		}
		bufferedReader.close();
		NetwokWriter writer = new NetwokWriter();
		writer.write(network, "I:\\test_data\\", 10);
	}
}
