/**
 * @QiaoJian
 */
package cn.edu.bjtu.qj.graph.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.edu.bjtu.qj.graph.component.IValueHandler;

/**
 * @author QiaoJian
 *
 */
public class WriteToFileValueHandler implements IValueHandler {

	FileOutputStream outputStream = null;
	String filePath;
	/**
	 * 
	 */
	public WriteToFileValueHandler(String filePath) {
		this.filePath = filePath;
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.qj.graph.component.IValueHandler#execute(java.lang.Number)
	 */
	@Override
	public void execute(Number value,Object ...args) {
		// TODO Auto-generated method stub

		String context = value+"\n";
		try {
			outputStream.write(context.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.qj.graph.component.IValueHandler#befor()
	 */
	@Override
	public void befor() {
		// TODO Auto-generated method stub
		try {
			outputStream = new FileOutputStream(new File(filePath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.qj.graph.component.IValueHandler#finish()
	 */
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		if(outputStream!=null)
			try {
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
