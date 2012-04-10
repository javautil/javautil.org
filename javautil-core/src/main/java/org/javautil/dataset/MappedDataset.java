package org.javautil.dataset;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.javautil.collections.ArrayComparator;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
public class MappedDataset {

	@SuppressWarnings("unchecked")
	private final TreeMap<Object[], MappedDatasetRow> rows = new TreeMap<Object[], MappedDatasetRow>(
			new ArrayComparator());

	private final TreeMap<Object[], Integer> rownumById = new TreeMap<Object[], Integer>(
			new ArrayComparator());
	private final TreeMap<Integer, Object[]> idByRownum = new TreeMap<Integer, Object[]>();
	private final List<String> keyCols;

	private final Dataset unmappedDataSet;
	private Integer[] colIdIndex;
	private DatasetIterator<?> iter;
	private DatasetMetadata unmappedMeta;
	private final Map<Integer, ColumnMetadata> idColMeta = new TreeMap<Integer, ColumnMetadata>();
	private final Map<Integer, ColumnMetadata> valColMeta = new TreeMap<Integer, ColumnMetadata>();

	public MappedDataset(final Dataset ds, final List<String> keyCol) {
		if (ds == null) {
			throw new IllegalArgumentException("ds is null");
		}
		if (keyCol == null) {
			throw new IllegalArgumentException("keyCol is null");
		}
		this.unmappedDataSet = ds;
		this.keyCols = keyCol;
		iter = unmappedDataSet.getDatasetIterator();
		resolveIdentifier();
		createMap();
	}

	private void createMap() {
		int rownum = 0;
		while (iter.next()) {
			// key
			final Object[] id = new Object[idColMeta.size()];
			int i = 0;
			for (final Integer index : colIdIndex) {
				id[i] = iter.getObject(index);
				i++;
			}
			// values
			int j = 0;
			final Object[] val = new Object[valColMeta.size()];
			for (final Integer index : valColMeta.keySet()) {
				val[j] = iter.getObject(index);
				j++;
			}
			final MappedDatasetRow row = new MappedDatasetRow(id, val, rownum);
			final MappedDatasetRow oldRow = rows.put(id, row);
			if (oldRow != null) {
				throw new IllegalArgumentException("duplicate mapping "
						+ row.toString() + " colides with " + oldRow.toString());
			}
			rownumById.put(id, rownum);
			idByRownum.put(rownum, id);
			rownum++;
		}
	}

	public MappedDatasetRow getRowById(final Object[] id) {
		return rows.get(id);
	}

	public Collection<MappedDatasetRow> getRows() {
		return rows.values();
	}

	public Collection<ColumnMetadata> getValueColumnMetaData() {
		return valColMeta.values();
	}

	private void resolveIdentifier() {
		colIdIndex = new Integer[keyCols.size()];
		// Object[] id = new Object[keyCols.size()];
		iter = unmappedDataSet.getDatasetIterator();
		unmappedMeta = iter.getDatasetMetadata();
		int i = 0;
		for (final String colName : keyCols) {
			final Integer index = unmappedMeta.getColumnIndex(colName);
			colIdIndex[i] = index;
			idColMeta.put(index, unmappedMeta.getColumnMetaData(index));
			i++;
		}
		for (int j = 0; j < unmappedMeta.getColumnCount(); j++) {
			if (idColMeta.get(j) == null) {
				valColMeta.put(j, unmappedMeta.getColumnMetaData(j));
			}
		}
	}

	@Override
	public String toString() {
		final StringBuilder b = new StringBuilder();
		for (final MappedDatasetRow row : rows.values()) {
			b.append(row.toString());
		}
		return b.toString();
	}

}
