/**
 *
 **/
package cn.edu.bjtu.cnetwork.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.cnetwork.handler.IResultHandler;

/**
 * @author QiaoJian
 *
 */
public abstract class Out2FileResultHandler implements IResultHandler {

	String outputFile;
	BufferedWriter bufferedWriter;
	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.handler.IResultHandler#handlerResult()
	 */

	/**
	 * @param outputFile
	 */
	public Out2FileResultHandler(String outputFile) {
		super();
		this.outputFile = outputFile;
	}
	private void init(){
		// TODO Auto-generated method stub
		File file = new File(outputFile);
		if(file.exists()){
			file.delete();
		}
		try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void handlerResult(Network result) {
		// TODO Auto-generated method stub
		init();
		writeResults(result);
		finish();

	}
	abstract void writeResults(Network result);
	private void finish(){
		try {
			bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
