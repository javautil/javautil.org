package org.javautil.collections;

import java.util.Comparator;

import org.apache.log4j.Logger;

/**
 * Comparator for Collection
 * 
 * Steps through the two collections comparing the corresponding entries. A null
 * entry compares low to a non null entry.
 * 
 * An entry of longer length compares high if the corresponding entries compare
 * equal.
 * 
 * All of the entries must implement Comparable.
 * 
 * Behavior of Comparison of objects of different types is dependent on the
 * compareTo method of the corresponding entry in the first Collection.
 * 
 * @author jjs
 * 
 */
@SuppressWarnings("unchecked")
// todo QA
public class ArrayComparator implements Comparator {

	private static Logger logger = Logger.getLogger(ArrayComparator.class);

	/**
	 * Comparator for Collection
	 * 
	 * Steps through the two collections comparing the corresponding entries. A
	 * null entry compares low to a non null entry.
	 * 
	 * An entry of longer length compares high if the corresponding entries
	 * compare equal.
	 * 
	 * All of the entries must implement Comparable.
	 * 
	 * Behavior of Comparison of objects of different types is dependent on the
	 * compareTo method of the corresponding entry in the first Collection.
	 * 
	 * The magnitude of the return value is the indexNumber + 1 of the entry
	 * that was different.
	 * 
	 * The sign of the return value is negative if the entry for a is less than
	 * b.
	 * 
	 * @author jjs
	 * 
	 */
	@Override
	@SuppressWarnings("unchecked")
	public synchronized int compare(final Object a, final Object b) {
		int diffColumn = Integer.MAX_VALUE;
		if (logger.isDebugEnabled()) {
			if (a != null) {
				logger.debug("a object " + a.getClass().getName());
			}
			if (b != null) {
				logger.debug("b object" + b.getClass().getName());
			}
		}
		if (a == null) {
			throw new IllegalArgumentException("a is null");
		}
		if (b == null) {
			throw new IllegalArgumentException("b is null");
		}
		if (!(a instanceof Object[])) {
			throw new IllegalArgumentException("a " + a.getClass().getName()
					+ " is not an array");
		}
		if (!(b instanceof Object[])) {
			throw new IllegalArgumentException("b " + b.getClass().getName()
					+ " is not an array");
		}
		final Object[] ac = (Object[]) a;
		final Object[] bc = (Object[]) b;
		int maxIndex;
		if (ac.length < bc.length) {
			maxIndex = ac.length - 1;
		} else {
			maxIndex = bc.length - 1;
		}

		for (int index = 0; index <= maxIndex; index++) {
			if (logger.isDebugEnabled()) {
				logger.debug("index :" + index);
			}
			final int compareResult = compareEntryAt(index, maxIndex, ac, bc);
			if (compareResult != 0) {
				if (compareResult < 0) {
					diffColumn = -1 * (index + 1);
				} else {
					diffColumn = index + 1;
				}
				break;
			}

		}
		// at this point we should have examined the common elements in both
		// if a collection has another entry even if it is null that collection
		// collates higher
		if (diffColumn == Integer.MAX_VALUE) {
			// todo when do we get Here?
			if (ac.length > bc.length) {
				diffColumn = (bc.length + 1);
			} else if (ac.length < bc.length) {
				diffColumn = -1 * (ac.length + 1);
			} else if (ac.length == bc.length) {
				diffColumn = Integer.valueOf(0);
			} else {
				throw new IllegalStateException("hopelessly confused");
			}
		}
		if (diffColumn == Integer.MAX_VALUE) {
			throw new IllegalStateException("logic failure");
		}

		return diffColumn;

	}

	int compareObjects(final Object a, final Object b)
			throws ComparisonException {
		Comparable ac = null;
		Comparable bc = null;
		try {
			ac = (Comparable<?>) a;

		} catch (final ClassCastException cce) {
			logger.debug("Why is this not showing up in my code coverage report?");
			throw new IllegalArgumentException("class "
					+ a.getClass().getName() + " does not implement comparable");
		}
		try {
			bc = (Comparable<?>) b;

		} catch (final ClassCastException cce) {
			throw new IllegalArgumentException("class "
					+ b.getClass().getName() + " does not implement comparable");
		}
		int retval = Integer.MAX_VALUE;
		if (a == null) {
			if (b == null) {
				retval = 0;
			} else {
				retval = -1;
			}
		} else {
			if (b == null) {
				retval = 1;
			} else {
				try {
					retval = ac.compareTo(bc);
				} catch (final ClassCastException cce) {
					throw new ComparisonException("unable to compare "
							+ a.getClass() + " to " + b.getClass());
				}
			}
		}
		if (retval == Integer.MAX_VALUE) {
			throw new IllegalStateException("comparison logic failure");
		}
		return retval;
	}

	private int compareEntryAt(final int index, final int maxIndex,
			final Object[] ac, final Object[] bc) {
		if (index > maxIndex) {
			throw new IllegalStateException("attempt to compare at index "
					+ index);
		}
		final Object aObj = ac[index];
		final Object bObj = bc[index];
		int objResult;

		objResult = compareObjects(aObj, bObj);

		return objResult;
	}

	public void ensureEquals(final Object[] expected, final Object[] actual) {
		if (compare(expected, actual) != 0) {
			final StringBuilder b = new StringBuilder();
			b.append("expected ");
			for (final Object s : expected) {
				b.append("'");
				b.append(s);
				b.append("' ");
			}
			b.append(" actual ");
			for (final Object s : actual) {
				b.append("'");
				b.append(s);
				b.append("' ");
			}
			throw new IllegalArgumentException(b.toString());
		}
	}

}
