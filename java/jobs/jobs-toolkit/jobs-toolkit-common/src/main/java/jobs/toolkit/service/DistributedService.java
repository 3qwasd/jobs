package jobs.toolkit.service;

/**
 * 分布式服务接口
 * @author jobs
 *
 */
public abstract class DistributedService extends CompositeService {
		
	private volatile ServiceDescriptor serviceDescriptor;
	
	public DistributedService(String name) {
		super(name);
	}
	
	@Override
	protected final void initialize() throws Exception {
		this.serviceInit();
		this.serviceDescriptor = this.initServiceDescriptor();
		super.initialize();
	}
	protected abstract void serviceInit() throws Exception;
	
	@Override
	protected final void startup() throws Exception {
		super.startup();
		this.serviceStart();
		this.register();
	}
	protected abstract void serviceStart() throws Exception;
	
	@Override
	protected final void shutdown() throws Exception {
		try{
			this.deregister();
		}catch(Throwable _t){
			LOG.error(String.format("Service[name=%1$s] deregister error", this.getName()), _t);
		}
		this.serviceStop();
		super.shutdown();
	}
	protected abstract void serviceStop() throws Exception;
	/**
	 * 初始化服务描述
	 * @return
	 */
	public abstract ServiceDescriptor initServiceDescriptor();
	
	/**
	 * 服务注册
	 * @throws Exception 
	 */
	public abstract void register() throws Exception;
	/**
	 * 重新注册服务, 用于更新服务信息
	 * @throws Exception
	 */
	public abstract void reregister() throws Exception;
	/**
	 * 服务注销
	 * @throws Exception
	 */
	public abstract void deregister() throws Exception;
	/**
	 * 通过服务名称发现服务, 并返回被发现服务的服务描述符
	 * @param serviceName
	 * @return
	 */
	public abstract ServiceDescriptor discovery(String serviceName);
	
	/**
	 * 获取当前服务的服务描述符
	 * @return
	 */
	public ServiceDescriptor getServiceDescriptor(){
		return this.serviceDescriptor;
	}
	
}
