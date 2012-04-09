package org.javautil.dataset;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class ColumnMetaMap {

	public static Map<String, Integer> getColumnNameIndexMap(
			final List<ColumnMetadata> columns) {
		LinkedHashMap<String, Integer> columnNameIndexMap = new LinkedHashMap<String, Integer>();

		columnNameIndexMap = new LinkedHashMap<String, Integer>();
		int indexNbr = 0;
		for (final ColumnMetadata col : columns) {
			if (col == null) {
				final Logger logger = Logger.getLogger(ColumnMetaMap.class);
				logger.warn("null column found at index " + indexNbr);
			} else {
				columnNameIndexMap.put(col.getColumnName(), indexNbr++);
			}
		}

		return columnNameIndexMap;
	}
}
