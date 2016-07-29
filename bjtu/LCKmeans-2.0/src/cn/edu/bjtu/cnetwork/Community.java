/**
 * @QiaoJian
 */
package cn.edu.bjtu.cnetwork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.edu.bjtu.qj.graph.component.Node;
import cn.edu.bjtu.utils.MathUtils;


/**
 * @author QiaoJian
 *
 */
public class Community extends Network implements Comparable<Community>{
	
	public int id;
	
	NetworkNode seed;
	
	public Map<String,Integer> classDistribute;
	
	private String classFlag;
	
	public Community() {
		this.pageRankValues = new ArrayList<>();
		classDistribute = new HashMap<String, Integer>();
	}

	/**
	 * @return the seed
	 */
	public NetworkNode getSeed() {
		return seed;
	}

	/**
	 * @param seed the seed to set
	 */
	public void setSeed(NetworkNode seed) {
		this.seed = seed;
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.cnetwork.Network#addNode(cn.edu.bjtu.qj.graph.component.Node)
	 */
	@Override
	public void addNode(Node node) {
		// TODO Auto-generated method stub
		this.getNodes().add(node);
		this.pageRankValues.add((ComparableValue) node.getAttribute("pagerank"));
		String classFlag = (String) node.getAttribute("class_flag");
		if(classDistribute.containsKey(classFlag)){
			classDistribute.put(classFlag, classDistribute.get(classFlag)+1);
		}else{
			classDistribute.put(classFlag, 1);
		}
		this.nodeNum++;
	}
	
	public String getClassFlag(){
		if(classFlag == null) classFlag = (String) this.seed.getAttribute("class_flag");
		return classFlag;
			
	}
	public void addAll(Community community){
		this.getNodes().addAll(community.getNodes());
		for(String key:community.classDistribute.keySet()){
			if(this.classDistribute.containsKey(key)){
				int value = this.classDistribute.get(key)+community.classDistribute.get(key);
				this.classDistribute.put(key, value);
			}else{
				this.classDistribute.put(key, community.classDistribute.get(key));
			}
		}
		this.nodeNum = this.getNodes().size();
	}
	public void setClassFlag(String classFlag){
		this.classFlag = classFlag;
		for(int i=0;i<this.getNodes().size();i++){
			this.getNode(i).putAttribute("class_flag_new", classFlag);
		}
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String str = "-----------------------------------------------------------------------------------\n";
		str+="id:"+id+" nodeNum:"+nodeNum+" classFlag:"+this.classFlag+"\n";
		str+="seed:"+seed.toString()+" seed.classFlag:"+seed.getAttribute("class_flag")+"\n";
		str+="[";
		for(String key:classDistribute.keySet()){
			str+=key+":"+classDistribute.get(key)+",";
		}
		str+="]\n";
		str+="[";
		for(String key:classDistribute.keySet()){
			str+=key+":"+MathUtils.cutDouble(((double)classDistribute.get(key))/nodeNum,4)*100+"%"+",";
		}
		str+="]";
		return str;
	}
	public String computeClassFlag(){
		int max = 0;
		for(String key:classDistribute.keySet()){
			int num = classDistribute.get(key);
			if(num>max){
				max = num;
				classFlag = key;
			}
		}
		for(int i=0;i<this.getNodes().size();i++){
			Node node = this.getNode(i);
			node.putAttribute("class_flag_new", classFlag);
			node.putAttribute("community_id", id);
		}
		return classFlag;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Community c) {
		// TODO Auto-generated method stub
		if(this.getNodes().size()<c.getNodes().size())
			return 1;
		if(this.getNodes().size()>c.getNodes().size())
			return -1;
		return 0;
	}
}
