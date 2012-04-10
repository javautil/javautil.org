package org.javautil.document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.javautil.dataset.Dataset;
import org.javautil.dataset.DatasetMetadata;
import org.springframework.beans.factory.InitializingBean;

/**
 * A small utility class implementing multiple break columns. Intended to be
 * used with a Dataset.
 * 
 * Most of the code of the code from this class is based on
 * com.dbexperts.dex.dexterous.jdbc.ResultSetHelper from dbexperts3.
 * 
 * todo more rigorous testing.
 * 
 * @author jjs
 * @author bcm
 */
public class BreakHelper implements InitializingBean {

	private final Logger logger = Logger.getLogger(getClass());

	private List<String> breaks;

	private Dataset dataset;

	private Map<String, Integer> columnNameIndex = new HashMap<String, Integer>();

	private boolean lowerCase = false;

	public BreakHelper() {
	}

	public BreakHelper(final Dataset dataset) {
		if (dataset == null) {
			throw new IllegalArgumentException("dataset is null");
		}
		this.dataset = dataset;
	}

	@Override
	public void afterPropertiesSet() {
		if (dataset != null) {
			final DatasetMetadata metadata = dataset.getMetadata();
			for (int i = 0; i < metadata.getColumnCount(); i++) {
				String columnName = metadata.getColumnName(i);
				// convert to all lower or uppercase
				if (isLowerCase()) {
					columnName = columnName.toLowerCase();
				} else {
					columnName = columnName.toUpperCase();
				}
				columnNameIndex.put(columnName, i);
			}
		}
		if (columnNameIndex == null && dataset == null) {
			throw new IllegalStateException("exactly one of the following "
					+ "properties is required: dataset, columnNameIndex");
		}
	}

	public void setBreaks(final List<String> _breaks) {
		this.breaks = _breaks;
		if (breaks != null) {
			// set uppercas for later processing
			for (int a = 0; a < breaks.size(); a++) {
				// convert to all lower or uppercase
				if (isLowerCase()) {
					breaks.set(a, breaks.get(a).toLowerCase());
				} else {
					breaks.set(a, breaks.get(a).toUpperCase());
				}
			}
		}
	}

	/**
	 * Gets the column name at the specified break index.
	 * 
	 * @param breakNbr
	 * @return
	 */
	public String getBreak(final int breakNbr) {
		return breaks.get(breakNbr);
	}

	/**
	 * How many break columns are there?
	 * 
	 * @return
	 */
	public int getBreakCount() {
		final int retval = breaks == null ? 0 : breaks.size();
		return retval;
	}

	/**
	 * Determines if there is a break in the data.
	 * 
	 * TODO throws bizarre null pointer exception if column not in select list
	 * while doing a
	 * 
	 * @return -1 if there is no break
	 */
	public int getBreakLevel(final Object[] previousRow,
			final Object[] currentRow) {
		int breakLevel = -1;
		if (breaks == null) {
			return breakLevel;
		}

		for (int a = 0; breakLevel == -1 && a < breaks.size(); a++) {
			final String currentBreak = breaks.get(a);
			// currentBreak values are already uppercase (see setBreaks)
			if (logger.isDebugEnabled()) {
				final StringBuilder b = new StringBuilder();
				b.append("columNameIndex " + columnNameIndex);
				b.append(", currentBreak " + currentBreak);
				b.append(", previousRow " + previousRow);
				b.append(", currentRow " + currentRow);
				for (final String columnName : columnNameIndex.keySet()) {
					b.append(", columnName " + columnName + " "
							+ columnNameIndex.get(columnName));
				}
				logger.debug(b.toString());
			}
			// if column not in select list this throws a null pointer exception
			// even though columnName index is not null and neither is
			// currentBreak
			final Integer column = columnNameIndex.get(currentBreak);
			if (column == null) {
				final StringBuilder b = new StringBuilder();
				for (final String columnName : columnNameIndex.keySet()) {
					b.append(columnName + " ");
				}
				throw new IllegalStateException("column " + currentBreak
						+ "is not in resultSet " + b.toString());
			}
			final Object lastValue = previousRow == null ? null
					: previousRow[column];
			final Object currentValue = currentRow[column];
			// System.out.println(lastValue + " ?= " + currentValue);
			// are both values null or otherwise equal?
			// TODO what if the previous value was a null that could be a break
			// TODO unit test this
			if (lastValue != null && currentValue != null
					&& !lastValue.equals(currentValue) || lastValue != null
					&& currentValue == null || lastValue == null
					&& currentValue != null) {
				breakLevel = a + 1; // values are not equal
			}
			// else the values are equal, try the next break
		}

		return breakLevel;
	}

	public List<String> getBreaks() {
		return breaks;
	}

	public Dataset getDataset() {
		return dataset;
	}

	public void setDataset(final Dataset dataset) {
		this.dataset = dataset;
	}

	public Map<String, Integer> getColumnNameIndex() {
		return columnNameIndex;
	}

	public void setColumnNameIndex(final Map<String, Integer> columnNameIndex) {
		this.columnNameIndex = new HashMap<String, Integer>();
		for (final String key : columnNameIndex.keySet()) {
			final Integer value = columnNameIndex.get(key);
			this.columnNameIndex.put(key.toUpperCase(), value);
		}
	}

	public boolean isLowerCase() {
		return lowerCase;
	}

	public void setLowerCase(final boolean lowerCase) {
		this.lowerCase = lowerCase;
	}

}
