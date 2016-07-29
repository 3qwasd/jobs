package jobs.toolkit.nio.event;

import java.nio.channels.SelectionKey;

import jobs.toolkit.event.EventType;

public class SelectedEvent extends NioEvent {
	
	public static final String NIO_SELECTED_READ_EVENT = "nio.selected.read.event";
	public static final String NIO_SELECTED_WRITE_EVENT = "nio.selected.write.event";
	public static final String NIO_SELECTED_ACCEPTE_EVENT = "nio.selected.accept.event";
	public static final String NIO_SELECTED_CONNECT_EVENT = "nio.selected.connect.event";
	
	private static final ThreadLocal<SelectedEvent> defaultEvent = new ThreadLocal<SelectedEvent>();
	
	private volatile SelectionKey selectionKey;
	
	private SelectedEvent() {
		super();
	}
	
	private void setSelectionKey(SelectionKey key){
		this.selectionKey = key;
	}
	protected SelectionKey getSelectionKey(){
		return this.selectionKey;
	}
	public static SelectedEvent newEvent(String typeName, SelectionKey selectionKey){
		SelectedEvent event = defaultEvent.get();
		if(event == null){
			event = new SelectedEvent();
			defaultEvent.set(event);
		}
		event.setSelectionKey(selectionKey);
		event.setEventType(EventType.of(typeName));
		return event;
	}
	
	public static void occur(SelectionKey selectionKey){
		if(selectionKey.isReadable()){
			NioEventDispatcher.getInstance().dispatch(SelectedEvent.newEvent(NIO_SELECTED_READ_EVENT, selectionKey));
		}
		if(selectionKey.isWritable()){
			NioEventDispatcher.getInstance().dispatch(SelectedEvent.newEvent(NIO_SELECTED_WRITE_EVENT, selectionKey));
		}
		if(selectionKey.isAcceptable()){
			NioEventDispatcher.getInstance().dispatch(SelectedEvent.newEvent(NIO_SELECTED_ACCEPTE_EVENT, selectionKey));
		}
		if(selectionKey.isConnectable()){
			NioEventDispatcher.getInstance().dispatch(SelectedEvent.newEvent(NIO_SELECTED_CONNECT_EVENT, selectionKey));
		}
	}
}
