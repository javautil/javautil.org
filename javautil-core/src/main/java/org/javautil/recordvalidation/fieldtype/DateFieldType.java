package org.javautil.recordvalidation.fieldtype;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.javautil.field.FieldTypeDefinition;

public class DateFieldType extends FieldTypeDefinition {

	private final String dateFormat;

	private final SimpleDateFormat simpleDateFormat;
	private final Logger logger = Logger.getLogger(getClass());

	public DateFieldType(final String dateFormat) {
		// TODO in conjunction with date format this could be wrong
		super("NUMERIC", "[0-9]+");
		this.dateFormat = dateFormat;
		simpleDateFormat = new SimpleDateFormat(dateFormat);
	}

	public Date getObject(final byte[] bytes) throws FieldParseException {
		try {

			final Date date = simpleDateFormat.parse(new String(bytes));
			if (logger.isDebugEnabled()) {
				logger.debug("field text '" + new String(bytes) + "' format '" + dateFormat
						+ "' " + date);
			}
			return date;
		} catch (final ParseException e) {
			throw new FieldParseException(e);
		}
	}
}
