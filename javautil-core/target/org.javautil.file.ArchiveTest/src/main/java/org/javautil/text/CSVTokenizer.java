package org.javautil.text;

import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * 
 * Converts the fields in a comma delimited list that is optionally enclosed by
 * quotes.
 * 
 * Excel will each '"' character in a cell to two occurrences of '"'. This
 * procedure will reduce each pair of '"' to one occurrence in the returned
 * string.
 * 
 * Open office on the other hand will put a character "\u201D" that is the
 * "right double quote" see
 * http://www.fileformat.info/info/unicode/char/201d/index.htm
 * 
 * This routine be default will substitute the '"' character for the if found
 * 
 * @author jjs
 * 
 *         todo support TAB as delimiter
 * 
 * @see CSVWriter the complementary class
 */
public class CSVTokenizer {

	private int index;

	private final ArrayList<Boolean> wasQuoted = new ArrayList<Boolean>();

	private static final char QUOTE_CHAR = '"';

	private static final String QUOTE_STRING = "\"";

	private static final String DOUBLE_QUOTE_STRING = "\"\"";

	private char[] chars;

	private String text;

	private int columnNbr = 0;

	/**
	 * The token separator.
	 * 
	 * In tab delimited files this should be '\t' In comma delimited files this
	 * should be ','
	 * 
	 * 
	 * per man ascii...
	 * 
	 * <pre>
	 *  Oct   Dec   Hex   Char                        Oct   Dec   Hex   Char
	 *  011   9     09    HT  ’\t’ (horizontal tab)   111   73    49    I
	 *  056   46    2E    .                           156   110   6E    n
	 * </pre>
	 */
	private char delimiter = ',';

	/**
	 * If you type a quote character in open office spreadsheets you get a
	 * single openOfficeQuoteChar when the file is saved as CSV.
	 */
	// @todo find this character by creating an opensource document and the
	private static final char openOfficeQuoteChar = '\u201D';

	// private static final char[] ooQte =new char[]{ openOfficeQuoteChar };
	private static final String openOfficeQuoteString = new String(
			new char[] { openOfficeQuoteChar });
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * 
	 * returns the next '"' character not followed by a '"' character
	 * 
	 * @throws TokenizingException
	 */

	private int getFieldDelimiterIndex() throws TokenizingException {
		int retval = -1;
		int p = index + 1;

		while (p < chars.length) {
			if (chars[p] == QUOTE_CHAR) {
				if (p == chars.length - 1) {
					retval = p;
					if (logger.isTraceEnabled()) {
						logger.trace("break on end of text");
					}
					break;
				}
				if (chars[p + 1] != QUOTE_CHAR) {
					retval = p;
					if (logger.isTraceEnabled()) {
						logger.trace("found end " + p + " " + chars[p] + " "
								+ chars[p + 1]);
					}

					break;
				}
				p++; // bad code form to increment a loop variable
				if (logger.isTraceEnabled()) {
					logger.trace("dquote found at " + index);
				}
			}
			p++;
		}
		if (retval == -1) {
			final String message = " no end quote for column " + columnNbr
					+ " starting at position " + index + " while processing '"
					+ text + "'";
			throw new TokenizingException(message, p);

		}
		if (logger.isTraceEnabled()) {
			logger.trace("the end quote for index " + index + " is " + retval
					+ " char is " + chars[retval]);
		}
		return retval;
	}

	private String getQuoted() throws TokenizingException {
		index++; // move past opening quote
		final int quotePosition = getFieldDelimiterIndex();
		// int endOfStringIndex = getFieldDelimiterIndex() - 1;
		String retval = text.substring(index, quotePosition);
		index = quotePosition + 1; // point to next delimiter
		// todo check for double quote and open office debug m
		// todo write a test for this condition
		retval = retval.replaceAll(DOUBLE_QUOTE_STRING, QUOTE_STRING);
		retval = retval.replaceAll(openOfficeQuoteString, QUOTE_STRING);
		if (logger.isTraceEnabled()) {
			logger.trace("getQuoted: '" + retval + "'");
		}
		return retval;
	}

	/*
	 * Returns E
	 */
	private String getUntilChar(final char delim) {

		int p = text.indexOf(delim, index);
		if (p == -1) {
			p = text.length();
		}
		final int endIndex = p;
		if (logger.isTraceEnabled()) {
			logger.trace("index " + index + " endIndex " + endIndex);
		}
		final String retval = text.substring(index, endIndex);
		index = p;
		if (logger.isTraceEnabled()) {
			logger.trace("getUntilChar " + delim + " '" + retval + "'");
		}
		return retval;
	}

	/**
	 * 
	 * @return
	 * @throws TokenizingException
	 */
	private String getDelimitedToken(final int columnIndex)
			throws TokenizingException {

		String retval;
		wasQuoted.ensureCapacity(columnIndex + 1);

		if (chars[index] == '"') {
			wasQuoted.add(Boolean.TRUE);
			retval = getQuoted();
		} else {
			wasQuoted.add(Boolean.FALSE);
			retval = getUntilChar(delimiter);
		}

		if (index < text.length() - 1) {
			if (chars[index] != delimiter) {
				throw new TokenizingException("expected '" + delimiter
						+ "' at index " + index + " found '" + chars[index]
						+ "'");
			}
		}
		if (logger.isTraceEnabled()) {
			logger.trace(columnNbr + " '" + retval + "'");
		}
		return retval;

	}

	/**
	 * @return the wasQuoted
	 */
	public ArrayList<Boolean> getWasQuoted() {
		return wasQuoted;
	}

	public ArrayList<String> parse(final String text)
			throws TokenizingException {
		wasQuoted.clear();
		if (text == null) {
			throw new IllegalArgumentException("text is null");
		}
		// logger.debug("parse: '" + text + "'");
		this.text = text;
		chars = text.toCharArray();
		index = 0;
		columnNbr = 0;

		final ArrayList<String> columns = new ArrayList<String>();

		while (index < chars.length) {
			columns.add(getDelimitedToken(index));
			columnNbr++;
			index++;
		}

		return columns;
	}

	public char getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(final char delimiter) {
		this.delimiter = delimiter;
	}

}
