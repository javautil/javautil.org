package org.javautil.recordvalidation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.javautil.field.FieldTypeDefinition;
import org.javautil.recordvalidation.fieldtype.AlphaFieldType;
import org.javautil.recordvalidation.fieldtype.AlphanumericFieldType;
import org.javautil.recordvalidation.fieldtype.NumericFieldType;
import org.javautil.recordvalidation.fieldtype.NumericLeadingOrTrailingSpacesFieldType;
import org.javautil.recordvalidation.fieldtype.NumericLeadingSpacesFieldType;
import org.javautil.recordvalidation.fieldtype.PrintFieldType;
import org.javautil.recordvalidation.fieldtype.YNFieldType;
import org.junit.Test;

public class FieldTypeTest {
	@Test
	public void testAlphaFieldTypeDefinition() {
		final FieldTypeDefinition alpha = new AlphaFieldType();
		assertTrue(alpha.isMatch("aa"));
		assertFalse(alpha.isMatch(" a"));
		assertTrue(alpha.isMatch("AB"));
		assertFalse(alpha.isMatch(" G"));
	}

	@Test
	public void testAlphaNumeric() {
		final FieldTypeDefinition alphaNumeric = new AlphanumericFieldType();
		assertTrue(alphaNumeric.isMatch("a2"));
		assertFalse(alphaNumeric.isMatch("a2@"));
		assertFalse(alphaNumeric.isMatch("a\t"));
		assertFalse(alphaNumeric.isMatch("a2 "));
	}

	@Test
	public void testNumeric() {
		final FieldTypeDefinition numeric = new NumericFieldType();
		assertTrue(numeric.isMatch("3"));
		assertFalse(numeric.isMatch(" 3"));
		assertFalse(numeric.isMatch("a"));
		assertFalse(numeric.isMatch("%"));
	}

	@Test
	public void testYN() {
		final FieldTypeDefinition yn = new YNFieldType();
		assertTrue(yn.isMatch("Y"));
		assertTrue(yn.isMatch("N"));
		assertFalse(yn.isMatch(" Y"));
		assertFalse(yn.isMatch(" N"));
		assertFalse(yn.isMatch("Y "));
		assertFalse(yn.isMatch("N "));
		assertFalse(yn.isMatch(" "));
		assertFalse(yn.isMatch(" K*"));
	}

	@Test
	public void testPrint() {
		final FieldTypeDefinition print = new PrintFieldType();
		// assertTrue(print.isMatch("a1$"));
		assertTrue(print.isMatch("a1$ "));
		// assertFalse(print.isMatch("  a1$"));
	}

	@Test
	public void testNumLeadingSpaces() {
		final FieldTypeDefinition numLeadingSpaces = new NumericLeadingSpacesFieldType();
		assertTrue(numLeadingSpaces.isMatch(" 7"));
		assertFalse(numLeadingSpaces.isMatch(" &9"));
		assertFalse(numLeadingSpaces.isMatch(" 7 "));
	}

	@Test
	public void testNumLeadingOrTrailingSpaces() {
		final FieldTypeDefinition numLeadingOrTrailingSpaces = new NumericLeadingOrTrailingSpacesFieldType();
		assertTrue(numLeadingOrTrailingSpaces.isMatch(" 7 "));
		assertFalse(numLeadingOrTrailingSpaces.isMatch(" &9 "));
	}

}
