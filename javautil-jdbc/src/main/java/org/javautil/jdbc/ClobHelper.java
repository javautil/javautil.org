package org.javautil.jdbc;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;

/**
 * 
 * @author jjs
 * 
 */
public class ClobHelper {

	/**
	 * Returns the contents of the Clob as a String.
	 */
	public static String getAsString(final Clob c) throws SQLException {

		final int buffLength = 1024;
		final char[] buff = new char[buffLength];
		final StringBuilder b = new StringBuilder();
		Reader r = null;
		if (c != null) {
			r = c.getCharacterStream();
			int charsRead = 0;

			try {
				while ((charsRead = r.read(buff)) != -1) {
					if (charsRead == buffLength) {
						b.append(buff);
					} else {
						for (int i = 0; i < charsRead; i++) {
							b.append(buff[i]);
						}
					}
				}
			} catch (final IOException e) {
				throw new SQLException(e);
			} finally {
				try {
					r.close();
				} catch (final IOException e) {
					throw new SQLException(e);
				}
			}
		}
		return b.toString();
	}
}