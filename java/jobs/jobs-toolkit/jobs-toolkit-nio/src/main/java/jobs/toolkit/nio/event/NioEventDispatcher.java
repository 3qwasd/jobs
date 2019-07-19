package jobs.toolkit.nio.event;

import jobs.toolkit.event.BaseDispatcher;
import jobs.toolkit.event.EventType;

public final class NioEventDispatcher extends BaseDispatcher<NioEvent> {
		
	private static final NioEventDispatcher instance = new NioEventDispatcher();
	
	private NioEventDispatcher() {
		super("NioEventDispatcher");
	}
	
	public static final NioEventDispatcher getInstance(){
		return instance;
	}
	
	@Override
	protected void initialize() throws Exception {
		super.initialize();
		this.register(EventType.of(SelectedEvent.NIO_SELECTED_ACCEPTE_EVENT), new AcceptEventHandler());
		this.register(EventType.of(SelectedEvent.NIO_SELECTED_CONNECT_EVENT), new ConnectEventHandler());
		this.register(EventType.of(SelectedEvent.NIO_SELECTED_READ_EVENT), new ReadEventHandler());
		this.register(EventType.of(SelectedEvent.NIO_SELECTED_WRITE_EVENT), new WriteEventHandler());
		this.register(EventType.of(RunnableNioEvent.TYPE_NAME), new ExecutorNioEventHandler());
	}
	
	
}
