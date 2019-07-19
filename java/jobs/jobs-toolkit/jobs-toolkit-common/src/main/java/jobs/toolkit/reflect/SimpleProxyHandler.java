package jobs.toolkit.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class SimpleProxyHandler<T> implements InvocationHandler {
	
	private final T target;
	
	public SimpleProxyHandler(T target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return method.invoke(target, args);
	}

}
