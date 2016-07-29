package jobs.toolkit.nio;

import java.util.concurrent.atomic.AtomicInteger;
import jobs.toolkit.service.BaseService;

/**
 * 网络层引擎, 不提供对外接口
 * @author jobs
 *
 */
final class NioEngine extends BaseService{
	
	/**
	 * 延迟初始化占位类
	 * @author jobs
	 *
	 */
	private static class EngineHolder {
		private static NioEngine engine = new NioEngine();
	}
		
	/*默认的引擎的线程数量, 默认4个线程*/
	private static final int DEFAULT_THREAD_NUM = 4;
	/*引擎中的最大线程数量*/
	private static final int MAX_THREAD_NUM = 32;
	/*引擎中的工作线程*/
	private volatile NioSelectorThread[] selectors;	
	/*线程数*/
	private volatile int threadNum;
	/*表示注册到引擎上的网络连接数, 也就是由SelectorThread服务的网络连接总数*/
	private final AtomicInteger registerCount = new AtomicInteger(0);
	
	
	private NioEngine() {
		super("NetworkEngine");
	}
	static NioEngine getInstance(){
		return EngineHolder.engine;
	}
	/**
	 * 验证引擎是否处于start状态
	 */
	static void verify(){
		if(!EngineHolder.engine.isStarted()){
			throw new IllegalStateException(String.format("%1$s is closed.", EngineHolder.engine.getName()));
		}
	}
	/**
	 * 得到一个 Selector 线程。
	 * @return
	 */
	static NioSelectorThread selector() {
		final int count = EngineHolder.engine.registerCount.incrementAndGet();
		final int index = count & (EngineHolder.engine.selectors.length - 1);
		return EngineHolder.engine.selectors[index];
	}
	@Override
	protected void initialize() throws Exception {
		super.initialize();
		int configThreadNum = this.getConfiguration().getInt(NioConfig.NIO_ENGINE_THREAD_NUM);
		if(configThreadNum == Integer.MIN_VALUE)
			configThreadNum = DEFAULT_THREAD_NUM;//configThreadNum的值应该从配置中获取, 先设置默认值, 后面实现配置
		/*引擎的SelectorThread数量需要时2的幂, 并且最大不超过MAX_THREAD_NUM*/
		this.threadNum = 1;
		while (this.threadNum < configThreadNum)
			this.threadNum <<= 1;
		if (this.threadNum > MAX_THREAD_NUM)
			this.threadNum = MAX_THREAD_NUM; 
		
		this.selectors = new NioSelectorThread[threadNum];
		for(int i = 0; i < this.threadNum; i++){
			this.selectors[i] = new NioSelectorThread("Network engine's selector thread" + i);
		}
	}
	
	@Override
	protected void startup() throws Exception {
		// TODO Auto-generated method stub
		super.startup();
		for(int i = 0; i < this.threadNum; i++){
			this.selectors[i].start();
		}	
		LOG.info("Network's engine started!");
	}

	@Override
	protected void shutdown() throws Exception {
		// TODO Auto-generated method stub
		super.shutdown();
		for(int i = 0; i < this.threadNum; i++){
			this.selectors[i].close();
		}
		//this.selectors = null;
		LOG.info("Network't engine stoped!");
	}
	
	/**
	 * 判断当前引擎内部线程的状态
	 * 该方法用于单元测试
	 * @param state
	 * @return
	 */
	boolean threadsIsInState(Thread.State state){
		boolean result = true;
		for(NioSelectorThread st : this.selectors){
			result = (result && (st.getState() == state));
		}
		return result;
	}
}
