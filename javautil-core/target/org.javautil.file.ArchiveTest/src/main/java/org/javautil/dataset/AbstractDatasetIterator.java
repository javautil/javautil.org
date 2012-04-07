package org.javautil.dataset;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
public abstract class AbstractDatasetIterator implements DatasetIterator {

	private int rowIndex = -1;

	boolean wasNull = false;

	@Override
	public abstract int getRowCount();

	public abstract Object getValue(int rowIndex, int columnIndex);

	public abstract Object getValue(int rowIndex2, String column);

	public AbstractDatasetIterator() {
		super();
	}

	@Override
	public boolean hasNext() {
		if (rowIndex == -1) {
			return getRowCount() > 0;
		}
		return rowIndex < getRowCount() - 1;
	}

	@Override
	public boolean next() {
		rowIndex++;
		return rowIndex < getRowCount();
	}

	public boolean wasNull() {
		return wasNull;
	}

	@Override
	public Object getObject(final int columnIndex) {
		if (rowIndex < 0) {
			throw new IllegalStateException("next was not called");
		}
		final Object value = getValue(rowIndex, columnIndex);
		wasNull = (value == null);
		return value;
	}

	@Override
	public Object getObject(final String column) {
		if (rowIndex < 0) {
			throw new IllegalStateException("next was not called");
		}
		final Object value = getValue(rowIndex, column);
		wasNull = (value == null);
		return value;
	}

	@Override
	public Number getNumber(final int columnIndex) {
		try {
			return (Number) getObject(columnIndex);
		} catch (final ClassCastException ce) {
			throw new IllegalArgumentException("column at index " + columnIndex
					+ " is not a number");
		}
	}

	@Override
	public Number getNumber(final String column) {
		try {
			return (Number) getObject(column);
		} catch (final ClassCastException ce) {
			throw new IllegalArgumentException("column " + column
					+ " is not a number");
		}
	}

	@Override
	public Date getDate(final int column) {
		final Object retVal = getObject(column);
		return (Date) retVal;
	}

	@Override
	public Date getDate(final String columnName) {
		final Object retVal = getObject(columnName);
		return (Date) retVal;
	}

	@Override
	public String getString(final int column) {
		final Object rv = getObject(column);
		final String retval = rv == null ? null : rv.toString();
		return retval;
	}

	@Override
	public String getString(final String column) {
		final Object rv = getObject(column);
		final String retval = rv == null ? null : rv.toString();
		return retval;
	}

	/*
	 * BigDecimal bd = getBigDecimal(n); Double d = bd == null ? null : new
	 * Double(bd.doubleValue());
	 */
	@Override
	public Integer getInteger(final int column) {
		final Number n = getNumber(column);
		return n == null ? null : n.intValue();

	}

	@Override
	public Integer getInteger(final String column) {
		final Number n = getNumber(column);
		return n == null ? null : new Integer(n.intValue());

	}

	@Override
	public Double getDouble(final int column) {
		final Number n = getNumber(column);
		return n == null ? null : new Double(n.doubleValue());
	}

	@Override
	public Double getDouble(final String column) {
		final Number n = getNumber(column);
		return n == null ? null : new Double(n.doubleValue());
	}

	@Override
	public Date getTimestamp(final int column) {
		// todo
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getTimestamp(final String column) {
		// todo
		throw new UnsupportedOperationException();
	}

	public int getRowIndex() {
		return rowIndex;
	}

	@Override
	public Map<String, Object> getRowAsMap() throws DatasetException {
		final DatasetMetadata metaData = getDatasetMetadata();
		final Map<String, Object> row = new LinkedHashMap<String, Object>();
		for (int i = 0; i < metaData.getColumnCount(); i++) {
			final String columnName = metaData.getColumnName(i);
			final Object columnValue = getObject(i);
			row.put(columnName, columnValue);
		}
		return row;
	}

	@Override
	public List<Object> getRowAsList() {
		final DatasetMetadata meta = getDatasetMetadata();
		final List<Object> row = new ArrayList<Object>(meta.getColumnCount());
		for (int i = 0; i < meta.getColumnCount(); i++) {
			next();
			row.add(getObject(i));
		}
		return row;
	}

	protected void setRowIndex(final int rowIndex) {
		this.rowIndex = rowIndex;
	}

}
