package jobs.toolkit.event;

/**
 * 事件接口, 事件驱动模型
 * @author jobs
 *
 * @param <T> 事件的结果类型
 */
public interface Event {
	
	/**
	 * 获取事件类型
	 * @return
	 */
	public EventType getEventType();
	/**
	 * 获取事件发生的时间
	 * @return
	 */
	long getOccurtime();
	/**
	 * 获取事件ID
	 * @return
	 */
	public String getId();
	/**
	 * 获取事件的结果
	 * @return
	 */
	public Object getOutcome();
	
}
