package jobs.toolkit.alg;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import jobs.toolkit.utils.RandomUtils;

public class SortAssistantTest {
	
	int len = 19999999;
	
	CInt[] input = new CInt[len];
	
	@Before
	public void setUp() throws Exception {
		for(int i=0;i<len;i++){
			input[i] = new CInt().setValue(RandomUtils.uniform(len));
		}
	}

	@After
	public void tearDown() throws Exception {
		for(int i=0;i<len - 1;i++){
			Assert.assertTrue(input[i].compareTo(input[i + 1]) <= 0);
		}
	}
	@Test
	public void testQuickForkJoin() throws Exception{
		long start = System.currentTimeMillis();
		ForkJoinPool pool = new ForkJoinPool(4);
		Future<Void> future = pool.submit(new QuickSortTask<CInt>(input));
		future.get();
		long consume = System.currentTimeMillis() - start;
		System.out.println(consume/1000 + "秒");
	}
	@Test
	public void testQuickSort(){
		SortAssistant.quickSort(null);
		SortAssistant.quickSort(new CInt[0]);
		SortAssistant.quickSort(new CInt[]{new CInt().setValue(10)});
		SortAssistant.quickSort(new CInt[]{new CInt().setValue(10), new CInt().setValue(10)});
		long start = System.currentTimeMillis();
		SortAssistant.quickSort(input);
		long consume = System.currentTimeMillis() - start;
		System.out.println(consume/1000 + "秒");
	}
	@Test
	public void testMaxHeapSort(){
		SortAssistant.maxHeapSort(null);
		SortAssistant.maxHeapSort(new CInt[0]);
		SortAssistant.maxHeapSort(new CInt[]{new CInt().setValue(10)});
		SortAssistant.maxHeapSort(new CInt[]{new CInt().setValue(10), new CInt().setValue(10)});
		long start = System.currentTimeMillis();
		SortAssistant.maxHeapSort(input);
		long consume = System.currentTimeMillis() - start;
		System.out.println(consume/1000 + "秒");
	}
	@Test
	public void testRandomInReplace(){
		for(int i=0;i<len;i++){
			input[i].setValue(i);
		}
		SortAssistant.randomInReplace(input);
		for(int i=0;i<len;i++){
			System.out.println(input[i].value);
		}
	}
	@Test
	public void testMergerSortForkJoin() throws Exception{
		long start = System.currentTimeMillis();
		ForkJoinPool pool = new ForkJoinPool(4);
		Future<Void> future = pool.submit(new MergerSortTask<CInt>(input));
		future.get();
		long consume = System.currentTimeMillis() - start;
		System.out.println(consume/1000 + "秒");
	}
	@Test
	public void testMergerSortRecursion() {
		long start = System.currentTimeMillis();
		SortAssistant.mergerSortRecursion(input);
		long consume = System.currentTimeMillis() - start;
		System.out.println(consume/1000 + "秒");		
	}

	@Test
	public void testInsertSort() {
		SortAssistant.insertSort(null);
		SortAssistant.insertSort(new CInt[0]);
		SortAssistant.insertSort(new CInt[]{new CInt().setValue(10)});
		SortAssistant.insertSort(new CInt[]{new CInt().setValue(10), new CInt().setValue(10)});
		long start = System.currentTimeMillis();
		SortAssistant.insertSort(input);
		long consume = System.currentTimeMillis() - start;
		System.out.println(consume/1000 + "秒");		
	}
}
