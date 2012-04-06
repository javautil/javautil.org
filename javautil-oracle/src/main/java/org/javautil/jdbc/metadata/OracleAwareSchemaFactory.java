package org.javautil.jdbc.metadata;

import java.sql.Connection;
import java.sql.SQLException;

import oracle.jdbc.OracleConnection;

import org.javautil.oracle.metadata.OracleSchema;

/**
 * Checks for an Oracle Connection and then returns corrective code.
 * 
 * @author jjs
 *  
 * TODO nuke
 */
public class OracleAwareSchemaFactory {
//	public static Schema getSchema(final Connection conn,
//			final String schemaName) throws SQLException,
//			NonexistantTableException {
//		if (conn == null) {
//			throw new IllegalArgumentException("conn is null");
//		}
//		if (schemaName == null) {
//			throw new IllegalArgumentException("schemaName is null");
//		}
//		OracleConnection oconn;
//
//		Schema schema = null;
//		try {
//			oconn = (OracleConnection) conn;
//			schema = new OracleSchema(oconn, schemaName);
//		} catch (final ClassCastException cce) {
//			schema = new JdbcSchema(conn, schemaName);
//		}
//		return schema;
//	}
}
