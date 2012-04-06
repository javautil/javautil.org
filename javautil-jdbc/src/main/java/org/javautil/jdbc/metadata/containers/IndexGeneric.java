package org.javautil.jdbc.metadata.containers;

import java.util.Map;
import java.util.TreeMap;

import org.javautil.jdbc.metadata.Index;
import org.javautil.jdbc.metadata.IndexColumn;


public class IndexGeneric implements Index {

	private Map<Short, IndexColumn> columnsById;
	private Map<String, IndexColumn> columnsByName;
	private String indexName;
	private String schemaName;
	private String tableName;

	public IndexGeneric() {
	}

	public IndexGeneric(final String schemaName, final String tableName,
			final String indexName) {
		this.schemaName = schemaName;
		this.indexName = indexName;
		this.tableName = tableName;
	}

	@Override
	public Map<Short, IndexColumn> getColumnsById() {
		return columnsById;
	}

	@Override
	public String getIndexName() {
		return indexName;
	}

	@Override
	public String getSchemaName() {
		return schemaName;
	}

	@Override
	public String getTableName() {
		return tableName;
	}

	@Override
	public IndexColumn getByColumnName(final String columnName) {
		return columnsByName.get(columnName);
	}

	@Override
	public IndexColumn getColumnById(final Integer id) {
		return columnsById.get(id);
	}

	@Override
	public Map<String, IndexColumn> getColumnsByColumnName() {
		return columnsByName;
	}

	@Override
	public void addIndexColumn(final IndexColumn indexColumn) {
		// todo check not null and dupes of position and column name;
		if (columnsById == null) {
			columnsById = new TreeMap<Short, IndexColumn>();
		}
		if (columnsByName == null) {
			columnsByName = new TreeMap<String, IndexColumn>();
		}
		columnsById.put(indexColumn.getOrdinalPosition(), indexColumn);
		columnsByName.put(indexColumn.getColumnName(), indexColumn);

	}

}
