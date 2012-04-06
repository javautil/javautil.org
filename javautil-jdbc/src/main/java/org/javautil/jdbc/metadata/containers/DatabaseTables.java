package org.javautil.jdbc.metadata.containers;

import java.util.Map;

import org.javautil.jdbc.metadata.Table;

public interface DatabaseTables extends Map<String,Table>{

	public abstract void addTable(final Table table);

	//public abstract String getSchemaName();

	//public abstract Table getTable(final String tableName);

	//public abstract Map<String, Table> getTableMap();

	//public abstract Collection<Table> getTables();

	//public abstract Iterator<Table> iterator();

	//public abstract int size();

	@Override
	public abstract String toString();


}