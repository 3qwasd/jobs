package jobs.toolkit.concurrency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CollectionTest {
	
	public static void main(String[] args){
		List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G"));

		for(Iterator<String> iter = list.iterator(); iter.hasNext();){
			String s = iter.next();
			list.remove(s);
			//list.add("xxxx");
		}
		System.out.println(list.size());
	}
}
