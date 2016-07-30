package jobs.toolkit.nio;

import java.nio.ByteBuffer;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import jobs.toolkit.nio.NioConfig.NioCoderAdapterConfig;
import jobs.toolkit.nio.NioConfig.NioCoderConfig;
import jobs.toolkit.nio.event.NioMessageEvent;

/**
 * 网络层编码器
 * @author jobs
 *
 */
public abstract class NioCoder {
	
	/*使用双端队列模拟管道, 注意java双端队列的用法*/
	private final Deque<NioCoderAdapter> pipe = new LinkedList<NioCoderAdapter>();
	
	public NioCoder() {
		
	}
	/**
	 * 解析配置构造网络编码器
	 * @param config
	 * @throws Exception
	 */
	public static NioCoder parseConfig(NioCoderConfig config) throws Exception{
		NioCoder nioCoder = (NioCoder) Class.forName(config.getNioCoderClassName()).newInstance();
		nioCoder.init(config);
		return nioCoder;
	}
	/**
	 * 执行编码
	 * @param t
	 * @return
	 */
	public final ByteBuffer doEncode(NioMessageEvent msgEvent){

		ByteBuffer b = this.encode(msgEvent);
		NioCoderAdapter adapter = null;
		for(Iterator<NioCoderAdapter> iter = pipe.iterator(); iter.hasNext();){
			adapter = iter.next();
			adapter.encode(b);
		}
		return b;
	}
	public final NioMessageEvent doDecode(ByteBuffer byteBuffer){
		
		if(this.checkDecode(byteBuffer)){
			NioCoderAdapter adapter = null;
			for(Iterator<NioCoderAdapter> iter = pipe.descendingIterator(); iter.hasNext();){
				adapter = iter.next();
				adapter.decode(byteBuffer);
			}
			return this.decode(byteBuffer);
		}
		return null;
	}
	public abstract ByteBuffer encode(NioMessageEvent msgEvent);
	public abstract NioMessageEvent decode(ByteBuffer byteBuffer);
	public abstract boolean checkDecode(ByteBuffer byteBuffer);
	public abstract boolean checkEncode(NioMessageEvent msgEvent);
	public void init(NioCoderConfig coderConfig) throws Exception{
		List<NioCoderAdapterConfig> adapterConfigs = coderConfig.getNioCoderAdapterConfig();
		if(adapterConfigs == null || adapterConfigs.isEmpty()) return;
		for(NioCoderAdapterConfig adapterConfig : adapterConfigs){
			this.pipe.addLast(NioCoderAdapter.parseConfig(adapterConfig));
		}
	}
}
