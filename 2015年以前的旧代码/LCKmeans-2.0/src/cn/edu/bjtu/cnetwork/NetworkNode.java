/**
 * @QiaoJian
 */
package cn.edu.bjtu.cnetwork;

import java.util.ArrayList;
import java.util.List;

import cn.edu.bjtu.qj.graph.component.AdjListNode;

/**
 * @author QiaoJian
 *
 */
public class NetworkNode extends AdjListNode{
	
	public double[] nodeContentDouble;
	public short[] nodeContentShort;
	public int[] nodeContentInt;
	public float[] nodeContentFloat;
	public byte[] nodeContentByte;
	public double[] nodeTopicDis;
	private List<NetworkNode> neighbours = new ArrayList<>();
	public NetworkNode(int id, String name) {
		super(id, name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public NetworkNode() {
		// TODO Auto-generated constructor stub
	}
	
	public void addNeighbours(NetworkNode neighbour){
		if(neighbours == null){
			neighbours = new ArrayList<>();
		}
		if(!neighbours.contains(neighbour)) neighbours.add(neighbour);
	}

	/**
	 * @return the neighbours
	 */
	public List<NetworkNode> getNeighbours() {
		return neighbours;
	}

	/* (non-Javadoc)
	 * @see cn.edu.bjtu.qj.graph.component.Node#toString()
	 */
	@Override
	public String toString() {
		String str = "";
		str += "id:"+this.getId();
		return str;
	}
	
}
