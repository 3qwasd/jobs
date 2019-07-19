package jobs.toolkit.nio.event;

import java.io.IOException;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import jobs.toolkit.event.BaseEventHandler;
import jobs.toolkit.nio.NioServer;

/**
 * 接收事件处理器
 * @author jobs
 *
 */
public class AcceptEventHandler extends BaseEventHandler<SelectedEvent> {

	@Override
	public void doHandle(SelectedEvent event) throws Throwable {
		
		SocketChannel sc = null;
		NioServer server = null;
		try{
			SelectionKey key = event.getSelectionKey();
			server = (NioServer) key.attachment();
			ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
			sc = ssc.accept();
			Socket so = sc.socket();			
			so.setSendBufferSize(server.getSendBufferSize());
			so.setReceiveBufferSize(server.getReceiveBufferSize());
			so.setTcpNoDelay(server.isTcpNoDelay());
			sc.configureBlocking(false);
			server.onConnectSucess(sc);
		}catch(Throwable _e){
			if(server != null){
				server.onConnectAbort(sc, _e);
			}else{
				if(sc != null)
					try{sc.close();}catch(IOException __){}
				throw new IllegalStateException("NioServer is null!", _e);
			}
		}
	}
}
