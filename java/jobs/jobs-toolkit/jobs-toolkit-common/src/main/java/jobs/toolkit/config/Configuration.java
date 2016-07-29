package jobs.toolkit.config;

import java.util.List;

/**
 * 配置接口
 * @author jobs
 *
 */
public interface Configuration {
	
	/**
	 * 获取某一个属性对象, 如果不存在应该返回defVal
	 * @param key
	 * @param defVal
	 * @return
	 */
	public Object getAttribute(String key, Object defVal);
	
	/**
	 * 获取某一个属性对象, 如果不存在应该返回null
	 * @param key
	 * @return
	 */
	public Object getAttribute(String key);
	/**
	 * 返回一个整数属性值, 如果该属性不存在返回Integer.MIN_VALUE
	 * @param key
	 * @return
	 */
	public int getInt(String key);
	/**
	 * 返回一个整数属性值, 如果该属性不存在返回defVal
	 * @param key
	 * @param defVal
	 * @return
	 */
	public int getInt(String key, int defVal);
	
	/**
	 * 返回一个长整数属性值, 如果该属性不存在返回Long.MIN_VALUE
	 * @param key
	 * @return
	 */
	public long getLong(String key);
	/**
	 * 返回一个长整数属性值, 如果该属性不存在返回defVal
	 * @param key
	 * @param defVal
	 * @return
	 */
	public long getLong(String key, long defVal);
	/**
	 * @param key
	 * @return
	 */
	public String getString(String key);
	/**
	 * @param key
	 * @param defVal
	 * @return
	 */
	public String getString(String key, String defVal);
	/**
	 * 返回boolean属性值
	 * @param key
	 * @return
	 */
	public Boolean getBoolean(String key);
	/**
	 * 返回boolean属性值
	 * @param key
	 * @param defVal
	 * @return
	 */
	public Boolean getBoolean(String key, boolean defVal);
	/**
	 * 判断是否包含配置项
	 * @param key
	 * @return
	 */
	public boolean containsKey(String key);
	
	/**
	 * 获取该配置的子配置列表, 如果是树形配置文件结构, 则应该实现该方法, 否则应该在实现中抛出UnsupportedOperationException()
	 * @return
	 */
	public List<Configuration> getSubConfigs();
	public List<Configuration> getSubConfigs(String name);
	public Configuration getSubConfig(String cfgName);
}
