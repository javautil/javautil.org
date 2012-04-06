package org.javautil.poi;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.javautil.dataset.ColumnMetaMap;
import org.javautil.dataset.ColumnMetadata;
import org.javautil.dataset.Dataset;
import org.javautil.dataset.DatasetIterator;
import org.javautil.document.style.Style;
import org.javautil.poi.style.HSSFCellStyleFactory;
import org.javautil.workbook.DataRange;

public class ListWorksheetHelper extends WorksheetHelper {
	private final Logger logger = Logger.getLogger(getClass());

	public ListWorksheetHelper(final HSSFSheet sheet,
			final HSSFCellStyleFactory styleFactory) {
		super(sheet, styleFactory);

	}

	public DataRange emitRegion(final Dataset dataset,
			final List<ColumnMetadata> columns, final int startingRow,
			final int startingColumn) {
		final DataRange range = new DataRange();

		range.setFirstRow(startingRow);
		range.setFirstColumn(startingColumn);
		range.setDataBeginsRow(startingRow + 1);
		range.setLastColumn(startingColumn + columns.size());
		range.setLastRow(startingRow
				+ dataset.getDatasetIterator().getRowCount() + 2);
		range.setFooterBeginsRow(startingRow
				+ dataset.getDatasetIterator().getRowCount() + 2);
		emitHeaders(columns, getStyleFactory().getBaseHeaderStyle(),
				startingRow, startingColumn, null, null);
		final int lastDataRowIndex = emitData(dataset, columns,
				getStyleFactory().getBaseDataStyle(), startingRow + 1,
				startingColumn);
		emitSummary(columns, lastDataRowIndex + 1, startingColumn,
				startingRow + 1, lastDataRowIndex, getStyleFactory()
						.getBaseFooterStyle());
		return range;
	}

	public void emitHeaders(final List<ColumnMetadata> columns,
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
				// if (column.getHorizontalAlignment() == null) {
				// System.out.println("worksheetHelper " + column);
				if (logger.isDebugEnabled()) {
					logger.debug("horizontal alignment is null for " + column);
				}
				// }
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
	 * 
	 * @param dataset
	 * @param columns
	 * @param baseStyle
	 * @return the row index of the last row generated
	 */
	protected int emitData(final Dataset dataset,
			final List<ColumnMetadata> columns, final Style baseStyle,
			final int startingRow, final int startingColumn) {
		if (dataset == null) {
			throw new IllegalArgumentException("dataset is null");
		}
		if (columns == null) {
			throw new IllegalArgumentException("columns is null");
		}
		if (baseStyle == null) {
			throw new IllegalArgumentException("baseStyle is null");
		}
		final Map<String, Integer> columnIndexByName = ColumnMetaMap
				.getColumnNameIndexMap(columns);

		final DatasetIterator datasetIterator = dataset.getDatasetIterator();
		int rowIndex = startingRow;
		while (datasetIterator.next()) {
			logger.debug("adding row " + rowIndex);
			int columnIndex = startingColumn;
			for (final ColumnMetadata column : columns) {
				if (column == null) {
					throw new IllegalStateException("column is null");
				}
				final Style cellStyle = getStyle(baseStyle,
						column.getExcelFormat(),
						column.getHorizontalAlignment());
				if (column.getWorkbookFormula() == null) {
					final Object data = datasetIterator.getObject(column
							.getColumnName());

					addCell(rowIndex, columnIndex, data, cellStyle);
				} else {
					final HSSFCell cell = addFormulaCell(
							column.getWorkbookFormula(), columnIndexByName,
							column.getColumnName(), rowIndex, columnIndex);
					cell.setCellStyle(getStyleFactory().getHSSFCellStyle(
							cellStyle));
				}
				columnIndex++;
			}
			rowIndex++;
		}
		return rowIndex - 1;
	}

	/**
	 * todo document
	 * 
	 * @param columns
	 * @param rowIndex
	 * @param columnIndex
	 * @param firstDataRowIndex
	 * @param lastDataRowIndex
	 * @param baseStyle
	 */
	public void emitSummary(final List<ColumnMetadata> columns,
			final int rowIndex, final int columnIndex,
			final int firstDataRowIndex, final int lastDataRowIndex,
			final Style baseStyle) {
		final Map<String, Integer> columnIndexByName = ColumnMetaMap
				.getColumnNameIndexMap(columns);

		int currentColumn = columnIndex;
		for (final ColumnMetadata column : columns) {
			if (logger.isDebugEnabled()) {
				logger.debug(column);
			}
			checkColumns(columns);
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

			} else if (column.getWorkbookFormula() != null) {
				final HSSFCell cell = addFormulaCell(
						column.getWorkbookFormula(), columnIndexByName,
						column.getColumnName(), rowIndex, currentColumn);
				cell.setCellStyle(getStyleFactory().getHSSFCellStyle(cellStyle));
			}
			currentColumn++;
		}
	}

}
