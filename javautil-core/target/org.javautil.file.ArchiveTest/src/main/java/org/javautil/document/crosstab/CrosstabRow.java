package org.javautil.document.crosstab;

import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.javautil.collections.ArrayComparator;
import org.javautil.lang.ArrayHelper;
import org.javautil.text.AsString;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
public class CrosstabRow {

	private final Logger logger = Logger.getLogger(getClass());
	private final Object[] rowId;
	private static final String newline = System.getProperty("line.separator");

	private final TreeMap<Object[], Object[]> cellsByColumnId = new TreeMap<Object[], Object[]>(
			new ArrayComparator());

	public CrosstabRow(final Object[] key) {
		this.rowId = key;
	}

	public void addCells(final Object columnId, final Object[] cellValues) {
		final Object[] oa = new Object[1];
		oa[0] = columnId;
		addCells(oa, cellValues);
	}

	public void addCells(final Object[] columnId, final Object[] cellValues) {
		try {
			if (cellsByColumnId.get(columnId) != null) {
				throw new IllegalArgumentException("duplicate columnId value "
						+ columnId + " found for "
						+ ArrayHelper.asString(rowId));
			}
		} catch (final ClassCastException cce) {
			final AsString as = new AsString();
			final String message = as.toString(columnId);
			logger.error("columnId was " + message + " "
					+ columnId.getClass().getName());
			throw new RuntimeException(cce);
		}
		cellsByColumnId.put(columnId, cellValues);

	}

	public Object[] getRowId() {
		return rowId;
	}

	public Object[] getCellsByColumnId(final Object columnId) {
		final Object[] oa = new Object[1];
		oa[0] = columnId;
		return cellsByColumnId.get(oa);
	}

	public Object[] getCellsByColumnId(final Object[] columnId) {
		return cellsByColumnId.get(columnId);
	}

	@Override
	public String toString() {
		final StringBuilder b = new StringBuilder();
		b.append("rowid: ");
		for (final Object o : rowId) {
			b.append(o);
			b.append(" ");
		}
		b.append(newline);
		for (final Object[] cellKey : cellsByColumnId.keySet()) {
			b.append("key ");
			for (final Object o : cellKey) {
				b.append(o);
				b.append(" ");
			}

			b.append(newline);
			b.append("cell values: ");
			for (final Object v : cellsByColumnId.get(cellKey)) {
				b.append(v);
				b.append(" ");
			}
			b.append(newline);
		}
		return b.toString();
	}

}
