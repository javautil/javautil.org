package org.javautil.dataset;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.javautil.document.style.HorizontalAlignment;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
public class DatasetMetadataImpl implements MutableDatasetMetadata {

	private final ArrayList<ColumnMetadata> list = new ArrayList<ColumnMetadata>();

	private final TreeMap<String, List<Integer>> columnIndexesByColumnName = new TreeMap<String, List<Integer>>();
	private final Logger logger = Logger.getLogger(getClass());

	private final String newline = System.getProperty("line.separator");

	public DatasetMetadataImpl() {

	}

	public DatasetMetadataImpl(final List<ColumnMetadata> columns) {
		for (final ColumnMetadata column : columns) {
			addColumn(column);
		}
		buildIndexes();
	}

	/**
	 * Adds the column and sets the column index in column
	 * 
	 * @param column
	 */
	@Override
	public void addColumn(final ColumnMetadata column) {
		list.add(column);
		buildIndexes();
	}

	@Override
	public void addColumn(final int index, final ColumnMetadata column) {
		// if (index == null) {
		// throw new IllegalArgumentException("index is null");
		// }
		if (column == null) {
			throw new IllegalArgumentException("column is null");
		}
		list.add(index, column);
		buildIndexes();
	}

	// todo jjs write test
	@Override
	public int[] getColumnIndexes(final String... columnNames) {
		final int[] columnIndexes = new int[columnNames.length];
		int columnIndex = 0;

		for (final String columnName : columnNames) {
			columnIndexes[columnIndex++] = getColumnIndex(columnName);
		}

		return columnIndexes;
	}

	/**
	 * Returns the indexes of everyColumn except those indicated.
	 * 
	 * @param columnNames
	 * @return
	 */
	@Override
	public int[] getNonColumnIndexes(final String... columnNames) {
		final TreeSet<Integer> indexes = new TreeSet<Integer>();
		for (int i = 0; i < list.size(); i++) {
			indexes.add(i);
		}
		for (final int j : getColumnIndexes(columnNames)) {
			indexes.remove(j);
		}
		final int[] retval = new int[indexes.size()];
		int k = 0;
		for (final Integer in : indexes) {
			retval[k++] = in;
		}
		return retval;
	}

	@Override
	public MutableDatasetMetadata getMetadataForColumns(
			final String... columnNames) {
		return getByIndex(getColumnIndexes(columnNames));
	}

	@Override
	public DatasetMetadata getMetadataForNonColumns(final String... columnNames) {
		return getByIndex(getNonColumnIndexes(columnNames));
	}

	public MutableDatasetMetadata getByIndex(final int[] indexes) {
		final DatasetMetadataImpl dmi = new DatasetMetadataImpl();
		for (final int index : indexes) {
			dmi.addColumn(getColumnMetaData(index));
		}
		return dmi;
	}

	/**
	 * todo review
	 */
	private void buildIndexes() {
		columnIndexesByColumnName.clear();
		for (int index = 0; index < list.size(); index++) {
			final ColumnMetadata columnMetadata = list.get(index);
			final String columnName = columnMetadata.getColumnName();
			if (columnName == null) {
				throw new IllegalArgumentException("columnName is null "
						+ columnMetadata);
			}
			List<Integer> indexList = columnIndexesByColumnName.get(columnName);
			if (indexList == null) {
				indexList = new ArrayList<Integer>();
				columnIndexesByColumnName.put(columnName, indexList);
			}
			indexList.add(index);
			if (logger.isDebugEnabled() && indexList.size() > 1) {
				// todo, what is this??
				logger.trace("duplicate column name : '" + columnName + "' at "
						+ index);
			}
		}
	}

	private ColumnMetadata get(final int index) {
		try {
			return list.get(index);
		} catch (final ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException(
					"unable to get column information for index " + index
							+ " columns " + list.size());
		}
	}

	@Override
	public int getColumnCount() throws DatasetException {
		return list.size();
	}

	@Override
	public Integer getColumnDisplaySize(final int columnNumber)
			throws DatasetException {
		return get(columnNumber).getColumnDisplaySize();
	}

	@Override
	public Integer getColumnIndex(final String columnName) {
		final List<Integer> retval = columnIndexesByColumnName.get(columnName);
		if (retval == null || retval.size() != 1) {
			final List<Integer> cids = columnIndexesByColumnName
					.get(columnName);
			final StringBuilder sb = new StringBuilder();
			sb.append("no such column or duplicate column '");
			sb.append(columnName);
			sb.append("' ");
			sb.append(newline);
			sb.append(toString());
			sb.append("indexes are " + cids);
			throw new MetadataException(sb.toString());
		}
		return retval.get(0);
	}

	@Override
	public ColumnMetadata getColumnMetaData(final int columnIndex) {
		try {
			return list.get(columnIndex);
		} catch (final ArrayIndexOutOfBoundsException e) {
			throw new IllegalArgumentException("request for column at index "
					+ columnIndex + " column count " + list.size());
		} catch (final IndexOutOfBoundsException e) {
			throw new IllegalArgumentException("request for column at index "
					+ columnIndex + " column count " + list.size());
		}
	}

	@Override
	public ColumnMetadata getColumnMetaData(final Integer index) {
		final ColumnMetadata retval = list.get(index);
		if (retval == null) {
			throw new IllegalArgumentException("no such column");
		}
		return retval;
	}

	@Override
	public String getColumnName(final int i) throws DatasetException {
		return get(i).getColumnName();
	}

	@Override
	public DataType getColumnType(final int index) throws DatasetException {
		return get(index).getDataType();
	}

	@Override
	public MutableDatasetMetadata getMutable() {
		return this;
	}

	@Override
	public Integer getPrecision(final int columnNumber) throws DatasetException {
		return get(columnNumber).getPrecision();
	}

	@Override
	public int getRowCount() throws DatasetException {
		return 0;
	}

	@Override
	public Integer getScale(final int columnNumber) throws DatasetException {
		return get(columnNumber).getScale();
	}

	@Override
	public String toString() {
		final StringBuilder b = new StringBuilder();
		// for (ColumnMetadata c : list) {
		// b.append(c.toString());
		// b.append(newline);
		// }
		for (int index = 0; index < list.size(); index++) {
			final ColumnMetadata columnMetaData = list.get(index);
			b.append(columnMetaData);
			b.append(newline);

		}
		return b.toString();
	}

	@Override
	public HorizontalAlignment getAlignment(final int i) {
		return get(i).getHorizontalAlignment();
	}

	@Override
	public String getExcelFormat(final int i) {
		return get(i).getExcelFormat();
	}

	@Override
	public String getJavaFormat(final int i) {
		return get(i).getJavaFormat();
	}

	@Override
	public ColumnMetadata getColumnMetaData(final String columnName) {
		return getColumnMetaData(getColumnIndex(columnName));
	}

	@Override
	public ArrayList<ColumnMetadata> getColumnMetadata() {
		return list;
	}

	@Override
	public Collection<Integer> getColumnIndexes(final String columnName) {
		return columnIndexesByColumnName.get(columnName);
	}

	@Override
	public void enhanceMetadata(final Map<String, ColumnMetadata> meta) {
		new HashSet<String>();
		meta.keySet();
		ColumnMetadata colMeta = null;

		for (final ColumnMetadata enhancedColMeta : meta.values()) {
			// logger.error(enhancedColMeta);
			colMeta = null;
			for (final ColumnMetadata md : list) {
				if (md.getColumnName().equals(enhancedColMeta.getColumnName())) {
					colMeta = getColumnMetaData(enhancedColMeta.getColumnName());
				}
			}
			// ColumnMetadata enhancedColMeta =
			// meta.get(colMeta.getColumnName());
			if (colMeta == null) {
				addColumn(enhancedColMeta);
				// if (logger.isDebugEnabled()) {
				// logger.debug("adding " + enhancedColMeta);
				// }
			} else {

				// if (enhancedColMeta != null) {

				// colMeta.setDataType(enhancedColMeta.getDataType());
				colMeta.setAggregateFunction(enhancedColMeta
						.getAggregateFunction());
				colMeta.setAttributeName(enhancedColMeta.getAttributeName());
				colMeta.setColumnDisplaySize(enhancedColMeta
						.getColumnDisplaySize());
				colMeta.setComments(enhancedColMeta.getComments());
				// colMeta.setDataType(enhancedColMeta.getDataType());
				colMeta.setExcelFormat(enhancedColMeta.getExcelFormat());
				colMeta.setJavaFormat(enhancedColMeta.getJavaFormat());
				colMeta.setExternalFlag(enhancedColMeta.isExternalFlag());
				colMeta.setGroupName(enhancedColMeta.getGroupName());
				colMeta.setHeading(enhancedColMeta.getHeading());
				colMeta.setHorizontalAlignment(enhancedColMeta
						.getHorizontalAlignment());
				colMeta.setLabel(enhancedColMeta.getLabel());
				colMeta.setWorkbookFormula(enhancedColMeta.getWorkbookFormula());
				// addColumn(colMeta);
			}
		}
	}

	@Override
	public String getColumnTypeName(int columnNumber) {
		return getColumnMetaData(columnNumber).getColumnTypeName();
	}

}
