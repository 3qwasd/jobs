package gateway.component;

import java.util.function.Consumer;

public class FunFilter<T> implements Filter<T>{

	private final Consumer<T> filter;
	
	
	
	public FunFilter(Consumer<T> filter) {
		this.filter = filter;
	}

	@Override
	public void filter(T message) {
		this.filter.accept(message);
	}

}
