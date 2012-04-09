package org.javautil.dataset;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 *         todo jjs review this ? hasFooterValues crap
 * 
 */
public class MatrixDatasetIterator extends AbstractDatasetIterator implements
		DatasetIterator {

	private final MatrixDataset matrix;

	// private final int rowCount;

	private final boolean hasFooterValues;

	private final int footerRowIndex;

	public MatrixDatasetIterator(final MatrixDataset matrix) {
		this.matrix = matrix;
		hasFooterValues = matrix.hasFooterValues();
		final int rowCount = hasFooterValues ? matrix.getRowCount() + 1
				: matrix.getRowCount();
		footerRowIndex = hasFooterValues ? rowCount - 1 : -1;
	}

	@Override
	public DatasetMetadata getDatasetMetadata() {
		return matrix.getMetadata();
	}

	@Override
	public int getRowCount() {
		return matrix.getRowCount();
	}

	// todo this needs to be reviewed
	@Override
	public Object getValue(final int rowIndex, final int columnIndex) {
		Object retval = null;
		if (hasFooterValues && rowIndex == footerRowIndex) {
			retval = matrix.getFooterValues()[columnIndex];
		} else {
			retval = matrix.getValue(rowIndex, columnIndex);
		}
		return retval;
	}

	@Override
	public Object getValue(final int rowIndex, final String columnName) {
		final int columnIndex = getDatasetMetadata().getColumnIndex(columnName);
		return getValue(rowIndex, columnIndex);
	}

}
