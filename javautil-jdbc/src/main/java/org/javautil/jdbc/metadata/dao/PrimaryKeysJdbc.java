package org.javautil.jdbc.metadata.dao;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.javautil.jdbc.metadata.PrimaryKey;

/**
 * 
 * @author jim
 */
public class PrimaryKeysJdbc {
	static Logger logger = Logger.getLogger(PrimaryKeysJdbc.class.getName());
	// TODO create interface and method that doesn't require meta or 
	// TODO create method for getting a bunch of PrimaryKeys in a fetch
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
				keySeq = rs.getShort("key_seq");  // TODO should probably be column seq
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
