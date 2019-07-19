/**
 * @QiaoJian
 */
package cn.edu.bjtu.qj.graph.io;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import cn.edu.bjtu.qj.graph.component.AdjListEdge;
import cn.edu.bjtu.qj.graph.component.AdjListGraph;
import cn.edu.bjtu.qj.graph.component.AdjListNode;
import cn.edu.bjtu.qj.graph.component.Edge;
import cn.edu.bjtu.qj.graph.component.Graph;
import cn.edu.bjtu.qj.graph.component.MGraph;
import cn.edu.bjtu.qj.graph.component.Node;
import cn.edu.bjtu.utils.StringUtils;

/**
 * @author QiaoJian
 *
 */
public class GMLReader extends AbstractGmlIo{
	
	/*图*/
	Graph graph = null;
	/*结点*/
	Node node = null;
	/*边*/
	Edge edge = null;
	/*操作符栈*/
	Stack<String> operaterStack = new Stack<String>();
	/*内容栈*/
	Stack<String> contextKeyStack = new Stack<String>();
	Stack<String> contextValueStack = new Stack<String>();
	Map<Integer,Node> nodes = new HashMap<Integer,Node>();
	public GMLReader(){};
	
	public MGraph readToMGraph(String filePath,int nodeNum,int edgeNum) throws Exception{
		/*读取文件*/
		File file = new File(filePath);
		RandomAccessFile randomAccessFile = new RandomAccessFile(file,"r");
		/*内容*/
		String context = null;
		/*是否有向边*/
		boolean isDirected = false;
		/*元素类型0是结点，1是边*/
		int elementType = -1;
		while((context = randomAccessFile.readLine())!=null){
			/*RandomAccessFile以ISO-8859-1编码读取文件，需转成UTF-8*/
			context = StringUtils.changeCharset(context,"ISO-8859-1","UTF-8");
			String[] contexts = context.trim().split(" ", 2);
			String key = contexts[0];
			String value = contexts.length>1?contexts[1]:"";
			if(key.equals("graph")){
				graph = new MGraph(nodeNum,edgeNum);
				continue;
			}
			if(key.equals("directed")){
				int dir = Integer.valueOf(context.substring(context.length()-2).trim());
				if(dir == 1)
					isDirected = true;
				graph.setDirected(isDirected);
				continue;
			}
			if(key.equals("node")){
				node = new AdjListNode();
				elementType = 0;
				continue;
			}
			if(key.equals("edge")){
				edge = new AdjListEdge();
				edge.setDirected(isDirected);
				elementType = 1;
				continue;
			}
			if(key.equals("[")){
				operaterStack.push(context);
				continue;
			}else if(key.equals("]")){
				operaterStack.push(context);
				switch (elementType) {
				case 0:
					readNodeInfo();
					break;
					
				case 1:
					readEdgeInfo();
					break;
				}
				operaterStack.pop();
				operaterStack.pop();
			}else{
				if(elementType>=0){
					contextKeyStack.push(key);
					contextValueStack.push(value);
				}
			}
		}
		randomAccessFile.close();
		return (MGraph) graph;
	}
	
	public Graph read(String filePath,int nodeNum,int edgeNum) throws Exception{
		/*读取文件*/
		File file = new File(filePath);
		RandomAccessFile randomAccessFile = new RandomAccessFile(file,"r");
		/*内容*/
		String context = null;
		/*是否有向边*/
		boolean isDirected = false;
		/*元素类型0是结点，1是边*/
		int elementType = -1;
		while((context = randomAccessFile.readLine())!=null){
			/*RandomAccessFile以ISO-8859-1编码读取文件，需转成UTF-8*/
			context = StringUtils.changeCharset(context,"ISO-8859-1","UTF-8");
			String[] contexts = context.trim().split(" ", 2);
			String key = contexts[0];
			String value = contexts.length>1?contexts[1]:"";
			if(key.equals("graph")){
				graph = new AdjListGraph(nodeNum,edgeNum);
				continue;
			}
			if(key.equals("directed")){
				int dir = Integer.valueOf(context.substring(context.length()-2).trim());
				if(dir == 1)
					isDirected = true;
				graph.setDirected(isDirected);
				continue;
			}
			if(key.equals("node")){
				node = new AdjListNode();
				elementType = 0;
				continue;
			}
			if(key.equals("edge")){
				edge = new AdjListEdge();
				edge.setDirected(isDirected);
				elementType = 1;
				continue;
			}
			if(key.equals("[")){
				operaterStack.push(context);
				continue;
			}else if(key.equals("]")){
				operaterStack.push(context);
				switch (elementType) {
				case 0:
					readNodeInfo();
					break;
					
				case 1:
					readEdgeInfo();
					break;
				}
				operaterStack.pop();
				operaterStack.pop();
			}else{
				if(elementType>=0){
					contextKeyStack.push(key);
					contextValueStack.push(value);
				}
			}
		}
		randomAccessFile.close();
		return graph;
	}
	public Graph read(String filePath) throws Exception{
		/*读取文件*/
		File file = new File(filePath);
		RandomAccessFile randomAccessFile = new RandomAccessFile(file,"r");
		/*内容*/
		String context = null;
		/*是否有向边*/
		boolean isDirected = false;
		/*元素类型0是结点，1是边*/
		int elementType = -1;
		while((context = randomAccessFile.readLine())!=null){
			/*RandomAccessFile以ISO-8859-1编码读取文件，需转成UTF-8*/
			context = StringUtils.changeCharset(context,"ISO-8859-1","UTF-8");
			String[] contexts = context.trim().split(" ", 2);
			String key = contexts[0];
			String value = contexts.length>1?contexts[1]:"";
			if(key.equals("graph")){
				graph = new AdjListGraph();
				continue;
			}
			if(key.equals("directed")){
				isDirected = Boolean.valueOf(context.substring(context.length()-2));
				graph.setDirected(isDirected);
				continue;
			}
			if(key.equals("node")){
				node = new AdjListNode();
				elementType = 0;
				continue;
			}
			if(key.equals("edge")){
				edge = new AdjListEdge();
				edge.setDirected(isDirected);
				elementType = 1;
				continue;
			}
			if(key.equals("[")){
				operaterStack.push(context);
				continue;
			}else if(key.equals("]")){
				operaterStack.push(context);
				switch (elementType) {
				case 0:
					readNodeInfo();
					break;
					
				case 1:
					readEdgeInfo();
					break;
				}
				operaterStack.pop();
				operaterStack.pop();
			}else{
				if(elementType>=0){
					contextKeyStack.push(key);
					contextValueStack.push(value);
				}
			}
		}
		randomAccessFile.close();
		return graph;
	}
	
	/**
	 * 读取结点信息
	 */
	private void readNodeInfo() {
		while(!contextKeyStack.isEmpty()){
			String key = contextKeyStack.pop();
			String value = contextValueStack.pop();
			if(key.equals("id")){
				int id = Integer.valueOf(value);
				node.setId(id);
				nodes.put(id, node);
			}else if(key.equals("label")){
				node.setName(value);
			}else if(key.equals("value")){
				node.putAttribute("class_flag", value);
			}
		}
		graph.addNode(node);
		node = null;
	}
	/**
	 * 读取边信息
	 */
	private void readEdgeInfo() {
		while(!contextKeyStack.isEmpty()){
			String key = contextKeyStack.pop();
			String value = contextValueStack.pop();
			if(key.equals("source")){
				int sourceId = Integer.valueOf(value);
				edge.setSourceNode(nodes.get(sourceId));
			}else if(key.equals("target")){
				int targetId = Integer.valueOf(value);
				edge.setTargetNode(nodes.get(targetId));
			}else{
				edge.putAttribute(key, value);
			}
		}
		graph.addEdge(edge);
		//edge.init();
	}
}
