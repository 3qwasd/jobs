package jobs.toolkit.distributed;

import java.util.List;

/**
 * Master类型节点
 * @author jobs
 *
 */
public interface Master extends Common{
	
	void runForLeader();
	
	void takeLeadShip();
	
	void takeCandidateShip();
	
	void watchLeader();
	
	void checkLeader();
	
	void watchWorkers();
	
	List<DistributedServiceDescriptor> getWorkerList();
	
	void initLocalWorkerList();
	
	void refreshLocalWorkerList();
	
	void handleUnKnowError();
	
	<T> void assignTask(T task);
	
	public <T> void submitTask(T task);
}
