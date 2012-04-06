package org.javautil.jdbc.metadata.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.javautil.text.StringBuilderHelper;

public class DatabaseMetadataHelper {

	public static String toString(Connection conn) throws SQLException {
		if (conn == null) {
			throw new IllegalArgumentException("conn is null");
		}
		DatabaseMetaData meta = conn.getMetaData();
		return toString(meta);
	}

	public static String toString(DatabaseMetaData meta) throws SQLException {
		if (meta == null) {
			throw new IllegalArgumentException("meta is null");
		}

		StringBuilderHelper h = new StringBuilderHelper();
		h.addNameValue("userName ", meta.getUserName());
		h.addNameValue("url", meta.getURL());
		h.addNameValue("Database Version", meta.getDatabaseMajorVersion() + "."
				+ meta.getDatabaseMinorVersion());
		String retval = h.toString();
		return retval;
	}
}
