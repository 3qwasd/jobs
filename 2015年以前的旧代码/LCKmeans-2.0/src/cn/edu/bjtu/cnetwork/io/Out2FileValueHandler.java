/**
*
**/
package cn.edu.bjtu.cnetwork.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import cn.edu.bjtu.cnetwork.handler.IValueHandler;

/**
 * @author QiaoJian
 *
 */
public class Out2FileValueHandler implements IValueHandler {
	
	String outputFilePath;
	BufferedWriter bufferedWriter;
	/**
	 * 
	 */
	public Out2FileValueHandler(String outputFilePath) {
		super();
		this.outputFilePath = outputFilePath;
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.handler.IValueHandler#before()
	 */
	@Override
	public void before() {
		// TODO Auto-generated method stub
		File file = new File(outputFilePath);
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

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.handler.IValueHandler#handlerValue(java.lang.Number)
	 */
	@Override
	public void handlerValue(Object ...value) {
		// TODO Auto-generated method stub
		try {
			bufferedWriter.write(value+"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.handler.IValueHandler#finish()
	 */
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		try {
			bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
