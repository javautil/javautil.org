package org.javautil.dataset.filter;

/**
 * 
 * 
 * 
 * TODO jjs review this
 * 
 */
public interface MutableDatasetFilter {
	public String getColumnName();

	public Object getColumnValue();

	public boolean isInverseMatch();

	public boolean isMatch(Object value);

	public void setColumnName(String columnName);

	public void setColumnValue(Object value);

	public void setInverseMatch(boolean isInverse);
}
