/**
 * @QiaoJian
 */
package cn.edu.bjtu.qj.graph.algorithms;

import cn.edu.bjtu.qj.graph.component.Graph;
import cn.edu.bjtu.qj.graph.component.IResultHandler;;

/**
 * @author QiaoJian
 *
 */
public abstract class Algorithms {
	
	protected Graph graph;
	
	protected IResultHandler resultHandler;
	public void attachGraph(Graph graph){
		this.graph = graph;
	}
	
	public void handleResult(){
		if(resultHandler!=null){
			resultHandler.handleResult();
		}
	}

	public void setResultHandler(IResultHandler resultHandler) {
		this.resultHandler = resultHandler;
	}
}
