package jobs.toolkit.event;

import jobs.toolkit.logging.Log;
import jobs.toolkit.logging.LogFactory;


public abstract class BaseEventHandler<T extends Event> implements EventHandler<T> {
	
	final static Log LOG = LogFactory.getLog(BaseEventHandler.class);
	
	@Override
	public void doExcepion(T event, Throwable e) {
		// TODO Auto-generated method stub
		LOG.error(String.format("This handle Handle event %1$s occur exception!", event), e);
	}
}
