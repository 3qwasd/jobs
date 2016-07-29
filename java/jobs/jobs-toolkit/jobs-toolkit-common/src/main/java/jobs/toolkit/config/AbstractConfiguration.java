package jobs.toolkit.config;

import java.io.InputStream;
import java.util.List;

/**
 * 抽象的
 * @author jobs
 *
 */
public abstract class AbstractConfiguration implements Configuration{
	
	public abstract void parse(String configFile);

	public abstract void parse(InputStream stream);
	
	@Override
	public Object getAttribute(String key) {
		return null;
	}

	@Override
	public int getInt(String key) {
		return 0;
	}

	@Override
	public long getLong(String key) {
		return 0;
	}

	@Override
	public String getString(String key) {
		return null;
	}

	@Override
	public Boolean getBoolean(String key) {
		return null;
	}
	
	@Override
	public boolean containsKey(String key) {
		return false;
	};
	
	@Override
	public List<Configuration> getSubConfigs() {
		throw new UnsupportedOperationException();
	}
	
	public List<Configuration> getSubConfigs(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Configuration getSubConfig(String cfgName) {
		throw new UnsupportedOperationException();
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
}
