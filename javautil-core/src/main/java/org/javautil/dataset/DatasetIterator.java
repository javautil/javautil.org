package org.javautil.dataset;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * All of the get... methods that take an index reference columns relative to 0.
 * 
 * @author jjs
 * 
 *         TODO document
 * 
 */
public interface DatasetIterator<T> {

	public DatasetMetadata getDatasetMetadata();

	/**
	 * Retrieves the value of the designated column (index, relative 0) in the
	 * current row of this DatasetIterator object as a java.util.Date object in
	 * the Java programming language.
	 **/
	public Date getDate(int column) throws DatasetException;

	/**
	 * Retrieves the value of the designated column, by name, in the current row
	 * of this DatasetIterator object as a java.util.Date.
	 * 
	 * If the column occurs more than once as in a crosstabbed dataset, this
	 * throws a DatasetException.
	 * 
	 * @parm columnName - the name of the column
	 * @return the column value
	 * @throws DatasetException
	 *             -
	 **/
	public Date getDate(String columnName) throws DatasetException;

	/**
	 * Retrieves the value of the designated column (index, relative 0) in the
	 * current row of this DatasetIterator object as a java.lang.Double.
	 **/
	public Double getDouble(int column) throws DatasetException;

	/**
	 * Retrieves the value of the designated column, by name, in the current row
	 * of this DatasetIterator object as a java.lang.Double.
	 * 
	 * If the column occurs more than once as in a crosstabbed dataset, this
	 * throws an exception.
	 **/
	public Double getDouble(String column) throws DatasetException;

	/**
	 * Retrieves the value of the designated column (index, relative 0) in the
	 * current row of this DatasetIterator object as a java.lang.Integer.
	 **/
	public Integer getInteger(int column) throws DatasetException;

	/**
	 * Retrieves the value of the designated column, by name, in the current row
	 * of this DatasetIterator object as a java.lang.Integer.
	 * 
	 * If the column occurs more than once as in a crosstabbed dataset, this
	 * throws an exception.
	 **/
	public Integer getInteger(String column) throws DatasetException;

	public Number getNumber(int columnIndex) throws DatasetException;

	/**
	 * Retrieves the value of the designated column, by name, in the current row
	 * of this DatasetIterator object as a class that implements
	 * java.lang.Number.
	 * 
	 * If the column occurs more than once as in a crosstabbed dataset, this
	 * throws an exception.
	 **/
	public Number getNumber(String column) throws DatasetException;

	public T getObject(int columnIndex) throws DatasetException;

	public T getObject(String column) throws DatasetException;

	public List<T> getRowAsList() throws DatasetException;

	public Map<String, Object> getRowAsMap() throws DatasetException;

	/**
	 * Retrieves the value of the designated column (index, relative 0) in the
	 * current row of this DatasetIterator object as a java.util.String object
	 * in the Java programming language.
	 **/
	public String getString(int column) throws DatasetException;

	/**
	 * Retrieves the value of the designated column, by name, in the current row
	 * of this DatasetIterator object as a java.lang.String.
	 * 
	 * If the column occurs more than once as in a crosstabbed dataset, this
	 * throws an exception.
	 **/
	public String getString(String column) throws DatasetException;

	public Date getTimestamp(int column) throws DatasetException;

	/**
	 * Retrieves the value of the designated column, by name, in the current row
	 * of this DatasetIterator object as a java.util.Date.
	 * 
	 * If the column occurs more than once as in a crosstabbed dataset, this
	 * throws an exception.
	 **/
	public Date getTimestamp(String column) throws DatasetException;

	public boolean hasNext() throws DatasetException;

	public boolean next() throws DatasetException;

	public int getRowCount() throws DatasetException;

}
