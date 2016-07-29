package jobs.toolkit.handler;

import java.io.File;

@FunctionalInterface
public interface FileHandler {
	
	public void handle(File file);
}

