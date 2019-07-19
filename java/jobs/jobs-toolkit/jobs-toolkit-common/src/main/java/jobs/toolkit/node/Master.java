package jobs.toolkit.node;

public interface Master extends Peer{
	
	void runForLeader();
	
	void checkLeader();
	
	void runAsCadidate();
	
}
