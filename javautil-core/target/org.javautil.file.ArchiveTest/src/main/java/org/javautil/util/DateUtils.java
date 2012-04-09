package org.javautil.util;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Convenience methods for converting to/from different date formats.
 * 
 * @author cvw
 * 
 */
public class DateUtils {
	public static XMLGregorianCalendar toXMLGregorianCalendar(
			final java.sql.Date sqlDate) {
		XMLGregorianCalendar ret = null;
		if (sqlDate != null) {
			final java.util.Date date = new java.util.Date(sqlDate.getTime());
			ret = toXMLGregorianCalendar(date);
		}
		return ret;
	}

	public static XMLGregorianCalendar toXMLGregorianCalendar(
			final java.util.Date date) {
		XMLGregorianCalendar ret = null;
		if (date != null) {
			try {
				final GregorianCalendar gc = new GregorianCalendar();
				gc.setTime(date);
				final DatatypeFactory dt = DatatypeFactory.newInstance();
				ret = dt.newXMLGregorianCalendar(gc);
			} catch (final DatatypeConfigurationException e) {
				throw new IllegalStateException(e);
			}
		}
		return ret;
	}

	public static java.sql.Date toSqlDate(final XMLGregorianCalendar xmlDate) {
		java.sql.Date ret = null;
		if (xmlDate != null) {
			final GregorianCalendar gc = xmlDate.toGregorianCalendar();
			ret = new java.sql.Date(gc.getTimeInMillis());
		}
		return ret;
	}

	public static java.util.Date toDate(final XMLGregorianCalendar xmlDate) {
		java.util.Date ret = null;
		if (xmlDate != null) {
			final GregorianCalendar gc = xmlDate.toGregorianCalendar();
			ret = new java.util.Date(gc.getTimeInMillis());
		}
		return ret;
	}
}
