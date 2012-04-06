package org.javautil.dataset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
public class DatasetAppender {

	private boolean requireSourceRowForEachTargetRow = false;
	/**
	 * Don't show the data that causes the problem in messages for security
	 * reasons todo implement
	 */
	private boolean suppressRowDataInformationLogging = false;

	/**
	 * Appends the data of another DetachedResultSet to this.
	 * 
	 * @param target
	 * @param columnIdentifiers
	 */
	public void appendRight(final MutableDataset target, final Dataset src,
			final Map<String, String> columnIdentifiers) {

		final List<String> leftKeys = new ArrayList<String>();
		final List<String> rightKeys = new ArrayList<String>();
		List<Object> pad = new ArrayList<Object>();
		for (final String key : columnIdentifiers.keySet()) {
			leftKeys.add(key);
			rightKeys.add(columnIdentifiers.get(key));
		}

		final MappedDataset targetMap = new MappedDataset(target, leftKeys);
		final MappedDataset srcMap = new MappedDataset(src, rightKeys);
		// Number of columns that will be added

		final int newColCount = srcMap.getValueColumnMetaData().size();
		// empty list to add to unmapped source row
		pad = new ArrayList<Object>(newColCount);
		for (int i = 0; i < newColCount; i++) {
			pad.add(null);
		}
		for (final ColumnMetadata columnMeta : srcMap.getValueColumnMetaData()) {
			target.addColumn(columnMeta);
		}

		for (final MappedDatasetRow row : targetMap.getRows()) {
			final Integer targetRowIndex = row.getRownum();
			final Object[] targetId = row.getRowId();

			final MappedDatasetRow srcRow = srcMap.getRowById(targetId);
			if (srcRow != null) {
				final Object[] srcValues = srcRow.getValues();
				target.appendToRow(targetRowIndex, srcValues);
			} else {
				if (requireSourceRowForEachTargetRow) {
					if (suppressRowDataInformationLogging) {
						throw new DatasetOperationException(
								"no source row id logging suppressed ");

					}
					throw new DatasetOperationException("no source row for "
							+ row.toString());
				}
				target.appendToRow(targetRowIndex, pad);
			}
		}

	}

	public boolean isSuppressRowDataInformationLogging() {
		return suppressRowDataInformationLogging;
	}

	public void setSuppressRowDataInformationLogging(
			final boolean suppressRowDataInformationLogging) {
		this.suppressRowDataInformationLogging = suppressRowDataInformationLogging;
	}

	public boolean isRequireSourceRowForEachTargetRow() {
		return requireSourceRowForEachTargetRow;
	}

	public void setRequireSourceRowForEachTargetRow(
			final boolean requireSourceRowForEachTargetRow) {
		this.requireSourceRowForEachTargetRow = requireSourceRowForEachTargetRow;
	}
}
