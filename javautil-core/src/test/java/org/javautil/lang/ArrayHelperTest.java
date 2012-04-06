package org.javautil.lang;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ArrayHelperTest {

	@Test
	public void test1() {
		assertEquals(-1, ArrayHelper.lastNonNullValueIndex(null));
		assertEquals(-1, ArrayHelper.lastNonNullValueIndex(new Object[0]));
		assertEquals(-1, ArrayHelper.lastNonNullValueIndex(new Object[1]));
		assertEquals(
				0,
				ArrayHelper.lastNonNullValueIndex(new Object[] { "A", null,
						null }));
		assertEquals(2, ArrayHelper.lastNonNullValueIndex(new Object[] { "A",
				null, "C" }));

	}

}
