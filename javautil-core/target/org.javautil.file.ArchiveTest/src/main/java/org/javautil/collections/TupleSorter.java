package org.javautil.collections;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;
import org.javautil.dataset.SortIndex;

/**
 *
 * 
 */

public class TupleSorter<T> implements Comparator<Tuple<Comparable<? super T>>> {
	private final Logger logger = Logger.getLogger(getClass());

	/**
	 * The columns to sort on.
	 */
	private final List<SortIndex> sortIndexes;

	private final NullCompare nullCompare = new NullCompare(false);

	private final List<Tuple<Comparable<? super T>>> tuples;

	public TupleSorter(final List<Tuple<Comparable<? super T>>> tuples,
			final SortIndex... sortColumns) {
		this.tuples = tuples;
		this.sortIndexes = Arrays.asList(sortColumns);
		checkArgs();
	}

	public TupleSorter(final List<Tuple<Comparable<? super T>>> tuples,
			final List<SortIndex> sortColumns) {
		this.tuples = tuples;
		this.sortIndexes = sortColumns;
		checkArgs();
	}

	private void checkArgs() {
		if (tuples == null) {
			throw new IllegalArgumentException("tuples is null");
		}
		if (sortIndexes == null) {
			throw new IllegalArgumentException("sortColumns is null");
		}
	}

	// TODO jjs should just try it, catch Exceptions evaluate and rethrow
	@Override
	public int compare(final Tuple<Comparable<? super T>> o1,
			final Tuple<Comparable<? super T>> o2) {
		int retVal = 0;
		int index = 0;
		if (o1 == null) {
			throw new IllegalArgumentException("o1 is null");
		}
		if (o2 == null) {
			throw new IllegalArgumentException("o2 is null");
		}
		final Object[] o1Data = o1.getObjects();
		final Object[] o2Data = o2.getObjects();

		while (retVal == 0 && index < sortIndexes.size()) {
			final SortIndex sortIndex = sortIndexes.get(index);
			final int columnIndex = sortIndex.getSortColumn();
			retVal = nullCompare.compare(o1Data[columnIndex],
					o2Data[columnIndex]);
			retVal = sortIndex.isAscending() ? retVal : -retVal;
			index++;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("retval " + retVal + " " + o1 + ":" + o2);
		}
		// todo jjs restore the indicator that said at what level this break
		// occurred
		// that would be the absolute value of the index + 1 * the sign of
		// retval
		return retVal;
	}

	public List<Tuple<Comparable<? super T>>> sort() {
		Collections.sort(tuples, this);
		return tuples;
	}

	public void nullCollatesLow(final boolean nullCollatesLow) {
		nullCompare.setNullSortsLow(nullCollatesLow);
	}

}
