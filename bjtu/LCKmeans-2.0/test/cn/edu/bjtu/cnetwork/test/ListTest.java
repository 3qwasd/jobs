/**
*
**/
package cn.edu.bjtu.cnetwork.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author QiaoJian
 *
 */
public class ListTest {
	
	@Test
	public void testList(){
		List<String> x = new ArrayList<>();
		x.add("1");x.add("2");x.add("3");x.add("4");
		List<String> y = new ArrayList<>();
		y.add("3");y.add("5");y.add("6");y.add("4");
		y.removeAll(x);
		x.addAll(y);
		System.out.println(x.size());
	}
}
