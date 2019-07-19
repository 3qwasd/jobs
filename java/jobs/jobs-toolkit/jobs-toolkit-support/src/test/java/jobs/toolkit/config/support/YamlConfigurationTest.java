package jobs.toolkit.config.support;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import jobs.toolkit.config.Configuration;
import jobs.toolkit.support.yaml.YamlConfiguration;

public class YamlConfigurationTest {
	
	YamlConfiguration yamlcfg;
	@Before
	public void init(){
		yamlcfg = new YamlConfiguration();
		String classPath = YamlConfigurationTest.class.getClassLoader().getResource(".").getPath();
		System.out.println(classPath);
		String path = classPath + "jobs/toolkit/resources/test.yaml";
		yamlcfg.parse(path);
	}
	
	@Test
	public void test(){
		Configuration cfg = yamlcfg.getSubConfig("billTo");
		System.out.println(cfg);
		List<Configuration> subList = yamlcfg.getSubConfigs("product");
		System.out.println(subList);
	}
	
	/*测试驱动开发*/
	public static void main(String[] args) throws FileNotFoundException{
		Yaml yaml = new Yaml();
		String path = YamlConfigurationTest.class.getClassLoader().getResource("test.yaml").getPath();
		Iterable<Object> iter = yaml.loadAll(new FileInputStream(path));
		
		for(Object obj : iter){
			System.out.println(obj.getClass());
		}
		
	}
}
