package jobs.toolkit.support.zookeeper.fun;

import org.apache.zookeeper.AsyncCallback.StringCallback;

public abstract class RetryStringCallback extends RetryAble<StringFun> implements StringCallback {
	
}
