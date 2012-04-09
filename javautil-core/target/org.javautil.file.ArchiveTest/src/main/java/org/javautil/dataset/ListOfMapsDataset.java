package org.javautil.dataset;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javautil.text.StringUtils;

/**
 * Dataset implementation backed by a List of Maps for use with templating
 * engines like FreeMarker that work best when the model object is of a simple
 * java collections type.
 * 
 * The backing arraylist is modifiable and is constructed from another dataset.
 * 
 * @author bcm
 */
public class ListOfMapsDataset extends ArrayList<Map<String, Object>> implements
		Dataset {

	private static final long serialVersionUID = 1L;

	private DatasetMetadata metadata = null;

	private String name = null;

	private final Log logger = LogFactory.getLog(getClass());

	public ListOfMapsDataset(final Dataset dataset) {
		this.metadata = dataset.getMetadata();
		this.name = dataset.getName();
		populate(dataset);
	}

	public ListOfMapsDataset(final Dataset dataset,
			final DatasetMetadata metadata) {
		this.name = dataset.getName();
		this.metadata = metadata;
		populate(dataset);
	}

	public ListOfMapsDataset(final String name, final DatasetMetadata metadata,
			final List<Map<String, Object>> data) {
		this.name = name;
		this.metadata = metadata;
		for (int rowIndex = 0; rowIndex < data.size(); rowIndex++) {
			final Map<String, Object> row = data.get(rowIndex);
			if (row.size() != metadata.getColumnCount()) {
				throw new IllegalArgumentException(row.size()
						+ " columns were found at rowIndex " + rowIndex
						+ " ; but " + metadata.getColumnCount()
						+ " columns are expected for metadata of type \""
						+ name + "\":\n" + metadata.toString() + "\n"
						+ "actual row map: " + StringUtils.asString(row));
			}
			for (int n = 0; n < metadata.getColumnCount(); n++) {
				final String colName = metadata.getColumnName(n);
				if (!row.containsKey(colName)) {
					throw new IllegalArgumentException("expected column  '"
							+ colName + "' does not exist in dataset row "
							+ rowIndex);
				}
			}
		}
		addAll(data);
	}

	@SuppressWarnings("unchecked")
	protected void populate(final Dataset sourceDataset) {
		final DatasetIterator iterator = sourceDataset.getDatasetIterator();
		final DatasetMetadata sourceMetadata = sourceDataset.getMetadata();
		final Set<String> colNames = new HashSet<String>();
		for (int n = 0; n < sourceMetadata.getColumnCount(); n++) {
			final String colName = sourceMetadata.getColumnName(n);
			if (colNames.contains(colName)) {
				throw new IllegalArgumentException("column " + colName
						+ " is repeated in dataset");
			}
			colNames.add(colName);
		}
		int rowCount = 0;
		while (iterator.next()) {
			final Map<String, Object> row = new LinkedHashMap<String, Object>();
			for (int n = 0; n < sourceMetadata.getColumnCount(); n++) {
				final String colName = sourceMetadata.getColumnName(n);
				final Object value = iterator.getObject(n);
				row.put(colName, value);
			}
			this.add(row);
			rowCount++;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("populated list of maps from dataset \""
					+ sourceDataset.getName() + "\"; " + rowCount
					+ " rows were added");
		}
	}

	@Override
	public void close() throws DatasetException {
	}

	@Override
	public DatasetIterator getDatasetIterator() {
		return new ListOfMapsDatasetIterator(this);
	}

	@Override
	public DatasetMetadata getMetadata() {
		return this.metadata;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(final String name) {
		this.name = name;
	}

	class ListOfMapsDatasetIterator extends AbstractDatasetIterator {

		private ListOfMapsDataset dataset = null;

		public ListOfMapsDatasetIterator(final ListOfMapsDataset dataset) {
			this.dataset = dataset;
		}

		@Override
		public int getRowCount() {
			return size();
		}

		@Override
		public Object getValue(final int rowIndex, final int columnIndex) {
			if (rowIndex < 0) {
				throw new IllegalArgumentException(
						"rowIndex specified cannot be negative");
			}
			final Map<String, ? extends Object> row = get(rowIndex);
			if (row == null) {
				throw new IllegalArgumentException(
						"rowIndex specified too large; last rowIndex is "
								+ rowIndex);
			}
			final String columnName = metadata.getColumnName(columnIndex);
			if (columnName == null) {
				throw new IllegalArgumentException("no column at columnIndex "
						+ columnIndex);
			}
			return row.get(columnName);
		}

		@SuppressWarnings("synthetic-access")
		@Override
		public Object getValue(final int rowIndex, final String columnName) {
			if (rowIndex < 0) {
				throw new IllegalArgumentException(
						"rowIndex specified cannot be negative");
			}
			final Map<String, ? extends Object> row = get(rowIndex);
			if (row == null) {
				throw new IllegalArgumentException(
						"rowIndex specified too large; last rowIndex is "
								+ rowIndex);
			}
			if (metadata.getColumnIndex(columnName) == -1) {
				throw new IllegalArgumentException("no column named "
						+ columnName);
			}
			final Object value = row.get(columnName);
			return value;
		}

		@Override
		@SuppressWarnings("synthetic-access")
		public DatasetMetadata getDatasetMetadata() {
			return metadata;
		}

		@Override
		public Map<String, Object> getRowAsMap() throws DatasetException {
			return dataset.getRowAsMap(super.getRowIndex());
		}

		@Override
		public List<Object> getRowAsList() throws DatasetException {
			return dataset.getRowAsList(super.getRowIndex());
		}
	}

	public List<Object> getRowAsList(final int rowIndex) {
		final ArrayList<Object> ret = new ArrayList<Object>();
		final Map<String, ? extends Object> row = get(rowIndex);
		if (row == null) {
			throw new IllegalArgumentException("no row at index " + rowIndex);
		}
		for (final String columnName : row.keySet()) {
			ret.add(row.get(columnName));
		}
		return ret;
	}

	public Map<String, Object> getRowAsMap(final int rowIndex) {
		final Map<String, Object> row = get(rowIndex);
		if (row == null) {
			throw new IllegalArgumentException("no row at index " + rowIndex);
		}
		return row;
	}

}
