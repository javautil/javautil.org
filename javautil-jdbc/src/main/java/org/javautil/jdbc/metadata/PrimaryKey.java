package org.javautil.jdbc.metadata;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

/**
 * 
 * @author jjs@javautil.org
 * 
 *         TODO why is this any different than a unique constraint or for that
 *         matter any other
 * @version 1.0
 */
public class PrimaryKey implements Iterable<String> {
	private final String schema;
	private final String tableName;
	private final String primaryKeyName;
	private final TreeMap<Integer, String> columns = new TreeMap<Integer, String>();

	public PrimaryKey(final String schema, final String tableName,
			final String primaryKeyName) {
		this.schema = schema;
		this.tableName = tableName;
		this.primaryKeyName = primaryKeyName;
	}

	public void addColumn(final String columnName, final int keySeq) {
		columns.put(new Integer(keySeq), columnName);
	}

	/**
	 * 
	 * @return the name of the columns in the primary key in the order in which
	 *         they occur.
	 */
	public List<String> getColumnNames() {
		final ArrayList<String> columnNames = new ArrayList<String>();
		for (final Integer seq : columns.keySet()) {
			columnNames.add(columns.get(seq));
		}
		return columnNames;
	}

	public String getPrimaryKeyName() {
		return primaryKeyName;
	}

	public String getSchema() {
		return schema;
	}

	public String getTableName() {
		return tableName;
	}

	@Override
	public Iterator<String> iterator() {
		return columns.values().iterator();
	}

}
