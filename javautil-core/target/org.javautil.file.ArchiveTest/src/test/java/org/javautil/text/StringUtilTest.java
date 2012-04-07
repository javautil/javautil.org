package org.javautil.text;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;

public class StringUtilTest {

	private final Logger logger = Logger.getLogger(getClass());

	@Test
	public void testTrimRight() {
		Assert.assertEquals("homer", StringUtils.trimRight("homer"));
		Assert.assertEquals("homer", StringUtils.trimRight("homer "));
		Assert.assertEquals("homer", StringUtils.trimRight("homer  "));
		Assert.assertEquals("homer",
				StringUtils.trimRight("homer                   "));
		Assert.assertEquals(" homer", StringUtils.trimRight(" homer "));
		Assert.assertEquals(" homer", StringUtils.trimRight(" homer  "));
		Assert.assertEquals(" homer",
				StringUtils.trimRight(" homer               "));
		Assert.assertEquals("  homer", StringUtils.trimRight("  homer    "));
		Assert.assertEquals("  homer bart",
				StringUtils.trimRight("  homer bart   "));
		Assert.assertEquals("  homer   bart",
				StringUtils.trimRight("  homer   bart   "));
		Assert.assertEquals("  homer bart",
				StringUtils.trimRight("  homer bart   "));
		Assert.assertEquals("", StringUtils.trimRight(" "));
		assertNull(StringUtils.trimRight(null));
	}

	@Test
	public void testLeftTrim() {
		Assert.assertEquals("homer", StringUtils.trimLeft("homer"));
		Assert.assertEquals("homer ", StringUtils.trimLeft("homer "));
		Assert.assertEquals("homer  ", StringUtils.trimLeft("homer  "));
		Assert.assertEquals("homer                   ",
				StringUtils.trimLeft("homer                   "));

		Assert.assertEquals("homer   ", StringUtils.trimLeft(" homer   "));

		Assert.assertEquals("homer               ",
				StringUtils.trimLeft(" homer               "));
		Assert.assertEquals("homer    ", StringUtils.trimLeft("  homer    "));
		Assert.assertEquals("homer bart   ",
				StringUtils.trimLeft("  homer bart   "));
		Assert.assertEquals("homer   bart   ",
				StringUtils.trimLeft("  homer   bart   "));
		Assert.assertEquals("homer bart   ",
				StringUtils.trimLeft("  homer bart   "));
		Assert.assertEquals("", StringUtils.trimLeft(" "));
		assertNull(StringUtils.trimLeft(null));
	}

	@Test
	public void testFromCamelCaseToWords() {
		Assert.assertEquals("us_dwarves_are_more_sprinters", StringUtils
				.fromCamelCaseToWords("usDwarvesAreMoreSprinters", '_'));
		Assert.assertEquals("aye! grab me a pint lad!",
				StringUtils.fromCamelCaseToWords("Aye!GrabMeAPintLad!", ' '));
		Assert.assertEquals("... dont tell the elf",
				StringUtils.fromCamelCaseToWords("...DontTellTheElf", ' '));
	}

	@Test
	public void testLpad() {
		assertEquals("   tom", StringUtils.lpad("tom", 6, " ", true));
		assertEquals("tom", StringUtils.lpad("tomy", 3, " ", true));
		assertEquals("tom", StringUtils.lpad("tom", 3, " ", true));
		assertEquals(null, StringUtils.lpad(null, 3, " ", true));
		assertEquals("12345", StringUtils.lpad("12345678", 5, "0", true));
		assertEquals("0000000055", StringUtils.lpad("55", 10, "0", true));
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testLpadArgs1() {
		assertEquals("tom   ", StringUtils.lpad("tom", 6, "  ", true));
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testLpadArgs2() {
		assertEquals("tom", StringUtils.lpad("tom", 0, " ", true));
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testLpadArgs3() {
		assertEquals("tom", StringUtils.lpad("tom", 3, null, true));
	}

	@Test
	public void testRightTrim() {
		assertEquals("tom", StringUtils.rightTrim("tommy", 3));
		assertEquals("tommy", StringUtils.rightTrim("tommy", 5));
		assertEquals("", StringUtils.rightTrim(null, 2));
		assertEquals("100", StringUtils.rightTrim(1000, 3));

	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testRightTrimNegativeLength() {
		StringUtils.rightTrim("3", -1);
	}

	@Test
	public void testToJavaString() {
		ArrayList<String> strings = new ArrayList<String>();
		strings.add("a");
		strings.add("b");
		String pad = "    ";
		String actual = StringUtils.toJavaString(strings, pad, false);
		String expected = pad + "\"a\" + //\n" + pad + "\"b\"";
		logger.info("\n" + expected);
		logger.info("\n" + actual);
		assertEquals(expected, actual);

	}

	@Test
	public void testToJavaStringWithNewline() {
		ArrayList<String> strings = new ArrayList<String>();
		strings.add("a");
		strings.add("b");
		String pad = "    ";

		String actual = StringUtils.toJavaString(strings, pad, true);
		String expected = pad + "\"a\\n\" + //\n" + pad + "\"b\\n\"";
		logger.info("\n" + expected);
		logger.info("\n" + actual);
		assertEquals(expected, actual);
	}
}
