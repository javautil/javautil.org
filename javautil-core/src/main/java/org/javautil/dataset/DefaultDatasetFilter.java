package org.javautil.dataset;

import org.javautil.dataset.filter.MutableDatasetFilter;

/**
 * 
 * 
 */
public class DefaultDatasetFilter implements MutableDatasetFilter {

	private String columnName;

	private Object value;

	private boolean inverseMatch;

	@Override
	public String getColumnName() {
		return columnName;
	}

	@Override
	public Object getColumnValue() {
		return value;
	}

	@Override
	public boolean isInverseMatch() {
		return inverseMatch;
	}

	@Override
	public boolean isMatch(final Object comparingValue) {
		boolean retVal;
		if (this.value == comparingValue) {
			retVal = true;
		} else if (this.value == null && comparingValue != null) {
			retVal = false;
		} else if (this.value != null && comparingValue == null) {
			retVal = false;
		} else {
			retVal = this.value.equals(comparingValue);
		}

		if (inverseMatch) {
			retVal = !retVal;
		}

		return retVal;
	}

	@Override
	public void setColumnName(final String columnName) {
		this.columnName = columnName;
	}

	@Override
	public void setColumnValue(final Object value) {
		this.value = value;
	}

	@Override
	public void setInverseMatch(final boolean isInverse) {
		this.inverseMatch = isInverse;
	}
}
