package org.javautil.util;

import junit.framework.Assert;

import org.junit.Test;

public class MathUtilTest {

	@Test
	public void testWithinBoundedRangeExclusive() {
		Assert.assertTrue(MathUtil.withinBoundedRangeExclusive(1, 0, 2));
		Assert.assertTrue(MathUtil.withinBoundedRangeExclusive(-1, -2, 0));
		Assert.assertFalse(MathUtil.withinBoundedRangeExclusive(1, 1, 1));
	}

	@Test
	public void testWithinBoundedRangeInclusive() {
		Assert.assertTrue(MathUtil.withinBoundedRangeInclusive(1, 1, 1));
		Assert.assertTrue(MathUtil.withinBoundedRangeInclusive(1, 1, 2));
		Assert.assertTrue(MathUtil.withinBoundedRangeInclusive(1, 0, 1));
	}

	@Test
	public void testWithinUnboundedRangeInclusive() {
		Assert.assertTrue(MathUtil.withinUnboundedRangeInclusive(1, null, null));
		Assert.assertTrue(MathUtil.withinUnboundedRangeInclusive(1, 2, null));
		Assert.assertTrue(MathUtil.withinUnboundedRangeInclusive(1, null, 2));
		Assert.assertTrue(MathUtil.withinUnboundedRangeInclusive(1, 1, null));
		Assert.assertTrue(MathUtil.withinUnboundedRangeInclusive(1, null, 1));
		Assert.assertFalse(MathUtil.withinUnboundedRangeInclusive(1, 0, null));
		Assert.assertFalse(MathUtil.withinUnboundedRangeInclusive(1, null, 0));
		Assert.assertFalse(MathUtil.withinUnboundedRangeInclusive(1, 2, 2));
	}

}
