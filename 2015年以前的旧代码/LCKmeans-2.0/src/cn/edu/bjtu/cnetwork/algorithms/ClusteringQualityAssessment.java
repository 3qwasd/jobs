/**
*
**/
package cn.edu.bjtu.cnetwork.algorithms;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.bjtu.cnetwork.Community;
import cn.edu.bjtu.cnetwork.Network;
import cn.edu.bjtu.cnetwork.ResultItem;
import cn.edu.bjtu.utils.MathUtils;

/**
 * @author QiaoJian
 *
 */
public class ClusteringQualityAssessment extends NetworkAlgorithms {
	ResultItem item;
	boolean hasGroundTruth;
	/**
	 * @param network
	 */
	public ClusteringQualityAssessment(Network network,ResultItem item,boolean hasGroundTruth) {
		super(network);
		// TODO Auto-generated constructor stub
		this.item = item;
		this.hasGroundTruth = hasGroundTruth;
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.algorithms.NetworkAlgorithms#execute()
	 */
	@Override
	void execute() {
		// TODO Auto-generated method stub
		if(hasGroundTruth){
			computeAcc(item);
			NMI nmi = new NMI(network);
			nmi.run();
			item.nmi = MathUtils.cutDouble(nmi.getNmi(),4);
			PWF pwf = new PWF(network);
			pwf.run();
			item.pwf =  MathUtils.cutDouble(pwf.getPwf(),4);
		}
		Modu modu = new Modu(network);
		modu.run();
		item.modu =  MathUtils.cutDouble(modu.getModu(),4);
		Entropy entropy = new Entropy(network);
		entropy.run();
		item.entr =  MathUtils.cutDouble(entropy.getEntropy(),4);
		System.out.println(item.toString());
		try {
			this.writeToResult(network.networkName, item.toString()+"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void computeAcc(ResultItem item){
		float correctNum = 0;
		List<String> flags = new ArrayList<String>();
		
		List<Community> communities = network.getCommunities();
		for(Community community:communities){
			String classFlag = community.getClassFlag();
			for(int i=0;i<community.getNodes().size();i++){
				if(community.getNode(i).getAttribute("class_flag").equals(classFlag)){
					correctNum++;
				}
			}
			flags.add(classFlag+":"+community.getNodes().size());
		}
		float accu = (correctNum/network.nodeNum);
		item.acc = MathUtils.cutDouble((double) accu,4);
	}
	public void writeToResult(String networkName,String text) throws IOException{
		BufferedWriter bufferedWriter = new BufferedWriter
				(new FileWriter(new File("I:\\network\\result\\"+networkName+".txt"),true));
		bufferedWriter.write(text);
		
		bufferedWriter.close();
	}
}
