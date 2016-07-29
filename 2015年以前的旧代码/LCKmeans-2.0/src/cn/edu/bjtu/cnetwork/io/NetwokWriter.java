/**
 * @QiaoJian
 */
package cn.edu.bjtu.cnetwork.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.qj.graph.component.AdjListGraph;
import cn.edu.bjtu.qj.graph.component.Edge;
import cn.edu.bjtu.qj.graph.component.Node;

/**
 * @author QiaoJian
 *
 */
public class NetwokWriter{

	/**
	 * 
	 */
	public NetwokWriter() {
		// TODO Auto-generated constructor stub
	}
	public void write(Network network,String filePath,int dicNum) throws Exception{
		File nodeFile = this.createFile(filePath+"nodes.txt");
		File edgeFile = this.createFile(filePath+"edges.txt");
		File contentFile = this.createFile(filePath+"content.txt");
		File dicFile = this.createFile(filePath+"dic.txt");
		FileOutputStream nodeFileOutputStream = new FileOutputStream(nodeFile,true);
		FileOutputStream contentFileOutputStream = new FileOutputStream(contentFile,true);
		
		Map<Integer,Integer> dic = new HashMap<Integer, Integer>();
		String context = network.nodeNum+"\t"+network.edgeNum+"\t"+network.topicNum+"\t"+"\n";
		nodeFileOutputStream.write(context.getBytes());
		context = network.nodeNum+"\n";
		contentFileOutputStream.write(context.getBytes());
		for(int i=0;i<network.nodeNum;i++){
			Node node = network.getNode(i);
			context = node.getId()+"\t"+node.getName()+"\t"+node.getAttribute("class_flag")+"\n";
			nodeFileOutputStream.write(context.getBytes());
			List<Integer> contents = (List<Integer>) node.getAttribute("content");
			context = "";
			for(int j=0;j<contents.size();j++){
				int wordId = contents.get(j);
				if(j==contents.size()-1)
					context += wordId+"\n";
				else
					context += wordId+"\t";
				if(!dic.containsKey(wordId)){
					dic.put(wordId, wordId);
				}
			}
			contentFileOutputStream.write(context.getBytes());
		}
		nodeFileOutputStream.close();
		contentFileOutputStream.close();
		Iterator<Edge> edges = network.getEdges().iterator();
		FileOutputStream edgeFileOutputStream = new FileOutputStream(edgeFile,true);
		
		while(edges.hasNext()){
			Edge edge = edges.next();
			context = edge.getSourceNode().getId()+"\t"+edge.getTargetNode().getId()+"\n";
			edgeFileOutputStream.write(context.getBytes());
		}
		edgeFileOutputStream.close();
		context = dicNum+"\n";
		FileOutputStream dicFileOutputStream = new FileOutputStream(dicFile,true);
		dicFileOutputStream.write(context.getBytes());
		for(int i=1;i<=dicNum;i++){
			context = i+"\t"+i+"\n";
			dicFileOutputStream.write(context.getBytes());
		}
		edgeFileOutputStream.close();
	}
	public void write(Network network,String filePath) throws Exception{
		File nodeFile = this.createFile(filePath+"nodes.txt");
		File edgeFile = this.createFile(filePath+"edges.txt");
		File contentFile = this.createFile(filePath+"content.txt");
		File dicFile = this.createFile(filePath+"dic.txt");
		FileOutputStream nodeFileOutputStream = new FileOutputStream(nodeFile,true);
		FileOutputStream contentFileOutputStream = new FileOutputStream(contentFile,true);
		
		Map<Integer,Integer> dic = new HashMap<Integer, Integer>();
		String context = network.nodeNum+"\t"+network.edgeNum+"\t"+network.topicNum+"\t"+"\n";
		nodeFileOutputStream.write(context.getBytes());
		context = network.nodeNum+"\n";
		contentFileOutputStream.write(context.getBytes());
		for(int i=0;i<network.nodeNum;i++){
			Node node = network.getNode(i);
			context = node.getId()+"\t"+node.getName()+"\t"+node.getAttribute("class_flag")+"\n";
			nodeFileOutputStream.write(context.getBytes());
			List<Integer> contents = (List<Integer>) node.getAttribute("content");
			context = "";
			for(int j=0;j<contents.size();j++){
				int wordId = contents.get(j);
				if(j==contents.size()-1)
					context += wordId+"\n";
				else
					context += wordId+"\t";
				if(!dic.containsKey(wordId)){
					dic.put(wordId, wordId);
				}
			}
			contentFileOutputStream.write(context.getBytes());
		}
		nodeFileOutputStream.close();
		contentFileOutputStream.close();
		Iterator<Edge> edges = network.getEdges().iterator();
		FileOutputStream edgeFileOutputStream = new FileOutputStream(edgeFile,true);
		
		while(edges.hasNext()){
			Edge edge = edges.next();
			context = edge.getSourceNode().getId()+"\t"+edge.getTargetNode().getId()+"\n";
			edgeFileOutputStream.write(context.getBytes());
		}
		edgeFileOutputStream.close();
		context = dic.size()+"\n";
		FileOutputStream dicFileOutputStream = new FileOutputStream(dicFile,true);
		dicFileOutputStream.write(context.getBytes());
		for(Integer key:dic.keySet()){
			context = key+"\t"+dic.get(key)+"\n";
			dicFileOutputStream.write(context.getBytes());
		}
		edgeFileOutputStream.close();
	}
	
	public File createFile(String path){
		File file = new File(path);
		if(file.exists()){
			try {
				file.delete();
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		return file;
	}
	public void write(AdjListGraph network,String filePath,int topicNum) throws Exception{
		File path = new File(filePath);
		if(!path.exists()){
			path.mkdirs();
		}
		File nodeFile = this.createFile(filePath+"nodes.txt");
		File edgeFile = this.createFile(filePath+"edges.txt");
		//File contentFile = this.createFile(filePath+"content.txt");
		FileOutputStream nodeFileOutputStream = new FileOutputStream(nodeFile,true);
		//FileOutputStream contentFileOutputStream = new FileOutputStream(contentFile,true);
		
		Map<Integer,Integer> dic = new HashMap<Integer, Integer>();
		String context = network.nodeNum+"\t"+network.edgeNum+"\t"+topicNum+"\t"+"\n";
		nodeFileOutputStream.write(context.getBytes());
		//context = network.nodeNum+"\n";
		//contentFileOutputStream.write(context.getBytes());
		for(int i=0;i<network.nodeNum;i++){
			Node node = network.getNode(i);
			context = node.getId()+"\t"+node.getName()+"\t"+node.getAttribute("class_flag")+"\n";
			nodeFileOutputStream.write(context.getBytes());
//			List<Integer> contents = (List<Integer>) node.getAttribute("content");
//			context = "";
//			for(int j=0;j<contents.size();j++){
//				int wordId = contents.get(j);
//				if(j==contents.size()-1)
//					context += wordId+"\n";
//				else
//					context += wordId+"\t";
//				if(!dic.containsKey(wordId)){
//					dic.put(wordId, wordId);
//				}
//			}
//           contentFileOutputStream.write(context.getBytes());
		}
		nodeFileOutputStream.close();
		//contentFileOutputStream.close();
		Iterator<Edge> edges = network.getEdges().iterator();
		FileOutputStream edgeFileOutputStream = new FileOutputStream(edgeFile,true);
		
		while(edges.hasNext()){
			Edge edge = edges.next();
			context = edge.getSourceNode().getId()+"\t"+edge.getTargetNode().getId()+"\n";
			edgeFileOutputStream.write(context.getBytes());
		}
		edgeFileOutputStream.close();
//		context = dic.size()+"\n";
//		FileOutputStream dicFileOutputStream = new FileOutputStream(dicFile,true);
//		dicFileOutputStream.write(context.getBytes());
//		for(Integer key:dic.keySet()){
//			context = key+"\t"+dic.get(key)+"\n";
//			dicFileOutputStream.write(context.getBytes());
//		}
		edgeFileOutputStream.close();
	}
	
	public void writeWithNewFlag(AdjListGraph network,String filePath,int topicNum) throws Exception{
		File path = new File(filePath);
		if(!path.exists()){
			path.mkdirs();
		}
		File nodeFile = this.createFile(filePath+"nodes.txt");
		File edgeFile = this.createFile(filePath+"edges.txt");
		//File contentFile = this.createFile(filePath+"content.txt");
		FileOutputStream nodeFileOutputStream = new FileOutputStream(nodeFile,true);
		//FileOutputStream contentFileOutputStream = new FileOutputStream(contentFile,true);
		
		Map<Integer,Integer> dic = new HashMap<Integer, Integer>();
		String context = network.nodeNum+"\t"+network.edgeNum+"\t"+topicNum+"\t"+"\n";
		nodeFileOutputStream.write(context.getBytes());
		//context = network.nodeNum+"\n";
		//contentFileOutputStream.write(context.getBytes());
		for(int i=0;i<network.nodeNum;i++){
			Node node = network.getNode(i);
			context = node.getId()+"\t"+node.getName()+"\t"+node.getAttribute("class_flag")+"\t"+
			node.getAttribute("class_flag_new")+"\t"+node.getAttribute("remark");
			if(!node.getAttribute("class_flag").equals(node.getAttribute("class_flag_new")))
				context+="modify";
			context+="\n";
			nodeFileOutputStream.write(context.getBytes());
//			List<Integer> contents = (List<Integer>) node.getAttribute("content");
//			context = "";
//			for(int j=0;j<contents.size();j++){
//				int wordId = contents.get(j);
//				if(j==contents.size()-1)
//					context += wordId+"\n";
//				else
//					context += wordId+"\t";
//				if(!dic.containsKey(wordId)){
//					dic.put(wordId, wordId);
//				}
//			}
//           contentFileOutputStream.write(context.getBytes());
		}
		nodeFileOutputStream.close();
		//contentFileOutputStream.close();
		Iterator<Edge> edges = network.getEdges().iterator();
		FileOutputStream edgeFileOutputStream = new FileOutputStream(edgeFile,true);
		
		while(edges.hasNext()){
			Edge edge = edges.next();
			context = edge.getSourceNode().getId()+"\t"+edge.getTargetNode().getId()+"\n";
			edgeFileOutputStream.write(context.getBytes());
		}
		edgeFileOutputStream.close();
//		context = dic.size()+"\n";
//		FileOutputStream dicFileOutputStream = new FileOutputStream(dicFile,true);
//		dicFileOutputStream.write(context.getBytes());
//		for(Integer key:dic.keySet()){
//			context = key+"\t"+dic.get(key)+"\n";
//			dicFileOutputStream.write(context.getBytes());
//		}
		edgeFileOutputStream.close();
	}
	public void writeWithNewFlag1(AdjListGraph network,String filePath,int topicNum) throws Exception{
		File path = new File(filePath);
		if(!path.exists()){
			path.mkdirs();
		}
		File nodeFile = this.createFile(filePath+"nodes.txt");
		File edgeFile = this.createFile(filePath+"edges.txt");
		//File contentFile = this.createFile(filePath+"content.txt");
		FileOutputStream nodeFileOutputStream = new FileOutputStream(nodeFile,true);
		//FileOutputStream contentFileOutputStream = new FileOutputStream(contentFile,true);
		
		Map<Integer,Integer> dic = new HashMap<Integer, Integer>();
		String context = network.nodeNum+"\t"+network.edgeNum+"\t"+topicNum+"\t"+"\n";
		nodeFileOutputStream.write(context.getBytes());
		//context = network.nodeNum+"\n";
		//contentFileOutputStream.write(context.getBytes());
		for(int i=0;i<network.nodeNum;i++){
			Node node = network.getNode(i);
			context = node.getId()+"\t"+node.getName()+"\t"+
			node.getAttribute("class_flag")+"\n";
			nodeFileOutputStream.write(context.getBytes());
//			List<Integer> contents = (List<Integer>) node.getAttribute("content");
//			context = "";
//			for(int j=0;j<contents.size();j++){
//				int wordId = contents.get(j);
//				if(j==contents.size()-1)
//					context += wordId+"\n";
//				else
//					context += wordId+"\t";
//				if(!dic.containsKey(wordId)){
//					dic.put(wordId, wordId);
//				}
//			}
//           contentFileOutputStream.write(context.getBytes());
		}
		nodeFileOutputStream.close();
		//contentFileOutputStream.close();
		Iterator<Edge> edges = network.getEdges().iterator();
		FileOutputStream edgeFileOutputStream = new FileOutputStream(edgeFile,true);
		
		while(edges.hasNext()){
			Edge edge = edges.next();
			context = edge.getSourceNode().getId()+"\t"+edge.getTargetNode().getId()+"\n";
			edgeFileOutputStream.write(context.getBytes());
		}
		edgeFileOutputStream.close();
//		context = dic.size()+"\n";
//		FileOutputStream dicFileOutputStream = new FileOutputStream(dicFile,true);
//		dicFileOutputStream.write(context.getBytes());
//		for(Integer key:dic.keySet()){
//			context = key+"\t"+dic.get(key)+"\n";
//			dicFileOutputStream.write(context.getBytes());
//		}
		edgeFileOutputStream.close();
	}
}
