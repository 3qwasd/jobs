package jobs.toolkit.alg;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DynamicProAssistantTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	@Test
	public void testLCS(){
		String x = "ABCBDAB";
		String y = "BDCABA";
		String res = DynamicProAssistant.LCS(x, y);
		System.out.println(res);
	}
	
	@Test
	public void testBottomUpCutRod() {
		int len = 10;
		int[] price = new int[]{1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
		int[] result = new int[10];
		int[] solution = new int[10];
		DynamicProAssistant.bottomUpCutRod(len, price, result, solution);
		for(int i=0;i<len;i++){
			System.out.println(result[i]);
			System.out.println(solution[i]);
		}
	}
	@Test
	public void testMartixChain(){
		int len = 6;
		int[] p = new int[]{30,35,15,5,10,20,25};
		int[][] m = new int[6][6];
		int[][] s = new int[6][6];
		DynamicProAssistant.matrixChainOrder(len, p, m, s);
		printOptimalParens(s, 1, 6);
	}
	private void printOptimalParens(int[][] s, int i, int j){
		int _i = i - 1, _j = j - 1;
		if(i == j) {
			System.out.print("A" + i);
		} else {
			System.out.print("(");
			printOptimalParens(s, i, s[_i][_j]);
			printOptimalParens(s, s[_i][_j] + 1, j);
			System.out.print(")");
		}
	}
}
