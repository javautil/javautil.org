package org.javautil.collections;

import java.util.Comparator;

import org.apache.log4j.Level;
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
 * @author jjs@dbexperts.com
 * 
 */
@SuppressWarnings("unchecked")
// TODO
public class StaticArrayComparator implements Comparator {

	@SuppressWarnings("unchecked")
	private static Logger logger = Logger
			.getLogger(StaticArrayComparator.class);

	static {
		logger.setLevel(Level.INFO);
	}

	@SuppressWarnings("unchecked")
	int compareObjects(final Object a, final Object b)
			throws IllegalArgumentException {
		Comparable ac = null;
		Comparable bc = null;
		try {
			ac = (Comparable) a;

		} catch (final ClassCastException cce) {
			throw new IllegalArgumentException("class "
					+ a.getClass().getName() + " does not implement comparable");
		}
		try {
			bc = (Comparable) b;

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
					throw new IllegalArgumentException("unable to compare "
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
	 * The sign of the return value is negative if the entry for 'a' is less
	 * than 'b'.
	 * 
	 * @author jjs
	 * 
	 */
	@Override
	@SuppressWarnings("unchecked")
	public synchronized int compare(final Object a, final Object b) {
		final int aIndex = 0;
		final int bIndex = 0;
		int diffColumn = Integer.MAX_VALUE;
		if (a != null && !(a instanceof Object[])) {
			throw new IllegalArgumentException("a " + a.getClass().getName()
					+ " is not an array");
		}
		if (b != null && !(b instanceof Object[])) {
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
			int compareResult = 0;
			try {
				compareResult = compareEntryAt(index, maxIndex, ac, bc);
			} catch (final IllegalArgumentException iae) {
				final StringBuilder sb = new StringBuilder();
				sb.append(iae.getMessage());
				sb.append(" A: ");
				for (final Object aco : ac) {
					if (aco == null) {
						sb.append(" null ");
					} else {
						sb.append(aco.getClass().getName());
						sb.append(" ");
						sb.append(aco.toString());
					}
				}
				sb.append(" B: ");
				// todo make a function of this copy paste
				for (final Object aco : bc) {
					if (aco == null) {
						sb.append(" null ");
					} else {
						sb.append(aco.getClass().getName());
						sb.append(" ");
						sb.append(aco.toString());
					}
				}
			}
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
			if (ac.length > bc.length) {
				diffColumn = (bc.length + 1);
				if (logger != null && logger.isDebugEnabled()) {
					logger.debug("ran out of items in this collection, more in other "
							+ aIndex + " other index " + bIndex);
				}
			} else if (ac.length < bc.length) {
				diffColumn = -1 * (ac.length + 1);
				if (logger != null && logger.isDebugEnabled()) {
					logger.debug("ran out of items in this collection, more in other "
							+ aIndex + " other index " + bIndex);
				}
			} else if (ac.length == bc.length) {
				if (logger != null && logger.isDebugEnabled()) {
					logger.debug("compares equal");
				}
				diffColumn = Integer.valueOf(0);
			} else {
				throw new IllegalStateException("hopelessly confused");
			}
		}
		if (diffColumn == Integer.MAX_VALUE) {
			throw new IllegalStateException("logic failure");
		}
		if (logger.isDebugEnabled()) {
			logger.info("returning " + diffColumn);
		}

		return diffColumn;

	}

	public void setLogLevel(final Level level) {
		if (logger == null) {
			logger = Logger.getLogger(this.getClass());
		}
		logger.setLevel(level);
	}

}
