package org.javautil.recordvalidation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.javautil.field.FieldTypeDefinition;
import org.javautil.recordvalidation.fieldtype.UnFormattedSignedIntegerFieldType;
import org.junit.Test;

public class UnformattedSignedIntegerFieldTypeTest {
	@Test
	public void testUnformattedSignedInteger() {
		final FieldTypeDefinition alpha = new UnFormattedSignedIntegerFieldType();
		assertTrue(alpha.isMatch("-1"));
		assertTrue(alpha.isMatch(" -1"));
		assertTrue(alpha.isMatch(" 1"));
		assertFalse(alpha.isMatch(" --1"));
		assertFalse(alpha.isMatch(" +1"));
		assertFalse(alpha.isMatch(" 1 "));
		assertFalse(alpha.isMatch(" "));
	}

}
