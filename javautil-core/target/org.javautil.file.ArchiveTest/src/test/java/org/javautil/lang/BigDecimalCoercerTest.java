package org.javautil.lang;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.junit.Test;

public class BigDecimalCoercerTest {

	private BigDecimalCoercer coercer = new BigDecimalCoercer();
	private Logger logger = Logger.getLogger(getClass());
	@Test
	public void testBigDecimal() {
		BigDecimal bd = new BigDecimal("14");
		logger.debug("bd is " + bd.toString());
		assertTrue(bd == coercer.coerce(bd) );
	}
	@Test
	public void testFloat() {
		assertTrue(coercer.coerce(new Float(4.3)).equals(new BigDecimal(new Float(4.3))));
	}
	@Test
	public void testDouble() {
		assertTrue(coercer.coerce(new Double(4.3)).equals(new BigDecimal(new Double(4.3))));
	}
		
	@Test
	public void testInteger() {
		assertTrue(coercer.coerce(new Integer(4)).equals(new BigDecimal(4)));
		
	}
	@Test
	public void testString() {
		assertTrue(coercer.coerce("314").equals(new BigDecimal(314)));
		
	}
	
	public static void main(String[] args) {
		BigDecimalCoercerTest tester = new BigDecimalCoercerTest();
		tester.testBigDecimal();
		
	}
}
