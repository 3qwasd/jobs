package jobs.toolkit.support.zookeeper.fun;

import org.apache.zookeeper.AsyncCallback.DataCallback;

public abstract class RetryDataCallback extends RetryAble<DataFun> implements DataCallback {

}
