package jobs.toolkit.alg;


class CInt implements Comparable<CInt>{
	int value;
	@Override
	public int compareTo(CInt o) {
		// TODO Auto-generated method stub
		return this.value - o.value;
	}
	public CInt setValue(int value){
		this.value = value;
		return this;
	}
	@Override
	public String toString() {
		return "value = " + this.value;
	}
	
}