package jobs.toolkit.protocol;

import java.util.LinkedList;
import java.util.Queue;

import com.google.protobuf.Message;

import jobs.toolkit.event.BaseEventHandler;

public abstract class PBMessageEventHandler<T extends Message> extends BaseEventHandler<PBMessageEvent> {
	
	ThreadLocal<Queue<Message>> outputQueue = new ThreadLocal<Queue<Message>>();
	
	@SuppressWarnings("unchecked")
	@Override
	public final void doHandle(PBMessageEvent event) throws Throwable {
		// TODO Auto-generated method stub
		T t = (T) event.getMessage();
		this.process(t);
		Queue<Message> queue = this.outputQueue.get();
		if(queue != null && !queue.isEmpty()){
			while(!queue.isEmpty()){
				Message message = queue.poll();
				short code = MessageEventDispatcher.getCodeByMessageClass(message.getClass());
				event.reset(code, message);
				event.transfer();
			}
		}
	}
	
	public abstract void process(T t) throws Throwable;
		
	protected void addOutput(Message message){
		Queue<Message> queue = this.outputQueue.get();
		if(queue == null){
			queue = new LinkedList<Message>();
			this.outputQueue.set(queue);
		}
		queue.offer(message);
	}
}
