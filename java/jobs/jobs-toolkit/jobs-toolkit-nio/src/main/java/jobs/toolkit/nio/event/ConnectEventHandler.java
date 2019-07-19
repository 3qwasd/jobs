package jobs.toolkit.nio.event;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import jobs.toolkit.event.BaseEventHandler;
import jobs.toolkit.nio.NioClient;

/**
 * 接收事件处理器
 * @author jobs
 *
 */
public class ConnectEventHandler extends BaseEventHandler<SelectedEvent> {

	@Override
	public void doHandle(SelectedEvent event) throws Throwable {
		SocketChannel sc = null;
		NioClient client = null;
		try{
			SelectionKey key = event.getSelectionKey();
			client = (NioClient) key.attachment();
			sc = (SocketChannel) key.channel();
			if(sc.finishConnect()){
				client.onConnectSucess(sc);
			}
		}catch(Throwable _e){
			if(client != null){
				client.onConnectAbort(sc, _e);
			}else{
				if(sc != null){
					try{sc.close();}catch(Throwable __){};
				}
				throw new IllegalStateException("NioClient is null!", _e);
			}
		}
	}
}
