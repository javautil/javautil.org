package org.javautil.dataset;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
public interface MutableDatasetMetadata extends DatasetMetadata {

	public void addColumn(ColumnMetadata column);

	public void addColumn(int index, ColumnMetadata column);

}
