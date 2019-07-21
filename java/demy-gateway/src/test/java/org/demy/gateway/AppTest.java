package org.demy.gateway;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
	
	@Test
	public void testMd5() throws NoSuchAlgorithmException {
		
		System.out.println("e10adc3949ba59abbe56e057f20f883e".length());
		
		String md5 = "e10adc3949ba59abbe56e057f20f883e";
		
		MessageDigest sha = MessageDigest.getInstance("MD5");
		
		byte[] md5Bytes = sha.digest(md5.getBytes());
		
		System.out.println(md5Bytes.length);
		
	//	ExecutorService es = Executors.newCachedThreadPool();
		
	}
	
	@Test
	public void testSwitch() {
		int currentState = 0;
		
		switch (currentState) {
		case 0:{
			System.out.println(currentState);
			currentState = 0;
		}
		case 1:
			System.out.println(currentState);
			currentState = 0;
		case 2:
			System.out.println(currentState);
		default:
			break;
		}
	}
}
