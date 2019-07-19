/**
 * @QiaoJian
 */
package cn.edu.bjtu.qj.graph.io;

import java.io.File;
import java.io.IOException;

/**
 * @author QiaoJian
 *
 */
public abstract class AbstractGmlIo {
	
	public  String getResFolderPath(){
		String classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		String realPath = classPath.substring(0, classPath.length()-4);
		return realPath;
	}
	
	public File createFile(String path){
		File file = new File(path);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		return file;
	}
}
