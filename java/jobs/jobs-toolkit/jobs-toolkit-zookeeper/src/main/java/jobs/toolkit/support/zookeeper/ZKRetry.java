package jobs.toolkit.support.zookeeper;

/**
 * 该接口表示在丢失连接后是否重试
 * @author jobs
 *
 * @param <T>
 */
public interface ZKRetry<T> {
	/**
	 * 设置是否重试的boolean标记值
	 * @param isRetry
	 * @return
	 */
	public T retry(boolean isRetry);
	/**
	 * 设置重试的次数
	 * @param limit
	 * @return
	 */
	public T retryLimit(int limit);
	/**
	 * 设置重试的间隔时间, 单位为秒
	 * @param interval
	 * @return
	 */
	public T retryInterval(int interval);
}
