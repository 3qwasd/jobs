package jobs.toolkit.config;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class MapConfiguration extends AbstractConfiguration{


	Map<String, Object> map;
	@Override
	public Object getAttribute(String key) {
		// TODO Auto-generated method stub
		return this.map.get(key);
	}

	@Override
	public int getInt(String key) {
		// TODO Auto-generated method stub
		return (int) this.map.getOrDefault(key, 0);
	}

	@Override
	public long getLong(String key) {
		// TODO Auto-generated method stub
		return (long) this.map.getOrDefault(key, 0);
	}

	@Override
	public String getString(String key) {
		// TODO Auto-generated method stub
		return (String) this.map.getOrDefault(key, "");
	}

	@Override
	public Boolean getBoolean(String key) {
		// TODO Auto-generated method stub
		return (Boolean) this.map.getOrDefault(key, Boolean.FALSE);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Configuration> getSubConfigs(String name) {
		List<Configuration> subCfgList = new ArrayList<Configuration>();
		Collection<Map<String, Object>> attrs = (Collection<Map<String, Object>>) this.getAttribute(name);
		for(Map<String, Object> attr : attrs ){
			MapConfiguration configuration = new MapConfiguration();
			configuration.setMap(attr);
			subCfgList.add(configuration);
		}
		return subCfgList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Configuration getSubConfig(String cfgName) {
		Map<String, Object> cfg = (Map<String, Object>) this.getAttribute(cfgName);
		MapConfiguration subCfg = new MapConfiguration();
		subCfg.setMap(cfg);
		return subCfg;
	}
	
	@Override
	public boolean containsKey(String key) {
		return this.map.containsKey(key);
	}

	@Override
	public void parse(String configFile) {
		throw new UnsupportedOperationException();
	}
	@Override
	public void parse(InputStream stream) {
		throw new UnsupportedOperationException();
	}
	
	protected void setMap(Map<String, Object> map) {
		this.map = map;
	}

	@Override
	public Object getAttribute(String key, Object defVal) {
		return this.map.getOrDefault(key, defVal);
	}

	@Override
	public int getInt(String key, int defVal) {
		return (int) this.map.getOrDefault(key, defVal);
	}

	@Override
	public long getLong(String key, long defVal) {
		return (long) this.map.getOrDefault(key, defVal);
	}

	@Override
	public String getString(String key, String defVal) {
		return (String) this.map.getOrDefault(key, defVal);
	}

	@Override
	public Boolean getBoolean(String key, boolean defVal) {
		return (Boolean) this.map.getOrDefault(key, defVal);
	}
}
