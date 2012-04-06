package org.javautil.dblogging.dao;

public enum ProcessLogLevel {
	SEVERE(1), WARNING(2), INFO(3), ACTION(4), SNAP(5), ENTERING(6), EXITING(6), FINE(
			7), FINER(8), FINEST(9), NONE(10);

	private int levelNumber;

	ProcessLogLevel() {
		this.levelNumber = -1;
	}

	ProcessLogLevel(final int levelNumber) {
		this.levelNumber = levelNumber;
	}

	public int valueOf() {
		return levelNumber;
	}

}
