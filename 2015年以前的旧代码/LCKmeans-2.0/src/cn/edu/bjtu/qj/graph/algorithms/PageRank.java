/**
 * @QiaoJian
 */
package cn.edu.bjtu.qj.graph.algorithms;

import java.io.FileOutputStream;
import java.util.Iterator;

import cn.edu.bjtu.qj.graph.algorithms.datastrut.IndexValue;
import cn.edu.bjtu.qj.graph.component.AdjListEdge;
import cn.edu.bjtu.qj.graph.component.AdjListNode;
import cn.edu.bjtu.qj.graph.component.Edge;
import cn.edu.bjtu.qj.graph.component.IResultHandler;
import cn.edu.bjtu.qj.graph.component.MGraph;
import cn.edu.bjtu.utils.MathUtils;

/**
 * @author QiaoJian
 *
 */
public class PageRank extends Algorithms{
	
	MGraph graph;
	int nodeNum = 0;
	double c = 0.85;
	double [] source;
	double [] target;
	IndexValue<Double>[] prValues;
	/**
	 * 
	 */
	public PageRank() {
		// TODO Auto-generated constructor stub
	}
	
	public PageRank(MGraph graph) {
		super();
		this.graph = graph;
		
	}
	private void init(){
		this.nodeNum = graph.getNodeNum();
		this.source = new double[nodeNum];
		this.target = new double[nodeNum];
		prValues = new IndexValue[nodeNum];
		for(int i=0;i<nodeNum;i++){
			source[i] = 1D/(double)nodeNum;
			prValues[i] = new IndexValue<Double>(i+1);
			prValues[i].value = 0.0D;
		}
		IResultHandler resHandler = new PageRankResultHandler();
		this.setResultHandler(resHandler);
	}
	public void execute(){
		init();
		double convergence = 0.0001;
		
		double residual = 1;
		
		int count = 0;
		int loopNum = 0;
		while(residual > convergence){
			Iterator<Edge> edges = graph.getEdges().iterator();
			while(edges.hasNext()){
				AdjListEdge edge = (AdjListEdge) edges.next();
				AdjListNode sourceNode = (AdjListNode) edge.getSourceNode();
				AdjListNode targetNode = (AdjListNode) edge.getTargetNode();
				target[targetNode.getId()-1] +=
						source[sourceNode.getId()-1]/sourceNode.getOutEdges().size();
				loopNum++;
						
			}
			double residualSum = 0;
			for(int i=0;i<nodeNum;i++){
				target[i] = c*target[i]+(1-c)/nodeNum;
				double residualItem = source[i]-target[i];
				residualSum += residualItem*residualItem;
				source[i] = target[i];
				prValues[i].value = source[i];
				
				graph.getNode(i).putAttribute("pagerank", prValues[i]);
				graph.getNode(i).putAttribute("prval", MathUtils.cutDouble(prValues[i].value, 4));
				target[i] = 0;
				loopNum++;
			}
			residual = Math.sqrt(residualSum);
			System.out.println(count+"次：residual="+residual);
			count++;
		}
		System.out.println(loopNum);
		this.handleResult();
	}
	private class PageRankResultHandler implements IResultHandler{

		/* (non-Javadoc)
		 * @see cn.edu.bjtu.qj.graph.component.IResultHandler#handleResult()
		 */
		@Override
		public void handleResult() {
			// TODO Auto-generated method stub
			sortResult();
			graph.setPageRanks(prValues);
			writeResult();
			
		}
		public void sortResult(){
			QuickSort quickSort = new QuickSort(prValues,1);
			quickSort.execute();
		}
		public void writeResult(){
			try {
				FileOutputStream outputStream = 
						new FileOutputStream("I:\\pageRank.txt");
				double sum = 0;
				for(int i=0;i<prValues.length;i++){
					sum = sum+prValues[i].value;
					String str = prValues[i].id+" "+prValues[i].value+"\n";
					outputStream.write(str.getBytes());
				}
				outputStream.close();
				System.out.println(sum);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
