package jobs.toolkit.study;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Base64;

import org.junit.Before;
import org.junit.Test;

public class Base64Test {
	
	@Before
	public void setUp(){
		
	}
	
	@Test
	public void testBasic() throws Exception{
		String src = "长风几厘米";
		String coder = Base64.getEncoder().encodeToString(src.getBytes("UTF-8"));
		System.out.println(coder);
		String des = new String(Base64.getDecoder().decode(coder), "UTF-8");
		System.out.println(des);
	}
	@Test
	public void testMime() throws Exception{
		String src = "明月出天山，苍茫云海间，长风几厘米，吹度玉门关！";
		String coder = Base64.getMimeEncoder().encodeToString(src.getBytes("UTF-8"));
		System.out.println(coder);
		BufferedReader reader = new BufferedReader(new StringReader(coder));
		for(String line = reader.readLine(); line != null && !line.isEmpty(); line = reader.readLine()){
			System.out.println(line + ":lenth-" + line.length());
		}
		String des = new String(Base64.getMimeDecoder().decode(coder));
		System.out.println(des);
	}
	@Test
	public void testUrl() throws Exception{
		String src = "http://localhost:8080/mediamatch/index.html";
		String coder = Base64.getUrlEncoder().encodeToString(src.getBytes("UTF-8"));
		System.out.println(coder);
		String des = new String(Base64.getUrlDecoder().decode(coder), "UTF-8");
		System.out.println(des);
	}
}
