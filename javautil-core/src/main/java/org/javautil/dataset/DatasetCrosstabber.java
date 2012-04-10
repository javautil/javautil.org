package org.javautil.dataset;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.javautil.collections.ListHelper;
import org.javautil.collections.StaticArrayComparator;
import org.javautil.document.crosstab.CrosstabColumns;
import org.javautil.document.crosstab.CrosstabRow;
import org.javautil.lang.ArrayHelper;
import org.javautil.text.SimpleDateFormatter;

/**
 * 
 * @author jjs @ dbexperts.com
 * 
 * 
 *         todo document
 */
public class DatasetCrosstabber {

	private final Logger logger = Logger.getLogger(getClass().getName());
	private static final String newline = System.getProperty("line.separator");
	private Dataset dataSet;
	private CrosstabColumns crosstabColumns;
	private boolean crosstabComplete = false;
	private final TreeMap<Object, Object> columnLabels = new TreeMap<Object, Object>();

	@SuppressWarnings("unchecked")
	/*
	 * todo consider a dataset rowid class
	 */
	private final TreeMap<Object[], CrosstabRow> rows = new TreeMap<Object[], CrosstabRow>(
			new StaticArrayComparator());

	private int rowCount = -1;

	private SimpleDateFormatter dateFormatter;

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

	protected final void crossTabDataSet() throws DatasetException {
		logger.debug("cross tabbing begins");

		rowCount = 0;

		if (dataSet == null) {
			throw new IllegalStateException("dataSet is null");
		}
		final DatasetIterator<?> dsi = dataSet.getDatasetIterator();

		while (dsi.next()) {
			if (logger.isDebugEnabled()) {
				logger.debug("input row " + rowCount);
			}
			rowCount++;
			// get row identifier
			final CrosstabRow row = getCrosstabRow(dsi);
			final Object columnId = getColumnId(dsi);
			final Object[] cellValues = getCellValues(dsi);

			// Get column identifiers
			row.addCells(columnId, cellValues);
		}
		if (logger.isDebugEnabled()) {
			logger.debug(toString());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("crosstab rows.size " + rows.size());
		}

	}

	Object[] getCellValues(final DatasetIterator<?> dsi) {
		final Object[] cellValues = new Object[crosstabColumns
				.getCellIdentifiers().size()];

		int cellIndex = 0;
		for (final String columnName : crosstabColumns.getCellIdentifiers()) {

			final Object val = dsi.getObject(columnName);
			cellValues[cellIndex++] = val;
			if (logger.isTraceEnabled()) {
				logger.trace(" cellIndex " + cellIndex + " " + val);
			}

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

	private Object getColumnId(final DatasetIterator<?> dsi) {
		final String columnIdentifier = crosstabColumns.getColumnIdentifier();
		final Object obj = dsi.getObject(columnIdentifier);
		logger.debug("getting columnId for " + obj);
		Object columnId = columnLabels.get(obj);

		if (columnId == null) {
			columnLabels.put(obj, obj);
			columnId = obj;
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
			for (final Object colId : columnLabels.values()) {
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
				logger.debug("row " + rowIndex + " "
						+ ArrayHelper.asString(row));
			}
			matrix[rowIndex] = row;
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
			final String message = "meta after column id " + newline
					+ newMeta.toString();
			logger.debug(message);
		}

		for (final Object columnLabel : columnLabels.keySet()) {
			logger.debug("adding group '" + columnLabel + "'");
			for (final String colName : crosstabColumns.getCellIdentifiers()) {
				final Integer sourceIndex = meta.getColumnIndex(colName);
				final ColumnMetadata src = meta.getColumnMetaData(sourceIndex);
				final ColumnMetadata cmd = src.getCopy();
				cmd.setColumnIndex(index);
				String groupName = null;
				if (columnLabel instanceof Date) {
					groupName = getDateFormatter().format((Date) columnLabel);
				} else
				// TODO do we need a number formatter? it does the same
				if (columnLabel instanceof Number) {
					groupName = columnLabel.toString();
				} else {
					groupName = columnLabel.toString();
				}
				logger.debug("setting meta group name to " + groupName);
				cmd.setGroupName(groupName);
				newMeta.addColumn(cmd);
				if (logger.isDebugEnabled()) {
					logger.debug(newMeta.toString());
				}
				index++;
			}
			if (logger.isDebugEnabled()) {
				logger.debug(newline + newMeta.toString());
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug(newline + newMeta.toString());
		}

		final MatrixDataset ds = new MatrixDataset(newMeta);
		ds.setData(getMatrixLists());
		if (logger.isDebugEnabled()) {
			logger.debug("ds" + newline + ds.toString());
		}

		return ds;
	}

	private SimpleDateFormatter getDateFormatter() {
		if (dateFormatter == null) {
			dateFormatter = new SimpleDateFormatter("yyyy-MM-dd");
		}
		return dateFormatter;
	}

	public void setDateFormat(final String dateFormat) {
		dateFormatter = new SimpleDateFormatter(dateFormat);
	}

	public DatasetMetadata getMetaData() {
		final DatasetMetadata meta = new DatasetMetadataImpl();

		return meta;
	}

	// // todo move to CollectionsHelper
	// @SuppressWarnings("unchecked")
	// List<Object> toList(Object... o) {
	// ArrayList al = new ArrayList(o.length);
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

	public CrosstabColumns getCrosstabColumns() {
		return crosstabColumns;
	}

	public void setCrosstabColumns(final CrosstabColumns crosstabColumns) {
		this.crosstabColumns = crosstabColumns;
	}

}
