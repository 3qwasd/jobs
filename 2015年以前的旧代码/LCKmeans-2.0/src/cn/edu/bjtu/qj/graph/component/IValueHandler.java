/**
 * @QiaoJian
 */
package cn.edu.bjtu.qj.graph.component;

/**
 * @author QiaoJian
 *
 */
public interface IValueHandler {
	
	public void befor();
	
	public void execute(Number value,Object ... args);
	
	public void finish();
	
}
