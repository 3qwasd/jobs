package jobs.toolkit.support.yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import jobs.toolkit.config.MapConfiguration;

public class YamlConfiguration extends MapConfiguration {

	@Override
	public void parse(String configFile) {
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(configFile);
			this.parse(inputStream);
		} catch (Exception e) {
			throw new IllegalArgumentException("Load configuration file " + configFile + "occur some error!", e);
		} 
	}
	@SuppressWarnings("unchecked")
	@Override
	public void parse(InputStream stream) {
		try{
			Yaml yaml = new Yaml();
			this.setMap((Map<String, Object>) yaml.load(stream));
		}finally{
			try { if(stream != null) stream.close(); } catch (IOException _e) {}
		}
	}
}
