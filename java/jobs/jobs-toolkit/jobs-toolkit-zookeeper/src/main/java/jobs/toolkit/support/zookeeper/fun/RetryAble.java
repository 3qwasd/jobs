package jobs.toolkit.support.zookeeper.fun;

abstract class RetryAble<Fun> {
	
	protected Fun retryFun;
	
	public void setRetryFun(Fun retryFun){
		this.retryFun = retryFun;
	}
}
