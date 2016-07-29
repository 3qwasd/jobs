package jobs.toolkit.service;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 组合型服务, 组合服务会维护一组服务
 * @author jobs
 *
 */
public abstract class CompositeService extends BaseService {

	/*服务列表*/
	private final Set<BaseService> services = new CopyOnWriteArraySet<BaseService>();
	
	public CompositeService(String name) {
		super(name);
	}

	@Override
	protected void initialize() throws Exception {
		LOG.info(String.format("Composite service %1$s initialize it's contains %2$s services", this.getName(), services.size()));
		super.initialize();
		for(BaseService service : services){
			service.init(this.getConfiguration());
		}
	}

	@Override
	protected void startup() throws Exception {
		LOG.info(String.format("Composite service %1$s startup it's contains %2$s services", this.getName(), services.size()));
		super.startup();
		for(BaseService service : services){
			service.start();
		}
	}

	@Override
	protected void shutdown() throws Exception {
		LOG.info(String.format("Composite service %1$s shutdown it's contains %2$s services", this.getName(), services.size()));
		super.shutdown();
		for(BaseService service : services){
			service.stop();
		}
	}
	
	protected void addService(BaseService service) {
		LOG.info(String.format("Composite service %1$s add service %2$s", this.getName(), service.getName()));
		services.add(service);
	}
	
	protected void removeService(BaseService service) {
		LOG.info(String.format("Composite service %1$s remove service %2$s", this.getName(), service.getName()));
		services.remove(service);
	}
}
