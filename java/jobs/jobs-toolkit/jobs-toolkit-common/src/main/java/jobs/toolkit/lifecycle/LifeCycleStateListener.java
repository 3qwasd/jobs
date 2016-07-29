package jobs.toolkit.lifecycle;

/**
 * 生命周期状态监听器
 * @author jobs
 *
 */
public interface LifeCycleStateListener {
	
	public void stateChanged(LifeCycle component);
}
