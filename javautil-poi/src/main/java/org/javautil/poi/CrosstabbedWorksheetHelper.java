package org.javautil.poi;

import java.awt.Color;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.javautil.dataset.ColumnMetadata;
import org.javautil.dataset.ColumnMetadataGrouper;
import org.javautil.dataset.Dataset;
import org.javautil.dataset.DatasetIterator;
import org.javautil.dataset.DatasetMetadata;
import org.javautil.document.style.HorizontalAlignment;
import org.javautil.document.style.Style;
import org.javautil.document.style.VerticalAlignment;
import org.javautil.poi.style.HSSFCellStyleFactory;
import org.javautil.workbook.DataRange;

/**
 * 
 * @author jjs
 * 
 *         TODO this should be a renderer, its not much of a helper. TOOD figure
 *         a renderer interface once again
 * @deprecated use CrosstabWorksheetRenderer
 */
@Deprecated
public class CrosstabbedWorksheetHelper extends WorksheetHelper {
	private final Logger logger = Logger.getLogger(getClass());

	private int[] sourceRowidColumnIndexes;

	/**
	 * Should each column have a single cell heading.
	 * 
	 * If true each column in the crosstabbed data has a single cell that is the
	 * heading cell.
	 * 
	 * If false a separate cell is created for the whole group and rendered
	 * above the group and spanned.
	 */
	private boolean singleCellCrosstabColumnHeading;

	public CrosstabbedWorksheetHelper(final HSSFSheet sheet,
			final HSSFCellStyleFactory styleFactory) {
		super(sheet, styleFactory);
	}

	/**
	 * The renderer needs to be told the order of the columns to be rendered
	 * within each group.
	 * 
	 * These are the nonRowIdentifyingColumns. The columns may be virtual
	 * columns or database columns.
	 * 
	 * After rendering the row identifying columns each group is processed in
	 * sequence. Within each group the columns are emitted in the order
	 * specified by the List of nonRowIdentifyingColumns.
	 * 
	 * TODO rewrite this There for columns that are present in the crosstabbed
	 * dataset may be dropped during the emission process. New columns, virtual
	 * columns may be injected. The order in which columns are emitted is not
	 * dependent on the the order in which they are present in the crosstabbed
	 * data.
	 * 
	 * If a list of columns is presented all of the columns which precede
	 * grouped columns must precede any grouped column.
	 * 
	 * 
	 * @param dataset
	 * @param startingRow
	 *            the index of the first row in the output worksheet relative 0.
	 * @param startingColumn
	 *            the index of the first column in the output worksheet relative
	 *            0.
	 * @param rowidColumnCount
	 *            number of columns prior to the crosstabbed data.
	 * @param nonRowIdentifyingColumns
	 *            the names of the columns that are repeating in the crosstabbed
	 *            portion of the region may contain virtual columns that are
	 *            formulas to be injected ??
	 * @param columnMeta
	 *            must be an entry by column name for every column name in
	 *            nonRowIndentifyingColumns
	 * @return
	 */
	public DataRange emitCrosstabRegion(final Dataset dataset,
			final int startingRow, final int startingColumn,
			final List<String> rowIdentifyingColumns,
			final List<String> nonRowIdentifyingColumns,
			final Collection<ColumnMetadata> columnMeta) {
		//
		setSourceRowidColumnIndexes(rowIdentifyingColumns,
				dataset.getMetadata());
		final ColumnMetadataGrouper grouper = new ColumnMetadataGrouper();
		final String[] groupNames = grouper.getGroupNames(dataset.getMetadata()
				.getColumnMetadata());

		//
		Map<String, ColumnMetadata> metaByColumnName = null;
		if (columnMeta != null) {
			metaByColumnName = new HashMap<String, ColumnMetadata>();
			for (final ColumnMetadata meta : columnMeta) {
				metaByColumnName.put(meta.getColumnName(), meta);
			}
		}
		//
		// define range
		//
		final DataRange range = new DataRange();
		if (logger.isDebugEnabled()) {
			logger.debug("Crosstab, Dataset Metadata has "
					+ dataset.getMetadata().getColumnCount() + " Columns");
		}
		range.setFirstRow(startingRow);
		range.setFirstColumn(startingColumn);
		range.setDataBeginsRow(startingRow + 1);

		range.setLastColumn(startingColumn + rowIdentifyingColumns.size()
				+ (groupNames.length * nonRowIdentifyingColumns.size()) - 1);

		if (logger.isDebugEnabled()) {
			logger.debug("Before Emitting Crosstab, Dataset Metadata has "
					+ dataset.getMetadata().getColumnCount() + " Columns");
		}
		range.setLastRow(startingRow
				+ dataset.getDatasetIterator().getRowCount() + 2);
		range.setFooterBeginsRow(startingRow
				+ dataset.getDatasetIterator().getRowCount() + 2);
		//
		// emit headers
		//

		emitHeadersForRowIdentifiers(dataset, getStyleFactory()
				.getBaseHeaderStyle(), startingRow, startingColumn);

		if (singleCellCrosstabColumnHeading) {
			// emit a spanning column with the group name above the group
			emitCrosstabHeadersForCrosstabData(dataset, groupNames,
					nonRowIdentifyingColumns, metaByColumnName,
					getStyleFactory().getBaseHeaderStyle(), startingRow,
					(sourceRowidColumnIndexes.length));
		} else {
			emitCrosstabHeadersForCrosstabDataWithSpannedHeaders(dataset,
					groupNames, nonRowIdentifyingColumns, metaByColumnName,
					getStyleFactory().getBaseHeaderStyle(), startingRow,
					(sourceRowidColumnIndexes.length));

		}

		//
		// emit data
		//

		final int adjustedStartingRow = isSingleCellCrosstabColumnHeading() ? startingRow + 1
				: startingRow + 2;
		final int lastDataRowIndex = emitCrosstabbedData(dataset, dataset
				.getMetadata().getColumnMetadata(), getStyleFactory()
				.getBaseDataStyle(), adjustedStartingRow, startingColumn,
				rowIdentifyingColumns, nonRowIdentifyingColumns, columnMeta,
				metaByColumnName, groupNames);

		// emit summary

		emitCrosstabbedSummary(dataset, dataset.getMetadata()
				.getColumnMetadata(), lastDataRowIndex + 1, startingColumn,
				startingRow + 1, lastDataRowIndex, getStyleFactory()
						.getBaseFooterStyle(), rowIdentifyingColumns,
				nonRowIdentifyingColumns, columnMeta, metaByColumnName,
				groupNames);

		return range;
	}

	protected void emitCrosstabbedDataValidateArgs(final Dataset dataset,
			final List<ColumnMetadata> _columns, final Style baseStyle,
			final int startingRow, final int startingColumn,
			final List<String> rowIdentifyingColumns) {
		if (dataset == null) {
			throw new IllegalArgumentException("dataset is null");
		}
		if (_columns == null) {
			throw new IllegalArgumentException("columns is null");
		}
		if (baseStyle == null) {
			throw new IllegalArgumentException("baseStyle is null");
		}
	}

	/**
	 * Renders the dataset to the specified region.
	 * 
	 * Creates a freeze frame in the first row after the header after the
	 * rowidColumnCount
	 * 
	 * @param dataset
	 *            The crosstabbed dataset
	 * @param columns
	 *            The Metadata for the crosstabbed columns
	 * @param baseStyle
	 * @param startingRow
	 *            The starting row in the worksheet for emitting the data
	 * @param startingColumn
	 *            The starting column in the worksheet for emitting the data
	 * @param rowIdentifyingColumns
	 *            The number of columns that make up the row identifier.
	 * @param nonIdentifyingRowColumns
	 *            is a list of the order in which the columns should be emitted
	 *            in each crosstabbed group
	 * @param nonRowIdentifyingColumnMetadata
	 *            The metadata for the crosstabbed columns
	 * @param metaMapByColumn
	 *            A HashMap of the Column Metadata for the crosstab columns
	 *            keyed by the Column Name.
	 * @param groupNames
	 *            A List of all the crosstab groups
	 * @return
	 */
	// todo review
	protected int emitCrosstabbedData(final Dataset dataset,
			final List<ColumnMetadata> columns, final Style baseStyle,
			final int startingRow, final int startingColumn,
			final List<String> rowIdentifyingColumns,
			final List<String> nonRowIdentifyingColumns,
			final Collection<ColumnMetadata> nonRowIdentifyingColumnMetadata,
			final Map<String, ColumnMetadata> metaMapByColumn,
			final String[] groupNames) {

		if (logger.isDebugEnabled()) {
			for (final ColumnMetadata col : metaMapByColumn.values()) {
				logger.debug(col);
			}
		}
		// validate arguments
		emitCrosstabbedDataValidateArgs(dataset, columns, baseStyle,
				startingRow, startingColumn, rowIdentifyingColumns);

		final DatasetIterator datasetIterator = dataset.getDatasetIterator();
		if (logger.isTraceEnabled()) {
			logger.trace(newline + ColumnMetadata.toString(columns));
		}
		getSheet().createFreezePane(
				startingColumn + rowIdentifyingColumns.size(), startingRow);
		int rowIndex = startingRow;
		while (datasetIterator.next()) {
			int datasetColumnIndex = startingColumn;
			int sheetColumnIndex = startingColumn;
			// add the data from the dataset in the order of the row identifying
			// columns
			for (final String rowIdentifyingColumn : rowIdentifyingColumns) {
				final ColumnMetadata column = columns.get(datasetColumnIndex);
				final Style cellStyle = getStyle(baseStyle,
						column.getExcelFormat(),
						column.getHorizontalAlignment());
				if (column.getWorkbookFormula() == null) {
					final Object data = datasetIterator
							.getObject(datasetColumnIndex);
					addCell(rowIndex, sheetColumnIndex, data, cellStyle);
				}
				datasetColumnIndex++;
				sheetColumnIndex++;
			}
			int groupIndex = 0;
			for (final String groupName : groupNames) {
				ColumnMetadata column;
				//
				// Map, keyed by column name with value of the index of the
				// column the crosstabbed
				// data for a given column name and group
				//
				// Map<String, Integer> columnIndexByName =
				// ColumnMetadataGrouper.getColumnNameIndexMap(dataset,
				// sheetColumnIndex,
				// nonRowIdentifyingColumnMetadata, groupName);

				final Map<String, Integer> columnIndexByName = getColumnNameIndexMap(
						dataset, sheetColumnIndex,
						nonRowIdentifyingColumnMetadata, groupName,
						nonRowIdentifyingColumns);
				// nonRowIdentifying columns is a list of the order in which the
				// columns should be emitted in each crosstabbed group
				for (final String nonRowIdentifyingColumn : nonRowIdentifyingColumns) {
					final List<Integer> columnIndexes = (List<Integer>) datasetIterator
							.getDatasetMetadata().getColumnIndexes(
									nonRowIdentifyingColumn);
					HSSFCell cell = null;
					if (columnIndexes != null) { // found the specified column
						// in the crosstabbed group
						column = dataset.getMetadata().getColumnMetaData(
								columnIndexes.get(groupIndex));
						final Object data = datasetIterator.getObject(column
								.getColumnIndex());
						cell = addCell(rowIndex, sheetColumnIndex, data, null);
					} else { // this is a formula column, not in the input
						// crosstabbed data
						column = metaMapByColumn.get(nonRowIdentifyingColumn);
						column.setGroupName(groupName);
						cell = addFormulaCell(column.getWorkbookFormula(),
								columnIndexByName, column.getColumnName(),
								rowIndex, sheetColumnIndex);
					}

					final Style cellStyle = getStyle(baseStyle,
							column.getExcelFormat(),
							column.getHorizontalAlignment());
					if ((groupIndex + 1) % 2 == 0) {
						// TODO what the hell is this?? s/b coming from
						// StyleFactory
						final Color color = new Color(192, 192, 192);
						cellStyle.setBackgroundColor(color);
					}
					cell.setCellStyle(getStyleFactory().getHSSFCellStyle(
							cellStyle));

					sheetColumnIndex++;
				}
				groupIndex++;
			}
			rowIndex++;
		}
		return rowIndex - 1;
	}

	private Map<String, Integer> getColumnNameIndexMap(final Dataset dataset,
			final int sheetColumnIndex,
			final Collection<ColumnMetadata> nonRowIdentifyingColumnMetadata,
			final String groupName, final List<String> nonRowIdentifyingColumns) {

		final LinkedHashMap<String, Integer> columnIndexByName = ColumnMetadataGrouper
				.getColumnNameIndexMap(dataset, sheetColumnIndex,
						nonRowIdentifyingColumnMetadata, groupName);

		final LinkedHashMap<String, Integer> columnIndexByNameWithInferred = new LinkedHashMap<String, Integer>();

		// compute the minimum index
		Integer minimumIndex = null;
		for (final Integer index : columnIndexByName.values()) {
			if (minimumIndex == null || minimumIndex.compareTo(index) > 1) {
				minimumIndex = index;
			}
		}

		//
		int columnIndex = minimumIndex;
		for (final String columnName : nonRowIdentifyingColumns) {
			columnIndexByNameWithInferred.put(columnName, columnIndex);
			columnIndex++;
		}

		return columnIndexByNameWithInferred;
	}

	/**
	 * Renders the Summary Row for the Worksheet
	 * 
	 * @param columns
	 *            The Metadata for the crosstabbed columns
	 * @param baseStyle
	 * @param startingRow
	 *            The starting row in the worksheet for emitting the data
	 * @param startingColumn
	 *            The starting column in the worksheet for emitting the data
	 * @param rowIdentifyingColumns
	 *            The number of columns that make up the row identifier.
	 * @param nonIdentifyingRowColumns
	 *            is a list of the order in which the columns should be emitted
	 *            in each crosstabbed group
	 * @param nonRowIdentifyingColumnMetadata
	 *            The metadata for the crosstabbed columns
	 * @param metaMapByColumn
	 *            A HashMap of the Column Metadata for the crosstab columns
	 *            keyed by the Column Name.
	 * @param groupNames
	 *            A List of all the crosstab groups
	 * @return
	 */
	// todo review

	private void emitCrosstabbedSummary(final Dataset dataset,
			final List<ColumnMetadata> columns, final int rowIndex,
			final int columnIndex, final int firstDataRowIndex,
			final int lastDataRowIndex, final Style baseStyle,
			final List<String> rowIdentifyingColumns,
			final List<String> nonRowIdentifyingColumns,
			final Collection<ColumnMetadata> nonRowIdentifyingColumnMetadata,
			final Map<String, ColumnMetadata> metaMapByColumn,
			final String[] groupNames) {
		int currentColumn = columnIndex;
		checkColumns(columns);
		ColumnMetadata column;
		for (final int rowidColumnIndex : sourceRowidColumnIndexes) {
			column = dataset.getMetadata().getColumnMetaData(rowidColumnIndex);
			final Style cellStyle = getStyle(baseStyle,
					column.getExcelFormat(), column.getHorizontalAlignment());
			if (column.getAggregateFunction() != null) {
				if (logger.isDebugEnabled()) {
					logger.debug("emitting aggregate function "
							+ column.getAggregateFunction() + " at column "
							+ columnIndex);
				}
				addColumnFormulaCell(rowIndex, currentColumn,
						firstDataRowIndex, lastDataRowIndex, cellStyle,
						column.getAggregateFunction());
			}
			currentColumn++;
		}
		int groupIndex = 0;
		for (final String groupName : groupNames) {
			final Map<String, Integer> columnIndexByName = ColumnMetadataGrouper
					.getColumnNameIndexMap(dataset, currentColumn,
							nonRowIdentifyingColumnMetadata, groupName);
			for (final String nonRowidColName : nonRowIdentifyingColumns) {
				Style cellStyle = null;
				final List<Integer> columnIndexes = (List<Integer>) dataset
						.getMetadata().getColumnIndexes(nonRowidColName);
				if (columnIndexes != null) {
					column = dataset.getMetadata().getColumnMetaData(
							columnIndexes.get(groupIndex));
				} else {
					column = metaMapByColumn.get(nonRowidColName);
					if (column == null) {
						throw new java.lang.IllegalStateException(
								"No Metadata found " + " for column "
										+ nonRowidColName);
					}
				}
				cellStyle = getStyle(baseStyle, column.getExcelFormat(),
						column.getHorizontalAlignment());
				column.setGroupName(groupName);
				if (column.getAggregateFunction() != null) {
					if (logger.isDebugEnabled()) {
						logger.debug("emitting aggregate function "
								+ column.getAggregateFunction() + " at column "
								+ columnIndex);
					}
					addColumnFormulaCell(rowIndex, currentColumn,
							firstDataRowIndex, lastDataRowIndex, cellStyle,
							column.getAggregateFunction());

				} else if (column.getWorkbookFormula() != null) {
					final HSSFCell cell = addFormulaCell(
							column.getWorkbookFormula(), columnIndexByName,
							column.getColumnName(), rowIndex, currentColumn);
					cell.setCellStyle(getStyleFactory().getHSSFCellStyle(
							cellStyle));
				}
				currentColumn++;
			}
			groupIndex++;
		}

	}

	/**
	 * TODO this should be deprecated
	 * 
	 * @param dataset
	 * @param startingRow
	 * @param startingColumn
	 * @param rowidColumnCount
	 * @return
	 */
	public DataRange emitCrosstabRegion(final Dataset dataset,
			final int startingRow, final int startingColumn,
			final int rowidColumnCount) {
		return emitCrosstabRegion(dataset, startingRow, startingColumn, null,
				null, null);
	}

	/**
	 * todo this started off the same as emitHeaders see what the difference is
	 * 
	 * @param columns
	 * @param baseStyle
	 * @param startingRow
	 * @param startingColumn
	 * @param nonRowIdentifyingColumns
	 * @param metadataByColumnName
	 */
	public void emitCrosstabHeaders(final List<ColumnMetadata> columns,
			final Style baseStyle, final int startingRow,
			final int startingColumn,
			final List<String> nonRowIdentifyingColumns,
			final Map<String, ColumnMetadata> metadataByColumnName) {

		if (baseStyle == null) {
			throw new IllegalArgumentException("baseStyle is null");
		}

		final int rowIndex = startingRow;
		int columnIndex = startingColumn;
		int entry = 0;
		for (final ColumnMetadata column : columns) {

			Style cellStyle = null;
			String heading = "";
			if (column == null) {
				logger.warn("null column at entry " + entry);
				cellStyle = baseStyle;
			} else {

				if (logger.isTraceEnabled()) {
					logger.trace("horizontal alignment is null for " + column);
				}

				cellStyle = getStyle(baseStyle, null,
						column.getHorizontalAlignment());
				if (column.getGroupName() != null) {
					heading = column.getGroupName() + "\n"
							+ column.getHeading();
				} else {
					heading = column.getHeading();
				}
			}
			if (heading == null) {
				logger.warn("heading is null for " + column);
			} else {
				if (heading.indexOf("\\n") > -1) {
					heading = heading.replace("\\n", "\n");
				}
			}
			if (heading.indexOf('\n') > -1) {
				cellStyle = addWordWrap(cellStyle);
			}

			addCell(rowIndex, columnIndex, heading, cellStyle);
			if (column != null && column.getColumnDisplaySize() != null) {
				setColumnWidth(columnIndex, column.getColumnDisplaySize()
						.intValue());
			}
			columnIndex++;
			entry++;
		}

	}

	/**
	 * Used when the group name and the column name should be in one cell.
	 * 
	 * @param column
	 * @return
	 */
	private String getMergedColumnHeading(final ColumnMetadata column) {
		String heading = getNonmergedColumnHeading(column);

		String columnHeading = column.getHeading();
		if (columnHeading == null) {
			columnHeading = column.getColumnName();
		}
		if (columnHeading == null) {
			throw new IllegalStateException("no heading or columnName for "
					+ column);
		}
		if (column.getGroupName() != null) {
			heading = column.getGroupName() + "\n" + columnHeading;
		}
		if (heading == null) {
			heading = columnHeading;
		} else {
			if (heading.indexOf("\\n") > -1) {
				heading = heading.replace("\\n", "\n");
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("returned heading '" + heading + "'");
		}
		return heading;
	}

	/**
	 * Used when the group name and the column name should be in one cell. this
	 * should be called from getMergedColumnHeading
	 * 
	 * @param column
	 * @return
	 */
	private String getNonmergedColumnHeading(final ColumnMetadata column) {
		String heading = null;

		String columnHeading = column.getHeading();
		if (columnHeading == null) {
			columnHeading = column.getColumnName();
		}
		if (columnHeading == null) {
			throw new IllegalStateException("no heading or columnName for "
					+ column);
		}

		if (heading == null) {
			heading = columnHeading;
		} else {
			if (heading.indexOf("\\n") > -1) {
				heading = heading.replace("\\n", "\n");
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("returned heading '" + heading + "'");
		}
		return heading;
	}

	/**
	 * 
	 * @param column
	 * @param baseStyle
	 * @param rowIndex
	 * @param columnIndex
	 * @param numberOfColumnsInGroup
	 * @param groupEntryIndex
	 *            which entry this is in the group base 0
	 */
	public void addGroupHeaderSpanCell(final ColumnMetadata column,
			final Style baseStyle, final int rowIndex, final int columnIndex,
			final int numberOfColumnsInGroup, final int groupEntryIndex) {
		Style cellStyle = null;
		String heading = "";
		if (logger.isTraceEnabled()) {
			if (column.getHorizontalAlignment() == null) {
				logger.trace("horizontal alignment is null for " + column);
			}
		}
		cellStyle = getStyle(baseStyle, null, column.getHorizontalAlignment());
		// now I need to get group heading
		// and column heading
		// with the row index one greater for the column

		if (groupEntryIndex == 0) {
			// emit merged group header
			final String groupName = column.getGroupName();
			// this cell Style should be centered ?? TODO unless there is only
			// one column in group?
			cellStyle = getStyle(baseStyle, null, HorizontalAlignment.CENTER);
			super.addCell(rowIndex, columnIndex, groupName, cellStyle);
			getSheet().addMergedRegion(new CellRangeAddress(rowIndex, // first
					// row
					// (0-based)
					rowIndex, // last row (0-based)
					columnIndex, // first column (0-based)
					columnIndex + numberOfColumnsInGroup - 1));
			if (logger.isDebugEnabled()) {
				final String message = "creating group heading cell "
						+ rowIndex + " " + columnIndex + " " + columnIndex
						+ numberOfColumnsInGroup + " '" + groupName + "'";
				logger.debug(message);
			}
		}

		//

		heading = getNonmergedColumnHeading(column);
		if (heading.indexOf("\\n") > -1) {
			heading = heading.replace("\\n", "\n");
		}
		final int detailRowIndex = rowIndex + 1;
		if (logger.isDebugEnabled()) {
			final String message = "creating group detail cell "
					+ detailRowIndex + " " + columnIndex + " " + " '" + heading
					+ "'";
			logger.debug(message);
		}
		super.addCell(detailRowIndex, columnIndex, heading, cellStyle);
		if (column.getColumnDisplaySize() != null) {
			setColumnWidth(columnIndex, column.getColumnDisplaySize()
					.intValue());
		}
	}

	public void addCell(final ColumnMetadata column, final Style baseStyle,
			final int rowIndex, final int columnIndex) {
		if (logger.isDebugEnabled()) {
			logger.debug("writing cell " + rowIndex + " " + columnIndex);
		}
		Style cellStyle = null;
		String heading = "";
		if (logger.isTraceEnabled()) {
			if (column.getHorizontalAlignment() == null) {
				logger.trace("horizontal alignment is null for " + column);
			}
		}
		cellStyle = getStyle(baseStyle, null, column.getHorizontalAlignment());
		heading = getMergedColumnHeading(column);

		if (heading.indexOf('\n') > -1) {
			cellStyle = addWordWrap(cellStyle);
		}
		super.addCell(rowIndex, columnIndex, heading, cellStyle);
		if (column.getColumnDisplaySize() != null) {
			setColumnWidth(columnIndex, column.getColumnDisplaySize()
					.intValue());
		}
	}

	/**
	 * Emit the leading columns of the regions, those columns that identify the
	 * data in the crosstabbed region.
	 * 
	 * TODO why is this public and we have everything we need except
	 * sourceRowidColumnIndexes is stored in state.
	 * 
	 * @param columns
	 * @param baseStyle
	 * @param startingRow
	 * @param startingColumn
	 * @param nonRowIdentifyingColumns
	 * @param metadataByColumnName
	 */
	public void emitHeadersForRowIdentifiers(final Dataset dataset,
			final Style baseStyle, final int startingRow,
			final int startingColumn) {

		if (baseStyle == null) {
			throw new IllegalArgumentException("baseStyle is null");
		}
		baseStyle.setVerticalAlignment(VerticalAlignment.TOP);
		int rowIndex = startingRow;
		if (!isSingleCellCrosstabColumnHeading()) {
			rowIndex++;
		}
		int columnIndex = startingColumn;
		// int entry = 0;
		ColumnMetadata column;
		for (final int rowidColumnIndex : sourceRowidColumnIndexes) {
			if (!isSingleCellCrosstabColumnHeading()) {
				super.addCell(rowIndex - 1, columnIndex, null, baseStyle);
			}
			column = dataset.getMetadata().getColumnMetaData(rowidColumnIndex);
			if (logger.isDebugEnabled()) {
				logger.debug("adding rowIdentifier header at " + rowIndex + ","
						+ columnIndex + " " + column);
			}
			addCell(column, baseStyle, rowIndex, columnIndex);
			columnIndex++;
		}
	}

	/**
	 * todo this started off the same as emitHeaders see what the difference is
	 * 
	 * @param columns
	 * @param baseStyle
	 * @param startingRow
	 * @param startingColumn
	 * @param nonRowIdentifyingColumns
	 * @param metadataByColumnName
	 */
	public void emitCrosstabHeadersForCrosstabData(final Dataset dataset,
			final String[] groupNames,
			final List<String> nonRowIdentifyingColumns,
			final Map<String, ColumnMetadata> metaByColumnName,
			final Style baseStyle, final int startingRow,
			final int startingColumn) {

		if (baseStyle == null) {
			throw new IllegalArgumentException("baseStyle is null");
		}
		baseStyle.setVerticalAlignment(VerticalAlignment.TOP);

		final int rowIndex = startingRow;
		int columnIndex = startingColumn;
		int groupIndex = 0;
		logger.trace("Starting Column is " + startingColumn);
		ColumnMetadata column;

		for (final String groupName : groupNames) {
			logger.debug("Working with Group " + groupName);
			for (final String nonRowidColName : nonRowIdentifyingColumns) {
				column = new ColumnMetadata();
				if (logger.isDebugEnabled()) {
					logger.debug("Working with Non Row Id Column "
							+ nonRowidColName + " expected at Index "
							+ groupIndex);
				}
				final List<Integer> columnIndexes = (List<Integer>) dataset
						.getMetadata().getColumnIndexes(nonRowidColName);
				if (columnIndexes != null) {
					column = dataset.getMetadata().getColumnMetaData(
							columnIndexes.get(groupIndex));
				} else {
					column = metaByColumnName.get(nonRowidColName);
					if (column == null) {
						throw new java.lang.IllegalStateException(
								"No Metadata found " + " for column "
										+ nonRowidColName);
					}
				}
				column.setGroupName(groupName);
				addCell(column, baseStyle, rowIndex, columnIndex);
				columnIndex++;
			}
			groupIndex++;
		}
	}

	public int getEmittedColumnsInGroupCount() {

		throw new UnsupportedOperationException();
	}

	/**
	 * TODO this started off the same as emitHeaders see what the difference is
	 * 
	 * TODO do a cut and paste analysis
	 * 
	 * @param columns
	 * @param baseStyle
	 * @param startingRow
	 * @param startingColumn
	 * @param nonRowIdentifyingColumns
	 * @param nonRowIdentifyingColumns2
	 * @param metadataByColumnName
	 */
	public void emitCrosstabHeadersForCrosstabDataWithSpannedHeaders(
			final Dataset dataset,
			final String[] groupNames,
			final List<String> nonRowIdentifyingColumns,
			// List<ColumnMetadata> columns,
			final Map<String, ColumnMetadata> metaByColumnName,
			final Style baseStyle, final int startingRow,
			final int startingColumn) {

		if (baseStyle == null) {
			throw new IllegalArgumentException("baseStyle is null");
		}
		baseStyle.setVerticalAlignment(VerticalAlignment.TOP);

		final int rowIndex = startingRow;
		int columnIndex = startingColumn;
		int groupIndex = 0;
		logger.info("Starting Column is " + startingColumn);
		ColumnMetadata column;

		for (final String groupName : groupNames) {
			logger.debug("Working with Group " + groupName);
			int groupEntryIndex = 0;
			for (final String nonRowidColName : nonRowIdentifyingColumns) {
				column = new ColumnMetadata();
				if (logger.isDebugEnabled()) {
					logger.debug("Working with Non Row Id Column "
							+ nonRowidColName + " expected at Index "
							+ groupIndex);
				}

				final List<Integer> columnIndexes = (List<Integer>) dataset
						.getMetadata().getColumnIndexes(nonRowidColName);
				if (columnIndexes != null) {
					column = dataset.getMetadata().getColumnMetaData(
							columnIndexes.get(groupIndex));
				} else {
					column = metaByColumnName.get(nonRowidColName);
					if (column == null) {
						throw new java.lang.IllegalStateException(
								"No Metadata found " + " for column "
										+ nonRowidColName);
					}
				}

				column.setGroupName(groupName);
				addGroupHeaderSpanCell(column, baseStyle, rowIndex,
						columnIndex, nonRowIdentifyingColumns.size(),
						groupEntryIndex++);
				columnIndex++;
			}
			groupIndex++;
		}
	}

	private void setSourceRowidColumnIndexes(
			final List<String> rowIdentifyingColumns, final DatasetMetadata meta) {
		int indx = 0;
		sourceRowidColumnIndexes = new int[rowIdentifyingColumns.size()];
		for (final String col : rowIdentifyingColumns) {
			sourceRowidColumnIndexes[indx++] = meta.getColumnIndex(col);
		}
	}

	public boolean isSingleCellCrosstabColumnHeading() {
		return singleCellCrosstabColumnHeading;
	}

	public void setSingleCellCrosstabColumnHeading(
			final boolean singleCellCrosstabColumnHeading) {
		this.singleCellCrosstabColumnHeading = singleCellCrosstabColumnHeading;
	}

}
