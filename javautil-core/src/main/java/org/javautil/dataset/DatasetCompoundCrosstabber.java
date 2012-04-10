package org.javautil.dataset;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.javautil.collections.ArrayComparator;
import org.javautil.collections.ListHelper;
import org.javautil.document.crosstab.CompoundCrosstabColumns;
import org.javautil.document.crosstab.CrosstabRow;
import org.javautil.text.SimpleDateFormatter;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 *         todo document todo share code with DatasetCrosstabber todo this is
 *         like DatasetCrosstabber but allows multiple columns to be considered
 *         as a single column header todo where is the test?
 */
public class DatasetCompoundCrosstabber<T> {

	private final Logger logger = Logger.getLogger(getClass().getName());
	private static final String newline = System.getProperty("line.separator");
	private Dataset dataSet;
	private CompoundCrosstabColumns crosstabColumns;
	private boolean crosstabComplete = false;
	private final TreeMap<Object[], Object[]> columnLabels = new TreeMap<Object[], Object[]>(
			new ArrayComparator());

	@SuppressWarnings("unchecked")
	/*
	 * todo consider a dataset rowid class
	 */
	private final TreeMap<Object[], CrosstabRow> rows = new TreeMap<Object[], CrosstabRow>(
			new ArrayComparator());

	private int rowCount = -1;

	private SimpleDateFormatter dateFormatter = new SimpleDateFormatter(
			"MM/dd/yyyy");

	public void setDataSet(final Dataset ds) {
		dataSet = ds;
	}

	private void crosstab() {
		if (!crosstabComplete) {
			crossTabDataSet();
			crosstabComplete = true;
		}
	}

	@Override
	public String toString() {
		final StringBuilder b = new StringBuilder();
		for (final CrosstabRow row : rows.values()) {
			b.append(row.toString());
		}
		return b.toString();
	}

	/**
	 * Crosstabs the data.
	 * 
	 * Iterates through the source data, Finds the corresponding row identified
	 * by the row id, Extracts the column identifier Adds the data using the
	 * column identifier as the key.
	 * 
	 * When does the densification occur?
	 * 
	 * @throws DatasetException
	 */
	protected final void crossTabDataSet() throws DatasetException {
		logger.debug("cross tabbing begins");

		rowCount = 0;

		if (dataSet == null) {
			throw new IllegalStateException("dataSet is null");
		}
		final DatasetIterator<?> dsi = dataSet.getDatasetIterator();

		while (dsi.next()) {
			if (logger.isDebugEnabled()) {
				if (rowCount % 100 == 0) {
					// logger.debug("input row " + rowCount);
				}
			}
			rowCount++;
			// get row identifier
			final CrosstabRow row = getCrosstabRow(dsi);
			final Object[] columnId = getColumnId(dsi);
			final Object[] cellValues = getCellValues(dsi);
			// Get column identifiers
			row.addCells(columnId, cellValues);
			columnLabels.put(columnId, columnId);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("crosstab rows.size " + rows.size());
		}
	}

	Object[] getCellValues(final DatasetIterator<?> dsi) {
		// logger.debug(crosstabColumns);

		final Object[] cellValues = new Object[crosstabColumns
				.getCellIdentifiers().size()];
		int cellIndex = 0;
		for (final String columnName : crosstabColumns.getCellIdentifiers()) {
			// logger.debug("Before getting object from DSI for Column " +
			// columnName);
			final Object val = dsi.getObject(columnName);
			// logger.debug("After getting object from DSI for Column " +
			// columnName);
			cellValues[cellIndex++] = val;

		}
		return cellValues;
	}

	// todo document

	public int getColumnCount() {
		// final ArrayList<Object> cols = new ArrayList<Object>();
		final int rowidCols = crosstabColumns.getRowIdentifiers().size();
		final int crosstabColumnCount = columnLabels.size();
		final int cellCount = crosstabColumns.getCellIdentifiers().size();
		final int retval = rowidCols + (crosstabColumnCount * cellCount);
		return retval;

	}

	private Object[] getColumnId(final DatasetIterator<?> dsi) {
		final int idLength = crosstabColumns.getColumnIdentifiers().size();

		final Object[] columnId = new Object[idLength];
		for (int i = 0; i < idLength; i++) {
			columnId[i] = dsi.getObject(crosstabColumns.getColumnIdentifiers()
					.get(i));
		}
		return columnId;
	}

	/**
	 * Convert the crosstab to a matrix
	 * 
	 * The width (number of columns) is defined by number of columns in the row
	 * identifier
	 * 
	 * @return
	 */
	public Object[][] getMatrix() {

		crosstab();

		final int columnCount = getColumnCount();
		final Object[][] matrix = new Object[rows.size()][];

		int rowIndex = 0;

		for (final CrosstabRow srcRow : rows.values()) {
			final Object[] row = matrix[rowIndex] = new Object[columnCount];

			int colIndex = 0;
			while (colIndex < srcRow.getRowId().length) {
				row[colIndex] = srcRow.getRowId()[colIndex];
				colIndex++;
			}
			for (final Object[] colId : columnLabels.values()) {
				// if (logger.isDebugEnabled()) {
				// String x = "row " + rowIndex + " " + " colId " +
				// colId.toString();
				// logger.debug(x);
				// }
				final Object[] cells = srcRow.getCellsByColumnId(colId);
				if (cells == null) {
					colIndex += crosstabColumns.getCellIdentifiers().size();
				} else {
					for (final Object cell : cells) {
						row[colIndex++] = cell;
					}
				}
			}

			if (logger.isDebugEnabled()) {
				// logger.debug("row " + rowIndex + " "
				// + ArrayHelper.asString(row));
			}
			matrix[rowIndex] = row;
			// System.out.println("row " + ArrayHelper.asString(row));
			// System.out.println("srcRow " + srcRow);
			rowIndex++;
		}
		return matrix;

	}

	private CrosstabRow getCrosstabRow(final DatasetIterator<?> dsi) {
		final Object[] rowIdentifier = new Object[crosstabColumns
				.getRowIdentifiers().size()];
		int i = 0;
		for (final String colName : crosstabColumns.getRowIdentifiers()) {
			final Object colVal = dsi.getObject(colName);
			rowIdentifier[i++] = colVal;
		}
		CrosstabRow row = rows.get(rowIdentifier);
		if (row == null) {
			row = new CrosstabRow(rowIdentifier);
			rows.put(rowIdentifier, row);
		}
		return row;
	}

	// todo merge this with DatasetCrosstab
	public AbstractDataset getDataSet() {
		crosstab();
		final DatasetIterator<?> dsi = dataSet.getDatasetIterator();
		dsi.getDatasetMetadata();
		final MutableDatasetMetadata meta = dsi.getDatasetMetadata()
				.getMutable();

		final DatasetMetadataImpl newMeta = new DatasetMetadataImpl();
		int index = 0;
		for (final String colName : crosstabColumns.getRowIdentifiers()) {
			final Integer sourceIndex = meta.getColumnIndex(colName);
			final ColumnMetadata src = meta.getColumnMetaData(sourceIndex);
			final ColumnMetadata cmd = src.getCopy();
			cmd.setColumnIndex(index);
			newMeta.addColumn(cmd);
			index++;
		}
		if (logger.isDebugEnabled()) {
			// logger.debug(newline + "after row identifiers " + newline
			// + newMeta.toString());
			// logger.debug("columnLabels.size " + columnLabels.size());
			// logger.debug("cellIdentifiers "
			// + crosstabColumns.getCellIdentifiers());
		}
		// todo jjs this is broken, creates a metadata which is larger than the
		// output result as
		// an entry is made for every column name
		for (final Object[] columns : columnLabels.keySet()) {
			for (final Object columnLabel : columns) {

				for (final String colName : crosstabColumns
						.getCellIdentifiers()) {
					final Integer sourceIndex = meta.getColumnIndex(colName);
					final ColumnMetadata src = meta
							.getColumnMetaData(sourceIndex);
					final ColumnMetadata cmd = src.getCopy();
					cmd.setColumnIndex(index);
					String groupName = null;
					if (logger.isDebugEnabled()) {
						// logger.debug("columnLabel class "
						// + columnLabel.getClass().getName());
					}

					if (columnLabel instanceof Date) {
						groupName = dateFormatter.format((Date) columnLabel);
					} else if (columnLabel instanceof Number) {
						groupName = columnLabel.toString();
					} else {
						groupName = columnLabel.toString();
					}
					cmd.setGroupName(groupName);
					newMeta.addColumn(cmd);
					index++;
				}
			}
		}
		if (logger.isDebugEnabled()) {
			// logger.debug("after column labels " + newline +
			// newMeta.toString());
		}

		final MatrixDataset ds = new MatrixDataset(newMeta);
		ds.setData(getMatrixLists());
		if (logger.isDebugEnabled()) {
			// logger.debug("ds" + newline + ds.toString());
		}

		return ds;
	}

	public void setDateFormat(final String dateFormat) {
		dateFormatter = new SimpleDateFormatter(dateFormat);
	}

	public DatasetMetadata getMetaData() {
		final DatasetMetadata meta = new DatasetMetadataImpl();

		return meta;
	}

	// // todo move to CollectionsHelper
	//
	// List<Object> toList(Object... o) {
	// ArrayList<Object> al = new ArrayList<Object>(o.length);
	// for (int i = 0; i < o.length; i++) {
	// al.add(o[i]);
	// }
	// return al;
	// }

	@SuppressWarnings("unchecked")
	public ArrayList<List<Object>> getMatrixLists() {
		final Object[][] matrix = getMatrix();
		final ArrayList<List<Object>> rows = new ArrayList<List<Object>>(
				matrix.length);

		for (final Object[] element : matrix) {

			rows.add(ListHelper.toList(element));
		}
		return rows;

	}

	public CompoundCrosstabColumns getCrosstabColumns() {
		return crosstabColumns;
	}

	public void setCrosstabColumns(final CompoundCrosstabColumns crosstabColumns) {
		this.crosstabColumns = crosstabColumns;
	}

}
