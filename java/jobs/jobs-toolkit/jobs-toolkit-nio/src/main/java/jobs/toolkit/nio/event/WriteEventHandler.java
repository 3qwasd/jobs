package jobs.toolkit.nio.event;

import jobs.toolkit.event.BaseEventHandler;
import jobs.toolkit.nio.Nio;

public class WriteEventHandler extends BaseEventHandler<SelectedEvent> {

	@Override
	public void doHandle(SelectedEvent event) throws Throwable {
		// TODO Auto-generated method stub
		Nio nio = null;
		try{
			nio = (Nio) event.getSelectionKey().attachment();
			nio.doWrite();
		}catch(Throwable _e){
			if(nio != null)
				nio.close(_e);
			else
				throw new IllegalStateException("Nio is null!", _e);
		}
	}

}
