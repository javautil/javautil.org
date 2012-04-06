package org.javautil.jdbc.metadata;

import org.javautil.jdbc.metadata.containers.DatabaseTables;
import org.javautil.jdbc.metadata.containers.DatabaseTablesImpl;

public class SchemaImpl implements Schema {

	public static final String revision = "$Revision: 1.2 $";

	private DatabaseTables tables = new DatabaseTablesImpl();

	private String schemaName;

	@Override
	public DatabaseTables getTables() {
		return tables;
	}

	@Override
	public void setSchemaName(final String schemaName) {
		this.schemaName = schemaName;
	}

	@Override
	public void setTables(DatabaseTables databaseTables) {
		this.tables = databaseTables;
	}

	public void addTable(Table t) {
		tables.addTable(t);

	}

	// only works for oracle
	// public abstract Map<String, DatabaseObject> getFunctions();
	//
	// public abstract Map<String, DatabaseObject> getProcedures();
	//
	// public abstract Map<String, DatabaseObject> getPackageSpecifications();
	//
	// public abstract Map<String, DatabaseObject> getPackageBodies();
	//
	// public abstract Map<String, DatabaseObject> getTriggers();
	//
	// public abstract Map<String, DatabaseObject> getViews();

	//
	// public abstract boolean canGetPackageBodies();
	//
	// public abstract boolean canGetPackageSpecifications();
	//
	// public abstract boolean canGetProcedures();
	//
	// public abstract boolean canGetFunctions();
	//
	// public abstract String getViewSource(String viewName);
	//
	// public abstract boolean canGetViewSource();

	// public abstract PackageSpecification getPackageSpecifications();

}