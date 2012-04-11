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

/**
 * 
 * @author jjs @ dbexperts
 * 
 */
public class CrosstabDataset {
	public Logger logger = Logger.getLogger(getClass());
	private static final String newline = System.getProperty("line.separator");

	@SuppressWarnings("boxing")
	private final DatasetMetadataImpl meta = new DatasetMetadataImpl() {
		{
			addColumn(new ColumnMetadata("STATE", 0, DataType.STRING, null,
					null, null));
			addColumn(new ColumnMetadata("CITY", 1, DataType.STRING, null,
					null, null));
			addColumn(new ColumnMetadata("MONTH", 2, DataType.INTEGER, null,
					null, null));
			addColumn(new ColumnMetadata("TICKETS", 3, DataType.DOUBLE, null,
					null, null));
		}
	};

	@SuppressWarnings("unchecked")
	private final MatrixDataset tickets = new MatrixDataset(meta) {
		{
			addRow(toList("TX", "DALLAS", new Integer(1), new Double(42)));
			addRow(toList("TX", "DALLAS", new Integer(2), new Double(27)));
			addRow(toList("TX", "HOUSTON", new Integer(1), new Double(32)));
			addRow(toList("TX", "Quoted\"Text", new Integer(3), new Double(17)));

		}
	};

	@SuppressWarnings("unchecked")
	ArrayList<Object> toList(final Object... o) {
		final ArrayList<Object> al = new ArrayList<Object>(o.length);
		for (final Object element : o) {
			al.add(element);
		}
		return al;
	}

	@SuppressWarnings("unchecked")
	List<String> toList(final String... o) {
		final ArrayList<String> al = new ArrayList<String>(o.length);
		for (final String element : o) {
			al.add(element);
		}
		return al;
	}

	public List<String> getRowIdentifyingColumns() {
		final List<String> rowId = toList("STATE", "CITY");
		return rowId;
	}

	public String getColumnIdentifyingColumns() {
		final String columnId = "MONTH";
		return columnId;
	}

	public List<String> getCellColumnNames() {
		return toList("TICKETS");
	}

	public Dataset getCrosstabbedDataset() {
		final DatasetCrosstabber coke = new DatasetCrosstabber();
		final List<String> rowId = getRowIdentifyingColumns();
		final String columnId = getColumnIdentifyingColumns();
		final List<String> cellId = getCellColumnNames();
		final CrosstabColumns ctc = new CrosstabColumnsImpl(rowId, columnId,
				cellId);
		coke.setCrosstabColumns(ctc);
		coke.setDataSet(tickets);

		final AbstractDataset ds = coke.getDataSet();
		return ds;
	}

}
