package org.javautil.util;

/**
 * 
 * @author bcm todo document, test and examples
 */
public abstract class MathUtil {

	public static boolean withinUnboundedRangeInclusive(final Number value,
			final Number floor, final Number ceiling) {
		boolean ret = false;
		if (value == null) {
			throw new NullPointerException("value is null");
		}
		if (ceiling == null && floor == null) {
			ret = true;
		} else if (ceiling != null && floor != null) {
			ret = MathUtil.withinBoundedRangeInclusive(value, floor, ceiling);
		} else if (ceiling != null) {
			ret = ceiling.doubleValue() >= value.doubleValue();
		} else if (floor != null) {
			ret = value.doubleValue() <= floor.doubleValue();
		}
		return ret;
	}

	public static boolean withinBoundedRangeInclusive(final Number value,
			final Number floor, final Number ceiling) {
		if (value == null) {
			throw new NullPointerException("value is null");
		}
		if (floor == null) {
			throw new NullPointerException("floor is null");
		}
		if (ceiling == null) {
			throw new NullPointerException("ceiling is null");
		}
		final double dv = value.doubleValue();
		final double df = floor.doubleValue();
		final double dc = ceiling.doubleValue();
		return df <= dv && dv <= dc;
	}

	public static boolean withinBoundedRangeExclusive(final Number value,
			final Number floor, final Number ceiling) {
		if (value == null) {
			throw new NullPointerException("value is null");
		}
		if (floor == null) {
			throw new NullPointerException("floor is null");
		}
		if (ceiling == null) {
			throw new NullPointerException("ceiling is null");
		}
		final double dv = value.doubleValue();
		final double df = floor.doubleValue();
		final double dc = ceiling.doubleValue();
		return df < dv && dv < dc;
	}

}
