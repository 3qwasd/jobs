package jobs.toolkit.toolkit;

public class ConfigFileUtilsTest {
	
	public static void main(String[] args){
		System.out.println(ConfigFileUtilsTest.class.getResource("/").getPath());
		System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath());
	}
}
