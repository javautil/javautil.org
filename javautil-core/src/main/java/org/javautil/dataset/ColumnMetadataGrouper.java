package org.javautil.dataset;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

public class ColumnMetadataGrouper {
	private static Logger logger = Logger
			.getLogger(ColumnMetadataGrouper.class);

	public String[] getGroupNames(final List<ColumnMetadata> meta) {
		final LinkedHashSet<String> groupNames = new LinkedHashSet<String>();
		for (final ColumnMetadata cm : meta) {
			if (cm.getGroupName() != null) {
				groupNames.add(cm.getGroupName());
			}
		}
		return groupNames.toArray(new String[groupNames.size()]);
	}

	public LinkedList<ColumnMetadata> getGroup(final List<ColumnMetadata> meta,
			final String groupName) {
		final LinkedList<ColumnMetadata> columns = new LinkedList<ColumnMetadata>();
		for (final ColumnMetadata cm : meta) {
			if ((groupName == null && cm.getGroupName() == null)
					|| (groupName != null && groupName
							.equals(cm.getGroupName()))) {
				columns.add(cm);
			}
		}
		return columns;
	}

	public static LinkedHashMap<String, Integer> getColumnNameIndexMap(
			final Dataset dataset, final int startingIndex,
			final Collection<ColumnMetadata> columns, final String groupName) {
		LinkedHashMap<String, Integer> columnNameIndexMap = new LinkedHashMap<String, Integer>();

		columnNameIndexMap = new LinkedHashMap<String, Integer>();
		int indexNbr = startingIndex;
		for (final ColumnMetadata col : columns) {
			if (col == null) {

				logger.warn("null column found at index " + indexNbr);
			} else {
				if (col.getGroupName() == groupName
						|| col.getGroupName() == null) {
					columnNameIndexMap.put(col.getColumnName(), indexNbr++);
				}
			}
		}
		return columnNameIndexMap;
	}

}
