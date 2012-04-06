package org.javautil.poi;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.javautil.dataset.ColumnMetaMap;
import org.javautil.dataset.ColumnMetadata;
import org.javautil.dataset.DataType;
import org.javautil.dataset.Dataset;
import org.javautil.dataset.DatasetIterator;
import org.javautil.dataset.DatasetMarshaller;
import org.javautil.dataset.MetadataException;
import org.javautil.document.style.HorizontalAlignment;
import org.javautil.document.style.Style;
import org.javautil.document.style.StyleImpl;
import org.javautil.lang.SystemProperties;
import org.javautil.poi.style.HSSFCellStyleFactory;
import org.javautil.workbook.CellAddress;
import org.javautil.workbook.CellFormula;

/**
 * 
 * @author jjs TODO need to have the ability to add properties from a column
 *         list adorn dataset metadata
 */
public class SheetStyleHelper {
	private final Logger logger = Logger.getLogger(getClass());

	private final HSSFSheet sheet;

	private HSSFCellStyleFactory styleFactory;

	public HSSFCellStyleFactory getStyleFactory() {
		return styleFactory;
	}

	public void setStyleFactory(final HSSFCellStyleFactory styleFactory) {
		this.styleFactory = styleFactory;
	}

	private String defaultDateFormat = "mm/dd/yyyy";

	private static final String newline = System.getProperty("line.separator");

	private static final int MAX_ROWS_PER_SHEET = 65535;

	public SheetStyleHelper(final HSSFSheet sheet,
			final HSSFCellStyleFactory styleFactory) {
		super();
		if (sheet == null) {
			throw new IllegalArgumentException("sheet is null");
		}
		this.sheet = sheet;
		this.styleFactory = styleFactory;
	}

	public HSSFCell addCell(final int rowIndex, final int columnIndex,
			final Object value, final Style style) {

		final HSSFCell cell = getCell(rowIndex, columnIndex);
		if (value != null) {
			if (value instanceof Number) {
				cell.setCellValue(((Number) value).doubleValue());
			} else if (value instanceof Date) {
				cell.setCellValue((Date) value);
			} else if (value instanceof String) {
				String val = (String) value;
				if (((String) value).indexOf("\\n") > -1) {
					val = val.replace("\\n", "\n");
				}
				cell.setCellValue(new HSSFRichTextString(val));
			} else if (value instanceof Boolean) {
				final Boolean bool = (Boolean) value;
				cell.setCellValue(bool.booleanValue());
			}
		} else {
			cell.setCellValue("");
		}
		if (style != null) {
			cell.setCellStyle(styleFactory.getHSSFCellStyle(style));
		}
		return cell;
	}

	public void addColumnSumCell(final int row, final int columnIndex,
			final int firstDataRowIndex, final int lastDataRowIndex,
			final Style style) {
		addColumnFormulaCell(row, columnIndex, firstDataRowIndex,
				lastDataRowIndex, style, "sum");
	}

	public HSSFCell getCell(final int rownum, final int colnum) {
		final HSSFRow row = getRow(rownum);
		HSSFCell cell = row.getCell(colnum);
		if (cell == null) {
			cell = row.createCell(colnum);
		}
		return cell;
	}

	public HSSFCell addFormulaCell(final int rownum, final int columnIndex,
			final String formula, final Style style) {
		if (formula == null) {
			throw new IllegalArgumentException("formula is null");
		}

		final HSSFCell cell = getCell(rownum, columnIndex);
		cell.setCellFormula(formula);
		final HSSFCellStyle hstyle = styleFactory.getHSSFCellStyle(style);
		cell.setCellStyle(hstyle);
		return cell;
	}

	/**
	 * Creates a formula cell.
	 * 
	 * Example: to create a sum cell on
	 * 
	 * 
	 * @param rowIndex
	 *            The index (rownumber - 1) of the row into which the formula
	 *            should be placed
	 * @param columnIndex
	 *            The index of the column into which the formula should be
	 *            placed
	 * @param firstDataRowIndex
	 *            The index of the row that starts the range
	 * @param lastDataRowIndex
	 *            The index of the row that ends the rang
	 * @param style
	 *            The style of the cell containing the formula
	 * @param function
	 *            The function to be applied.
	 * @return
	 */
	public HSSFCell addColumnFormulaCell(final int rowIndex,
			final int columnIndex, final int firstDataRowIndex,
			final int lastDataRowIndex, final Style style, final String function) {

		if (logger.isDebugEnabled()) {
			final String message = "addColumnFormulaCell " + rowIndex + " "
					+ columnIndex + " " + firstDataRowIndex + " "
					+ lastDataRowIndex + " " + function;
			logger.debug(message);
		}
		final String firstAddress = CellAddress.getCellAddress(
				firstDataRowIndex, columnIndex);
		final String secondAddress = CellAddress.getCellAddress(
				lastDataRowIndex, columnIndex);
		final String formula = function + "(" + firstAddress + ":"
				+ secondAddress + ")";
		final HSSFRow row = getRow(rowIndex);
		final HSSFCell cell = row.createCell(columnIndex);
		cell.setCellFormula(formula);
		final HSSFCellStyle hstyle = styleFactory.getHSSFCellStyle(style);
		cell.setCellStyle(hstyle);
		return cell;
	}

	HSSFRow getRow(final int index) {
		HSSFRow row = sheet.getRow(index);
		if (row == null) {
			row = sheet.createRow(index);

		}
		return row;
	}

	// todo this needs to be moved to a more common place
	public void fixColumnWidths(final int startColumnIndex,
			final int endColumnIndex) {
		for (int i = startColumnIndex; i <= endColumnIndex; i++) {
			fixColumnWidth(i);
		}
	}

	public void setColumnWidth(final int columnIndex, final int characterWidth) {
		final int unitsPerChar = 300;
		final int funkyWidth = characterWidth * unitsPerChar;
		final short shortFunkyWidth = funkyWidth < Short.MAX_VALUE ? (short) funkyWidth
				: Short.MAX_VALUE;
		sheet.setColumnWidth(columnIndex, shortFunkyWidth);
	}

	public void fixColumnWidth(final int columnIndex) {
		final int rowCount = sheet.getPhysicalNumberOfRows();
		int maxWidth = 0;
		for (int i = 0; i < rowCount; i++) {
			final HSSFRow r = sheet.getRow(i);
			final HSSFCell c = r.getCell(columnIndex);

			if (c != null) {
				final String v = c.getStringCellValue();
				if (v != null && v.length() > maxWidth) {
					maxWidth = v.length();
				}
			}
		}
		if (maxWidth > 8) {
			sheet.setColumnWidth(columnIndex, maxWidth);
		}
	}

	public void emitRegion(final Dataset dataset,
			final List<ColumnMetadata> columns, final int startingRow,
			final int startingColumn) {
		if (logger.isDebugEnabled()) {
			final StringBuilder b = new StringBuilder();
			for (final ColumnMetadata column : columns) {
				b.append(column);
				b.append(SystemProperties.newline);
			}
			b.append("starting row " + startingRow + SystemProperties.newline);
			b.append("startingColumn " + startingColumn
					+ SystemProperties.newline);

			b.append(DatasetMarshaller.asString(dataset));
			logger.debug(b.toString());

		}
		final int dataRows = dataset.getDatasetIterator().getRowCount();
		if (startingRow + dataRows + 2 > MAX_ROWS_PER_SHEET) {
			throw new IllegalArgumentException(
					"would produce too many rows in sheet: " + dataRows
							+ " startingRow " + startingRow);
		}
		emitHeaders(columns, styleFactory.getBaseHeaderStyle(), startingRow,
				startingColumn);
		final int lastDataRowIndex = emitData(dataset, columns,
				styleFactory.getBaseDataStyle(), startingRow + 1,
				startingColumn);
		emitSummary(columns, lastDataRowIndex + 1, startingColumn,
				startingRow + 1, lastDataRowIndex,
				styleFactory.getBaseFooterStyle());
	}

	/**
	 * @param columns
	 * @param baseStyle
	 * @param startingRow
	 * @param startingColumn
	 */
	public void emitHeaders(final List<ColumnMetadata> columns,
			final Style baseStyle, final int startingRow,
			final int startingColumn) {

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
				cellStyle = getStyle(baseStyle, null,
						column.getHorizontalAlignment());
				heading = column.getHeading();
			}
			if (heading == null) {
				heading = column.getColumnName();
			}
			addCell(rowIndex, columnIndex, heading, cellStyle);
			setColumnWidth(columnIndex, getColumnWidth(column));

			columnIndex++;
			entry++;
		}

	}

	int getColumnWidth(final ColumnMetadata meta) {
		int columnWidth;
		if (meta.getColumnDisplaySize() != null) {
			columnWidth = meta.getColumnDisplaySize();
		} else {
			if (meta.getHeading() != null) {
				columnWidth = meta.getHeading().length() + 1;
			} else {
				columnWidth = meta.getColumnName().length() + 1;
			}
			// todo should try to figure out more based on the actual data
		}
		return columnWidth;
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
			if (logger.isTraceEnabled()) {
				logger.trace("adding row " + rowIndex);
			}
			int columnIndex = startingColumn;
			for (final ColumnMetadata column : columns) {
				if (column == null) {
					throw new IllegalStateException("column is null");
				}

				final Style cellStyle = getStyle(baseStyle, column);

				if (column.getWorkbookFormula() == null) {
					final Object data = datasetIterator.getObject(column
							.getColumnName());

					addCell(rowIndex, columnIndex, data, cellStyle);
					if (logger.isDebugEnabled()) {
						final String message = "adding column " + rowIndex
								+ " " + columnIndex + " " + data + " "
								+ cellStyle + " " + column.toString();
						logger.debug(message);
					}
				} else {
					final HSSFCell cell = addFormulaCell(
							column.getWorkbookFormula(), columnIndexByName,
							column.getColumnName(), rowIndex, columnIndex);
					if (logger.isDebugEnabled()) {
						final String message = "adding formua " + rowIndex
								+ " " + columnIndex + " " + cellStyle + " "
								+ column.toString();
						logger.debug(message);
					}
					cell.setCellStyle(styleFactory.getHSSFCellStyle(cellStyle));
				}
				columnIndex++;
			}
			rowIndex++;
		}
		return rowIndex - 1;
	}

	public void checkColumns(final List<ColumnMetadata> columns) {
		boolean nullFound = false;
		for (final ColumnMetadata col : columns) {
			if (col == null) {
				nullFound = true;
			}
		}
		if (nullFound) {
			final StringBuilder b = new StringBuilder();
			for (final ColumnMetadata col : columns) {
				b.append(col);
				b.append(newline);
			}
			// AsString as = new AsString();
			// logger.error(as.toString(columns));

			logger.error(columns);
		}
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
		if (logger.isDebugEnabled()) {
			for (final ColumnMetadata column : columns) {
				logger.debug(column);
			}
		}

		final Map<String, Integer> columnIndexByName = ColumnMetaMap
				.getColumnNameIndexMap(columns);

		int currentColumn = columnIndex;
		for (final ColumnMetadata column : columns) {
			checkColumns(columns);
			final Style cellStyle = getStyle(baseStyle,
					column.getExcelFormat(), column.getHorizontalAlignment());

			if (column.getAggregateFunction() != null) {
				if (logger.isDebugEnabled()) {
					logger.debug("columnFormulaCell " + columnIndex + " "
							+ lastDataRowIndex);
				}
				addColumnFormulaCell(rowIndex, columnIndex, firstDataRowIndex,
						lastDataRowIndex, cellStyle,
						column.getAggregateFunction());

			} else if (column.getWorkbookFormula() != null) {
				if (logger.isDebugEnabled()) {
					logger.debug("formulaCell " + columnIndex + " " + rowIndex);
				}
				final HSSFCell cell = addFormulaCell(
						column.getWorkbookFormula(), columnIndexByName,
						column.getColumnName(), rowIndex, columnIndex);
				cell.setCellStyle(styleFactory.getHSSFCellStyle(cellStyle));
			}
			currentColumn++;
		}
	}

	public Style getStyle(final Style baseStyle, final String excelFormat,
			final HorizontalAlignment horizontalAlignment) {
		if (baseStyle == null) {
			throw new IllegalArgumentException("baseStyle is null");
		}
		final StyleImpl s = new StyleImpl();
		s.setFormatMask(excelFormat);
		// HorizontalAlignment ha = HorizontalAlignment
		// .getFromAbbreviation(horizontalAlignment);
		s.setHorizontalAlignment(horizontalAlignment);
		final Style cellStyle = s.getStyle(baseStyle, s);
		return cellStyle;
	}

	public Style getStyle(final Style baseStyle, final ColumnMetadata meta) {
		if (baseStyle == null) {
			throw new IllegalArgumentException("baseStyle is null");
		}
		final StyleImpl s = new StyleImpl();
		String format = meta.getExcelFormat();
		if (format == null) {
			if (DataType.DATE.equals(meta.getDataType())) {
				format = defaultDateFormat;
			}
		}
		s.setFormatMask(format);
		// HorizontalAlignment ha = HorizontalAlignment
		// .getFromAbbreviation(horizontalAlignment);
		s.setHorizontalAlignment(meta.getHorizontalAlignment());
		final Style cellStyle = s.getStyle(baseStyle, s);
		return cellStyle;
	}

	/**
	 * todo cam document
	 * 
	 * @param template
	 * @param columnIndexByName
	 * @param columnName
	 * @return
	 */
	public HSSFCell addFormulaCell(final String template,
			final Map<String, Integer> columnIndexByName,
			final String columnName, final int rowIndex, final int columnIndex) {
		if (template == null || columnIndexByName == null) {
			throw new IllegalArgumentException(); // / todo cam clean up
		}

		if (logger.isTraceEnabled()) {
			logger.trace("template : " + template + " columnIndexByName "
					+ columnIndexByName + " " + columnName + " " + rowIndex
					+ " " + columnIndex);
		}

		String formula;
		try {
			formula = CellFormula.getAsFormula(template, columnIndexByName,
					rowIndex);
		} catch (final MetadataException me) {
			final StringBuilder b = new StringBuilder();
			b.append("template : ").append(template).append(" columnName: ")
					.append(columnName).append(" ").append(me.getMessage());
			throw new MetadataException(b.toString());
		}
		final HSSFCell cell = getCell(rowIndex, columnIndex);
		if (logger.isTraceEnabled()) {
			logger.trace("cell is " + cell.getRowIndex() + " "
					+ cell.getColumnIndex() + ", formula is " + formula);
		}
		try {
			cell.setCellFormula(formula);
		} catch (final Exception wtf) {
			final StringBuilder sb = new StringBuilder();
			sb.append("this is really goofy\n");
			sb.append("but go ahead and replace the semicolons with commas and poi will change them back to ';' \n");
			sb.append("exception was " + wtf.getClass().getName() + "\n");
			sb.append(wtf.getMessage());
			throw new IllegalArgumentException(sb.toString());
		}

		return cell;
	}

	public void setDefaultDateFormat(final String format) {
		this.defaultDateFormat = format;
	}
}
