/**
 * @QiaoJian
 */
package cn.edu.bjtu.qj.graph.algorithms;

import cn.edu.bjtu.qj.graph.algorithms.datastrut.IndexValue;

/**
 * @author QiaoJian
 *	
 * 快速排序
 */
public class QKSort {

	/**
	 * 快速排序
	 * @param array
	 * @param type 0 asc 1 desc
	 */
	public IndexValue<Double>[] execute(IndexValue<Double>[] array,int type){
		int low = 0;
		int high = array.length-1;
		if(type == 0){
			sortAsc(low, high,array);
		}else{
			sortDesc(low,high,array);
		}
		return array;
	}
	
	/**
	 * @param low
	 * @param high
	 */
	private void sortDesc(int plow, int phigh,IndexValue<Double>[] array) {
		// TODO Auto-generated method stub
		if(!(plow<phigh)){
			return;
		}
		int low = plow;
		int high = phigh;
		IndexValue<Double> privotkey = array[low];
		while(low<high){
			while(low<high&&(array[high].value<=privotkey.value)){
				high--;
			}
			swap(low, high,array);
			while(low<high&&(array[low].value>=privotkey.value)){
				low++;
			}
			swap(low, high,array);
		}
		sortDesc(plow,low-1,array);
		sortDesc(low+1,phigh,array);
	}
	private void sortAsc(int plow,int phigh,IndexValue<Double>[] array){
		if(!(plow<phigh)){
			return;
		}
		int low = plow;
		int high = phigh;
		IndexValue<Double> privotkey = array[low];
		while(low<high){
			while(low<high&&(array[high].value>=privotkey.value)){
				high--;
			}
			swap(low, high,array);
			while(low<high&&(array[low].value<=privotkey.value)){
				low++;
			}
			swap(low, high,array);
		}
		sortAsc(plow,low-1,array);
		sortAsc(low+1,phigh,array);
	}
	private void swap(int low,int high,IndexValue<Double>[] array){
		IndexValue<Double> temp = array[low];
		array[low] = array[high];
		array[high] = temp;
	}
}
