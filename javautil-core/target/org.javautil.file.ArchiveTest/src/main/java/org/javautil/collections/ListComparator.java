package org.javautil.collections;

import java.util.Comparator;
import java.util.List;

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
// todo QA
public class ListComparator implements Comparator {

	@SuppressWarnings("unchecked")
	private int aIndex;
	private int bIndex;
	private static Logger logger = Logger.getLogger(ListComparator.class);
	private List ac;
	private List bc;
	private int maxIndex;

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
		Integer retval = null;
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
		if (retval == null) {
			throw new IllegalStateException("comparison logic failure");
		}
		return retval;
	}

	private int compareEntryAt(final int index) {
		if (index > maxIndex) {
			throw new IllegalStateException("attempt to compare at index "
					+ index);
		}
		final Object aObj = ac.get(index);
		final Object bObj = bc.get(index);
		int objResult;
		try {
			objResult = compareObjects(aObj, bObj);
		} catch (final IllegalArgumentException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
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
		aIndex = 0;
		bIndex = 0;
		Integer diffColumn = null;
		if (a != null && !(a instanceof List)) {
			throw new IllegalArgumentException("a " + a.getClass().getName()
					+ " does not implement List");
		}
		if (b != null && !(b instanceof List)) {
			throw new IllegalArgumentException("b " + b.getClass().getName()
					+ " does not implement List");
		}
		ac = (List) a;
		bc = (List) b;
		if (ac.size() < bc.size()) {
			maxIndex = ac.size() - 1;
		} else {
			maxIndex = bc.size() - 1;
		}

		for (int index = 0; index <= maxIndex; index++) {
			if (logger.isDebugEnabled()) {
				logger.debug("index :" + index);
			}
			final int compareResult = compareEntryAt(index);
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
		if (diffColumn == null) {
			if (ac.size() > bc.size()) {
				diffColumn = (bc.size() + 1);
				if (logger != null && logger.isDebugEnabled()) {
					logger.debug("ran out of items in this collection, more in other "
							+ aIndex + " other index " + bIndex);
				}
			} else if (ac.size() < bc.size()) {
				diffColumn = -1 * (ac.size() + 1);
				if (logger != null && logger.isDebugEnabled()) {
					logger.debug("ran out of items in this collection, more in other "
							+ aIndex + " other index " + bIndex);
				}
			} else if (ac.size() == bc.size()) {
				if (logger != null && logger.isDebugEnabled()) {
					logger.debug("compares equal");
				}
				diffColumn = Integer.valueOf(0);
			} else {
				throw new IllegalStateException("hopelessly confused");
			}
		}
		if (diffColumn == null) {
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
