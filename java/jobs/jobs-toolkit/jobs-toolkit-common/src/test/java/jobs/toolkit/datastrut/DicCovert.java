package jobs.toolkit.datastrut;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import jobs.toolkit.io.TextFileTools;

public class DicCovert {
	
	public static void main(String[] args) throws IOException{
		File file = new File("D:\\中文分词词库整理\\30万 中文分词词库.txt");
		BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\中文分词词库整理\\simple.dic"));
		TextFileTools.catFileLBL(file, (l, i) -> {
			String word = l.split("\\s+")[1];
			try {
				writer.write(word);
				writer.write("\n");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		writer.flush();
		writer.close();
	}
}
