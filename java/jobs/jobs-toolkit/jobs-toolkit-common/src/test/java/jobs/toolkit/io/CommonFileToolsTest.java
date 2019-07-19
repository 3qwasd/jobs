package jobs.toolkit.io;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Test;

import jobs.toolkit.handler.FileHandler;
import jobs.toolkit.handler.FileProcesser;
import jobs.toolkit.io.CommonFileTools;

/**
 * @author jobs
 *
 */
public class CommonFileToolsTest {

	/**
	 * Test method for {@link jobs.toolkit.io.CommonFileTools#BFSDir(java.lang.String, jobs.toolkit.handler.FileProcesser)}.
	 */
	@Test
	public void testBFSDirStringFileProcesserOfT() {
		String root = "D:\\opensource\\quartz-2.2.1-distribution";
		try {
			List<String> result = CommonFileTools.BFS(root, new FileProcesser<String>() {

				@Override
				public String process(File file) {
					// TODO Auto-generated method stub
					String fileName = file.getName();
					if(fileName.matches("\\.\\_.*")){
						if(!file.delete()){
							System.out.println("file "+fileName+" delete failed!");
							return null;
						}
					}
					return fileName;
				}
			});
			for(String deleteName : result){
				System.out.println(deleteName);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link jobs.toolkit.io.CommonFileTools#BFSDir(java.io.File, jobs.toolkit.handler.FileProcesser)}.
	 */
	@Test
	public void testBFSDirFileFileProcesserOfT() {
		String root = "D:\\opensource\\quartz-2.2.1-distribution\\quartz-2.2.1\\src";
		try {
			List<String> result = CommonFileTools.BFS(root, new FileProcesser<String>() {

				@Override
				public String process(File file) {
					// TODO Auto-generated method stub
					String fileName = file.getName();
					if(fileName.matches("\\.\\_.*")){
						if(!file.delete()){
							System.out.println("file "+fileName+" delete failed!");
							return null;
						}
					}
					return fileName;
				}
			});
			for(String deleteName : result){
				System.out.println(deleteName);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link jobs.toolkit.io.CommonFileTools#BFSDir(java.lang.String, jobs.toolkit.handler.FileHandler)}.
	 */
	@Test
	public void testBFSDirStringFileHandler() {
		String filePath = "D:\\opensource\\quartz-2.2.1-distribution\\quartz-2.2.1\\src";
		try {
			CommonFileTools.BFS(filePath, new FileHandler() {
				
				@Override
				public void handle(File file) {
					// TODO Auto-generated method stub
					if(file.getName().matches("\\.\\_.*")){
						System.out.println(file.getName());
					}
				}
			});
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link jobs.toolkit.io.CommonFileTools#BFSDir(java.io.File, jobs.toolkit.handler.FileHandler)}.
	 */
	@Test
	public void testBFSDirFileFileHandler() {
		fail("Not yet implemented");
	}

}
