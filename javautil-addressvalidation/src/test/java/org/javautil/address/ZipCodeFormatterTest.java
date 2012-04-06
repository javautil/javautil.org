package org.javautil.address;

import static org.junit.Assert.assertEquals;

import org.javautil.address.exception.AddressStandardizationException;
import org.javautil.address.usps.ZipCodeFormatter;
import org.javautil.test.StandardTest;
import org.junit.Test;

/**
 * <p>ZipCodeFormatterTest class.</p>
 *
 * @author jjs
 * @version $Id: ZipCodeFormatterTest.java,v 1.2 2012/03/04 12:31:11 jjs Exp $
 * @since 0.11.0
 */
public class ZipCodeFormatterTest extends StandardTest {

	private final ZipCodeFormatter formatter = new ZipCodeFormatter();

	/**
	 * <p>zipTest.</p>
	 *
	 * @throws org.javautil.address.exception.AddressStandardizationException if any.
	 */
	@Test
	public void zipTest() throws AddressStandardizationException {
		assertEquals("76092", formatter.formatUSPostalCode("76092"));
		assertEquals("76092-4500", formatter.formatUSPostalCode("76092-4500"));

	}

	/**
	 * <p>zip9Test.</p>
	 *
	 * @throws org.javautil.address.exception.AddressStandardizationException if any.
	 */
	public void zip9Test() throws AddressStandardizationException {
		assertEquals("76092-4500", formatter.formatUSPostalCode("760924500"));
	}

	/**
	 * <p>invalidZips.</p>
	 *
	 * @throws org.javautil.address.exception.AddressStandardizationException if any.
	 */
	@Test(expected = org.javautil.address.usps.ZipCodeFormatException.class)
	public void invalidZips() throws AddressStandardizationException {
		assertEquals("76092-4500", formatter.formatUSPostalCode("76092-450"));
	}

	/**
	 * <p>nonNumericZip.</p>
	 *
	 * @throws org.javautil.address.exception.AddressStandardizationException if any.
	 */
	@Test(expected = org.javautil.address.usps.ZipCodeFormatException.class)
	public void nonNumericZip() throws AddressStandardizationException {
		assertEquals("7609A", formatter.formatUSPostalCode("76092-450"));
	}
}
