/**
*
**/
package cn.edu.bjtu.cnetwork.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.junit.Test;

import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.cnetwork.algorithms.SignalPropagate;
import cn.edu.bjtu.cnetwork.handler.IResultHandler;
import cn.edu.bjtu.cnetwork.io.NetworkReader;
import cn.edu.bjtu.qj.graph.algorithms.datastrut.Triple;

/**
 * @author QiaoJian
 *
 */
public class SignalPropagateTest {
	
	@Test
	public void testSignalPropagate(){
		NetworkReader networkReader = new NetworkReader();
		String networkName = "cornell";
		Network network = null;
		try {
			network = networkReader.readNode(networkName, true);
		} catch (Throwable e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SignalPropagate signalPropagate = new SignalPropagate(network, 3);
		signalPropagate.setResultHandler(new IResultHandler() {
			
			@Override
			public void handlerResult(Network network) {
				// TODO Auto-generated method stub
				try {
					Random random = new Random();
					BufferedWriter bufferedWriter = 
							new BufferedWriter(new FileWriter(new File("I:\\someresult\\out"+random.nextInt(10)+".txt")));
					for(int i=0;i<network.signalTriplesTable.length;i++){
						Triple<Double> item = network.signalTriplesTable.datas[i];
						bufferedWriter.write(item.row+" "+item.col+" "+item.getWeight()+" "+
						network.signalTriplesTable.postions[item.row]+"\n");
					}
					bufferedWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		signalPropagate.run();
	}
}
