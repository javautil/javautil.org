package org.javautil.collections;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.javautil.text.AsString;

/**
 * An array of objects with supporting methods for use in Collections.
 * 
 * @author jjs
 */

public class Tuple<T> {
	T[] data;

	public Tuple(final T[] data) {
		this.data = data;
	}

	private final ArrayComparator comparator = new ArrayComparator();

	@SuppressWarnings("unchecked")
	/**
	 * Creates a new tuple with data from the argument tuple and newCol.
	 * 
	 * todo document
	 */
	public Tuple(final Tuple<T> tuple, final T newCol) {
		data = (T[]) new Object[tuple.data.length + 1];
		for (int i = 0; i < tuple.data.length; i++) {
			data[i] = tuple.data[i];
		}
		data[data.length - 1] = newCol;

	}

	@Override
	public int hashCode() {
		int retval = 0;
		if (data != null) {
			for (final T o : data) {
				if (o != null) {
					retval += o.hashCode();
				}
			}
		}
		return retval;
	}

	@Override
	public boolean equals(final Object other) {
		Object[] otherObjects = null;
		final boolean retval = false;
		if (other instanceof Object[] || other instanceof Tuple) {
			if (other instanceof Object[]) {
				otherObjects = (Object[]) other;
			} else {
				if (other instanceof Tuple) {
					otherObjects = ((Tuple<?>) other).data;
				}
			}

			return comparator.compare(data, otherObjects) == 0 ? true : false;
		}
		return retval;
	}

	public T[] getObjects() {
		return data;
	}

	public Set<Tuple<T>> getDistinct(final Collection<Tuple<T>> tuples) {
		final HashMap<Tuple<T>, Integer> set = new HashMap<Tuple<T>, Integer>();
		for (final Tuple<T> tuple : tuples) {
			final Integer oldCount = set.get(tuple);
			if (oldCount == null) {
				set.put(tuple, 1);
			} else {
				set.put(tuple, oldCount + 1);
			}
		}
		return set.keySet();

	}

	public double getDouble(final int index) {
		final Number n = getNumber(index);
		final double retval = n == null ? 0 : n.doubleValue();
		return retval;
	}

	public Number getNumber(final int index) {
		if (index < 0) {
			throw new IllegalArgumentException("index < 0");
		}
		if (index > data.length) {
			throw new IllegalArgumentException("index > " + data.length);
		}
		// try {
		// Object o = data[index];

		try {
			return (Number) data[index];
		} catch (final ClassCastException ce) {
			throw new IllegalArgumentException("column " + index
					+ " is not a number is " + data[index].getClass().getName());
		} catch (final ArrayIndexOutOfBoundsException aiobe) {
			throw new IllegalArgumentException("index: " + index
					+ " requested tuple length " + data.length);
		}
	}

	// todo test and document
	@Override
	public String toString() {
		return new AsString().toString(this);
	}

	public List<T> list() {
		// ArrayList<T> al = new ArrayList<T>();
		return Arrays.asList(data);
		// for (T t : data) {
		// al.add(t);
		// }
		// return al;
	}
}
