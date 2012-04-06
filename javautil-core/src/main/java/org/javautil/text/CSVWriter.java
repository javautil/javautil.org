package org.javautil.text;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;

import org.apache.log4j.Logger;
import org.javautil.lang.ArrayHelper;

/**
 * 
 * @author jjs TODO replaceall \r \c \r\c \c\r with specified newline
 */
public class CSVWriter {
	private final Writer out;
	private static final String newline = System.getProperty("line.separator");
	private boolean flushEveryLine = true;
	private SimpleDateFormatter dateFormatter = new SimpleDateFormatter(
			CommonDateFormat.ISO_SECOND);
	private final Logger logger = Logger.getLogger(getClass());
	private String fieldSeparator = ",";
	/**
	 * Some utilities get pretty pissed if the correct number of fields aren't
	 * found set this to true for them.
	 */
	private boolean suppressTrailingNulls = true;
	/**
	 * If a String is zero length or null emit null.
	 */
	private boolean emitEmptyStringsAsNull = true;

	private final AsString objectFormatter = new AsString();

	/**
	 * @return the suppressTrailingNulls
	 */
	public boolean isSuppressTrailingNulls() {
		return suppressTrailingNulls;
	}

	/**
	 * @param suppressTrailingNulls
	 *            the suppressTrailingNulls to set
	 */
	public void setSuppressTrailingNulls(boolean suppressTrailingNulls) {
		this.suppressTrailingNulls = suppressTrailingNulls;
	}

	/**
	 * @return the fieldSeparator
	 */
	public String getFieldSeparator() {
		return fieldSeparator;
	}

	/**
	 * @param fieldSeparator
	 *            the fieldSeparator to set
	 */
	public void setFieldSeparator(String fieldSeparator) {
		this.fieldSeparator = fieldSeparator;
	}

	public CSVWriter() {
		out = null;
	}

	public CSVWriter(final Writer w) {
		if (w == null) {
			throw new IllegalArgumentException("w is null");
		}
		out = w;
	}

	public String escape(final String in) {
		String retval = in;
		if (in != null) {
			if (in.indexOf("\"") > -1) {
				retval = in.replace("\"", "\"\"");
			}
			if (in.indexOf("\n") > -1) {
				retval = in.replace("\n", "\\n");
			}
		}
		return retval;
	}

	/**
	 * Converts an array of objects to a comma separated values String.
	 * 
	 * @param args
	 * @return
	 */
	public String asString(final Object... args) {
		if (args == null) {
			throw new IllegalArgumentException("objects is null");
		}
		Object[] objects = null;

		if (args.length == 1 && args[0] != null && args[0].getClass().isArray()) {
			objects = (Object[]) args[0];
		} else {
			objects = args;
		}

		final StringBuilder sb = new StringBuilder();
		final int n;
		if (suppressTrailingNulls) {
			n = ArrayHelper.lastNonNullValueIndex(objects);
		} else {
			n = args.length;
		}

		for (int i = 0; i <= n; i++) {
			final Object o = objects[i];
			if (o != null) {
				if (o instanceof String) {
					String str = (String) o;
					if ((str.length() == 0 && !emitEmptyStringsAsNull)
							|| str.length() > 0) {
						sb.append("\"");
						sb.append(escape((String) o));
						sb.append("\"");
					}
				} else if (o instanceof Number) {
					sb.append(((Number) o).toString());
				} else if (o instanceof Date) {
					sb.append((dateFormatter.format((Date) o)));
				} else {
					sb.append("\"");
					sb.append(escape(o.toString()));
					sb.append("\"");
				}
			}
			if (i < n) {
				sb.append(fieldSeparator);
			}

		}
		final String retval = sb.toString();
		if (logger.isDebugEnabled()) {
			StringBuilder dsb = new StringBuilder();
			dsb.append("Objects: " + objectFormatter.toString(objects) + "\n");
			dsb.append("output string: " + retval);
			logger.debug(dsb.toString());
		}
		return retval;

	}

	// todo jjs what happend to the number formatters
	public void write(final Object... objects) throws IOException {
		if (objects == null) {
			throw new IllegalArgumentException("objects is null");
		}
		out.write(asString(objects));
		out.write(newline);
		if (flushEveryLine) {
			out.flush();
		}
	}

	public void close() throws IOException {
		out.close();

	}

	/**
	 * @return the flushEveryLine
	 */
	public boolean isFlushEveryLine() {
		return flushEveryLine;
	}

	/**
	 * @param flushEveryLine
	 *            the flushEveryLine to set
	 */
	public void setFlushEveryLine(final boolean flushEveryLine) {
		this.flushEveryLine = flushEveryLine;
	}

	public SimpleDateFormatter getDateFormatter() {
		return dateFormatter;
	}

	public void setDateFormatter(final SimpleDateFormatter dateFormatter) {
		this.dateFormatter = dateFormatter;
	}

	public boolean isEmitEmptyStringsAsNull() {
		return emitEmptyStringsAsNull;
	}

	public void setEmitEmptyStringsAsNull(boolean value) {
		this.emitEmptyStringsAsNull = value;
	}
}
