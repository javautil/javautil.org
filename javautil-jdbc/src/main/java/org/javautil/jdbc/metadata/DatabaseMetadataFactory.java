package org.javautil.jdbc.metadata;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class DatabaseMetadataFactory {

	public static DatabaseMetaData getDatabaseMetadata(final Connection conn)
			throws SQLException {
		final DatabaseMetaData retval = conn.getMetaData();

		// if (conn instanceof oracle.jdbc.OracleConnection ||
		// conn instanceof oracle.jdbc.driver.OracleConnection ||
		// conn instanceof oracle.jdbc.internal.OracleConnection) {
		//
		// retval = new OracleDatabaseMetaDataHelper((OracleConnection) conn);
		//
		//
		// } else {
		// retval = conn.getMetaData();
		// }
		return retval;
	}
}
