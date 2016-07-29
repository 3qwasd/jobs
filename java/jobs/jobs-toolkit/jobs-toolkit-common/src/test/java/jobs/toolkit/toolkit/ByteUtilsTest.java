package jobs.toolkit.toolkit;


import java.nio.ByteBuffer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import jobs.toolkit.utils.ByteUtils;

public class ByteUtilsTest {
	byte[] bytes = new byte[Integer.BYTES + Short.BYTES + Long.BYTES];
	int offset = 0;
	int i = 1232112313;
	long l = 12312312311233L;
	short s = 1213;
	@Before
	public void setUp() throws Exception {
		
	}
	@Test
	public void test(){
		offset = ByteUtils.encodeIntBigEndian(i, bytes, offset);
		offset = ByteUtils.encodeLongBigEndian(l, bytes, offset);
		offset = ByteUtils.encodeShortBigEndian(s, bytes, offset);
		
		ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES + Short.BYTES + Long.BYTES);
		buffer.putInt(i).putLong(l).putShort(s);
		byte[] bufferArray = buffer.array();
		for(int i = 0 ; i<bytes.length;i++){
			Assert.assertTrue(bytes[i] == bufferArray[i]);
		}
		int i_1 = ByteUtils.decodeIntBigEndian(bufferArray, 0);
		Assert.assertTrue(i_1 == i);
		long l_1 = ByteUtils.decodeLongBigEndian(bufferArray, Integer.BYTES);
		Assert.assertTrue(l_1 == l);
		short s_1 = ByteUtils.decodeShortBigEndian(bufferArray, Long.BYTES + Integer.BYTES);
		Assert.assertTrue(s_1 == s);
	}
	
	@After
	public void tearDown() throws Exception {
	}
}
