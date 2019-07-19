package jobs.toolkit.protocol;

import java.nio.ByteBuffer;

public  interface BinaryPacket extends DataPacket<byte[]> {

	public void unMarshal(byte[] bytes, int offset, int len);
	
	public ByteBuffer marshalToByteBuffer();
}
