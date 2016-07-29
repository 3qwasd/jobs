package jobs.toolkit.config;

import java.util.List;

import jobs.toolkit.config.Configuration;

public class SimpleConfig implements Configuration{

	@Override
	public Object getAttribute(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getInt(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLong(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getString(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean getBoolean(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Configuration> getSubConfigs() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public Configuration getSubConfig(String cfgName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Configuration> getSubConfigs(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public Object getAttribute(String key, Object defVal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getInt(String key, int defVal) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLong(String key, long defVal) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getString(String key, String defVal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean getBoolean(String key, boolean defVal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsKey(String key) {
		// TODO Auto-generated method stub
		return false;
	}

}
