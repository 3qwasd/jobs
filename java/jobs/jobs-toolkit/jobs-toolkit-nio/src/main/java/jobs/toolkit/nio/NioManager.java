package jobs.toolkit.nio;

import java.nio.channels.SocketChannel;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicLong;

import jobs.toolkit.service.BaseService;
import jobs.toolkit.nio.NioConfig.NioConnectorConfig;
import jobs.toolkit.nio.NioConfig.NioManagerConfig;

/**
 * 网络IO管理, 负责管理IO连接, 负责Nio的创建, 关闭等
 * @author jobs
 *
 */
public abstract class NioManager extends BaseService{
		
	/*当前的连接总数*/
	private final AtomicLong createNioCount = new AtomicLong(0);
	/*由该Manager负责管理的Nio集合*/
	private final CopyOnWriteArraySet<Nio> nioSet = new CopyOnWriteArraySet<Nio>();
	/*Nio 连接器*/
	private volatile NioConnector connector;
	
	private volatile NioCoder coder;
	
	public NioManager(String name) {
		super(name);
	}

	@Override
	protected void initialize() throws Exception {
		super.initialize();
		NioConfig nc = (NioConfig) this.getConfiguration();
		NioManagerConfig nmc = nc.getNioManagerConfig(this.getName());
		NioConnectorConfig ncc = nmc.getNioConnectorConfig();
		this.connector = this.createNioConnector(ncc.getNioCreatorName(), ncc.getNioCreatorClassName());
		connector.init(ncc);
		this.coder = NioCoder.parseConfig(nmc.getNioCoderConfig());
	}

	@Override
	protected void startup() throws Exception {
		super.startup();
		connector.start();
	}

	@Override
	protected void shutdown() throws Exception {
		super.shutdown();
		this.connector.stop();
		for(Nio nio : nioSet){
			this.closeNio(nio, null);
		}
	}
	/**
	 * 创建一个新的Nio, 该方法由NioCreator回调
	 * @param selector
	 * @param channel
	 * @param op
	 * @return
	 * @throws Throwable
	 */
	protected Nio createNewNio(NioSelectorThread selector, SocketChannel channel, int op) throws Throwable{
		Nio nio = new Nio(this);
		this.nioSet.add(nio);
		nio.init(selector, channel, op);
		createNioCount.incrementAndGet();
		return nio;
	}
	/**
	 * 关闭一个已经存在的Nio
	 * @param nio
	 * @param e
	 */
	protected void closeNio(Nio nio, Throwable e){
		
		if(this.nioSet.remove(nio)){
			if(e != null)
				LOG.info(String.format("Nio manager [name=%1$s] close Nio [%2$s] because of an exception.", this.getName(), nio.toString()), e);
			try {
				nio.closeChannel();
				this.connector.onClose(nio, e);
			} catch (Throwable _e) {
				LOG.error(String.format("Nio manager [name=%1$s] close Nio [%2$s] occure error.", this.getName(), nio.toString()), _e);
			}
		}else{
			LOG.error(String.format("Nio manager [name=%1$s] not hava Nio [%2$s], can't remove", this.getName(), nio.toString()), null);
		}
	}
	/**
	 * 返回现有的nioSet的size
	 * @return
	 */
	public int nioSetSize(){
		return this.nioSet.size();
	}
	/**
	 * 返回创建的nio的数量
	 * @return
	 */
	public long createNewNioCount(){
		return this.createNioCount.get();
	}
	/**
	 * 通过class和name来创建NioManager
	 * @param name
	 * @param className
	 * @return
	 */
	private NioConnector createNioConnector(String name, String className){
		
		try{
			return (NioConnector) Class.forName(className).getConstructor(NioManager.class, String.class).newInstance(this, name);
		}catch(Exception e){
			LOG.error(String.format("Nio manager %3$s create nio creator [name=%1$s] and [class=%2$s] error.", name, className, this.getName()), e);
			throw new IllegalArgumentException(e);
		}
	}
	final NioCoder getNioCoder(){
		return this.coder;
	}
	final NioConnector getNioConnector(){
		return this.connector;
	}
	//以下方法用于单元测试
	protected NioServer getNioServer(){
		return (NioServer) this.connector;
	}
	protected NioClient getNioClient(){
		return (NioClient) this.connector;
	}
	protected CopyOnWriteArraySet<Nio> getNioSet(){
		return this.nioSet;
	}
	protected NioManager initTestComponent(){
		this.coder = initTestCoder();
		this.connector = initTestConnector();
		return this;
	}
	protected NioCoder initTestCoder() {
		return null;
	}
	protected NioConnector initTestConnector(){
		return null;
	}
}
