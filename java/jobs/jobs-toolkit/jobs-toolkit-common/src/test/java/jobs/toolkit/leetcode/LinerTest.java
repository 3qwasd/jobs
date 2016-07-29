package jobs.toolkit.leetcode;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import jobs.toolkit.leetcode.Liner;

public class LinerTest {
	
	@Test
	public void testRemoveDuplicates() {
		Integer[] intArray = new Integer[100];
		Random random = new Random();
		for(int i = 0; i < 100; i++){
			intArray[i] = random.nextInt(20);
		}
		Arrays.sort(intArray);
		for(int i = 0; i < 100; i++){
			System.out.print(intArray[i]+",");
		}
		System.out.println();
		int len = Liner.removeDuplicates(intArray);
		Integer[] newArray = new Integer[len];
		System.arraycopy(intArray, 0, newArray, 0, len);
		for(int i = 0; i < len; i++){
			System.out.print(intArray[i]+",");
		}
		for(int i = 0; i < len; i++){
			System.out.print(newArray[i]+",");
		}
	}

}
