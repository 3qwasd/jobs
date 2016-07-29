/**
*
**/
package cn.edu.bjtu.cnetwork.algorithms;

import java.util.HashMap;

/**
 * @author QiaoJian
 *
 */
public class TriadMap<T> extends HashMap<TriadMapKey, T> {

	private static final long serialVersionUID = 3337363740635914248L;

	/* (non-Javadoc)
	 * @see java.util.HashMap#get(java.lang.Object)
	 */
	public void put(int row,int col,T value){
		TriadMapKey key = new TriadMapKey(row, col);
		this.put(key, value);
	}
	public T get(int row,int col){
		TriadMapKey key = new TriadMapKey(row, col);
		if(this.containsKey(key)) return this.get(key);
		
		return null;
	}
	public boolean contains(int row,int col){
		TriadMapKey key = new TriadMapKey(row, col);
		return this.containsKey(key);
	}
}

class TriadMapKey {
	int row;
	int col;
	/**
	 * @param row
	 * @param col
	 */
	public TriadMapKey(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(!(obj instanceof TriadMapKey)) return false;
		TriadMapKey key = (TriadMapKey) obj;
		if(TriadMapKey.this.row == key.row&&TriadMapKey.this.col == key.col) return true;

		return super.equals(obj);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		String rcStr = row+" "+col;
		return rcStr.hashCode();
	}
}
