package org.javautil.jdbc.metadata.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.javautil.jdbc.metadata.DatabaseMetadataFactory;
import org.javautil.jdbc.metadata.Schema;
import org.javautil.jdbc.metadata.SchemaImpl;

/**
 * 
 * @author jjs
 * @version "$Revision: 1.2 $"
 */
public class SchemaDaoJdbc {

	private final Schema schema = new SchemaImpl();

	private String schemaName;
	private String tablePattern = "%";
	private final DatabaseMetaData meta;
	private final Connection conn;
	private final String catalog;

	public SchemaDaoJdbc(final Connection conn, final String schemaName,
			final String catalog, final String tablePattern)
			throws SQLException {
		if (conn == null) {
			throw new IllegalArgumentException("conn is null");
		}
		if (schemaName == null) {
			throw new IllegalArgumentException("schemaName is null");
		}
		this.conn = conn;
		meta = DatabaseMetadataFactory.getDatabaseMetadata(conn);

		this.schemaName = schemaName;
		this.catalog = catalog;
		this.tablePattern = tablePattern;
	}

	public void populateTables() throws SQLException {
		if (meta == null) {
			throw new IllegalStateException("meta is null");
		}
		if (schemaName == null) {
			throw new IllegalStateException("schemaName is null");
		}
		TableDaoJdbc dao = new TableDaoJdbc(conn, meta, schemaName, catalog,
				tablePattern);
		dao.process();
		schema.setTables(dao.getDatabaseTables());
	}

	public void setSchemaName(final String schemaName) {
		this.schemaName = schemaName;
	}

	// public Map<String, DatabaseObject> getFunctions() {
	// throw new UnsupportedOperationException();
	// }
	//
	//
	// public Map<String, DatabaseObject> getPackageBodies() {
	// throw new UnsupportedOperationException();
	// }
	//
	//
	// public Map<String, DatabaseObject> getPackageSpecifications() {
	// throw new UnsupportedOperationException();
	// }
	//
	//
	// public Map<String, DatabaseObject> getProcedures() {
	// throw new UnsupportedOperationException();
	// }
	//
	//
	// public Map<String, DatabaseObject> getTriggers() {
	// throw new UnsupportedOperationException();
	// }

	public boolean canGetFunctions() {

		return false;
	}

	public boolean canGetPackageBodies() {
		return false;
	}

	public boolean canGetPackageSpecifications() {
		return false;
	}

	public boolean canGetProcedures() {
		return false;
	}

	public boolean canGetTriggers() {
		return false;
	}

	public boolean canGetViewSource() {
		return false;
	}

	/**
	 * @return the schema
	 */
	public Schema getSchema() {
		return schema;
	}

	// public Map<String, DatabaseObject> getViews() {
	// return null;
	// }
}
