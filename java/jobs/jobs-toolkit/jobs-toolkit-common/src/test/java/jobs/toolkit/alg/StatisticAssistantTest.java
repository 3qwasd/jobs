package jobs.toolkit.alg;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import jobs.toolkit.utils.RandomUtils;
public class StatisticAssistantTest {
	
	int len = 999999;
	
	CInt[] input = new CInt[len];
	
	CInt selected;
	
	int k = 999999;
	
	@Before
	public void setUp() throws Exception {
		for(int i=0;i<len;i++){
			input[i] = new CInt().setValue(RandomUtils.uniform(len));
		}
	}

	@Test
	public void testSelectTArrayInt() {
		long start = System.currentTimeMillis();
		selected = StatisticAssistant.select(input, k);
		long consume = System.currentTimeMillis() - start;
		System.out.println(consume/1000 + "秒");
	}
	@After
	public void tearDown() throws Exception {
		System.out.println(selected);
		SortAssistant.quickSort(input);
		for(int i=0;i<k;i++){
			System.out.print(input[i].value + "  ");
			if(i % 10 == 0) System.out.println();
		}
		Assert.assertTrue(selected == input[k-1]);
	}


}
