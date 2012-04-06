package org.javautil.sales.populate;

import java.util.Date;

import org.javautil.util.DateHelper;

//import org.javautil.calendar.DateHelper;

public class RandomDateGenerator {

	private final Date minDate;

	private final int daysInRange;

	// todo jjs allow for work days , week of and assigning randomly based on
	// work days
	/**
	 * 
	 * @param minDate
	 * @param maxDate
	 */
	public RandomDateGenerator(final Date minDate, final Date maxDate) {
		if (minDate == null) {
			throw new IllegalArgumentException("minDate is null");
		}
		if (maxDate == null) {
			throw new IllegalArgumentException("minDate is null");
		}
		this.minDate = minDate;
		this.daysInRange = DateHelper.daysBetween(minDate, maxDate);
	}

	public Date getDate() {
		final int delta = (int) (Math.random() * daysInRange);
		final Date retval = DateHelper.addDays(minDate, delta);
		return retval;
	}
}
