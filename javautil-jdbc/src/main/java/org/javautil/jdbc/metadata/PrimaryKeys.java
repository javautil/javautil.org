package org.javautil.jdbc.metadata;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * TODO delete only needed by JDBCTables
 * @author jim
 */
public class PrimaryKeys {
	static Logger logger = Logger.getLogger(PrimaryKeys.class.getName());

	public static PrimaryKey getPrimaryKey(final DatabaseMetaData meta,
			final String catalog, final String schemaPattern,
			final String tableName) throws SQLException {
		PrimaryKey pk = null;
		final ResultSet rs = meta.getPrimaryKeys(catalog, schemaPattern,
				tableName);
		if (rs.next()) {

			final String schema = rs.getString("table_schem");
			// String tableName = rs.getString("table_name");
			final String pkName = rs.getString("pk_name");
			pk = new PrimaryKey(schema, tableName, pkName);
			short keySeq = rs.getShort("key_seq");
			String columnName = rs.getString("column_name");
			pk.addColumn(columnName, keySeq);
			while (rs.next()) {
				keySeq = rs.getShort("key_seq");
				columnName = rs.getString("column_name");
				pk.addColumn(columnName, keySeq);
			}
		}

		rs.close();

		if (pk == null) {
			logger.info(" Index infos not found");
		}
		return pk;
	}

}
