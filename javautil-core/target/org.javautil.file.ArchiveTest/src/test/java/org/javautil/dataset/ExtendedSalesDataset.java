package org.javautil.dataset;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.javautil.document.crosstab.CrosstabColumns;
import org.javautil.document.crosstab.CrosstabColumnsImpl;
import org.javautil.document.style.HorizontalAlignment;
import org.javautil.util.Day;
import org.junit.Before;

/**
 * 
 * @author jjs
 * 
 */
public class ExtendedSalesDataset {
	public Logger logger = Logger.getLogger(getClass());
	private static final String newline = System.getProperty("line.separator");

	@Before
	public void before() {
		BasicConfigurator.configure();
	}

	private static final ColumnMetadata invoiceDateMeta = new ColumnMetadata(
			"INVOICE_DT", 2, DataType.DATE, null, null, null);

	private static final ColumnMetadata monthMeta = new ColumnMetadata("MONTH",
			2, DataType.DATE, null, null, null);

	private static final ColumnMetadata boxesMeta = new ColumnMetadata("BOXES",
			3, DataType.DOUBLE, null, null, null);
	private static final ColumnMetadata boxesPriorYrMeta = new ColumnMetadata(
			"BOXES_PRIOR_YR", 3, DataType.DOUBLE, null, null, null);

	private static final ColumnMetadata casesMeta = new ColumnMetadata("CASES",
			3, DataType.INTEGER, null, null, null);
	private static final ColumnMetadata casesPriorYrMeta = new ColumnMetadata(
			"CASES_PRIOR_YR", 3, DataType.INTEGER, null, null, null);

	static {
		invoiceDateMeta.setJavaFormat("yyyy/MM/dd");
	}
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
			addColumn(invoiceDateMeta);
			addColumn(monthMeta);
			addColumn(boxesMeta);
			addColumn(boxesPriorYrMeta);
			addColumn(casesMeta);
			addColumn(casesPriorYrMeta);
		}
	};

	public List<ColumnMetadata> getColumnMetadata() {
		final ArrayList<ColumnMetadata> list = new ArrayList<ColumnMetadata>();

		list.add(boxesMeta);
		list.add(boxesPriorYrMeta);
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
					9, 01), new Day(2009, 9, 30), new Double(10),
					new Double(42), new Integer(3), new Integer(17)));
			addRow(toList(Integer.valueOf(1), "1-1", "Nacho Dip", new Day(2009,
					8, 01), new Day(2009, 8, 31), new Double(2),
					new Double(27), new Integer(15), new Integer(19)));

			addRow(toList(Integer.valueOf(2), "1-1", "Nacho Dip", new Day(2009,
					9, 01), new Day(2009, 9, 30), new Double(10),
					new Double(35), new Integer(4), new Integer(15)));
			addRow(toList(Integer.valueOf(2), "1-1", "Nacho Dip", new Day(2009,
					8, 01), new Day(2009, 8, 31), new Double(2),
					new Double(12), new Integer(3), new Integer(8)));
		}
	};

	public Dataset getDataset() {
		return purchases;
	}

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

	// public List<String> getNonRowIdentifyingColumns() {
	// List<String> rowId = toList("CST_ID", "PRODUCT_ID",
	// "PRODUCT_DESCR","DIFFERENCE");
	// return rowId;
	// }

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
