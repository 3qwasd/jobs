/**
 * @QiaoJian
 */
package cn.edu.bjtu.qj.graph.algorithms.datastrut;

/**
 * @author QiaoJian
 *
 */
public class Triple<T extends Number> implements Comparable<Triple<Number>>{
	
	public enum SortType{
		BY_COL,BY_ROW
	}
	public static SortType sortType = SortType.BY_ROW;
	public int row;
	public int col;
	private T weight;
	/**
	 * 
	 */
	public Triple(int row,int col,T weight) {
		// TODO Auto-generated constructor stub
		this.row = row;
		this.col = col;
		this.weight = weight;
	}
	public T getWeight() {
		return weight;
	}
	public void setWeight(T weight) {
		this.weight = weight;
	}
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Triple<Number> o) {
		// TODO Auto-generated method stub
		switch (sortType) {
		case BY_ROW:
			if(this.row>o.row)
				return 1;
			if(this.row<o.row)
				return -1;
			return 0;
		case BY_COL:
			if(this.col>o.col)
				return 1;
			if(this.col<o.col)
				return -1;
			return 0;
		default:
			return 0;
		}
	}
	
	
}
