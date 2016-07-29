package jobs.toolkit.support.zookeeper;


import org.apache.zookeeper.KeeperException.Code;

import jobs.toolkit.logging.Log;
import jobs.toolkit.logging.LogFactory;
import jobs.toolkit.support.zookeeper.fun.CommonFun;
import jobs.toolkit.support.zookeeper.fun.EventFun;
import jobs.toolkit.support.zookeeper.fun.StringFun;
import jobs.toolkit.support.zookeeper.impl.ZKSupportImpl;

/**
 * 一些常量以及方法
 * @author jobs
 *
 */
public final class ZK {
	
	private static final Log LOG = LogFactory.getLog(ZKSupporter.class);
	
	public static final String ZK_PATH_DELIMITER = "/";
	
	public static final EventFun LOG_DEBUG_EVENT_FUN = (event) -> LOG.debug("Zookeeper event : " + event.toString());
	
	public static final CommonFun LOG_DEBUG_COMMON_FUN = (path, rc, ctx) -> LOG.debug("Path: %1$s; ResultCode : %2$s", path, Code.get(rc).name());
	
	public static final StringFun LOG_INFO_NODE_EXIST_FUN = path -> LOG.info("Node %1$s already exists.", path);
	
	public static final StringFun LOG_INFO_NO_NODE_FUN = path -> LOG.info("Node %1$s not exists.", path);
	
	public static final long CONNECT_TIME_OUT = 30 * 1000; //连接到Zookeeper服务器的超时时间

	public static final int SESSION_TIME_OUT = 15 * 1000; //Zookeeper会话超时时间
	
	public static final boolean RETRY = true;//该变量表示如果zookeeper操作超时, 是否重新尝试的默认值, 默认重新尝试
	
	public static final int RETRY_LIMIT = 10;//重新尝试限制的次数, 限制的是10次
	
	public static final int RETRY_INTERVAL = 6 * 1000;//重新尝试的间隔, 默认是1秒, 6000毫秒
	
	/**
	 * 将给定的字串连接成zookeeper的path
	 * @param pathSegs
	 * @return
	 */
	public static String JOIN_PATH(String ...pathSegs){
		if(pathSegs == null || pathSegs.length < 1) return ZK_PATH_DELIMITER;
		return ZK_PATH_DELIMITER.concat(String.join(ZK_PATH_DELIMITER, pathSegs));
	}
	
	public static ZKSupporter newSupporter(){
		return new ZKSupportImpl();
	}
	
}
