package jobs.toolkit.nio2;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



public class Nio2Test {
	
	Path path;
	
	Path path1;
	
	@Before
	public void before(){
		path = FileSystems.getDefault().getPath("D:\\中文分词词库整理");
		path1 = FileSystems.getDefault().getPath("D:\\不存在的目录");
	}
	
	@Test
	public void FilesTest(){
		List<?> list = new ArrayList<>();
		Assert.assertTrue(Files.exists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS}));
		Assert.assertFalse(Files.exists(path1));
		Assert.assertTrue(Files.notExists(path1));
		
		try(DirectoryStream<Path> ds = Files.newDirectoryStream(path)){
			
			for(Path file : ds){
				System.out.println(file.getFileName());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Path newPath = path.resolve("30万 中文分词词库.txt");
		try {
			List<String> lines = Files.readAllLines(newPath, Charset.forName("UTF-8"));
			for(String line:lines){
				System.out.println(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(FileVisitResult constant : FileVisitResult.values())
			System.out.println(constant);
	}
	@Test
	public void FileVistorTest(){
		for(FileVisitResult constant : FileVisitResult.values())
			System.out.println(constant);
	
		
	}
}
