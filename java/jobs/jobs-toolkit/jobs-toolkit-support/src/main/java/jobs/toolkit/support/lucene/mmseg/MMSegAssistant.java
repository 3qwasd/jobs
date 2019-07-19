package jobs.toolkit.support.lucene.mmseg;

import com.chenlb.mmseg4j.Dictionary;

public class MMSegAssistant {
	
	private static final MMSegAssistant intance = new MMSegAssistant();
	
	private MMSegAssistant(){};
	
	public static MMSegAssistant getInstance(){
		return intance;
	}
	
	private volatile Dictionary dictionary;
	
	public static Dictionary getDictionary(){
		return getInstance().dictionary;
	}
	
	public static void init(String path){
		getInstance().dictionary = Dictionary.getInstance(path);
	}
}
