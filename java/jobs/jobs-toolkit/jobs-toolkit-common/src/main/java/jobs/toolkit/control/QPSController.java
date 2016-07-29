package jobs.toolkit.control;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
/**
 * 访问频率控制器
 * @author jobs
 *
 */
public abstract class QPSController implements Runnable{
		
	private Sync sync = new Sync();
	
	private volatile boolean isStop = true;
	
	private volatile ScheduledFuture<?> future;
	
	public void start(){
		if(!this.isStop) return;
		this.isStop = false;
		future = QPSControllerAssistant.getInstance().schedule(this, delay(), TimeUnit.MILLISECONDS);
	}
	
	@Override
	public void run() {
		this.release();
		if(!this.isStop){
			future = QPSControllerAssistant.getInstance().schedule(this, delay(), TimeUnit.MILLISECONDS);
		}
	}
	
	public abstract long delay();
	
	public void stop(){
		this.isStop = true;
		future.cancel(true);
	}
	
	public void acquire() throws InterruptedException {
		this.sync.acquireSharedInterruptibly(1);
	}
	
	protected void release() {
		this.sync.releaseShared(1);
	}
	
	private final class Sync extends AbstractQueuedSynchronizer{

		private static final long serialVersionUID = 5089094685102455756L;

		@Override
		protected int tryAcquireShared(int acquires) {
			while(true){
				int available = this.getState();
				
				int remaining = available - acquires;
				if(remaining < 0 || compareAndSetState(available, remaining))
					return remaining;
			}
		}

		@Override
		protected boolean tryReleaseShared(int releases) {
			while(true){
				int p = getState();
				if(p == 1 || compareAndSetState(0, 1))
					return true;
			}
		}

		
		
	}
}
