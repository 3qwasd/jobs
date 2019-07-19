/**
*
**/
package cn.edu.bjtu.cnetwork.test;

import java.io.File;

import org.junit.Test;

import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.cnetwork.algorithms.PageRank;
import cn.edu.bjtu.cnetwork.algorithms.StandardPageRank;
import cn.edu.bjtu.cnetwork.handler.IValueHandler;
import cn.edu.bjtu.cnetwork.io.NetworkReader;
import cn.edu.bjtu.cnetwork.io.OutPageRankHandler;
import cn.edu.bjtu.qj.graph.io.GMLWriter;

/**
 * @author QiaoJian
 *
 */
public class PageRankTest {
	
	@Test
	public void mathtest(){
		String str = "——";
		System.out.println(str.matches("[\u2014]+"));
	}
	
	@Test
	public void testPageRank(){
		NetworkReader networkReader = new NetworkReader();
		String networkName = "citeseer";
		Network network = null;
		try {
			network = networkReader.readNode(networkName, true);
		} catch (Throwable e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StandardPageRank pageRank = new StandardPageRank(network);
		pageRank.mu = 0.00001;
		OutPageRankHandler handler = new OutPageRankHandler(NetworkReader.DIRECTOR+networkName+File.separator
				+networkName+"_pagerank.txt");
		pageRank.setResultHandler(handler);
		pageRank.run();
	}
}
