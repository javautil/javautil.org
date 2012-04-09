package org.javautil.dataset.math;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
public class Summation implements CollectionMathOperation {

	@Override
	public double compute(final Number... numbers) {
		double retVal = 0;
		for (final Number num : numbers) {
			retVal += (num == null ? 0 : num.doubleValue());
		}
		return retVal;
	}

}
