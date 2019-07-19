package jobs.toolkit.nio;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import jobs.toolkit.nio.NioDataTransfer;
import jobs.toolkit.utils.RandomUtils;

public class NioDataTransferTest {

	NioDataTransfer transfer;

	int cap = 4096;

	TestNio nio;

	@Before
	public void setUp() throws Exception {
		this.nio = new TestNio(cap);
		transfer = new NioDataTransfer(this.nio);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRead()  {
		int total = 0;
		while(true){
			ByteBuffer src = ByteBuffer.allocate(2048);
			src.putInt(2044);
			for(int i = 0; i < 2044/4; i++){
				src.putInt(RandomUtils.uniform(10000000));
			}
			src.flip();
			try {
				this.nio.getWriteableChannel().write(new ByteBuffer[]{src});
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			int rc = 0;
			try {
				rc = transfer.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Assert.assertTrue(e.getMessage().equals("Nio input buffer has no space to recive more bytes!"));
				break;
			}
			total += rc;
			Assert.assertTrue(rc == 2048);
		}
		System.out.println(total);
	}
	@Test
	public void testWrite(){
		this.transfer.add(new TestNioMessageEvent().init(1000));
		this.transfer.add(new TestNioMessageEvent().init(1000));


		Assert.assertTrue(this.transfer.getSendQueue().size() == 2);
		try {
			int remaing = this.transfer.write();
			Assert.assertTrue(remaing <= (1000 + 1+ 1)*4*2 - this.cap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void test(){
		for(int i=0;i<100;i++){
			try{
				testConcurrent();
			}catch(Throwable e){
				e.printStackTrace();
			}
			nio.sendList.clear();
			nio.reciveList.clear();
		}
	}
	@Test
	public void testConcurrent() throws Exception{
		final CyclicBarrier cyclicBarrier = new CyclicBarrier(13);
		final AtomicBoolean isStop = new AtomicBoolean(false);
		final AtomicInteger readCount = new AtomicInteger(0);
		final AtomicInteger writeCount = new AtomicInteger(0);

		for(int i=0;i<4;i++){
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						cyclicBarrier.await();
						for(int i=0;i<100;i++){
							TestNioMessageEvent event = new TestNioMessageEvent();
							event.init(100);
							synchronized (NioDataTransferTest.this) {
								nio.sendList.add(event);
								transfer.add(event);
							}
							Thread.sleep(100);
						}
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally{
						try{
							cyclicBarrier.await();
						}catch(Exception e){

						}
					}
				}
			}).start();
		}
		for(int i=0;i<4;i++){
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						cyclicBarrier.await();
						while(!isStop.get()){
							try{
								int rc = transfer.read();
								readCount.addAndGet(rc);
							}catch(Throwable e){
								e.printStackTrace();
							}
							
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally{
						try{
							cyclicBarrier.await();
						}catch(Exception e){

						}
					}
				}
			}).start();
		}
		for(int i=0;i<4;i++){
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						cyclicBarrier.await();
						Thread.sleep(100);
						while(!isStop.get()){
							int remainning = transfer.write();
							//System.out.println("write count:" + remainning);
							writeCount.addAndGet(remainning);
							Thread.sleep(200);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally{
						try{
							cyclicBarrier.await();
						}catch(Exception e){

						}
					}
				}
			}).start();
		}
		cyclicBarrier.await();
		
		Thread.sleep(1000*15);
		isStop.set(true);
		
		cyclicBarrier.await();
		System.out.println("sendList len:" + nio.sendList.size());
		System.out.println("reciveList len:" + nio.reciveList.size());
		System.out.println("read count:" + readCount);
		//System.out.println(writeCount);
		//Assert.assertTrue(writeCount.get() == ((100+2)*4)*400);
		for(int i=0;i<nio.sendList.size();i++){
			if(nio.sendList.get(i) == nio.reciveList.get(i)){
				Assert.fail();
			}
			if(!nio.sendList.get(i).equals(nio.reciveList.get(i))){
				Assert.fail("i="+i);
			}
		}
	}
}
