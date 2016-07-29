/**
 * @QiaoJian
 */
package cn.edu.bjtu.cnetwork.handler;

/**
 * @author QiaoJian
 *
 */
public interface IValueHandler {
	
	public void before();
	
	public void handlerValue(Object ...args);
	
	public void finish();
}
