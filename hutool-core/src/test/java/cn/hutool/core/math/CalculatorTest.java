package cn.hutool.core.math;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class CalculatorTest {

	@Test
	public void conversationTest(){
		final double conversion = Calculator.conversion("(0*1--3)-5/-4-(3*(-2.13))");
		Assert.assertEquals(10.64, conversion, 2);
	}

	@Test
	public void conversationTest2(){
		final double conversion = Calculator.conversion("77 * 12");
		Assert.assertEquals(924.0, conversion, 2);
	}

	@Test
	public void conversationTest3(){
		final double conversion = Calculator.conversion("1");
		Assert.assertEquals(1, conversion, 2);
	}

	@Test
	public void conversationTest4(){
		final double conversion = Calculator.conversion("(88*66/23)%26+45%9");
		Assert.assertEquals((88D * 66 / 23) % 26, conversion, 2);
	}

	@Test
	@Ignore
	public void conversationTest5(){
		// https://github.com/dromara/hutool/issues/1984
		final double conversion = Calculator.conversion("((1/1) / (1/1) -1) * 100");
		Assert.assertEquals((88D * 66 / 23) % 26, conversion, 2);
	}
}
