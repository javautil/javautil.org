package org.javautil.dataset;

/**
 * 

 *
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.javautil.collections.NullCompare;

/**
 * Sorts the Dataset.
 * 
 * 
 * Properly handles multiple column sorts.
 * 
 * Example: procedure returns result set from the following query
 * 
 * <pre>
 * select id, cy_sales, py_sales, yr, per, mth from sales_table
 * </pre>
 * 
 * We'd like this result set to be sorted by yr, per, and mth. In that order.
 * 
 * <pre>
 * DetachedResultSet resultSet = ...; 
 * 
 * List<DataSorter> sortColumns = new ArrayList<DataSorter>();
 * sortColumns.add(new DataSorter("yr"));
 * sortColumns.add(new DataSorter("per"));
 * sortColumns.add(new DataSorter("mth"));
 * DetachedResultSetSorter sorter = new DetachedResultSetSorter(resultSet, sortColumns);
 * if ( sorter.shouldWeBother() ) {
 *   Collections.sort(resultSet.resultArray, sorter);
 * }
 * </pre>
 * 
 * We leave the sorting algorithm used up to the Collections class
 * 
 */

public class DatasetComparator<T> implements Comparator<List<T>> {

	private Dataset drs;

	/**
	 * The columns to sort on.
	 */
	private List<SortColumn> columns;

	private final NullCompare nullCompare = new NullCompare(false);

	// /**
	// * If a null value is encountered, is it considered "less" than a non-null
	// * value?
	// *
	// * Default is false, the null value is "more" than a non-null value.
	// */
	// private boolean nullIsPrior = false;

	/**
	 * The column indexes that will be sorted.
	 */
	private int[] indexes;

	/**
	 * Stores whether this index is to be sorted ascending or not.
	 */
	private boolean[] isAscending;

	public DatasetComparator(final Dataset _drs, final List<SortColumn> sorts) {
		prepare(_drs, sorts);
	}

	public DatasetComparator(final Dataset _drs, final SortColumn... sorts) {
		final List<SortColumn> dataSorts = new ArrayList<SortColumn>();
		for (final SortColumn sorter : sorts) {
			dataSorts.add(sorter);
		}
		prepare(_drs, dataSorts);
	}

	/**
	 * Invoked by the Collections sort.
	 */
	@Override
	public int compare(final List<T> o1, final List<T> o2) {
		int retVal = 0;
		int index = 0;
		while (retVal == 0 && index < indexes.length) {
			final int columnIndex = indexes[index];
			final Comparable<?> c1 = (Comparable<?>) o1.get(columnIndex);
			final Comparable<?> c2 = (Comparable<?>) o2.get(columnIndex);
			retVal = nullCompare.compare(c1, c2);
			retVal = isAscending[index] ? retVal : -retVal;
			index++;
		}
		return retVal;
	}

	private void prepare(final Dataset _drs, final List<SortColumn> sorts) {
		this.drs = _drs;
		this.columns = sorts;
		indexes = new int[columns.size()];
		isAscending = new boolean[columns.size()];

		final DatasetMetadata meta = drs.getMetadata();

		// Prepare the column indexes
		for (int a = 0; a < indexes.length; a++) {
			final SortColumn sort = columns.get(a);
			final String sortColumn = sort.getSortColumn();
			indexes[a] = meta.getColumnIndex(sortColumn);
			isAscending[a] = sort.isAscending();
		}
	}

	public void nullCollatesLow(final boolean nullCollatesLow) {
		nullCompare.setNullSortsLow(nullCollatesLow);
	}

}
