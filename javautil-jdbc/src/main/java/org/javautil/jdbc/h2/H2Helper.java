package org.javautil.jdbc.h2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.javautil.text.StringBuilderHelper;

public class H2Helper {
	public static String getInfo(Connection conn) throws SQLException {
		// TODO check for H2 in DatabaseMetaData
		PreparedStatement ps = conn
				.prepareStatement("select * from information_schema.tables");
		ResultSet rs = ps.executeQuery();
		StringBuilderHelper h = new StringBuilderHelper();
		while (rs.next()) {
			h.addNameValue("schema", rs.getString("TABLE_SCHEMA"));
			h.addNameValue("catalog", rs.getString("TABLE_CATALOG"));
			h.addNameValue("table", rs.getString("TABLE_NAME"));
		}
		return h.toString();
	}
}
