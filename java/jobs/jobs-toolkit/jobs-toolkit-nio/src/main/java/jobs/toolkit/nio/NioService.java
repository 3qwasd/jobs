package jobs.toolkit.nio;


import java.util.HashMap;
import java.util.Map;
import jobs.toolkit.nio.NioConfig.NioManagerConfig;
import jobs.toolkit.nio.event.NioEventDispatcher;
import jobs.toolkit.service.CompositeService;

/**
 * Nio服务
 * @author jobs
 *
 */
public final class NioService extends CompositeService {
		
	/*网络层引擎*/
	private NioEngine engine;
	/*网络层管理者, 通常一个NioService有一个网络层引擎, 多个网络管理者, 
	 *网络管理者包括服务端或者客户端, 用来管理应用使用的网络连接, 
	 *所有的网络连接都由网络引擎提供的线程处理*/
	private Map<String, NioManager> managers;
	
	private NioEventDispatcher dispatcher;
	
	public NioService(String name) {
		super(name);
	}

	@Override
	protected void initialize() throws Exception {
		this.engine = NioEngine.getInstance();
		this.managers = new HashMap<String, NioManager>();
		this.dispatcher = NioEventDispatcher.getInstance();
		NioConfig config = (NioConfig) this.getConfiguration();
		this.addService(this.engine);
		this.addService(this.dispatcher);
		Map<String, NioManagerConfig> managerConfigs = config.getMangerConfigs();	
		for(String name : managerConfigs.keySet()){
			NioManager manager = this.createNioManager(name, managerConfigs.get(name).getNioManagerClassName());
			this.managers.put(name, manager);
			this.addService(manager);
		}
		
		super.initialize();
	}
	/**
	 * 通过class和name来创建NioManager
	 * @param name
	 * @param className
	 * @return
	 */
	private NioManager createNioManager(String name, String className){
		
		try{
			return (NioManager) Class.forName(className).getConstructor(String.class).newInstance(name);
		}catch(Exception e){
			LOG.error(String.format("Create nio manager [name=%1$s] and [class=%2$s] error.", name, className), e);
			throw new RuntimeException(e);
		}
	}
}
