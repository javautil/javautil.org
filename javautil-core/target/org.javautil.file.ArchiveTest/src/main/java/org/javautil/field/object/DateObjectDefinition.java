package org.javautil.field.object;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class DateObjectDefinition extends AbstractObjectDefinition implements ObjectDefinition {

	private String pattern = null;
	private SimpleDateFormat simpleDateFormat = null;
	private final Logger logger = Logger.getLogger(getClass());

	public DateObjectDefinition(final String dateFormat) {
		this.pattern = dateFormat;
		simpleDateFormat = new SimpleDateFormat(dateFormat);
	}

	@Override
	public Date getObject(String externalRepresentation) throws ObjectParseException {
			try {

				final Date date = simpleDateFormat.parse(externalRepresentation);
				if (logger.isDebugEnabled()) {
					logger.debug("field text '" + externalRepresentation + "' format '" + pattern
							+ "' " + date);
				}
				return date;
			} catch (final ParseException e) {
				throw new ObjectParseException(externalRepresentation, e);
			}
	}


}
