package jobs.toolkit.lifecycle;

/**
 * 生命周期状态异常
 * @author jobs
 *
 */
public class LifeCycleStateException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public LifeCycleStateException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LifeCycleStateException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public LifeCycleStateException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public LifeCycleStateException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}	
}
