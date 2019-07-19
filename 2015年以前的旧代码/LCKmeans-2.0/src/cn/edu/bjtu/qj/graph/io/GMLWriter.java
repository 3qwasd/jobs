/**
 * @QiaoJian
 */
package cn.edu.bjtu.qj.graph.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import cn.edu.bjtu.qj.graph.component.Edge;
import cn.edu.bjtu.qj.graph.component.Graph;
import cn.edu.bjtu.qj.graph.component.Node;

/**
 * @author QiaoJian
 *
 */
public class GMLWriter extends AbstractGmlIo{
	
	public void write(Graph graph,String outFilePath) throws Exception{
		File gmlFile = this.createFile(outFilePath);
		FileOutputStream outputStream = null;
		outputStream = new FileOutputStream(gmlFile,true);
	
		
		String title = "graph"+"\n"+"[\n"+"\tdirected\t{isDirected}\n";
		if(graph.isDirected()){
			title = title.replace("{isDirected}", "1");
		}else{
			title = title.replace("{isDirected}", "0");
		}
		
		outputStream.write(title.getBytes());
		
		List<Node> nodes = graph.getNodes();
		String str;
		for(Node node:nodes){
			str = "\tnode"+"\n"+
					"\t["+"\n"+
					"\t\tid\t"+node.getId()+"\n"+
					"\t\tlabel\t\""+node.getName()+"\"\n"+
					"\t]"+"\n";
			outputStream.write(str.getBytes());
		}
		
		Iterator<Edge> edges = graph.getEdges().iterator();
		while(edges.hasNext()){
			Edge edge = edges.next();
			str = "\tedge"+"\n"+
					"\t["+"\n"+
					"\t\tsource\t"+edge.getSourceNode().getId()+"\n"+
					"\t\ttarget\t"+edge.getTargetNode().getId()+"\n"+
					"\t]"+"\n";
			outputStream.write(str.getBytes());
		}
		
		str="]";
		outputStream.write(str.getBytes());
		
		outputStream.close();
	}
	
}
