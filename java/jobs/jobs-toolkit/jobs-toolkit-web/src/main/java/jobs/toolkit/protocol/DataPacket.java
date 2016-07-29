package jobs.toolkit.protocol;

public interface DataPacket<T> {
	
	public T marshal();
	
	public void unMarshal(T t);
}
