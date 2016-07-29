package jobs.toolkit.utils;

import java.lang.reflect.Proxy;

import jobs.toolkit.reflect.SimpleProxyHandler;

/**
 * 反射工具类
 * @author jobs
 *
 */
public class ReflectUtils {
	
	/**
	 * 
	 * @param className 类名称 className表示的类必须是clz表示类型的子类
	 * @param clz 返回值的类型
	 * @return
	 * @throws ReflectiveOperationException 
	 */
	@SuppressWarnings("unchecked")
	public static <T> T newInstanceByClassName(String className) throws ReflectiveOperationException{
		try {
			Class<? extends T> c = (Class<? extends T>) Class.forName(className);
			T t = c.newInstance();
			return t;
		} catch (Exception e) {
			throw new ReflectiveOperationException("Create new instance of class name [" + className + "] error.", e);
		}
	}
	/**
	 * 创建一个简单的动态代理对象
	 * @param target <T> 表示的接口的一个真实实现类
	 * @param clz <T> 表示的接口的Class对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T buildSimpleProxy(T target, Class<T> clz){
		T proxy = (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), 
				new Class[]{clz}, new SimpleProxyHandler<T>(target));
		return proxy;
	}
}
