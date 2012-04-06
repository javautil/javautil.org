package org.javautil.mp3.persistence;

import org.apache.log4j.Logger;

public abstract class AbstractPersistence {
	private int insertedSinceLastCommit;

	//
	private int insertCountBetweenCommits;

	private int insertedCount = 0;

	/**
	 * @return the insertCountBetweenCommits
	 */
	public int getInsertCountBetweenCommits() {
		return insertCountBetweenCommits;
	}

	/**
	 * @param insertCountBetweenCommits
	 *            the insertCountBetweenCommits to set
	 */
	public void setInsertCountBetweenCommits(final int insertCountBetweenCommits) {
		this.insertCountBetweenCommits = insertCountBetweenCommits;
	}

	private final Logger logger = Logger.getLogger(getClass());

	public String trim(final String tagName, final String text,
			final int maxLength) {
		String returnValue = text;
		if ((text != null) && (text.length() > maxLength)) {
			returnValue = text.substring(0, maxLength - 1);
			logger.warn("trimming " + tagName + " from '" + text + "'"
					+ " to '" + returnValue + "'");
		}
		return returnValue;
	}

	boolean needsCommit() {
		boolean retval = false;
		if ((insertCountBetweenCommits > 0)
				&& (insertedSinceLastCommit >= insertCountBetweenCommits)) {
			insertedSinceLastCommit = 0;
			retval = true;
		}
		return retval;
	}

	void inserted(final int insertCount) {
		insertedSinceLastCommit += insertCount;
		insertedCount += insertCount;

	}

	void checkCommit() {
		if (needsCommit()) {
			flushAndCommit();
			logger.info("committing after " + insertedCount + " inserts");
		}
	}

	abstract void commit();

	abstract void flushAndCommit();

	public boolean supportsStreamWriter() {
		return false;
	}

}
