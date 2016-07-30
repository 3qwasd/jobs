package jobs.toolkit.nio.event;

import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import jobs.toolkit.event.EventHandler;
import jobs.toolkit.logging.Log;
import jobs.toolkit.logging.LogFactory;

public class ExecutorNioEventHandler extends ScheduledThreadPoolExecutor implements EventHandler<RunnableNioEvent> {

	protected final Log LOG = LogFactory.getLog(this.getClass());

	public ExecutorNioEventHandler() {
		super(0);
	}

	@Override
	public void doHandle(RunnableNioEvent event) {
		this.schedule(event, event.getDelay(), TimeUnit.MILLISECONDS);
	}

	@Override
	public void doExcepion(RunnableNioEvent event, Throwable e) {
		LOG.info(String.format("This handler handle runnable nio event %1$s occur exception!", event), e);
	}


	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		
		NioEventRunnableScheduledFuture<?> task =  (NioEventRunnableScheduledFuture<?>) r;
		if(t != null) this.doExcepion(task.getNioEvent(), t);
		try{
			task.get();
		} catch (ExecutionException _e) {
			this.doExcepion(task.getNioEvent(), _e.getCause());	
		} catch (InterruptedException _e) {}

	}

	@Override
	protected <V> RunnableScheduledFuture<V> decorateTask(final Runnable runnable, final RunnableScheduledFuture<V> task) {
		return new NioEventRunnableScheduledFuture<V>((RunnableNioEvent)runnable, task);
	}

	private final class NioEventRunnableScheduledFuture<V> implements RunnableScheduledFuture<V>{

		private RunnableNioEvent nioEvent;

		private RunnableScheduledFuture<V> task;


		public NioEventRunnableScheduledFuture(RunnableNioEvent nioEvent, RunnableScheduledFuture<V> task) {
			super();
			this.nioEvent = nioEvent;
			this.task = task;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			this.task.run();
		}

		@Override
		public boolean cancel(boolean mayInterruptIfRunning) {
			// TODO Auto-generated method stub
			return this.task.cancel(mayInterruptIfRunning);
		}

		@Override
		public boolean isCancelled() {
			// TODO Auto-generated method stub
			return this.task.isCancelled();
		}

		@Override
		public boolean isDone() {
			// TODO Auto-generated method stub
			return this.task.isDone();
		}

		@Override
		public V get() throws InterruptedException, ExecutionException {
			// TODO Auto-generated method stub
			return this.task.get();
		}

		@Override
		public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
			// TODO Auto-generated method stub
			return this.task.get(timeout, unit);
		}

		@Override
		public long getDelay(TimeUnit unit) {
			// TODO Auto-generated method stub
			return this.task.getDelay(unit);
		}

		@Override
		public int compareTo(Delayed o) {
			// TODO Auto-generated method stub
			return this.task.compareTo(o);
		}

		@Override
		public boolean isPeriodic() {
			// TODO Auto-generated method stub
			return this.task.isPeriodic();
		}

		public RunnableNioEvent getNioEvent() {
			return nioEvent;
		}
	}
}
