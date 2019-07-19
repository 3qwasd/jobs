package jobs.toolkit.study;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Formatter;



import org.junit.Before;
import org.junit.Test;

public class BigDecimalTest {
	
	BigDecimal positive;
	BigDecimal negative;
	Formatter formatter;
	String str = "BigDecimal is [%1$s], unscale value is [%2$s], scale is [%3$s]\r\n";
	@Before
	public void setUp(){
		positive = new BigDecimal("234234234.12313");
		negative = new BigDecimal("-234234234.12313");
		formatter = new Formatter();
	}
	
	@Test
	public void test(){
		formatter
		.format(str, positive, positive.unscaledValue(), positive.scale())
		.format(str, negative, negative.unscaledValue(), negative.scale());
		positive = positive.setScale(4, RoundingMode.HALF_UP);
		formatter.format(str, positive, positive.unscaledValue(), positive.scale());
		System.out.println(formatter.out());
	}
}
