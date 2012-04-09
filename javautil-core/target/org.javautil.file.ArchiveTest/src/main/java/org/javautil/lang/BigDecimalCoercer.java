package org.javautil.lang;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

public class BigDecimalCoercer {

	private Logger logger = Logger.getLogger(getClass());
	public BigDecimal coerce(Object obj) {
		BigDecimal retval = null;
		while (obj != null && true) {
			if (Double.class.isAssignableFrom(obj.getClass())) {
				retval = new BigDecimal((Double) obj);
				break;
			}
			if (Float.class.isAssignableFrom(obj.getClass())) {
				retval = new BigDecimal((Float) obj);
				break;
			}
			if (Integer.class.isAssignableFrom(obj.getClass())) {
				retval = new BigDecimal((Integer) obj);
				break;
			}
			if (String.class.isAssignableFrom(obj.getClass())) {
				retval = new BigDecimal((String) obj);
				break;
			}
			if (BigDecimal.class.isAssignableFrom(obj.getClass())) {
				retval = (BigDecimal) obj;
				break;
			}
			throw new IllegalArgumentException("unsupported class " +  obj.getClass());
		}

		return retval;
	}
}
