package org.javautil.jdbc.metadata;

import java.util.Map;

public interface Index {
	public String getSchemaName();

	public String getTableName();

	public String getIndexName();

	public Map<Short, IndexColumn> getColumnsById();

	public Map<String, IndexColumn> getColumnsByColumnName();

	public IndexColumn getColumnById(Integer id);

	public IndexColumn getByColumnName(String columnName);

	public void addIndexColumn(IndexColumn indexColumn);
}
