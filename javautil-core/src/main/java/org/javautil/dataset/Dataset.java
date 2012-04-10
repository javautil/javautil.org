package org.javautil.dataset;

/**
 * A Dataset is conceptually a two dimensional in memory table. This should not
 * be confused with a JDBC ResulSet, however we frequently encapsulate the
 * results of a ResultSet in a Dataset and release the cursor. This disposes of
 * resources and allows the Dataset to be passed around.
 * 
 * Think of it as the contents of a spreadsheet where all of the objects in a
 * given column are of the same type. A Dataset may be used to store the results
 * of a Sql Query to be passed around after the query has been closed and the
 * connection returned to the connection pool.
 * 
 * 
 * 
 * The metadata for a Dataset provides for rendering information. @see
 * DatasetMetadata.
 * 
 * @author jjs
 * 
 *         TODO needs positional getRow()
 * 
 *         TODO needs getById()
 * 
 */
public interface Dataset {

	/**
	 * No further rows will be retrieved from the underlying data source. Any
	 * open resources should be closed or released.
	 * 
	 * @throws DatasetException
	 */
	public void close() throws DatasetException;

	/**
	 * Returns an iterator positioned before the first row of data with every
	 * call.
	 * 
	 * @return DatasetIterator
	 */
	public DatasetIterator<?> getDatasetIterator();

	/**
	 * 
	 * @return DatasetMetadata
	 */
	public DatasetMetadata getMetadata();

	/**
	 * 
	 * @return the name of the Dataset
	 */
	public String getName();

	/**
	 * Set the name of the Dataset.
	 * 
	 * @param _name
	 */
	public void setName(String _name);

}
