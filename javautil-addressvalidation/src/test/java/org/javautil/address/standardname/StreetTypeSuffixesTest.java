package org.javautil.address.standardname;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apache.log4j.Logger;
import org.javautil.test.StandardTest;
import org.junit.Test;

/**
 * <p>StreetTypeSuffixesTest class.</p>
 *
 * @author jjs
 * @version $Id: StreetTypeSuffixesTest.java,v 1.2 2012/03/04 12:31:18 jjs Exp $
 * @since 0.11.0
 */
public class StreetTypeSuffixesTest extends StandardTest {

	private final StreetTypeSuffixes suffixes = new StreetTypeSuffixes();

	private final Logger logger = Logger.getLogger(getClass());

	/**
	 * <p>test1.</p>
	 */
	@Test
	public void test1() {
		final List<StreetTypeSuffix> suffixList = suffixes
				.getStreetTypeSuffixes();
		logger.debug("suffixList " + suffixList.size());
	}

	/**
	 * <p>test2.</p>
	 */
	@Test
	public void test2() {
		suffixes.init();
		// logger.debug(suffixes.getKnownVariants());
		final StreetTypeSuffix alley = suffixes.getStreetTypeSuffix("allee");
		assertNotNull(alley);
		assertEquals("ALLEY", alley.getPrimarySuffix());
		assertEquals("ALY", alley.getStandardAbbreviation());
		suffixes.getStreetTypeSuffixes();
	}
}
