package jobs.toolkit.nio;

import java.nio.ByteBuffer;

import jobs.toolkit.nio.NioCoder;
import jobs.toolkit.nio.event.NioMessageEvent;

public class TestNioCoder extends NioCoder {

	@Override
	public ByteBuffer encode(NioMessageEvent msgEvent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NioMessageEvent decode(ByteBuffer byteBuffer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkDecode(ByteBuffer byteBuffer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkEncode(NioMessageEvent msgEvent) {
		// TODO Auto-generated method stub
		return false;
	}

}
