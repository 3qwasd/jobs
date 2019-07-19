package jobs.toolkit.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.Pipe;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import jobs.toolkit.nio.NioOutputBuffer;
import jobs.toolkit.utils.NioUtils;
import jobs.toolkit.utils.RandomUtils;
import junit.framework.Assert;

public class NioOutputBufferTest {
	
	NioOutputBuffer outputBuffer;
	
	ByteBuffer src;
	
	int testFlagIndex;
	
	int testFlag;
	
	int pieceSize = 512;
	
	int bufferSize = (int) Math.pow(2, 13);
	
	Pipe pipe;
	
	TestChannel testChannel;
	
	@Before
	public void setUp() throws Exception {
		//System.out.println(bufferSize);
		outputBuffer = NioOutputBuffer.newInstance(bufferSize);
		pipe = Pipe.open();
		testChannel = new TestChannel(1000);
		Assert.assertTrue(this.outputBuffer.getPieceSize() == NioUtils.roudup(pieceSize));
		int outputBufferSize = NioUtils.roudup(bufferSize);
		//System.out.println(outputBufferSize);
		Assert.assertTrue(this.outputBuffer.getOutputBufferSize() == outputBufferSize);
		Assert.assertTrue(outputBuffer.size() == 0);
		Assert.assertTrue(outputBuffer.getBuffers().isEmpty());
		Assert.assertTrue(outputBuffer.getOutputs().isEmpty());
		Assert.assertTrue(outputBuffer.getCurrPiece() == null);
		
		pipe = Pipe.open();
	}
	
	@Test
	public void testPut() {
		int srcSize = (int) Math.pow(2, 12) + 8;
		
		src = ByteBuffer.allocate(srcSize);
		testFlagIndex = RandomUtils.uniform(srcSize/4);
		System.out.println(testFlagIndex);
		for(int i = 0; i < srcSize / 4; i++){
			int random = RandomUtils.uniform(-10000000, +10000000);
			if( i == testFlagIndex )
				testFlag = random;
			src.putInt(random);
		}
		Assert.assertTrue(src.position() == srcSize);
		Assert.assertFalse(src.hasRemaining());
		src.flip();
		Assert.assertTrue(src.remaining() == srcSize);
		try {
			this.outputBuffer.put(src);
			Assert.assertTrue(this.outputBuffer.size() == srcSize);
			Assert.assertTrue(outputBuffer.getBuffers().isEmpty());
			Assert.assertTrue(outputBuffer.getOutputs().size() == srcSize / pieceSize);
			Assert.assertTrue(outputBuffer.getCurrPiece().position() == 8 );
//			int intNumPerPiece = pieceSize/4;
//			int pieceNum = testFlagIndex/intNumPerPiece;
//			int pieceIndex = testFlagIndex%intNumPerPiece;
//			ByteBuffer buf = null;
//			for(int i = 0; i < pieceNum;i++){
//				outputBuffer.getOutputs().pollFirst();
//			}
//			buf = outputBuffer.getOutputs().pollFirst();
//			buf.position(pieceIndex * 4);
//			int bufInt = buf.getInt();
//			System.out.println(testFlag + " = " + bufInt);
//			Assert.assertEquals(bufInt, testFlag);
		} catch (IOException e) {
			Assert.fail();
			e.printStackTrace();
		}
		int newSrcSize = (int) Math.pow(2, 12) - 16;
		src = ByteBuffer.allocate(newSrcSize);
		for(int i = 0; i < newSrcSize / 4; i++){
			int random = RandomUtils.uniform(-10000000, +10000000);
			src.putInt(random);
		}
		src.flip();
		try {
			this.outputBuffer.put(src);
			Assert.assertTrue(this.outputBuffer.size() == (srcSize + newSrcSize));
			System.out.println(srcSize + newSrcSize);
			System.out.println(this.outputBuffer.getOutputBufferSize());
			Assert.assertTrue(outputBuffer.getBuffers().isEmpty());
			Assert.assertTrue(outputBuffer.getOutputs().size() == (srcSize + newSrcSize) / pieceSize);
			Assert.assertTrue(outputBuffer.getCurrPiece().position() == pieceSize - 8 );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		try {
//			this.outputBuffer.put((ByteBuffer) ByteBuffer.allocate(20).putInt(100).putInt(100).putInt(100).putInt(100).flip());
//			Assert.fail();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			Assert.assertTrue(e.getMessage() == "Output buffer has no space!");
//		}
	}
	
	@Test
	public void testOutputUseTestChannel() throws IOException{
		int srcSize = (int) Math.pow(2, 12) + 64;
		ByteBuffer intBuffer = ByteBuffer.allocate(srcSize);
		for(int i = 0; i < srcSize / 4; i++){
			int random = RandomUtils.uniform(-10000000, +10000000);
			intBuffer.putInt(random);
		}
		intBuffer.flip();
		this.outputBuffer.put(intBuffer);
		int size = this.outputBuffer.output(testChannel);
		Assert.assertTrue(size == srcSize - 1000);
		Assert.assertTrue(size == this.outputBuffer.size());
		Assert.assertTrue(this.outputBuffer.getBuffers().size() == 1000/this.pieceSize);
		Assert.assertTrue(this.outputBuffer.getOutputs().size() == srcSize/this.pieceSize);
		Assert.assertTrue(this.outputBuffer.getOutputs().peekFirst().remaining() == (512*2-1000));
	}
	
	@Test
	public void testOutput() {
		List<Integer> ints = new ArrayList<>();
		int srcSize = (int) Math.pow(2, 12) + 8;
		
		src = ByteBuffer.allocate(srcSize);
		testFlagIndex = RandomUtils.uniform(srcSize/4);
		System.out.println(testFlagIndex);
		for(int i = 0; i < srcSize / 4; i++){
			int random = RandomUtils.uniform(-10000000, +10000000);
			if( i == testFlagIndex )
				testFlag = random;
			src.putInt(random);
			ints.add(random);
		}
		Assert.assertTrue(src.position() == srcSize);
		Assert.assertFalse(src.hasRemaining());
		src.flip();
		Assert.assertTrue(src.remaining() == srcSize);
		try {
			this.outputBuffer.put(src);
			Assert.assertTrue(this.outputBuffer.size() == srcSize);
			Assert.assertTrue(outputBuffer.getBuffers().isEmpty());
			Assert.assertTrue(outputBuffer.getOutputs().size() == srcSize / pieceSize);
			Assert.assertTrue(outputBuffer.getCurrPiece().position() == 8 );
//			int intNumPerPiece = pieceSize/4;
//			int pieceNum = testFlagIndex/intNumPerPiece;
//			int pieceIndex = testFlagIndex%intNumPerPiece;
//			ByteBuffer buf = null;
//			for(int i = 0; i < pieceNum;i++){
//				outputBuffer.getOutputs().pollFirst();
//			}
//			buf = outputBuffer.getOutputs().pollFirst();
//			buf.position(pieceIndex * 4);
//			int bufInt = buf.getInt();
//			System.out.println(testFlag + " = " + bufInt);
//			Assert.assertEquals(bufInt, testFlag);
		} catch (IOException e) {
			Assert.fail();
			e.printStackTrace();
		}
		int newSrcSize = (int) Math.pow(2, 12) - 16;
		src = ByteBuffer.allocate(newSrcSize);
		for(int i = 0; i < newSrcSize / 4; i++){
			int random = RandomUtils.uniform(-10000000, +10000000);
			src.putInt(random);
			ints.add(random);
		}
		src.flip();
		try {
			this.outputBuffer.put(src);
			Assert.assertTrue(this.outputBuffer.size() == (srcSize + newSrcSize));
			System.out.println(srcSize + newSrcSize);
			System.out.println(this.outputBuffer.getOutputBufferSize());
			Assert.assertTrue(outputBuffer.getBuffers().isEmpty());
			Assert.assertTrue(outputBuffer.getOutputs().size() == (srcSize + newSrcSize) / pieceSize);
			Assert.assertTrue(outputBuffer.getCurrPiece().position() == pieceSize - 8 );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			int size = this.outputBuffer.output(pipe.sink());
			Assert.assertTrue(size == 0);
			Assert.assertTrue(this.outputBuffer.size() == 0);
			Assert.assertTrue(this.outputBuffer.size() == size);
			Assert.assertTrue(this.outputBuffer.getBuffers().size() == 16);
			Assert.assertTrue(this.outputBuffer.getOutputs().isEmpty());
			Assert.assertTrue(this.outputBuffer.getCurrPiece() == null);
			src = ByteBuffer.allocate(srcSize + newSrcSize);
			pipe.source().read(src);
			Assert.assertFalse(src.hasRemaining());
			src.flip();
			Assert.assertTrue(src.remaining() == (newSrcSize + srcSize));
			for(int i = 0 ; i < ints.size(); i++){
				Assert.assertTrue(ints.get(i).equals(src.getInt()));
			}
			Assert.assertFalse(src.hasRemaining());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
