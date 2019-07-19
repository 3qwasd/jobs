/**
 * @QiaoJian
 */
package cn.edu.bjtu.cnetwork.algorithms;

import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.cnetwork.handler.IResultHandler;
import cn.edu.bjtu.cnetwork.handler.IValueHandler;

/**
 * @author QiaoJian
 *
 */
public abstract class NetworkAlgorithms implements Runnable{

	Network network;

	IValueHandler valueHandler;

	IResultHandler resultHandler;
	/**
	 * 
	 */
	public NetworkAlgorithms(Network network) {
		// TODO Auto-generated constructor stub
		this.network = network;
	}

	void beforeExecute(){
		if(valueHandler!=null)
			valueHandler.before();
	}
	abstract void execute();

	void afterExecute(){
		if(valueHandler!=null)
			valueHandler.finish();
		if(resultHandler!=null)
			resultHandler.handlerResult(network);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		beforeExecute();
		execute();
		afterExecute();
	}

	public void setValueHandler(IValueHandler valueHandler) {
		this.valueHandler = valueHandler;
	}

	public void setResultHandler(IResultHandler resultHandler) {
		this.resultHandler = resultHandler;
	}
}
