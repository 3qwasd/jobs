package jobs.toolkit.support.lucene.mmseg;

import com.chenlb.mmseg4j.Dictionary;

import jobs.toolkit.utils.TestUtils;


public class MMSegDicTest {
	
	public static void main(String[] args){
		
		String path = Thread.currentThread().getContextClassLoader().getResource("./dics").getPath();
		System.out.println(path);
		//Dictionary dic = Dictionary.getInstance("./src");
		Dictionary dic = Dictionary.getInstance(path);
		TestUtils.showMemoryStatus();
	}
}
