package org.javautil.dataset;

import java.util.List;

import org.apache.log4j.Logger;
import org.javautil.collections.Tuple;

public class GroupedByOperations {

	private static Logger logger = Logger.getLogger(GroupedByOperations.class);

	/**
	 * Sum the given column for each GroupedBy in GroupBy. todo edit
	 * <ul>
	 * <li>Computes the sum of value of the specified column for each GroupedBy.
	 * </li>
	 * <li>Alters the meta data for the group, adding the definition for the new
	 * column which has the name specified by _columnAlias, if specified
	 * otherwise gets the value of "sum(COLUMN_NAME)" where COLUMN_NAME is the
	 * value of columnName.</li>
	 * </ul>
	 * 
	 * @param groups
	 * @param columnName
	 * @param _columnAlias
	 */
	@SuppressWarnings("unchecked")
	public static void sum(final GroupBy<?> groups, final String columnName,
			final String _columnAlias) {

		if (groups == null) {
			throw new IllegalArgumentException("groups is null");
		}
		if (columnName == null) {
			throw new IllegalArgumentException("columnName is null");
		}

		final String columnAlias = _columnAlias == null ? "sum(" + columnName
				+ ")" : _columnAlias;

		// add the new column to the metadata for the grouped columns
		// this is special. We are doing this with the knowledge that
		// GroupedBy meta information is shared by all GroupedBy participants in
		// a GroupBy
		final MutableDatasetMetadata dm = groups.getGroupedByMeta();
		final ColumnMetadata cm = new ColumnMetadata();
		cm.setColumnName(columnAlias);
		cm.setDataType(DataType.DOUBLE);
		cm.setInjectedGroupField(true);
		dm.addColumn(cm);
		//
		for (final GroupedBy groupedBy : groups.getGroupedBy()) {
			final double groupSum = sum(groupedBy, columnName);
			final Tuple t = new Tuple(groupedBy.getGroupColumns(), new Double(
					groupSum));
			groupedBy.setGroupColumns(t);
			if (logger.isDebugEnabled()) {
				logger.debug(groupedBy.toString());
			}
		}

		// loop through the grouped data, compute the value and add to the

	}

	static double sum(final GroupedBy groupedBy, final String columnName) {
		// does not return null under any circumstances, even if the set is
		// empty

		if (groupedBy == null) {
			throw new IllegalArgumentException("groupedBy is null");
		}
		if (columnName == null) {
			throw new IllegalArgumentException("columnName is null");
		}
		final DatasetMetadata meta = groupedBy.getNongroupedColumnsMeta();
		final int columnIndex = meta.getColumnIndex(columnName);
		final List<Tuple<?>> tuples = groupedBy.getNongroupedColumns();
		double d = 0.0;
		for (final Tuple<?> tuple : tuples) {
			d += tuple.getDouble(columnIndex);
		}
		return d;
	}

}
