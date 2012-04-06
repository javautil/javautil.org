package org.javautil.jdbc.metadata;

import java.util.List;

public interface IndexColumns {

	public abstract List<IndexColumn> getIndexColumns();


	public String getTableName();

	public String getSchemaName();

	public boolean isUnique();

	// public List<IndexColumn> getInfoList();
}