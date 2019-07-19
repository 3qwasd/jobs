package jobs.toolkit.datastrut;

import java.util.List;

public interface TreeNodeVisitor<T> {
	
	public void visit(T node);
	
	public boolean isLeaf(T node);
	
	public List<T> getChildrean(T node);
}
