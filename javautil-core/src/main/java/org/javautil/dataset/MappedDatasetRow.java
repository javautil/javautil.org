package org.javautil.dataset;

import org.apache.log4j.Logger;

/**
 * 
 * todo document
 * 
 * @author jjs@dbexperts.com
 * 
 */
public class MappedDatasetRow {

	private final Logger logger = Logger.getLogger(getClass());
	private final Object[] rowId;
	private static String newline = System.getProperty("line.separator");

	private final Object[] values;
	private final String tab = " ";

	private final Integer rownum;

	public MappedDatasetRow(final Object[] id, final Object[] values,
			final Integer rownum) {
		if (id == null) {
			throw new IllegalArgumentException("id is null");
		}
		if (values == null) {
			throw new IllegalArgumentException("values is null");
		}
		if (rownum == null) {
			throw new IllegalArgumentException("rownum is null");
		}
		this.rowId = id;
		this.values = values;
		this.rownum = rownum;
	}

	public Integer getRownum() {
		return rownum;
	}

	public Object[] getRowId() {
		return rowId;
	}

	public Object[] getValues() {
		return values;
	}

	@Override
	public String toString() {
		final StringBuilder b = new StringBuilder();
		b.append("rowid: ");
		for (final Object o : rowId) {
			b.append(o);
			b.append(tab);
		}
		b.append(" values: ");
		for (final Object value : values) {
			b.append(value);
			b.append(tab);
		}
		return b.toString();
	}

}
