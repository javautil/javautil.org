package org.javautil.dataset;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.javautil.document.crosstab.CrosstabColumns;
import org.javautil.document.crosstab.CrosstabColumnsImpl;
import org.junit.Ignore;
import org.junit.Test;

/**
 * 
 * @author jjs @ dbexperts
 * 
 */
public class CrosstabDatasetTest extends BaseTest {
	public Logger logger = Logger.getLogger(getClass());
	private static final String newline = System.getProperty("line.separator");
	// TODO clean up
	// private ResultValidator resultValidator =new ResultValidator();

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

	private final MatrixDataset tickets = new MatrixDataset(meta) {
		{
			addRow(toList("TX", "DALLAS", new Integer(1), new Double(42)));
			addRow(toList("TX", "DALLAS", new Integer(2), new Double(27)));
			addRow(toList("TX", "HOUSTON", new Integer(1), new Double(32)));
			addRow(toList("TX", "Quoted\"Text", new Integer(3), new Double(17)));

		}
	};

	@SuppressWarnings("boxing")
	private final DatasetMetadataImpl deferredAdjudicationMeta = new DatasetMetadataImpl() {
		{
			addColumn(new ColumnMetadata("STATE", 0, DataType.STRING, null,
					null, null));
			addColumn(new ColumnMetadata("CITY", 1, DataType.STRING, null,
					null, null));
			addColumn(new ColumnMetadata("MONTH", 2, DataType.INTEGER, null,
					null, null));
			addColumn(new ColumnMetadata("Fine", 3, DataType.DOUBLE, null,
					null, null));
			addColumn(new ColumnMetadata("Legal Fee", 4, DataType.DOUBLE, null,
					null, null));
		}
	};

	private final DatasetMetadataImpl deferredAdjudicationSumMeta = new DatasetMetadataImpl() {
		{
			addColumn(new ColumnMetadata("STATE", 0, DataType.STRING, null,
					null, null));
			addColumn(new ColumnMetadata("CITY", 1, DataType.STRING, null,
					null, null));
			addColumn(new ColumnMetadata("Fine", 2, DataType.DOUBLE, null,
					null, null));
			addColumn(new ColumnMetadata("Legal Fee", 3, DataType.DOUBLE, null,
					null, null));
		}
	};

	private final MatrixDataset fees = new MatrixDataset(
			deferredAdjudicationMeta) {
		{
			addRow(toList("TX", "DALLAS", new Integer(1), new Double(311),
					new Double(380)));
			addRow(toList("TX", "DALLAS", new Integer(2), new Double(321), null));
			addRow(toList("TX", "HOUSTON", new Integer(1), new Double(312),
					new Double(0)));
			addRow(toList("TX", "Southlake", new Integer(3), new Double(333),
					null));

		}
	};

	private final MatrixDataset feeSum = new MatrixDataset(
			deferredAdjudicationSumMeta) {
		{
			addRow(toList("TX", "DALLAS", new Double(633), new Double(380)));
			addRow(toList("TX", "HOUSTON", new Double(312), new Double(0)));
			addRow(toList("TX", "Southlake", new Double(333), null));

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

	public Dataset getCrosstabbedDataset() {
		final DatasetCrosstabber coke = new DatasetCrosstabber();
		final List<String> rowId = getRowIdentifyingColumns();
		final String columnId = getColumnIdentifyingColumns();
		final List<String> cellId = toList("TICKETS");
		final CrosstabColumns ctc = new CrosstabColumnsImpl(rowId, columnId,
				cellId);
		coke.setCrosstabColumns(ctc);
		coke.setDataSet(tickets);

		final AbstractDataset ds = coke.getDataSet();
		return ds;
	}

	// TODO check that the metadata is what was expected
	@Ignore
	@Test
	public void test2() throws IOException {
		logger.debug("test2");
		final DatasetCrosstabber coke = new DatasetCrosstabber();
		final List<String> rowId = toList("STATE", "CITY");
		final List<String> cellId = toList("TICKETS");

		final CrosstabColumns ctc = new CrosstabColumnsImpl(rowId, "MONTH",
				cellId);
		coke.setCrosstabColumns(ctc);
		coke.setDataSet(tickets);

		final AbstractDataset ds = coke.getDataSet();

		assertExpected(ds);
	}

	@Test
	public void test3() throws IOException {
		logger.debug("test3");
		final DatasetCrosstabber coke = new DatasetCrosstabber();
		final List<String> rowId = toList("STATE", "CITY");
		final List<String> cellId = toList("Fine", "Legal Fee");

		final CrosstabColumns ctc = new CrosstabColumnsImpl(rowId, "MONTH",
				cellId);
		coke.setCrosstabColumns(ctc);
		coke.setDataSet(fees);
		final Dataset ds = coke.getDataSet();
		assertExpected(ds);

	}

	@Test
	public void test4() throws IOException {
		logger.debug("test4");
		final DatasetCrosstabber coke = new DatasetCrosstabber();
		final List<String> rowId = toList("STATE", "CITY");
		final List<String> cellId = toList("Fine", "Legal Fee");

		final CrosstabColumns ctc = new CrosstabColumnsImpl(rowId, "MONTH",
				cellId);
		coke.setCrosstabColumns(ctc);
		final Dataset feesData = fees;
		logger.debug("about to crosstab " + newline + fees);
		coke.setDataSet(feesData);

		final AbstractDataset ds = coke.getDataSet();
		assertExpected(ds);

	}

	/*
	 * Take a dataset, crosstab it and then right append to the crosstabbed
	 * result
	 * 
	 * todo cam create an expected resultset and compare
	 */

	@SuppressWarnings("serial")
	@Test
	public void test7() throws IOException {
		logger.debug("test7");
		// todo does this make any sense ?
		final Map<String, String> idKeyMap = new TreeMap<String, String>() {
			{
				put("STATE", "STATE");
				put("CITY", "CITY");
				// put("MONTH", "MONTH");
			}
		};

		final DatasetCrosstabber coke = new DatasetCrosstabber();
		final List<String> rowId = toList("STATE", "CITY");
		final List<String> cellId = toList("Fine", "Legal Fee");

		final CrosstabColumns ctc = new CrosstabColumnsImpl(rowId, "MONTH",
				cellId);
		coke.setCrosstabColumns(ctc);
		logger.debug("about to crosstab " + newline + fees);
		logger.debug("using crosstab rule " + ctc);
		coke.setDataSet(fees);

		final MutableDataset crosstabbed = (MutableDataset) coke.getDataSet();
		logger.debug("crosstabbed result is " + newline + crosstabbed);

		final DatasetAppender dsa = new DatasetAppender();
		// TODO have to check DatasetAppender
		dsa.appendRight(crosstabbed, feeSum, idKeyMap);
		assertExpected(crosstabbed);
	}

	@SuppressWarnings("serial")
	@Test
	public void test8() {
		logger.debug("test7");
		new TreeMap<String, String>() {
			{
				put("STATE", "STATE");
				put("CITY", "CITY");
				put("MONTH", "MONTH");
			}
		};
		final DatasetCrosstabber coke = new DatasetCrosstabber();
		final List<String> rowId = toList("STATE", "CITY");
		final List<String> cellId = toList("Fine", "Legal Fee");
		logger.debug("dataset \n" + fees);
		logger.debug("dataset meta\n " + fees.getMetadata());
		final CrosstabColumns ctc = new CrosstabColumnsImpl(rowId, "MONTH",
				cellId);
		coke.setCrosstabColumns(ctc);
		coke.setDataSet(fees);

		final MutableDataset crosstabbed = (MutableDataset) coke.getDataSet();
		logger.debug("crosstabbed meta\n " + crosstabbed.getMetadata());
		// TODO what is this? What is being tested here?
		new DatasetAppender();

	}

}
