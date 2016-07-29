/**
 * @QiaoJian
 */
package cn.edu.bjtu.qj.graph.algorithms;

import cn.edu.bjtu.qj.graph.algorithms.datastrut.IndexValue;


/**
 * @author QiaoJian
 *
 */
@Deprecated
public class QuickSort{

	IndexValue<Double>[] source;
	//1代表desc,0代表asc
	int type = 0;
	/**
	 * 
	 */
	public QuickSort(IndexValue<Double>[] source) {
		// TODO Auto-generated constructor stub
		this.source = source;
	}
	public QuickSort(IndexValue<Double>[] source,int sortType) {
		// TODO Auto-generated constructor stub
		this.source = source;
		this.type = sortType;
	}
	public void execute(){
		int low = 0;
		int high = source.length-1;
		if(type == 0){
			sortAsc(low, high);
		}else{
			sortDesc(low,high);
		}
	}
	/**
	 * @param low
	 * @param high
	 */
	private void sortDesc(int plow, int phigh) {
		// TODO Auto-generated method stub
		if(!(plow<phigh)){
			return;
		}
		int low = plow;
		int high = phigh;
		IndexValue<Double> privotkey = source[low];
		while(low<high){
			while(low<high&&(source[high].value<=privotkey.value)){
				high--;
			}
			swap(low, high);
			while(low<high&&(source[low].value>=privotkey.value)){
				low++;
			}
			swap(low, high);
		}
		sortDesc(plow,low-1);
		sortDesc(low+1,phigh);
	}
	private void sortAsc(int plow,int phigh){
		if(!(plow<phigh)){
			return;
		}
		int low = plow;
		int high = phigh;
		IndexValue<Double> privotkey = source[low];
		while(low<high){
			while(low<high&&(source[high].value>=privotkey.value)){
				high--;
			}
			swap(low, high);
			while(low<high&&(source[low].value<=privotkey.value)){
				low++;
			}
			swap(low, high);
		}
		sortAsc(plow,low-1);
		sortAsc(low+1,phigh);
	}
	private void swap(int low,int high){
		IndexValue<Double> temp = source[low];
		source[low] = source[high];
		source[high] = temp;
	}
}
