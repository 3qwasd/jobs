/**
*
**/
package cn.edu.bjtu.cnetwork.test;

import org.junit.Test;

import cn.edu.bjtu.cnetwork.algorithms.TriadMap;

/**
 * @author QiaoJian
 *
 */
public class TriadMapTest {
	
	@Test
	public void testTriadMap(){
		TriadMap<double[]> map = new TriadMap<>();
		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				double[] val = new double[]{1.0*(i+j+1),1.5*(i+j+1),1.9*(i+j+1)};
				map.put(i, j, val);
			}
		}
		
		double[] val = map.get(0,0);
		System.out.println(map.contains(1, 1));
		for(int i=0;i<val.length;i++){
			System.out.println(val[i]);
			val[i] = Math.random();
		}
		val = map.get(0,0);
		for(int i=0;i<val.length;i++){
			System.out.println(val[i]);
		}
	}
}
