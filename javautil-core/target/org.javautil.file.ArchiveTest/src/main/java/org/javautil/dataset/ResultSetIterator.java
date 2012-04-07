package org.javautil.dataset;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
public class ResultSetIterator implements DatasetIterator {

	private final ResultSet rset;

	private final ResultSetDataset resultSetWrapper;

	public ResultSetIterator(final ResultSetDataset resultSetWrapper,
			final ResultSet _rset) {
		this.resultSetWrapper = resultSetWrapper;
		this.rset = _rset;
	}

	@Override
	public Date getDate(final int column) throws DatasetException {
		try {
			return rset.getDate(column + 1);
		} catch (final SQLException e) {
			throw new DatasetException(e);
		}
	}

	@Override
	public Date getDate(final String columnName) throws DatasetException {
		try {
			return rset.getDate(columnName);
		} catch (final SQLException e) {
			throw new DatasetException(e);
		}
	}

	@Override
	public Double getDouble(final int column) throws DatasetException {
		Double retval = null;

		try {
			final double d = rset.getDouble(column + 1);
			retval = rset.wasNull() ? null : new Double(d);
		} catch (final SQLException e) {
			throw new DatasetException(e);
		}
		return retval;
	}

	@Override
	public Double getDouble(final String column) throws DatasetException {
		try {
			return rset.getDouble(column);
		} catch (final SQLException e) {
			throw new DatasetException(e);
		}
	}

	@Override
	public Integer getInteger(final int column) throws DatasetException {
		try {
			return rset.getInt(column + 1);
		} catch (final SQLException e) {
			throw new DatasetException(e);
		}
	}

	@Override
	public Integer getInteger(final String column) throws DatasetException {
		try {
			return rset.getInt(column);
		} catch (final SQLException e) {
			throw new DatasetException(e);
		}
	}

	@Override
	public Object getObject(final int columnIndex) throws DatasetException {
		try {
			return rset.getObject(columnIndex + 1);
		} catch (final SQLException e) {
			throw new DatasetException(e);
		}
	}

	@Override
	public Object getObject(final String column) throws DatasetException {
		try {
			return rset.getObject(column);
		} catch (final SQLException e) {
			throw new DatasetException(e);
		}
	}

	@Override
	public String getString(final int column) throws DatasetException {
		try {
			return rset.getString(column + 1);
		} catch (final SQLException e) {
			throw new DatasetException(e);
		}
	}

	@Override
	public String getString(final String column) throws DatasetException {
		try {
			return rset.getString(column);
		} catch (final SQLException e) {
			throw new DatasetException(e);
		}
	}

	@Override
	public Timestamp getTimestamp(final int column) throws DatasetException {
		try {
			return rset.getTimestamp(column + 1);
		} catch (final SQLException e) {
			throw new DatasetException(e);
		}
	}

	@Override
	public Timestamp getTimestamp(final String column) throws DatasetException {
		try {
			return rset.getTimestamp(column);
		} catch (final SQLException e) {
			throw new DatasetException(e);
		}
	}

	@Override
	public boolean hasNext() throws DatasetException {
		throw new DatasetException("Not implemented in java.sql.ResultSet");
	}

	@Override
	public boolean next() throws DatasetException {
		try {
			return rset.next();
		} catch (final SQLException e) {
			throw new DatasetException(e);
		}
	}

	public boolean wasNull() throws DatasetException {
		try {
			return rset.wasNull();
		} catch (final SQLException e) {
			throw new DatasetException(e);
		}
	}

	@Override
	public DatasetMetadata getDatasetMetadata() {
		return resultSetWrapper.getMetadata();
	}

	@Override
	public Number getNumber(final int columnIndex) throws DatasetException {
		Object o;
		try {
			o = rset.getObject(columnIndex + 1);
		} catch (final SQLException e) {
			throw new DatasetException(e);
		}
		if (o != null && !(o instanceof Number)) {
			throw new IllegalArgumentException("column " + columnIndex
					+ " is not an instance of Number ");
		}
		return (Number) o;
	}

	@Override
	public Number getNumber(final String column) throws DatasetException {
		Object o;
		try {
			o = rset.getObject(column);
		} catch (final SQLException e) {
			throw new DatasetException(e);
		}
		if (o != null && !(o instanceof Number)) {
			throw new IllegalArgumentException("column " + column
					+ " is not an instance of Number ");
		}
		return (Number) o;
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
			row.add(getObject(i));
		}
		return row;
	}

	@Override
	public int getRowCount() throws DatasetException {
		throw new UnsupportedOperationException();
	}
}
