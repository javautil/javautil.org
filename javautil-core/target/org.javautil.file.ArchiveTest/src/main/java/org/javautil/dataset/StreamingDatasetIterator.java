package org.javautil.dataset;

/**
 * Simplified base DatasetIterator class to be extended for per row basis
 * datasets. When the row accessed is not the current row, this dataset simply
 * fails with an IllegalStateException.
 * 
 * @author bcm
 */
public abstract class StreamingDatasetIterator extends AbstractDatasetIterator {

	protected abstract Object getObjectInternal(int columnIndex)
			throws DatasetException;

	@Override
	public Object getObject(final int columnIndex) throws DatasetException {
		final int columnCount = getDatasetMetadata().getColumnCount() - 1;
		if (columnIndex > columnCount) {
			throw new IllegalArgumentException("cannot get column at index "
					+ columnIndex + ", there are only " + columnCount
					+ " columns in the row");
		}
		if (columnIndex < 0) {
			throw new IllegalArgumentException("columnIndex values less "
					+ "than 0 are not allowed");
		}
		return getObjectInternal(columnIndex);
	}

	@Override
	public Object getObject(final String columnName) throws DatasetException {
		final int columnIndex = getDatasetMetadata().getColumnIndex(columnName);
		return getObjectInternal(columnIndex);
	}

	@Override
	public Object getValue(final int rowIndex, final int columnIndex) {
		if (getRowIndex() == -1 || getRowIndex() != rowIndex) {
			return getObject(columnIndex);
		} else {
			throw new UnsupportedOperationException("rowIndex " + rowIndex
					+ " is not allowed; it cannot be any index "
					+ "other than the current row");
		}
	}

	@Override
	public Object getValue(final int rowIndex, final String column) {
		if (getRowIndex() == -1 || getRowIndex() != rowIndex) {
			return getObject(column);
		} else {
			throw new UnsupportedOperationException("rowIndex " + rowIndex
					+ " is not allowed; it cannot be any index "
					+ "other than the current row");
		}
	}
}
