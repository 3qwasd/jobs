package jobs.toolkit.support.zookeeper;

import jobs.toolkit.support.zookeeper.fun.CommonFun;

/**
 * 构造zookeeper异步回调类的接口
 * @author jobs
 *
 * @param <T>
 * @param <Fun>
 */
public interface ZKCallback<T, Fun> extends ZKHelper<T>{
	
	/**
	 * 操作成功时的回调函数
	 * @param fun
	 * @return
	 */
	public T okFun(Fun fun);
	/**
	 * 目标节点存在的回调函数
	 * @param fun
	 * @return
	 */
	public T nodeExistFun(Fun fun);
	/**
	 * 目标节点不存在的回调函数
	 * @param fun
	 * @return
	 */
	public T noNodeFun(Fun fun);
	/**
	 * 发生连接丢失状态时的回调函数
	 * @param fun
	 * @return
	 */
	public T connLossFun(Fun fun);
	/**
	 * 通用回调方法, 没有设置回调函数则默认掉用CommonFun, 如果CommonFun没有设置, 则不作处理
	 * @param fun
	 * @return
	 */
	public T commonFun(CommonFun fun);
	/**
	 * 设置验证失败的回调函数
	 * @param fun
	 * @return
	 */
	public T authFailedFun(Fun fun);
	/**
	 * 设置session过期的回调函数
	 * @param fun
	 * @return
	 */
	public T sessionExpiredFun(Fun fun);
	/**
	 * 设置version号不匹配的回调函数
	 * @param fun
	 * @return
	 */
	public T badVersionFun(Fun fun);
}
