package jobs.toolkit.datastrut;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import jobs.toolkit.io.TextFileTools;

public class TrieTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws IOException {
		Trie trie = new Trie('中');
		trie.addWordSuffix(new char[]{'华'});
		trie.addWordSuffix(new char[]{'华','人','民'});
		trie.addWordSuffix(new char[]{'华','人','民', '共', '和', '国'});
		String s1 = "中华人民共和国";
		Assert.assertTrue(trie.match(s1.toCharArray()));
		Assert.assertFalse(trie.match(s1.substring(0, 5).toCharArray()));
		Assert.assertTrue(trie.maxMatch(s1.toCharArray()) == 7);
		System.out.println(trie.allMatch(s1.toCharArray()));
		Assert.assertTrue(trie.allMatch(s1.toCharArray()).equals(Arrays.asList(2,4,7)));
	}

}
