/**
*
**/
package cn.edu.bjtu.cnetwork;

/**
 * @author QiaoJian
 *
 */
public class ComparableValue implements Comparable<ComparableValue>{
	
	public int id;
	public Comparable value;
	
	public static int SORT_TYPE = 0;
	
	/**
	 * @param id
	 * @param value
	 */
	public ComparableValue(int id, Comparable value) {
		super();
		this.id = id;
		this.value = value;
	}


	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(ComparableValue o) {
		// TODO Auto-generated method stub
		if(SORT_TYPE == 0)
			return this.value.compareTo(o.value);
		else
			return -this.value.compareTo(o.value);
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj instanceof ComparableValue){
			if(this.id == ((ComparableValue)obj).id)
				return true;
		}
		return false;
	}
	
	public String toString(){
		String str="id:"+id+"-"+"pr:"+value;
		return str;
	}
}
