package org.javautil.oracle.metadata;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.driver.OracleSql;
import oracle.jdbc.internal.OracleResultSet;

import org.apache.log4j.Logger;

public class OracleDatabaseMetaDataHelper
// extends
// oracle.jdbc.OracleDatabaseMetaData implements DatabaseMetaData {
{
	private final Logger logger = Logger.getLogger(getClass());
	private final OracleConnection connection;

	public OracleDatabaseMetaDataHelper(final OracleConnection conn) {
		// super(conn);
		this.connection = conn;
	}

	// @Override
	public synchronized ResultSet getIndexInfo(final String catalog,
			final String schema, final String table, final boolean unique,
			final boolean approximate) throws SQLException {
		final Statement localStatement = this.connection.createStatement();

		if (((schema != null) && (schema.length() != 0) && (!(OracleSql
				.isValidObjectName(schema))))
				|| ((table != null) && (table.length() != 0) && (!(OracleSql
						.isValidObjectName(table))))) {
			throw new IllegalArgumentException("the schema " + schema
					+ " or table " + table + " has an invalid name");
			// DatabaseError.throwSqlException(
			// getConnectionDuringExceptionHandling(), 68);
		}

		if (!(approximate)) {
			final String str1 = new StringBuilder()
					.append("analyze table ")
					.append(new StringBuilder().append(schema).append(".")
							.toString()).append(table)
					.append(" compute statistics").toString();

			localStatement.executeUpdate(str1);
		}

		final StringBuilder str1 = new StringBuilder(
				"select null as table_cat,\n "
						+ "      owner as table_schem,\n "
						+ "      table_name,\n      "
						+ " 0 as NON_UNIQUE,\n     "
						+ "  null as index_qualifier,\n "
						+ "      null as index_name, 0 as type,\n   "
						+ "    0 as ordinal_position, "
						+ "null as column_name,\n     "
						+ "  null as asc_or_desc,\n   "
						+ "    num_rows as cardinality,\n   "
						+ "    blocks as pages,\n    "
						+ "   null as filter_condition\n" + "from all_tables\n"
						+ "where table_name = :tableName \n");

		if ((schema != null) && (schema.length() > 0)) {
			str1.append("  and owner = :schema ");
		}

		final StringBuilder selectBuilder = new StringBuilder(
				"select null as table_cat,\n       "
						+ "i.owner as table_schem,\n      "
						+ " i.table_name,\n   "
						+ "    decode (i.uniqueness, 'UNIQUE', 0, 1) non_unique ,\n "
						+ "      null as index_qualifier,\n  "
						+ "     i.index_name,\n  "
						+ "     1 as type,\n    "
						+ "   c.column_position as ordinal_position,\n    "
						+ "   c.column_name,\n   "
						+ "    case descend when 'ASC' then 'A' when 'DESC' then 'D' else 'WTF' end  as asc_or_desc,\n   "
						+ "    i.distinct_keys as cardinality,\n  "
						+ "     i.leaf_blocks as pages,\n  "
						+ "     null as filter_condition\n"
						+ "from all_indexes i, all_ind_columns c\n"
						+ "where i.table_name = :tableName and "
						+ " (i.owner = :owner or :owner is null) \n");

		// String str4 = "";

		// if ((schema != null) && (schema.length() > 0))
		// str4 = new StringBuilder().append("  and i.owner = '").append(
		// schema).append("'\n").toString();

		// String str5 = "";

		if (unique) {
			selectBuilder.append("  and i.uniqueness = 'UNIQUE'\n");
		}

		selectBuilder.append("  and i.index_name = c.index_name\n  and "
				+ "i.table_owner = c.table_owner\n  "
				+ "and i.table_name = c.table_name\n  "
				+ "and i.owner = c.index_owner\n");

		selectBuilder
				.append("order by non_unique, type, index_name, ordinal_position\n");

		// String str8 = new StringBuilder().append(str1).append(str2).append(
		// "union\n").append(str3).append(str4).append(str5).append(str6)
		// .append(str7).toString();

		final String selectText = selectBuilder.toString();
		logger.debug("executing " + selectText);
		final PreparedStatement ps = connection.prepareStatement(selectText);
		int bindNbr = 1;
		logger.info("binding for table '" + table + "' schema '" + schema + "'");
		ps.setString(bindNbr++, table);

		ps.setString(bindNbr++, schema);
		ps.setString(bindNbr++, schema);

		final OracleResultSet localOracleResultSet = (OracleResultSet) ps
				.executeQuery();
		// .executeQuery(selectBuilder.toString());

		localOracleResultSet.closeStatementOnClose();

		return localOracleResultSet;
	}

	// SQLException fail() {
	// SQLException localSQLException = new SQLException("Not implemented yet");

	// return localSQLException;
	// }

}
