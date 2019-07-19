package jobs.toolkit.protocol;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import com.google.protobuf.Message;
import com.google.protobuf.Message.Builder;

/**
 * 使用protocolbuffer作为载体
 * @author jobs
 *
 */
public abstract class PBMessageEvent extends MessageEvent implements BinaryPacket{
		
	private volatile Message message;
	
	public PBMessageEvent(short code, Message message) {
		super(code);
		this.message = message;
	}
	public PBMessageEvent() {
		super();
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public void reset(short code, Message message){
		this.setCode(code);
		this.setMessage(message);
	}
	@Override
	public byte[] marshal() {
		int length = Integer.BYTES + Short.BYTES + this.message.getSerializedSize();
		byte[] bytes = new byte[length];
		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		buffer.putInt(length).putShort(this.getCode()).put(this.message.toByteArray());
		return bytes;
	}
	@Override
	public void unMarshal(byte[] bytes) {
		this.unMarshal(bytes, 0, bytes.length);
	}
	@Override
	public void unMarshal(byte[] bytes, int offset, int len) {
		// TODO Auto-generated method stub
		ByteBuffer buffer = ByteBuffer.wrap(bytes, offset, len);
		int length = buffer.getInt();
		this.setCode(buffer.getShort());
		byte[] msgBytes = new byte[length - Integer.BYTES - Short.BYTES];
		buffer.get(msgBytes);
		this.setMessage(buildPbMessage(this.getCode(), msgBytes));
	}
	private static Message buildPbMessage(short code, byte[] data){
		try {
			Class<?> pbMsgClz = MessageEventDispatcher.getMessageClassByCode(code);
			Method method = pbMsgClz.getMethod("newBuilder");
			Message.Builder builder = (Builder) method.invoke(null);
			return builder.mergeFrom(data).build();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public ByteBuffer marshalToByteBuffer() {
		// TODO Auto-generated method stub
		return ByteBuffer.wrap(this.marshal());
	}
}
