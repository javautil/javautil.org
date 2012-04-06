package org.javautil.exceptionprocessing;

@SuppressWarnings("serial")
public class ExceptionProcessingServerArgs implements java.io.Serializable {

	private Integer runNumber;
	private int threadPoolSize;
	// if necessary
	private int exceptionCount;

	public ExceptionProcessingServerArgs() {
	}

	/**
	 * @return the exceptionCount
	 */
	public int getExceptionCount() {
		return exceptionCount;
	}

	/**
	 * @param exceptionCount
	 *            the exceptionCount to set
	 */
	public void setExceptionCount(final int exceptionCount) {
		this.exceptionCount = exceptionCount;
	}

	/**
	 * @return the threadPoolSize
	 */
	public int getThreadPoolSize() {
		return threadPoolSize;
	}

	/**
	 * @param threadPoolSize
	 *            the threadPoolSize to set
	 */
	public void setThreadPoolSize(final int threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}

	/**
	 * @return the runNumber
	 */
	public Integer getRunNumber() {
		return runNumber;
	}

	/**
	 * @param runNumber
	 *            the runNumber to set
	 */
	public void setRunNumber(final Integer runNumber) {
		this.runNumber = runNumber;
	}

}
