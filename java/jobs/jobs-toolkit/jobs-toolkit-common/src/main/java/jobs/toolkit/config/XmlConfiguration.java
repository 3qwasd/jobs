package jobs.toolkit.config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlConfiguration extends AbstractConfiguration {
	
	Element config;
	
	public XmlConfiguration(Element config) {
		this.config = config;
	}
	public XmlConfiguration(String configFile){
		this.parse(configFile);
	}
	public XmlConfiguration(){}
	@Override
	public void parse(String configFile) {
		try {
			FileInputStream inputStream = new FileInputStream(configFile);
			this.parse(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Loading configuration file" + configFile + " occur some error!", e);
		}
	}
	
	
	@Override
	public List<Configuration> getSubConfigs(){
		// TODO Auto-generated method stub
		if(!this.config.hasChildNodes())
			return null;
		NodeList nodeList = this.config.getChildNodes();
		List<Configuration> subConfigs = new ArrayList<Configuration>();
		for(int i = 0; i < nodeList.getLength(); i++){
			Node node = nodeList.item(i);
			if(node != null && node.getNodeType() == Node.ELEMENT_NODE){
				subConfigs.add(new XmlConfiguration((Element) node));
			}
		}
		return subConfigs.isEmpty() ? null : subConfigs;
	}
	@Override
	public String getAttribute(String key) {
		// TODO Auto-generated method stub
		String attr = this.config.getAttribute(key);
		if(attr != null && !attr.isEmpty())
			return attr;
		return null;
	}
	@Override
	public int getInt(String key) {
		// TODO Auto-generated method stub
		String attr = this.config.getAttribute(key);
		if(attr != null && !attr.isEmpty())
			return Integer.valueOf(attr);
		
		return Integer.MIN_VALUE;
	}

	@Override
	public long getLong(String key) {
		String attr = this.config.getAttribute(key);
		if(attr != null && !attr.isEmpty())
			return Long.valueOf(attr);
		
		return Long.MIN_VALUE;
	}

	@Override
	public String getString(String key) {
		// TODO Auto-generated method stub
		return this.getAttribute(key);
	}
	
	/**
	 * 获取boolean属性, 如果属性为空, 返回null, 如果属性为字符串true则返回Boolean.true, 否则返回Boolean.false
	 */
	@Override
	public Boolean getBoolean(String key) {
		String attr = this.config.getAttribute(key);
		if(attr == null || attr.isEmpty())
			return null;
		return Boolean.valueOf(this.config.getAttribute(key));
	}
	
	public String getConfigName(){
		if(this.config == null) return null;
		return this.config.getNodeName();
	}
	@Override
	public Configuration getSubConfig(String cfgName) {
		if(!this.config.hasChildNodes())
			return null;
		NodeList nodeList = this.config.getElementsByTagName(cfgName);
		if(nodeList != null && nodeList.getLength() > 0){
			Node node = nodeList.item(0);
			if(node.getNodeType() == Node.ELEMENT_NODE){
				return new XmlConfiguration((Element) node);
			}
		}
		return null;
	}
	@Override
	public void parse(InputStream stream) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try{
			Document doc = dbf.newDocumentBuilder().parse(stream);
			this.config = doc.getDocumentElement();
		}catch(Exception e){
			throw new RuntimeException("Loading configuration file occur some error!", e);
		}
	}
	@Override
	public String getAttribute(String key, Object defVal) {
		String attr = this.config.getAttribute(key);
		if(attr != null && !attr.isEmpty())
			return attr;
		return (String) defVal;
	}
	@Override
	public int getInt(String key, int defVal) {
		// TODO Auto-generated method stub
				String attr = this.config.getAttribute(key);
				if(attr != null && !attr.isEmpty())
					return Integer.valueOf(attr);
				
				return defVal;
	}
	@Override
	public long getLong(String key, long defVal) {
		String attr = this.config.getAttribute(key);
		if(attr != null && !attr.isEmpty())
			return Long.valueOf(attr);
		
		return defVal;
	}
	@Override
	public String getString(String key, String defVal) {
		String attr = this.config.getAttribute(key);
		if(attr != null && !attr.isEmpty())
			return attr;
		return defVal;
	}
	@Override
	public Boolean getBoolean(String key, boolean defVal) {
		String attr = this.config.getAttribute(key);
		if(attr == null || attr.isEmpty())
			return defVal;
		return Boolean.valueOf(this.config.getAttribute(key));
	}
}
