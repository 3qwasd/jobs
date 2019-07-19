package jobs.toolkit.handler;

import java.io.File;

@FunctionalInterface
public interface FileProcesser<T> {
	
	public T process(File file);
}
