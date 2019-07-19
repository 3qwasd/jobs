package jobs.toolkit.nio;

import java.nio.ByteBuffer;

import jobs.toolkit.nio.NioConfig.NioCoderAdapterConfig;

public abstract class NioCoderAdapter {
	
	public abstract void encode(ByteBuffer b);
	
	public abstract void decode(ByteBuffer b);
	
	public abstract void init(NioCoderAdapterConfig config);
	
	public static NioCoderAdapter parseConfig(NioCoderAdapterConfig config) throws Exception{
		NioCoderAdapter nioCoderAdapter = (NioCoderAdapter) Class.forName(config.getNioCoderAdapterClassName()).newInstance();
		nioCoderAdapter.init(config);
		return nioCoderAdapter;
	}
}
