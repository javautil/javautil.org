package org.javautil.address.parser;

import static org.junit.Assert.assertEquals;

import org.javautil.test.StandardTest;
import org.junit.Test;

/**
 * <p>StreetParserTest class.</p>
 *
 * @author jjs
 * @version $Id: StreetParserTest.java,v 1.2 2012/03/04 12:31:20 jjs Exp $
 * @since 0.11.0
 */
public class StreetParserTest extends StandardTest {

	private final StreetParser parser = new StreetParser();

	/**
	 * <p>test1.</p>
	 */
	@Test
	public void test1() {
		parser.parse("1095 Burney Lane");
		assertEquals("1095", parser.getStreetNumber());
		assertEquals("Burney", parser.getStreetName());
		assertEquals("LANE", parser.getStreetType());
	}

	/**
	 * Lanne is not a known variant left in street name
	 */
	@Test
	public void test2() {
		parser.parse("1095 Burney Lanne");
		assertEquals("1095", parser.getStreetNumber());
		assertEquals("Burney Lanne", parser.getStreetName());
		assertEquals(null, parser.getStreetType());
	}
}
