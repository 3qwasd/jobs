package jobs.toolkit.lifecycle;

public class ExceptionLifeCycle extends MyLifeCycle{

	public ExceptionLifeCycle(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doInit() {
		// TODO Auto-generated method stub
		super.doInit();
		throw new NullPointerException();
	}

	@Override
	public void doStart() {
		// TODO Auto-generated method stub
		super.doStart();
		throw new NullPointerException();
	}

	@Override
	public void doStop() {
		// TODO Auto-generated method stub
		super.doStop();
		throw new NullPointerException();
	}
	
	
}
