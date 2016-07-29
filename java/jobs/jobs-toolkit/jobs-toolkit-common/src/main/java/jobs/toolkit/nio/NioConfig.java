package jobs.toolkit.nio;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jobs.toolkit.config.AbstractConfiguration;
import jobs.toolkit.config.Configuration;
import jobs.toolkit.config.XmlConfiguration;

/**
 * 网络服务配置
 * @author jobs
 *
 */
public class NioConfig extends XmlConfiguration{
	
	/*配置的属性名*/
	public static final String NIO_ENGINE_NAME         = "NioEngine";
	public static final String NIO_MANAGER_NAME        = "NioManager";
	public static final String NIO_CONNECTOR_NAME      = "NioConnector";
	public static final String NIO_CODER_NAME          = "NioCoder";
	public static final String NIO_ENGINE_THREAD_NUM   = "engineThreadNum";
	public static final String INPUT_BUFFER_SIZE       = "inputBufferSize";
	public static final String KEEP_INPUT_BUFFER       = "keepInputBuffer";
	public static final String OUTPUT_BUFFER_SIZE 	   = "outputBufferSize";
	public static final String KEEP_OUTPUT_BUFFER      = "keepOutputBuffer";
	public static final String RECEIVE_BUFFER_SIZE     = "receiveBufferSize";
	public static final String SEND_BUFFER_SIZE        = "sendBufferSize";
	public static final String TCP_NO_DELAY            = "tcpNoDelay";
	public static final String BACK_LOG                = "backlog";
	public static final String RECONNECT               = "RECONNECT";
	public static final String NIO_REMOTE_IP           = "remoteIp";
	public static final String NIO_REMOTE_PORT         = "remotePort";
	public static final String NIO_SERVER_IP           = "serverIp";
	public static final String NIO_SERVER_PORT         = "serverPort";
	/*一些默认的属性配置值*/
	public static final int DEFAULT_INPUT_BUFFER_SIZE   = 131072;
	public static final int DEFAULT_OUTPUT_BUFFER_SIZE 	= 10485760;
	public static final int DEFAULT_RECEIVE_BUFFER_SIZE = 16384;
	public static final int DEFAULT_SEND_BUFFER_SIZE    = 16384;
	public static final int DEFAULT_BACK_LOG            = 1000;
	public static final boolean DEFAULT_TCP_NO_DELAY       = false;
	public static final boolean DEFAULT_KEEP_INPUT_BUFFER  = false;
	public static final boolean DEFAULT_KEEP_OUTPUT_BUFFER = false;
	public static final boolean DEFAULT_RECONNECT          = true;          
	
	private final Map<String,NioManagerConfig> mangerConfigs = new LinkedHashMap<String,NioManagerConfig>();

	@Override
	public void parse(String configFile) {
		// TODO Auto-generated method stub
		super.parse(configFile);
		if(!this.getConfigName().equals(NIO_ENGINE_NAME))
			throw new IllegalArgumentException("This is not a NIO configuration!");
		List<Configuration> subConfigs = this.getSubConfigs();
		if(subConfigs == null || subConfigs.isEmpty())
			throw new IllegalArgumentException("This NIO configuration did not contain any manager configuration!");
		for(int i = 0; i < subConfigs.size(); i++){
			XmlConfiguration xc = (XmlConfiguration) subConfigs.get(i);
			if(!xc.getConfigName().equals(NIO_MANAGER_NAME)){
				throw new IllegalArgumentException("This configuration node did not manager configuration!");
			}
			NioManagerConfig nmc = new NioManagerConfig(xc);
			this.mangerConfigs.put(xc.getAttribute("name"), nmc);
		}
	}
	
	
	
	public Map<String, NioManagerConfig> getMangerConfigs() {
		return this.mangerConfigs;
	}

	public NioManagerConfig getNioManagerConfig(String name){
		return this.mangerConfigs.get(name);
	}
	
	private static class NioComponentConfig extends AbstractConfiguration{
		
		XmlConfiguration config;

		public NioComponentConfig(XmlConfiguration componentConfig) {
			this.config = componentConfig;
		}
		@Override
		public final void parse(String configFile) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public final Object getAttribute(String key) {
			// TODO Auto-generated method stub
			return this.config.getAttribute(key);
		}

		@Override
		public final int getInt(String key) {
			// TODO Auto-generated method stub
			return this.config.getInt(key);
		}

		@Override
		public final long getLong(String key) {
			// TODO Auto-generated method stub
			return this.config.getLong(key);
		}

		@Override
		public final String getString(String key) {
			// TODO Auto-generated method stub
			return this.config.getString(key);
		}

		@Override
		public final Boolean getBoolean(String key) {
			// TODO Auto-generated method stub
			return this.config.getBoolean(key);
		}
		@Override
		public Configuration getSubConfig(String cfgName) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public void parse(InputStream stream) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public static final class NioManagerConfig extends NioComponentConfig{
		
		
		private NioManagerConfig(XmlConfiguration config) {
			// TODO Auto-generated constructor stub
			super(config);
		}
		public NioConnectorConfig getNioConnectorConfig(){
			for(Iterator<Configuration> iter = this.config.getSubConfigs().iterator(); iter.hasNext();){
				XmlConfiguration config = (XmlConfiguration) iter.next();
				if(config.getConfigName().equals(NIO_CONNECTOR_NAME)){
					return new NioConnectorConfig(config);
				}
			}
			return null;
		}
		public NioCoderConfig getNioCoderConfig(){
			for(Iterator<Configuration> iter = this.config.getSubConfigs().iterator(); iter.hasNext();){
				XmlConfiguration config = (XmlConfiguration) iter.next();
				if(config.getConfigName().equals(NIO_CODER_NAME)){
					return new NioCoderConfig(config);
				}
			}
			return null;
		}
		public String getNioManagerClassName(){
			return this.config.getString("class");
		}
		
		public String getNioManagerName(){
			return this.config.getString("name");
		}
		
		
	}
	public static final class NioCoderConfig extends NioComponentConfig{

		public NioCoderConfig(XmlConfiguration config) {
			super(config);
		}
		
		public String getNioCoderClassName(){
			return this.config.getString("class");
		}
		
		public List<NioCoderAdapterConfig> getNioCoderAdapterConfig(){
			List<Configuration> configs = this.config.getSubConfigs();
			if(configs == null || configs.isEmpty()) return null;
			List<NioCoderAdapterConfig> ncaConfigs = new ArrayList<NioCoderAdapterConfig>();
			for(Configuration config : configs){
				ncaConfigs.add(new NioCoderAdapterConfig((XmlConfiguration) config));
			}
			return ncaConfigs;
		}
	}
	public static final class NioCoderAdapterConfig extends NioComponentConfig{

		public NioCoderAdapterConfig(XmlConfiguration componentConfig) {
			super(componentConfig);
		}
		public String getNioCoderAdapterClassName(){
			return this.config.getString("class");
		}
	}
	public static final class NioConnectorConfig extends NioComponentConfig{
				
		public NioConnectorConfig(XmlConfiguration config) {
			super(config);
		}
		
		public String getNioCreatorClassName(){
			return this.config.getAttribute("class");
		}
		
		public String getNioCreatorName(){
			return this.config.getAttribute("name");
		}
		public int getInputBufferSize() {
			int val = this.config.getInt(INPUT_BUFFER_SIZE);
			if(val > Integer.MIN_VALUE){
				return val;
			}else{
				return DEFAULT_INPUT_BUFFER_SIZE;
			}
		}

		public int getOutputBufferSize() {
			int val = this.config.getInt(OUTPUT_BUFFER_SIZE);
			if(val > Integer.MIN_VALUE){
				return val;
			}else{
				return DEFAULT_OUTPUT_BUFFER_SIZE;
			}
		}

		public boolean isKeepOutputBuffer() {
			Boolean val = this.config.getBoolean(KEEP_OUTPUT_BUFFER);
			if(val == null)
				return false;
			return val;
		}

		public boolean isKeepInputBuffer() {
			Boolean val = this.config.getBoolean(KEEP_INPUT_BUFFER);
			if(val == null)
				return false;
			return val;
		}

		public boolean isTcpNoDelay() {
			Boolean val = this.config.getBoolean(TCP_NO_DELAY);
			if(val == null)
				return false;
			return val;
		}

		public int getReceiveBufferSize() {
			int val = this.config.getInt(RECEIVE_BUFFER_SIZE);
			if(val > Integer.MIN_VALUE){
				return val;
			}else{
				return DEFAULT_RECEIVE_BUFFER_SIZE;
			}
		}

		public int getSendBufferSize() {
			int val = this.config.getInt(SEND_BUFFER_SIZE);
			if(val > Integer.MIN_VALUE){
				return val;
			}else{
				return DEFAULT_SEND_BUFFER_SIZE;
			}
		}
		public int getBackLog(){
			int val = this.config.getInt(BACK_LOG);
			if(val > Integer.MIN_VALUE){
				return val;
			}else{
				return DEFAULT_BACK_LOG;
			}
		}
		public boolean isReconnect(){
			Boolean val = this.config.getBoolean(RECONNECT);
			if(val == null)
				return DEFAULT_RECONNECT;
			return val;
		}
		public String getRemoteIp(){
			String ip = this.config.getString(NIO_REMOTE_IP);
			if(ip == null || ip.trim().isEmpty())
				throw new IllegalArgumentException(String.format("The remote ip is unvalid!"));
			return ip;
		}
		public int getRemotePort(){
			int port = this.config.getInt(NIO_REMOTE_PORT);
			if(port > 0){
				return port;
			}
			throw new IllegalArgumentException(String.format("The remote port is unvalid!"));
		}
		public String getServerIp() {
			String ip = this.config.getString(NIO_SERVER_IP);
			if(ip == null || ip.trim().isEmpty())
				return null;
			return ip;
		}
		public int getServerPort(){
			int port = this.config.getInt(NIO_SERVER_PORT);
			if(port > 0){
				return port;
			}
			throw new IllegalArgumentException(String.format("The server port is unvalid!"));
		}
	}
}
