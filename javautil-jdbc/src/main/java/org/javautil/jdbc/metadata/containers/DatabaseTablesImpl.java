package org.javautil.jdbc.metadata.containers;

import java.util.TreeMap;

import org.javautil.jdbc.metadata.Table;

public class DatabaseTablesImpl extends TreeMap<String,Table> implements DatabaseTables{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public  void addTable(final Table table) {
		put(table.getTableName(),table);
	}



}