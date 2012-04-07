package org.javautil.dataset;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.javautil.document.style.HorizontalAlignment;

/**
 * 
 * @author jjs
 * 
 */
public interface DatasetMetadata {
	public int getColumnCount() throws DatasetException;

	public Integer getColumnDisplaySize(int columnNumber)
			throws DatasetException;

	public Integer getColumnIndex(String column);

	/**
	 * columnIndex is relative 0 as in everything sane in java not 1 as in jdbc
	 * TODO need to resolve these cases TODO change to getColumnMetadata
	 * 
	 * @param columnIndex
	 * @return
	 */
	public ColumnMetadata getColumnMetaData(int columnIndex);

	/**
	 * 
	 * @param index
	 * @return
	 */
	public ColumnMetadata getColumnMetaData(Integer index);

	public ColumnMetadata getColumnMetaData(String columnName);

	public List<ColumnMetadata> getColumnMetadata();

	/**
	 * Returns the name of the column at the specified index.
	 * 
	 * @param index
	 * @return
	 * @throws DatasetException
	 */
	public String getColumnName(int index) throws DatasetException;

	public DataType getColumnType(int index) throws DatasetException;

	/**
	 * Return a mutable if meta is not already mutable else just return meta.
	 */
	public MutableDatasetMetadata getMutable();

	/**
	 * Get the designated column's number of decimal digits.
	 * 
	 * Indexes are relative 0. This may return a null result. Get the designated
	 * column's number of decimal digits.
	 * 
	 * @param columnNumber
	 * @return
	 * @throws DatasetException
	 */
	public Integer getPrecision(int columnNumber) throws DatasetException;

	public Integer getScale(int columnNumber) throws DatasetException;

	/**
	 * 
	 * @return the number of rows in the dataset.
	 * @throws DatasetException
	 */
	public int getRowCount() throws DatasetException;

	public HorizontalAlignment getAlignment(int index);

	/**
	 * Returns the ExcelFormat associated with the column at the specified
	 * index.
	 * 
	 * @param i
	 * @return
	 */
	public String getExcelFormat(int index);

	/**
	 * TODO what is this?
	 * 
	 * @param index
	 * @return
	 */
	public String getJavaFormat(int index);

	/**
	 * Return the indexes, relative 0 of columns with the specified name. More
	 * than one column may have the same name if this is a crosstabbed Dataset.
	 * 
	 * @param columnName
	 * @return
	 */
	public Collection<Integer> getColumnIndexes(String columnName);

	public int[] getColumnIndexes(String... columnNames);

	/**
	 * Returns the indexes of everyColumn except those indicated.
	 * 
	 * @param columnNames
	 * @return
	 */
	public int[] getNonColumnIndexes(String... columnNames);

	public MutableDatasetMetadata getMetadataForColumns(String... columnNames);

	public DatasetMetadata getMetadataForNonColumns(String... columnNames);

	/**
	 * TODO what is this
	 * 
	 * @param meta
	 */
	public void enhanceMetadata(Map<String, ColumnMetadata> meta);

	public String getColumnTypeName(int columnNumber);

}
