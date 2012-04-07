package org.javautil.dataset.operations;

import java.util.HashMap;
import java.util.Set;

import org.javautil.collections.Tuple;
import org.javautil.dataset.Dataset;
import org.javautil.dataset.DatasetIterator;

/**
 * Returns distinct tuples of the specified columns from the dataset.
 * 
 * TODO document for the computer illiterate.
 * 
 * @author jjs
 * 
 */

public class Distinct<T> {
	/**
	 * Returns distinct tuples of the specified columns from the dataset.
	 * 
	 * Each columnNames value must be unique within the dataset otherwise an
	 * Exception will be thrown indicating that the request was ambiguous,
	 * illogical, Illegal.
	 * 
	 * The columns reflected by the column names must implement Comparable TODO
	 * write unit tests
	 * 
	 * @author jjs
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Set<Tuple<T>> getDistinctTuples(final Dataset<T> dataset,
			final String... columnNames) {
		checkArguments(dataset, columnNames);

		final HashMap<Tuple<T>, Integer> set = new HashMap<Tuple<T>, Integer>();

		final DatasetIterator di = dataset.getDatasetIterator();

		final int[] columnIndexes = dataset.getMetadata().getColumnIndexes(
				columnNames);

		while (di.next()) {
			final Object[] objects = new Object[columnNames.length];
			int tupleIndex = 0;
			// for the tuple in the dataset create an object array for the
			// specified columns
			for (final int columnIndexe : columnIndexes) {
				objects[tupleIndex++] = di.getObject(columnIndexe);
			}
			final Integer oldCount = set.get(objects);
			final Tuple<T> tuple = new Tuple(objects);
			// TODO we bothered to count the occurence, but the answer is not
			// exposable, this is bollocks.
			if (oldCount == null) {
				set.put(tuple, 1); // not in the distinct results add it with a
									// count of 1
			} else {
				set.put(tuple, oldCount + 1); // was in the distinct results,
												// increment the count
			}
		}
		// todo should we sort?
		return set.keySet();
	}

	private void checkArguments(final Dataset<T> dataset,
			final String[] columnNames) {
		if (dataset == null) {
			throw new IllegalArgumentException("dataset is null");
		}
		if (columnNames == null) {
			throw new IllegalArgumentException("columnNames is null");
		}
		if (columnNames.length == 0) {
			throw new IllegalArgumentException("columnNames is empty");
		}

	}

}
