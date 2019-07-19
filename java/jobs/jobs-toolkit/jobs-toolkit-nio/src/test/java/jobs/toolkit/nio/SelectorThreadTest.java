package jobs.toolkit.nio;

import java.io.IOError;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import jobs.toolkit.nio.NioSelectorThread;

public class SelectorThreadTest {
	
	NioSelectorThread selector;
	
	ServerSocketChannel server;
	
	final List<SocketChannel> channelSet = new ArrayList<>();
	
	@Before
	public void setUp() throws Throwable{
		selector = new NioSelectorThread("Test selector Thread"){
			
		};
		selector.start();
		this.server = ServerSocketChannel.open();
		ServerSocket ss = this.server.socket();
		ss.setReuseAddress(true);
		ss.setReceiveBufferSize(10240);
		ss.bind(new InetSocketAddress(80), 10);
		
		
	}
	
	/**
	 * 测试submitRegsiterTask方法, 同时还会测试resgister, 以及executeNow三个方法
	 * @throws Throwable
	 */
	@Test
	public void testSubmitRegsiterTask() throws Throwable{
		
		Assert.assertEquals(selector.getState(), Thread.State.RUNNABLE);
		/*提交注册任务*/
		FutureTask<SelectionKey> registerTask = selector.submitRegsiterTask(this.server, SelectionKey.OP_ACCEPT, this);		
		/*等待注册任务完成*/
		SelectionKey key = registerTask.get(1000, TimeUnit.SECONDS);
		Assert.assertTrue(key.isValid());
		Assert.assertTrue((key.interestOps() & SelectionKey.OP_ACCEPT) !=0);
		Assert.assertTrue(key.channel() == server);
		
		/*测试并发环境下SelectThread注册的正确性*/
		for(int i = 0; i < 10; i++){
			SocketChannel channel = SocketChannel.open();
			channel.configureBlocking(false);
			channel.connect(new InetSocketAddress(80));
			channelSet.add(channel);
			if(i == 5){
				/*关闭一个channel测试异常处理机制*/
				channel.close();
			}
		}
		
		final List<SelectionKey> keyList = new CopyOnWriteArrayList<>();
		
		final CyclicBarrier barrier = new CyclicBarrier(11);
		
		for(int i=0;i<10;i++){
			final int index = i;
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						barrier.await();
						
						SocketChannel channel = channelSet.get(index);
						FutureTask<SelectionKey> key = selector.submitRegsiterTask(channel, SelectionKey.OP_READ, index);
						if(index != 5)
							keyList.add(key.get());
						else{
							try{
								key.get();
								Assert.fail();
							}catch(ExecutionException  _){
								Assert.assertTrue(_.getCause() instanceof IOError);
							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally{
						try {
							barrier.await();
						} catch (InterruptedException | BrokenBarrierException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}
			}).start();
		}
		barrier.await();
		barrier.await();
		
		Assert.assertTrue(keyList.size() == 9);
		for(SelectionKey sk: keyList){
			System.out.println(sk);
			int index = (int) sk.attachment();
			SocketChannel sc = channelSet.get(index);
			Assert.assertTrue(sc == sk.channel());
			Assert.assertTrue((sk.interestOps() & SelectionKey.OP_READ) != 0);
		}
		
	}
	@After
	public void testClose(){
		selector.close();

		Assert.assertEquals(selector.getState(), Thread.State.TERMINATED);
	}
}
