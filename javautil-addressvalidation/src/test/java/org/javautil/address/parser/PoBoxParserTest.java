package org.javautil.address.parser;

import static org.junit.Assert.assertEquals;

import org.javautil.address.exception.AddressStandardizationException;
import org.javautil.address.usps.AddressValidationException;
import org.junit.Test;

/**
 * <p>PoBoxParserTest class.</p>
 *
 * @author jjs
 * @version $Id: PoBoxParserTest.java,v 1.2 2012/03/04 12:31:20 jjs Exp $
 * @since 0.11.0
 */
public class PoBoxParserTest {

	/**
	 * <p>test.</p>
	 *
	 * @throws org.javautil.address.usps.AddressValidationException if any.
	 */
	@Test
	public void test() throws AddressValidationException {
		final POBoxParser parser = new POBoxParser();
		parser.parse("P.O. Box 42");
		assertEquals("42", parser.getPoBox());

		// todo should this be an exeption
		parser.parse("P.O. Box ");
		assertEquals("", parser.getPoBox());

		parser.parse("PO Box 132 ");
		assertEquals("132", parser.getPoBox());

		parser.parse("po box 132 ");
		assertEquals("132", parser.getPoBox());

		parser.parse("po. box 132 ");
		assertEquals("132", parser.getPoBox());

	}

	/**
	 * <p>test2.</p>
	 *
	 * @throws org.javautil.address.exception.AddressStandardizationException if any.
	 */
	@Test(expected = org.javautil.address.exception.AddressStandardizationException.class)
	public void test2() throws AddressStandardizationException {
		final POBoxParser parser = new POBoxParser();
		parser.parse("PO Box 1701 PO Box 3025");

	}

	/**
	 * <p>test3.</p>
	 *
	 * @throws org.javautil.address.exception.AddressStandardizationException if any.
	 */
	@Test(expected = org.javautil.address.exception.AddressStandardizationException.class)
	public void test3() throws AddressStandardizationException {
		final POBoxParser parser = new POBoxParser();
		parser.parse("PO Box 1701 Backdoor");

	}
}
