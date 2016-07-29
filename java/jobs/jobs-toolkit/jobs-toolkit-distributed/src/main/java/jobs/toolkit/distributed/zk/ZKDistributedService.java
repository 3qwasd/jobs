package jobs.toolkit.distributed.zk;

import jobs.toolkit.service.DistributedService;
import jobs.toolkit.service.ServiceDescriptor;

public abstract class ZKDistributedService extends DistributedService{

	public ZKDistributedService(String name) {
		super(name);
	}

	@Override
	protected void serviceInit() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void serviceStart() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void serviceStop() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ServiceDescriptor initServiceDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void register() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reregister() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deregister() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ServiceDescriptor discovery(String serviceName) {
		// TODO Auto-generated method stub
		return null;
	}

}
