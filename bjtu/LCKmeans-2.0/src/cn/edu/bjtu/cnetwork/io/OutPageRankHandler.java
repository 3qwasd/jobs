/**
*
**/
package cn.edu.bjtu.cnetwork.io;

import java.io.IOException;
import java.util.Collections;

import cn.edu.bjtu.cnetwork.ComparableValue;
import cn.edu.bjtu.cnetwork.Network;

/**
 * @author QiaoJian
 *
 */
public class OutPageRankHandler extends Out2FileResultHandler {

	/**
	 * @param outputFile
	 */
	public OutPageRankHandler(String outputFile) {
		super(outputFile);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.io.Out2FileResultHandler#writeResults()
	 */
	@Override
	void writeResults(Network result) {
		// TODO Auto-generated method stub
		ComparableValue.SORT_TYPE=1;
		Collections.sort(result.pageRankValues);
		for(int i=0;i<result.pageRankValues.size();i++){
			try {
				this.bufferedWriter.write(result.pageRankValues.get(i).id+"\t"+
						result.pageRankValues.get(i).value+"\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
