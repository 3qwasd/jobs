/**
 * @QiaoJian
 */
package cn.edu.bjtu.qj.graph.algorithms.datastrut;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Collections;

/**
 * @author QiaoJian
 *
 */
public class TriplesTable<T extends Number> {
	
	/*值*/
	public Triple<T>[] datas;

	/*行数*/
	public int rn;
	/*列数*/
	public int cn;
	/*长度*/
	public int length;
	/**/
	public int[] postions;
	/**
	 * 
	 */
	public TriplesTable() {
		// TODO Auto-generated constructor stub
	}
	
	public void writeToTxt(String path){
		File file = new File(path);
		if(file.exists()){
			file.delete();
		}

		try {
			file.createNewFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			FileOutputStream outputStream = new FileOutputStream(file,true);
			for(int i=0;i<datas.length;i++){
				Triple<T> data = datas[i];
				String context = data.row+" "+data.col+" "+data.getWeight()+"\n";
				outputStream.write(context.getBytes());
			}
			outputStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
