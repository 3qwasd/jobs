/**
 * @QiaoJian
 */
package cn.edu.bjtu.cnetwork.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import jgibblda.LDADataset;
import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.cnetwork.NetworkNode;

/**
 * @author QiaoJian
 *
 */
public class NetworkReader {

	public static String DIRECTOR = "I:/network/";
	/**
	 * 
	 */
	public NetworkReader() {
		// TODO Auto-generated constructor stub
	}
	public Network readNode(String networkName,boolean isDirected) throws Throwable{
		String dataDir = DIRECTOR+networkName+File.separator;
		File nodeFile = new File(dataDir+"nodes.txt");
		Reader nodeFileReader = null;
		try {
			nodeFileReader = new FileReader(nodeFile);
		} catch (Exception e) {
			throw new Exception(networkName+" node file is not found!",e);
		}
		BufferedReader nodeBufferedReader = new BufferedReader(nodeFileReader);

		Network network = new Network(networkName, isDirected);
		Map<Integer,NetworkNode> temp = new HashMap<Integer,NetworkNode>();
		String text = null;
		int contentNum = 0;
		try {
			text = nodeBufferedReader.readLine();
		} catch (IOException e) {
			nodeBufferedReader.close();
			throw new Exception("Network files exception!",e);
		}

		String[] strs = text.split(" |\t|\n", 3);
		network.nodeNum = Integer.valueOf(strs[0].trim());
		network.edgeNum = Integer.valueOf(strs[1].trim());
		network.topicNum = Integer.valueOf(strs[2].trim());
		
		network.init();
		for(int i=0;i<network.nodeNum;i++){
			try {
				text = nodeBufferedReader.readLine();
			} catch (IOException e) {
				nodeBufferedReader.close();
				throw new Exception("Network files exception!",e);
			}
			strs = text.split(" |\t|\n", 3);
			Map<String,Object> attributes = new HashMap<String, Object>();
			attributes.put("class_flag", strs[2]);
			network.addClassFlag(strs[2]);
			NetworkNode node = network.createNode(Integer.valueOf(strs[0].trim()), strs[1], attributes);
			
			
			temp.put(node.getId(), node);
		}
		try {
			nodeBufferedReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File edgeFile = new File(dataDir+"edges.txt");
		FileReader edgeFileReader = null;
		BufferedReader edgeBufferReader = null;
		try{
			edgeFileReader = new FileReader(edgeFile);
			edgeBufferReader = new BufferedReader(edgeFileReader);
		}catch(Exception e){
			throw new Exception("Network files exception!",e);	
		}
		for(int i=0;i<network.edgeNum;i++){
			try {
				text = edgeBufferReader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				edgeBufferReader.close();
				throw new Exception("Network files exception!",e);	
			}
			strs = text.split(" |\t|\n",2);
			NetworkNode source = temp.get(Integer.valueOf(strs[0].trim()));
			NetworkNode target = temp.get(Integer.valueOf(strs[1].trim()));
			network.createEdge(source, target);
		}

		edgeBufferReader.close();
	
		return network;
	}
	public Network readNodeWithTopics(String networkName,boolean isDirected,int topicModelNum) throws Throwable{
		String dataDir = DIRECTOR+networkName+File.separator;
		File nodeFile = new File(dataDir+"nodes.txt");
		File nodeContentFile = new File(dataDir+"content.txt");
		Reader nodeFileReader = null;
		Reader nodeContentFileReader = null;
		try {
			nodeFileReader = new FileReader(nodeFile);
			nodeContentFileReader = new FileReader(nodeContentFile);
		} catch (Exception e) {
			throw new Exception(networkName+" node file is not found!",e);
		}
		BufferedReader nodeBufferedReader = new BufferedReader(nodeFileReader);
		BufferedReader contentBufferedReader = new BufferedReader(nodeContentFileReader);

		Network network = new Network(networkName, isDirected);
		Map<Integer,NetworkNode> temp = new HashMap<Integer,NetworkNode>();
		String text = null;

		text = nodeBufferedReader.readLine();
			

		String[] strs = text.split(" |\t|\n", 3);
		network.nodeNum = Integer.valueOf(strs[0].trim());
		network.edgeNum = Integer.valueOf(strs[1].trim());
		network.topicNum = Integer.valueOf(strs[2].trim());
		int contentNum = topicModelNum;
		
		network.init();
		for(int i=0;i<network.nodeNum;i++){
			try {
				text = nodeBufferedReader.readLine();
			} catch (IOException e) {
				nodeBufferedReader.close();
				contentBufferedReader.close();
				throw new Exception("Network files exception!",e);
			}
			strs = text.split(" |\t|\n", 4);
			Map<String,Object> attributes = new HashMap<String, Object>();
			attributes.put("class_flag", strs[2]);
			network.addClassFlag(strs[2]);
			if(strs.length>3){
				attributes.put("remark", strs[3]);
			}
			NetworkNode node = network.createNode(Integer.valueOf(strs[0].trim()), strs[1], attributes);
			try {
				text = contentBufferedReader.readLine();
			} catch (IOException e) {
				nodeBufferedReader.close();
				contentBufferedReader.close();
				throw new Exception("Network files exception!",e);			
			}
			strs = text.split(" |\t|\n",contentNum);
			node.nodeTopicDis = new double[contentNum];
			for(int j = 0;j<strs.length;j++){
				double value = Double.valueOf(strs[j]);
				node.nodeTopicDis[j] = value;
			}
			temp.put(node.getId(), node);
		}
		try {
			nodeBufferedReader.close();
			contentBufferedReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File edgeFile = new File(dataDir+"edges.txt");
		FileReader edgeFileReader = null;
		BufferedReader edgeBufferReader = null;
		try{
			edgeFileReader = new FileReader(edgeFile);
			edgeBufferReader = new BufferedReader(edgeFileReader);
		}catch(Exception e){
			throw new Exception("Network files exception!",e);	
		}
		for(int i=0;i<network.edgeNum;i++){
			try {
				text = edgeBufferReader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				edgeBufferReader.close();
				throw new Exception("Network files exception!",e);	
			}
			strs = text.split(" |\t|\n",2);
			NetworkNode source = temp.get(Integer.valueOf(strs[0].trim()));
			NetworkNode target = temp.get(Integer.valueOf(strs[1].trim()));
			network.createEdge(source, target);
		}

		edgeBufferReader.close();
	
		return network;
	}

	public Network readNodeWithTopics(String networkName,boolean isDirected) throws Throwable{
		String dataDir = DIRECTOR+networkName+File.separator;
		File nodeFile = new File(dataDir+"nodes.txt");
		File nodeContentFile = new File(dataDir+"content.txt");
		Reader nodeFileReader = null;
		Reader nodeContentFileReader = null;
		try {
			nodeFileReader = new FileReader(nodeFile);
			nodeContentFileReader = new FileReader(nodeContentFile);
		} catch (Exception e) {
			throw new Exception(networkName+" node file is not found!",e);
		}
		BufferedReader nodeBufferedReader = new BufferedReader(nodeFileReader);
		BufferedReader contentBufferedReader = new BufferedReader(nodeContentFileReader);

		Network network = new Network(networkName, isDirected);
		Map<Integer,NetworkNode> temp = new HashMap<Integer,NetworkNode>();
		String text = null;

		text = nodeBufferedReader.readLine();
			

		String[] strs = text.split(" |\t|\n", 3);
		network.nodeNum = Integer.valueOf(strs[0].trim());
		network.edgeNum = Integer.valueOf(strs[1].trim());
		network.topicNum = Integer.valueOf(strs[2].trim());
		int contentNum = network.topicNum;
		
		network.init();
		for(int i=0;i<network.nodeNum;i++){
			try {
				text = nodeBufferedReader.readLine();
			} catch (IOException e) {
				nodeBufferedReader.close();
				contentBufferedReader.close();
				throw new Exception("Network files exception!",e);
			}
			strs = text.split(" |\t|\n", 4);
			Map<String,Object> attributes = new HashMap<String, Object>();
			attributes.put("class_flag", strs[2]);
			network.addClassFlag(strs[2]);
			if(strs.length>3){
				attributes.put("remark", strs[3]);
			}
			NetworkNode node = network.createNode(Integer.valueOf(strs[0].trim()), strs[1], attributes);
			try {
				text = contentBufferedReader.readLine();
			} catch (IOException e) {
				nodeBufferedReader.close();
				contentBufferedReader.close();
				throw new Exception("Network files exception!",e);			
			}
			strs = text.split(" |\t|\n",contentNum);
			node.nodeTopicDis = new double[contentNum];
			for(int j = 0;j<strs.length;j++){
				double value = Double.valueOf(strs[j]);
				node.nodeTopicDis[j] = value;
			}
			temp.put(node.getId(), node);
		}
		try {
			nodeBufferedReader.close();
			contentBufferedReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File edgeFile = new File(dataDir+"edges.txt");
		FileReader edgeFileReader = null;
		BufferedReader edgeBufferReader = null;
		try{
			edgeFileReader = new FileReader(edgeFile);
			edgeBufferReader = new BufferedReader(edgeFileReader);
		}catch(Exception e){
			throw new Exception("Network files exception!",e);	
		}
		for(int i=0;i<network.edgeNum;i++){
			try {
				text = edgeBufferReader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				edgeBufferReader.close();
				throw new Exception("Network files exception!",e);	
			}
			strs = text.split(" |\t|\n",2);
			NetworkNode source = temp.get(Integer.valueOf(strs[0].trim()));
			NetworkNode target = temp.get(Integer.valueOf(strs[1].trim()));
			network.createEdge(source, target);
		}

		edgeBufferReader.close();
	
		return network;
	}
	public Network readNodeWithContent(String networkName,boolean isDirected) throws Throwable{
		String dataDir = DIRECTOR+networkName+File.separator;
		File nodeFile = new File(dataDir+"nodes.txt");
		File nodeContentFile = new File(dataDir+"content_src.txt");
		Reader nodeFileReader = null;
		Reader nodeContentFileReader = null;
		try {
			nodeFileReader = new FileReader(nodeFile);
			nodeContentFileReader = new FileReader(nodeContentFile);
		} catch (Exception e) {
			throw new Exception(networkName+" node file is not found!",e);
		}
		BufferedReader nodeBufferedReader = new BufferedReader(nodeFileReader);
		BufferedReader contentBufferedReader = new BufferedReader(nodeContentFileReader);

		Network network = new Network(networkName, isDirected);
		Map<Integer,NetworkNode> temp = new HashMap<Integer,NetworkNode>();
		String text = null;
		int contentNum = 0;
		try {
			text = nodeBufferedReader.readLine();
			contentNum = Integer.valueOf(contentBufferedReader.readLine());
		} catch (IOException e) {
			nodeBufferedReader.close();
			contentBufferedReader.close();
			throw new Exception("Network files exception!",e);
		}

		String[] strs = text.split(" |\t|\n", 4);
		network.nodeNum = Integer.valueOf(strs[0].trim());
		network.edgeNum = Integer.valueOf(strs[1].trim());
		network.topicNum = Integer.valueOf(strs[2].trim());
		network.attrSize = contentNum;
		network.init();
		for(int i=0;i<network.nodeNum;i++){
			try {
				text = nodeBufferedReader.readLine();
			} catch (IOException e) {
				nodeBufferedReader.close();
				contentBufferedReader.close();
				throw new Exception("Network files exception!",e);
			}
			strs = text.split(" |\t|\n", 4);
			Map<String,Object> attributes = new HashMap<String, Object>();
			attributes.put("class_flag", strs[2]);
			network.addClassFlag(strs[2]);
			if(strs.length>3){
				attributes.put("remark", strs[3]);
			}
			NetworkNode node = network.createNode(Integer.valueOf(strs[0].trim()), strs[1], attributes);
			try {
				text = contentBufferedReader.readLine();
			} catch (IOException e) {
				nodeBufferedReader.close();
				contentBufferedReader.close();
				throw new Exception("Network files exception!",e);			
			}
			strs = text.split(" |\t|\n");
			node.nodeContentDouble = new double[contentNum];
			for(int j = 0;j<strs.length;j++){
				int index = Integer.valueOf(strs[j].trim());
				node.nodeContentDouble[index-1] = 1;
			}
			temp.put(node.getId(), node);
		}
		try {
			nodeBufferedReader.close();
			contentBufferedReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File edgeFile = new File(dataDir+"edges.txt");
		FileReader edgeFileReader = null;
		BufferedReader edgeBufferReader = null;
		try{
			edgeFileReader = new FileReader(edgeFile);
			edgeBufferReader = new BufferedReader(edgeFileReader);
		}catch(Exception e){
			throw new Exception("Network files exception!",e);	
		}
		for(int i=0;i<network.edgeNum;i++){
			try {
				text = edgeBufferReader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				edgeBufferReader.close();
				throw new Exception("Network files exception!",e);	
			}
			strs = text.split(" |\t|\n",2);
			NetworkNode source = temp.get(Integer.valueOf(strs[0].trim()));
			NetworkNode target = temp.get(Integer.valueOf(strs[1].trim()));
			network.createEdge(source, target);
		}

		edgeBufferReader.close();
	
		return network;
	}
	public Network readNodeWithContentAndLDAData(String networkName,boolean isDirected) throws Throwable{
		String dataDir = DIRECTOR+networkName+File.separator;
		File nodeFile = new File(dataDir+"nodes.txt");
		File nodeContentFile = new File(dataDir+"content_src.txt");
		Reader nodeFileReader = null;
		Reader nodeContentFileReader = null;
		try {
			nodeFileReader = new FileReader(nodeFile);
			nodeContentFileReader = new FileReader(nodeContentFile);
		} catch (Exception e) {
			throw new Exception(networkName+" node file is not found!",e);
		}
		BufferedReader nodeBufferedReader = new BufferedReader(nodeFileReader);
		BufferedReader contentBufferedReader = new BufferedReader(nodeContentFileReader);

		Network network = new Network(networkName, isDirected);
		Map<Integer,NetworkNode> temp = new HashMap<Integer,NetworkNode>();
		String text = null;
		int contentNum = 0;
		try {
			text = nodeBufferedReader.readLine();
			contentNum = Integer.valueOf(contentBufferedReader.readLine());
		} catch (IOException e) {
			nodeBufferedReader.close();
			contentBufferedReader.close();
			throw new Exception("Network files exception!",e);
		}

		String[] strs = text.split(" |\t|\n", 4);
		network.nodeNum = Integer.valueOf(strs[0].trim());
		network.edgeNum = Integer.valueOf(strs[1].trim());
		network.topicNum = Integer.valueOf(strs[2].trim());
		network.attrSize = contentNum;
		network.init();
		LDADataset ldaDataset = new LDADataset(network.nodeNum);
		for(int i=0;i<network.nodeNum;i++){
			try {
				text = nodeBufferedReader.readLine();
			} catch (IOException e) {
				nodeBufferedReader.close();
				contentBufferedReader.close();
				throw new Exception("Network files exception!",e);
			}
			strs = text.split(" |\t|\n", 4);
			Map<String,Object> attributes = new HashMap<String, Object>();
			attributes.put("class_flag", strs[2]);
			network.addClassFlag(strs[2]);
			if(strs.length>3){
				attributes.put("remark", strs[3]);
			}
			NetworkNode node = network.createNode(Integer.valueOf(strs[0].trim()), strs[1], attributes);
			try {
				text = contentBufferedReader.readLine();
			} catch (IOException e) {
				nodeBufferedReader.close();
				contentBufferedReader.close();
				throw new Exception("Network files exception!",e);			
			}
			strs = text.split(" |\t|\n");
			ldaDataset.setDoc(text, i);
			node.nodeContentDouble = new double[contentNum];
			for(int j = 0;j<strs.length;j++){
				int index = Integer.valueOf(strs[j].trim());
				node.nodeContentDouble[index-1] = 1.0;
			}
			temp.put(node.getId(), node);
		}
		try {
			nodeBufferedReader.close();
			contentBufferedReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File edgeFile = new File(dataDir+"edges.txt");
		FileReader edgeFileReader = null;
		BufferedReader edgeBufferReader = null;
		try{
			edgeFileReader = new FileReader(edgeFile);
			edgeBufferReader = new BufferedReader(edgeFileReader);
		}catch(Exception e){
			throw new Exception("Network files exception!",e);	
		}
		for(int i=0;i<network.edgeNum;i++){
			try {
				text = edgeBufferReader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				edgeBufferReader.close();
				throw new Exception("Network files exception!",e);	
			}
			strs = text.split(" |\t|\n",2);
			NetworkNode source = temp.get(Integer.valueOf(strs[0].trim()));
			NetworkNode target = temp.get(Integer.valueOf(strs[1].trim()));
			network.createEdge(source, target);
		}

		edgeBufferReader.close();
		network.ldaDataset = ldaDataset;
		return network;
	}
	/**
	 * @param networkName
	 * @param b
	 * @return
	 * @throws Exception 
	 */
	public Network readNodeWithContentAndTopics(String networkName, boolean b) throws Exception {
		String dataDir = DIRECTOR+networkName+File.separator;
		File nodeFile = new File(dataDir+"nodes.txt");
		File nodeContentFile = new File(dataDir+"content_src.txt");
		Reader nodeFileReader = null;
		Reader nodeContentFileReader = null;
		try {
			nodeFileReader = new FileReader(nodeFile);
			nodeContentFileReader = new FileReader(nodeContentFile);
		} catch (Exception e) {
			throw new Exception(networkName+" node file is not found!",e);
		}
		BufferedReader nodeBufferedReader = new BufferedReader(nodeFileReader);
		BufferedReader contentBufferedReader = new BufferedReader(nodeContentFileReader);
		BufferedReader topicReader = new  BufferedReader(new FileReader(new File(dataDir+"content.txt")));
		Network network = new Network(networkName, b);
		Map<Integer,NetworkNode> temp = new HashMap<Integer,NetworkNode>();
		String text = null;
		int contentNum = 0;
		try {
			text = nodeBufferedReader.readLine();
			contentNum = Integer.valueOf(contentBufferedReader.readLine());
		} catch (IOException e) {
			nodeBufferedReader.close();
			contentBufferedReader.close();
			throw new Exception("Network files exception!",e);
		}

		String[] strs = text.split(" |\t|\n", 4);
		network.nodeNum = Integer.valueOf(strs[0].trim());
		network.edgeNum = Integer.valueOf(strs[1].trim());
		network.topicNum = Integer.valueOf(strs[2].trim());
		network.attrSize = contentNum;
		network.init();
		for(int i=0;i<network.nodeNum;i++){
			try {
				text = nodeBufferedReader.readLine();
			} catch (IOException e) {
				nodeBufferedReader.close();
				contentBufferedReader.close();
				throw new Exception("Network files exception!",e);
			}
			strs = text.split(" |\t|\n", 4);
			Map<String,Object> attributes = new HashMap<String, Object>();
			attributes.put("class_flag", strs[2]);
			network.addClassFlag(strs[2]);
			if(strs.length>3){
				attributes.put("remark", strs[3]);
			}
			NetworkNode node = network.createNode(Integer.valueOf(strs[0].trim()), strs[1], attributes);
			try {
				text = contentBufferedReader.readLine();
			} catch (IOException e) {
				nodeBufferedReader.close();
				contentBufferedReader.close();
				throw new Exception("Network files exception!",e);			
			}
			strs = text.split(" |\t|\n");
			node.nodeContentDouble = new double[contentNum];
			for(int j = 0;j<strs.length;j++){
				int index = Integer.valueOf(strs[j].trim());
				node.nodeContentDouble[index-1] = 1;
			}
			text = topicReader.readLine();
			strs = text.split(" |\t|\n", network.topicNum);
			node.nodeTopicDis = new double[network.topicNum];
			for(int j = 0;j<strs.length;j++){
				node.nodeTopicDis[j] = Double.valueOf(strs[j]);
			}
			temp.put(node.getId(), node);
		}
		try {
			nodeBufferedReader.close();
			contentBufferedReader.close();
			topicReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File edgeFile = new File(dataDir+"edges.txt");
		FileReader edgeFileReader = null;
		BufferedReader edgeBufferReader = null;
		try{
			edgeFileReader = new FileReader(edgeFile);
			edgeBufferReader = new BufferedReader(edgeFileReader);
		}catch(Exception e){
			throw new Exception("Network files exception!",e);	
		}
		for(int i=0;i<network.edgeNum;i++){
			try {
				text = edgeBufferReader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				edgeBufferReader.close();
				throw new Exception("Network files exception!",e);	
			}
			strs = text.split(" |\t|\n",2);
			NetworkNode source = temp.get(Integer.valueOf(strs[0].trim()));
			NetworkNode target = temp.get(Integer.valueOf(strs[1].trim()));
			network.createEdge(source, target);
		}

		edgeBufferReader.close();
	
		return network;
	}
}
