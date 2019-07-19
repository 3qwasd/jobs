/**
 * @QiaoJian
 */
package cn.edu.bjtu.qj.graph.algorithms.datastrut;

/**
 * @author QiaoJian
 *
 */
public class IndexValue<T> {
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */


	public int id;
	public T value;
	
	
	public IndexValue() {
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * 
	 */
	public IndexValue(int id) {
		// TODO Auto-generated constructor stub
		this.id = id;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(!(obj instanceof IndexValue)){
			return false;
		}
		IndexValue<T> other = (IndexValue<T>) obj;
		if(this.id == other.id){
			return true;
		}
		return false;
	}

}
