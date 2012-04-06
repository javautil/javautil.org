package org.javautil.jdbc.metadata;

import org.javautil.jdbc.metadata.containers.DatabaseTables;

public interface Schema {

	public static final String revision = "$Revision: 1.4 $";

	public abstract DatabaseTables getTables();

	public abstract void setSchemaName(final String schemaName);

	public abstract void setTables(DatabaseTables databaseTables);

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

	// public abstract boolean canGetTriggers();
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