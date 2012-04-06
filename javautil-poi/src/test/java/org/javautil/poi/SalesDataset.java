package org.javautil.poi;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.javautil.dataset.AbstractDataset;
import org.javautil.dataset.ColumnMetadata;
import org.javautil.dataset.DataType;
import org.javautil.dataset.Dataset;
import org.javautil.dataset.DatasetCrosstabber;
import org.javautil.dataset.DatasetMetadataImpl;
import org.javautil.dataset.MatrixDataset;
import org.javautil.document.crosstab.CrosstabColumns;
import org.javautil.document.crosstab.CrosstabColumnsImpl;
import org.javautil.document.style.HorizontalAlignment;
import org.javautil.util.Day;

/**
 * 
 * @author jjs
 * 
 */
public class SalesDataset {
	public Logger logger = Logger.getLogger(getClass());
	private static final String newline = System.getProperty("line.separator");

	private static final ColumnMetadata monthMeta = new ColumnMetadata("MONTH",
			2, DataType.DATE, null, null, null);

	private static final ColumnMetadata qtyMeta = new ColumnMetadata("QTY", 3,
			DataType.DOUBLE, null, null, null);
	private static final ColumnMetadata qtyPriorYrMeta = new ColumnMetadata(
			"QTY_PRIOR_YR", 3, DataType.DOUBLE, null, null, null);

	@SuppressWarnings("boxing")
	private final DatasetMetadataImpl meta = new DatasetMetadataImpl() {
		{
			addColumn(new ColumnMetadata("CST_ID", 0, DataType.INTEGER, null,
					null, null));
			addColumn(new ColumnMetadata("PRODUCT_ID", 1, DataType.STRING,
					null, null, null));
			final ColumnMetadata column = new ColumnMetadata("PRODUCT_DESCR",
					1, DataType.STRING, null, null, null);
			column.setHeading("Product\nDescription");
			addColumn(column);
			addColumn(monthMeta);
			addColumn(qtyMeta);
			addColumn(qtyPriorYrMeta);
		}
	};

	public List<ColumnMetadata> getColumnMetadata() {
		final ArrayList<ColumnMetadata> list = new ArrayList<ColumnMetadata>();

		list.add(qtyMeta);
		list.add(qtyPriorYrMeta);
		meta.getColumnMetadata();
		final ColumnMetadata differenceColumn = new ColumnMetadata(
				"DIFFERENCE", 3, null, null, null, 12,
				HorizontalAlignment.RIGHT, "###,###,###", null);
		final String formula = "${QTY} - ${QTY_PRIOR_YR}";
		differenceColumn.setWorkbookFormula(formula);
		differenceColumn.setHeading("Difference\nFrom\nLast Year");
		list.add(differenceColumn);
		final ColumnMetadata ratioColumn = new ColumnMetadata("RATIO", 3, null,
				null, null, 12, HorizontalAlignment.RIGHT, "#,###.000", null);
		final String ratioFormula = "${QTY} / ${QTY_PRIOR_YR}";
		ratioColumn.setWorkbookFormula(ratioFormula);
		list.add(ratioColumn);
		return list;
	}

	@SuppressWarnings("unchecked")
	private final MatrixDataset purchases = new MatrixDataset(meta) {
		{
			addRow(toList(Integer.valueOf(1), "1-1", "Nacho Dip", new Day(2009,
					9, 01), new Double(10), new Double(42)));
			addRow(toList(Integer.valueOf(1), "1-1", "Nacho Dip", new Day(2009,
					8, 01), new Double(2), new Double(27)));

			addRow(toList(Integer.valueOf(2), "1-1", "Nacho Dip", new Day(2009,
					9, 01), new Double(10), new Double(35)));
			addRow(toList(Integer.valueOf(2), "1-1", "Nacho Dip", new Day(2009,
					8, 01), new Double(2), new Double(12)));
		}
	};

	@SuppressWarnings("unchecked")
	ArrayList<Object> toList(final Object... o) {
		final ArrayList al = new ArrayList(o.length);
		for (final Object element : o) {
			al.add(element);
		}
		return al;
	}

	@SuppressWarnings("unchecked")
	List<String> toList(final String... o) {
		final ArrayList al = new ArrayList(o.length);
		for (final String element : o) {
			al.add(element);
		}
		return al;
	}

	public List<String> getRowIdentifyingColumns() {
		final List<String> rowId = toList("CST_ID", "PRODUCT_ID",
				"PRODUCT_DESCR");
		return rowId;
	}

	public String getColumnIdentifyingColumns() {
		final String columnId = "MONTH";
		return columnId;
	}

	public List<String> getCellColumnNames() {
		return toList("QTY", "QTY_PRIOR_YR");
	}

	public List<String> getRenderCellColumnNames() {
		return toList("RATIO", "QTY", "QTY_PRIOR_YR", "DIFFERENCE");
	}

	public Dataset getCrosstabbedDataset() {
		final DatasetCrosstabber coke = new DatasetCrosstabber();
		final List<String> rowId = getRowIdentifyingColumns();
		final String columnId = getColumnIdentifyingColumns();
		final List<String> cellId = getCellColumnNames();
		final CrosstabColumns ctc = new CrosstabColumnsImpl(rowId, columnId,
				cellId);
		coke.setCrosstabColumns(ctc);
		coke.setDataSet(purchases);

		final AbstractDataset ds = coke.getDataSet();
		return ds;
	}

}
