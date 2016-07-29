package jobs.toolkit.alg;

/**
 * 动态规划法
 * @author jobs
 *
 */
public class DynamicProAssistant {

	/**
	 * 求最长公共子串
	 * @param x
	 * @param y
	 * @return
	 */
	public static String LCS(CharSequence x, CharSequence y){
		if(x == null || y == null ) return "";
		int m = x.length(), n = y.length();
		if(m < 1 || n < 1) return "";

		StringBuffer lcs = new StringBuffer();

		int[][] c = new int[m + 1][n + 1];

		for(int i = 1; i <= m; i++){
			for(int j = 1; j <= n; j++){
				int _i = i - 1, _j = j - 1;
				if(x.charAt(_i) == y.charAt(_j))
					c[i][j] = c[_i][_j] + 1;
				else if(c[_i][j] >= c[i][_j])
					c[i][j] = c[_i][j];
				else
					c[i][j] = c[i][_j];
			}
		}
		buildLCS(lcs, x, m, n, c);
		return lcs.toString();
	}
	
	private static void buildLCS(StringBuffer lcs, CharSequence x, int m, int n, int[][] c){
		if(m == 0 || n == 0) return;
		int _m = m - 1, _n = n - 1;
		if(c[m][n] == c[_m][_n] + 1){
			buildLCS(lcs, x, _m, _n, c);
			lcs.append(x.charAt(_m));
		} else if(c[_m][n] >= c[m][_n]){
			buildLCS(lcs, x, _m, n, c);
		} else {
			buildLCS(lcs, x, m, _n, c);
		}
	}
	/**
	 * 钢条切割类似问题的求解方法
	 * @param len 钢条长度
	 * @param price 收益表, 下标+1表示长度, 值表示对应长度的收益
	 * @param result 用于接收结构的数组, 不能为空, 长度需大于等于len
	 * @param solution 用于接收解决方案的数组对象, 不能为空, 长度需大于等于len
	 */
	public static void bottomUpCutRod(int len, int[] price, int[] result, int[] solution){
		if(len == 0) return;
		if(price == null || price.length < len)
			throw new IllegalArgumentException("price must be not null and it's length must be equal len");
		if(result == null || result.length < len) 
			throw new IllegalArgumentException("result must be not null and it's length must be more than len");
		if(solution == null || solution.length < len)
			throw new IllegalArgumentException("solution must be not null and it's length must be more than len");

		for(int i = 1; i <= len; i++){
			int max = Integer.MIN_VALUE;
			//内层循环用于求解j = i的子问题, 
			//通过比较不同的切割组合方案, 
			//选取收益最大的子问题方案, 
			//并将该子问题的最优解保存在result中
			//解决方案保存在solution种
			for(int j = 1; j < i; j++){
				if(max < price[j - 1] + result[i - j - 1]){
					max = price[j - 1] + result[i - j - 1];
					solution[i - 1] = j;
				}
			}
			if(max < price[i - 1]){ 
				max = price[i - 1];
				solution[i - 1] = i;
			}
			result[i - 1] = max;
		}
	}
	/**
	 * 矩阵链乘法问题解决方法
	 * @param len 矩阵个数
	 * @param p 矩阵链的行列数数组
	 * @param m 保存各子问题的最优解的数组
	 * @param s 保存分割方案的数组
	 */
	public static void matrixChainOrder(int len, int[] p, int[][] m, int[][] s){
		if(len == 0) return;
		if(p == null || p.length < len + 1)
			throw new IllegalArgumentException("p must be not null and it's length must be equal len");
		if(m == null || m.length < len || m[0].length < len)
			throw new IllegalArgumentException("m must be not null and it's rows and cols must be equal len");
		if(s == null || s.length < len || s[0].length < len)
			throw new IllegalArgumentException("m must be not null and it's rows and cols must be equal len");
		//外层循环控制子问题规模, 先计算长度为2的子问题, 然后计算长度为3的子问题, 依次类推增大子问题规模, 大规模的问题可以利用小规模的子问题的解
		for(int l = 2; l <= len; l++){
			//计算i到j的子问题, i与j的距离由l控制, 通过循环计算固定距离的i->j的子问题
			for(int i = 1; i <= len - l + 1; i++){
				int j = i + l - 1;
				m[i - 1][j - 1] = Integer.MAX_VALUE;
				//通过比对不同的分割点k, 来计算i到j的最优解
				for(int k = i; k < j; k++){
					int _i = i - 1, _j = j - 1, _k = k - 1;
					int v = m[_i][_k] + m[k][_j] + p[_i] * p[k] * p[j];
					if(v < m[_i][_j]){
						m[_i][_j] = v;
						s[_i][_j] = k;
					}
				}
			}

		}
	}
}
