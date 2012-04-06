package org.javautil.oracle.stats;

public class SessionStat {
	/**
	 * Oracle sid.
	 */
	private int sid;

	/**
	 * v$sesstat.statistic#
	 */
	private int statNbr;

	/**
	 * v$sesstat.value.
	 */
	private long value;

	public SessionStat(final int sid, final int statNbr, final long value) {
		super();
		this.sid = sid;
		this.statNbr = statNbr;
		this.value = value;
	}

	/**
	 * @return the sid
	 */
	public int getSid() {
		return sid;
	}

	/**
	 * @param sid
	 *            the sid to set
	 */
	public void setSid(final int sid) {
		this.sid = sid;
	}

	/**
	 * @return the statNbr
	 */
	public int getStatNbr() {
		return statNbr;
	}

	/**
	 * @param statNbr
	 *            the statNbr to set
	 */
	public void setStatNbr(final int statNbr) {
		this.statNbr = statNbr;
	}

	/**
	 * @return the value
	 */
	public long getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(final long value) {
		this.value = value;
	}
}
