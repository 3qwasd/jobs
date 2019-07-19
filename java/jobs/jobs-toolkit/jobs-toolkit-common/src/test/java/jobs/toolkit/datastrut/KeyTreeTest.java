package jobs.toolkit.datastrut;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import jobs.toolkit.io.CommonFileTools;

public class KeyTreeTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void test() throws FileNotFoundException {
		KeyTree<String> keyTree = new KeyTree<String>("D:");
		
		CommonFileTools.BFSALL("D:\\v8-master", f -> {
			String path = f.getAbsolutePath();
			keyTree.add(path.split("\\\\"));
		});
		String testPath = "D:\\v8-master\\tools\\generate_shim_headers\\generate_shim_headers.py";
		String[] test1 = testPath.split("\\\\");
		String[] test2 = Arrays.copyOf(test1, 4);
		String[] test3 = Arrays.copyOf(test1, 3);
		String[] test4 = Arrays.copyOf(test1, 2);
		String[] test5 = {"tools","generate_shim_headers","generate_shim_headers.py"};
		String[] test6 = {"xxxx", "xxxxx", "D:","v8-master","tools","generate_shim_headers","generate_shim_headers.py"};
		Assert.assertTrue(keyTree.match(test1));
		Assert.assertTrue(keyTree.match(test2));
		Assert.assertTrue(keyTree.match(test3));
		Assert.assertFalse(keyTree.match(test4));
		Assert.assertFalse(keyTree.match(test5));
		Assert.assertFalse(keyTree.match(null));
		Assert.assertFalse(keyTree.match(new String[0]));
		Assert.assertFalse(keyTree.match(test1, 5, 5));
		Assert.assertTrue(keyTree.match(test6, 2, 5));
		Assert.assertTrue(keyTree.maxMatch(test6, 2) == 5);
		Assert.assertTrue(keyTree.maxMatch(test3) == 3);
		Assert.assertTrue(keyTree.allMatch(new ArrayList<>(), test6, 2).equals(Arrays.asList(3,4,5)));
	}

}
