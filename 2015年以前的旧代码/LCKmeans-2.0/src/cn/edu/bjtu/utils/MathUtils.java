/**
 * @QiaoJian
 */
package cn.edu.bjtu.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author QiaoJian
 *
 */
public class MathUtils {
	
	/**
	 * 截取浮点数，保留小数点后@num位
	 * @param source 浮点数
	 * @param num 小数点后的位数
	 * @return
	 */
	public static double cutDouble(Double source,int num){
		BigDecimal bg = new BigDecimal(source);
        double result = bg.setScale(num, BigDecimal.ROUND_HALF_UP).doubleValue();
        return result;
	}
	/**
	 * 格式化浮点数
	 * @param source
	 * @param pattern
	 * @return
	 */
	public static String formatDouble(Double source,String pattern){
		DecimalFormat format = new DecimalFormat(pattern);
		return format.format(source);
	}
	
	/**
	 * 计算2向量距离
	 * @param a
	 * @param b
	 * @return
	 */
	public static double vectorDistance(double[] a,double[] b){
		
		double sum = 0;
		
		for(int i=0;i<a.length;i++){
			sum += (a[i]-b[i])*(a[i]-b[i]);
		}
		return Math.sqrt(sum);
	}
	/**
	 * 归一化欧氏距离计算公式
	 * @param a
	 * @param b
	 * @return
	 */
	public static double normalizationEucliDistance(double[] a,double[] b){
		
		double sum = 0;
		
		for(int i=0;i<a.length;i++){
			sum += (a[i]-b[i])*(a[i]-b[i]);
		}
		return 1.0/(1.0+Math.sqrt(sum));
	}
	/**
	 * 向量乘法
	 * @param a
	 * @param b
	 * @return
	 */
	public static double vectorMulti(double[] a,double[] b){
		
		double sum = 0;
		
		for(int i=0;i<a.length;i++){
			sum += a[i]*b[i];
		}
		
		return sum;
	}
	/**
	 * 计算向量的模
	 * @param a
	 * @return
	 */
	public static double vectorModule(double[] a){
		
		return Math.sqrt(vectorMulti(a, a));
	}
	/**
	 * 计算2向量余弦值
	 */
	@Deprecated
	public static double vectorCosine(double[] a,double[] b){
		return vectorMulti(a, b)/(vectorModule(a)*vectorModule(b));
	}
	/**
	 * 计算2向量余弦值
	 */
	public static double vectorCos(double[] a,double[] b){
		double abMulit = 0;
		double aModule = 0;
		double bModule = 0;
		for(int i=0;i<a.length;i++){
			abMulit += a[i]*b[i];
			aModule += a[i]*a[i];
			bModule += b[i]*b[i];
		}
		
		return abMulit/(Math.sqrt(aModule)*Math.sqrt(bModule));
	}
	
	/**
	 * 余弦值归一化 0.5 + 0.5 * cosθ
	 * @param value
	 * @return
	 */
	public static double cosNormalization(double value){
		
		return 0.5+0.5*value;
	}
	/**
	 * 向量距离归一化 1 / (1 + dist(X,Y))
	 * @return
	 */
	public static double distanceNormalization(double value){
		return 1/(1+value);
	}
}
