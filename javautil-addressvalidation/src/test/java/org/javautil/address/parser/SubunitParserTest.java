package org.javautil.address.parser;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * <p>
 * SubunitParserTest class.
 * </p>
 * 
 * @author jjs
 * @version $Id: SubunitParserTest.java,v 1.3 2012/04/11 18:29:24 jjs Exp $
 * @since 0.11.0
 */
public class SubunitParserTest {

	private static SubunitParser parser = new SubunitParser();

	private static Logger logger = Logger.getLogger(SubunitParserTest.class);

	/**
	 * <p>
	 * beforeClass.
	 * </p>
	 */
	@BeforeClass
	public static void beforeClass() {
		logger.debug(parser.getCodes());
	}

	/**
	 * <p>
	 * testApt.
	 * </p>
	 */
	@Test
	public void testApt() {
		parser.parse("1314 Romeo apt. b");
		assertEquals("b", parser.getSubunitCode());
		assertEquals("APT", parser.getSubunitType());

		parser.parse("1314 Romeo apart 12b");
		assertEquals("12b", parser.getSubunitCode());
		assertEquals("APT", parser.getSubunitType());

		parser.parse("1314 Romeo apart1 12b");
		assertEquals(null, parser.getSubunitCode());
		assertEquals(null, parser.getSubunitType());
	}

	/**
	 * <p>
	 * testApartment.
	 * </p>
	 */
	@Test
	public void testApartment() {
		parser.parse("1314 Romeo apt b");
		assertEquals("b", parser.getSubunitCode());
		assertEquals("APT", parser.getSubunitType());

		parser.parse("1314 Romeo apartment 12b");
		assertEquals("12b", parser.getSubunitCode());
		assertEquals("APT", parser.getSubunitType());

		parser.parse("1314 Romeo apart 12b");
		assertEquals("12b", parser.getSubunitCode());
		assertEquals("APT", parser.getSubunitType());
	}

	/**
	 * <p>
	 * test2.
	 * </p>
	 */
	@Test
	public void test2() {
		parser.parse("1314 Romeo basement");
		assertEquals(null, parser.getSubunitCode());
		assertEquals("BSMT", parser.getSubunitType());
	}

	/**
	 * <p>
	 * test3.
	 * </p>
	 */
	@Test
	public void test3() {
		parser.parse("1314 Romeo Building A");
		logger.debug(parser.getCodes());
		assertEquals("A", parser.getSubunitCode());

		assertEquals("BLDG", parser.getSubunitType());
	}

	/**
	 * <p>
	 * testSuite.
	 * </p>
	 */
	@Test
	public void testSuite() {
		parser.parse("1314 Romeo Suite 3");

		assertEquals("3", parser.getSubunitCode());
		assertEquals("STE", parser.getSubunitType());

		// todo this is a bug, should get pissed as this is a qualified entry
		parser.parse("1314 Romeo Suite");
		logger.debug(parser.getCodes());
		assertEquals(null, parser.getSubunitCode());
		assertEquals(null, parser.getSubunitType());
	}

	/**
	 * <p>
	 * testHangar.
	 * </p>
	 */
	@Test
	public void testHangar() {
		parser.parse("1314 Romeo Hangar Bldg");
		assertEquals("HNGR", parser.getSubunitType());
		assertEquals("Bldg", parser.getSubunitCode());
	}

	/**
	 * <p>
	 * testFloor.
	 * </p>
	 */
	@Test
	public void testFloor() {
		parser.parse("1314 Romeo Floor 32");
		assertEquals("FL", parser.getSubunitType());
		assertEquals("32", parser.getSubunitCode());

		// todo this should be qualified is it an exception or just plain null
		parser.parse("1314 Romeo Floor");
		assertEquals(null, parser.getSubunitType());
		assertEquals(null, parser.getSubunitCode());
	}

	/**
	 * <p>
	 * testFrontUnqualified.
	 * </p>
	 */
	@Test
	public void testFrontUnqualified() {
		parser.parse("1314 Romeo Front");
		assertEquals("FRNT", parser.getSubunitType());
		assertEquals(null, parser.getSubunitCode());
	}

	/**
	 * <p>
	 * testFrontQualified.
	 * </p>
	 */
	@Test
	public void testFrontQualified() {
		parser.parse("1314 Front Street");
		assertEquals(null, parser.getSubunitType());
		assertEquals(null, parser.getSubunitCode());
	}

	/**
	 * <p>
	 * testSlip.
	 * </p>
	 */
	@Test
	public void testSlip() {
		parser.parse("1314 Beach Front Slip 32");
		assertEquals("SLIP", parser.getSubunitType());
		assertEquals("32", parser.getSubunitCode());

	}

}
