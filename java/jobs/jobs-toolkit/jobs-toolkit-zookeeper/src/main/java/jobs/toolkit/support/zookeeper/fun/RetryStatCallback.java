package jobs.toolkit.support.zookeeper.fun;

import org.apache.zookeeper.AsyncCallback.StatCallback;

public abstract class RetryStatCallback extends RetryAble<StatFun> implements StatCallback {
	
}
