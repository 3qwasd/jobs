package jobs.toolkit.support.yarn;

import org.apache.hadoop.yarn.client.api.YarnClient;

import jobs.toolkit.service.BaseService;

public class YarnClientServer extends BaseService{

	
	
	public YarnClientServer(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initialize() throws Exception {
		YarnClient yarnClient = YarnClient.createYarnClient();
	}
	
	
}
