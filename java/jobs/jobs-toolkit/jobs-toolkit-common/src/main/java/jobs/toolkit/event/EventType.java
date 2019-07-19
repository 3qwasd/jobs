package jobs.toolkit.event;

import java.util.HashMap;
import java.util.Map;

/**
 * 事件类型
 * @author jobs
 *
 */
public final class EventType {
	/*EventType名称需要唯一*/
	private final String name;
	
	private static final Map<String, EventType> eventTypes = new HashMap<String, EventType>();
	
	private EventType(String name){
		this.name = name;
	}
	
	public static EventType of(String name){
		synchronized (eventTypes) {
			EventType type = eventTypes.get(name);
			if(type == null){
				type = new EventType(name);
				eventTypes.put(name, type);
			}
			return type;
		}
	}
	public String getName(){
		return this.name;
	}

	@Override
	public String toString() {
		return this.name;
	}
	
}
