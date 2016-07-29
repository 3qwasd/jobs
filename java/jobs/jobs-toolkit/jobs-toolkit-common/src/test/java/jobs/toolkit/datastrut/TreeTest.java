/**
 * 
 */
package jobs.toolkit.datastrut;


import java.io.File;


import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jobs.toolkit.datastrut.Tree;
import jobs.toolkit.datastrut.TreeNodeVisitor;

/**
 * @author jobs
 *
 */
public class TreeTest {
	
	public File root;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		root = new File("D:\\deploy\\MediaMatchView-0.0.1-SNAPSHOT\\MediaMatchView");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link jobs.toolkit.datastrut.Tree#preorderTraversal(java.lang.Object, jobs.toolkit.datastrut.TreeNodeVisitor)}.
	 */
	@Test
	public void testPreorderTraversal() {
		Tree.preorderTraversal(root, new TreeNodeVisitor<File>() {

			@Override
			public void visit(File node) {
				// TODO Auto-generated method stub
				System.out.println(node.getAbsolutePath());
			}

			@Override
			public boolean isLeaf(File node) {
				// TODO Auto-generated method stub
				return node.isFile() || node.list().length == 0;
			}

			@Override
			public List<File> getChildrean(File node) {
				// TODO Auto-generated method stub
				File[] children = node.listFiles();
				return Arrays.asList(node.listFiles());
			}
		});
	}

	/**
	 * Test method for {@link jobs.toolkit.datastrut.Tree#postorderTraversal(java.lang.Object, jobs.toolkit.datastrut.TreeNodeVisitor)}.
	 */
	@Test
	public void testPostorderTraversal() {
		Tree.postorderTraversal(root, new TreeNodeVisitor<File>() {

			@Override
			public void visit(File node) {
				// TODO Auto-generated method stub
				System.out.println(node.getAbsolutePath());
			}

			@Override
			public boolean isLeaf(File node) {
				// TODO Auto-generated method stub
				return node.isFile() || node.list().length == 0;
			}

			@Override
			public List<File> getChildrean(File node) {
				// TODO Auto-generated method stub
				return Arrays.asList(node.listFiles());
			}
		});
	}

}
