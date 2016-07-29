package jobs.toolkit.protocol;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jobs.toolkit.config.Configuration;
import jobs.toolkit.event.BaseDispatcher;
import jobs.toolkit.event.EventHandler;
import jobs.toolkit.http.CFG;

public class MessageEventDispatcher extends BaseDispatcher<MessageEvent> {
	
	private final static MessageEventDispatcher instance = new MessageEventDispatcher(MessageEventDispatcher.class.getName());
	
	private Map<Short, Class<?>> codeMessageMap = new ConcurrentHashMap<Short, Class<?>>();
	
	private Map<Class<?>, Short> messageCodeMap = new ConcurrentHashMap<Class<?>, Short>();
	
	private MessageEventDispatcher(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	public static MessageEventDispatcher getInstance(){
		return instance;
	}
	public static void dispatchEvent(MessageEvent event){
		instance.dispatch(event);
	}
	@SuppressWarnings("unchecked")
	@Override
	protected void initialize() throws Exception {
		Configuration messageConfig = this.getConfiguration();
		String packageName = messageConfig.getString(CFG.NAME_MESSAGE_PACKAGE);
		List<Configuration> subCfgList =  messageConfig.getSubConfigs(CFG.NAME_MESSAGES);
		for(Configuration c : subCfgList){
			short code = (short) c.getInt(CFG.NAME_CODE);
			Class<?> clz = Class.forName(packageName.concat(".").concat(c.getString(CFG.NAME_CLASS)));
			this.codeMessageMap.putIfAbsent(code, clz);
			this.messageCodeMap.putIfAbsent(clz, code);
			String handlerName = c.getString(CFG.NAME_HANDLER);
			if(handlerName == null || handlerName.trim().isEmpty()) continue;
			this.register(MessageEvent.getEventTypeByCode(code), (EventHandler<? extends MessageEvent>) Class.forName(handlerName).newInstance());
		}
	}
	public String  messageJsonRI(){
		if(this.codeMessageMap == null || this.codeMessageMap.isEmpty()) return "[]";
		StringBuffer msgRI = new StringBuffer("[");
		for(Short code : this.codeMessageMap.keySet()){
			msgRI.append("{").append("\"").append("code").append("\":")
			.append(code).append(",\"").append("name").append("\":")
			.append("\"").append(this.codeMessageMap.get(code).getSimpleName()).append("\"")
			.append("}").append(",");
		}
		return msgRI.deleteCharAt(msgRI.length() - 1).append("]").toString();
	}
	public static Class<?> getMessageClassByCode(short code){
		return instance.codeMessageMap.getOrDefault(code, null);
	}
	public static short getCodeByMessageClass(Class<?> clz){
		return instance.messageCodeMap.getOrDefault(clz, (short) -1);
	}
}
