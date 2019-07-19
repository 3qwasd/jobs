package jobs.toolkit.node;

public enum NodeType {
	MASTER(0, "master"), WORKER(1, "worker"), PEER(2, "Peer");

	private int code;

	private String name;

	private NodeType(int code, String name){
		this.code = code;
		this.name = name;
	}
	public int getCode(){
		return this.code;
	}
	@Override
	public String toString() {
		return this.name;
	}
}
