package org.javautil.document.crosstab;

import java.util.List;

import org.javautil.text.AsString;

/**
 * @author jjs
 */
public class CrosstabColumnsCompoundImpl implements CompoundCrosstabColumns {
	private final List<String> cellIndentifiers;
	private final List<String> columnIdentifier;
	private final List<String> rowIdentifiers;

	public CrosstabColumnsCompoundImpl(final List<String> rowIdentifiers,
			final List<String> columnIdentifier,
			final List<String> cellIdentifiers) {
		this.rowIdentifiers = rowIdentifiers;
		this.columnIdentifier = columnIdentifier;
		this.cellIndentifiers = cellIdentifiers;

	}

	@Override
	public List<String> getCellIdentifiers() {
		return cellIndentifiers;
	}

	public List<String> getCellIndentifiers() {
		return cellIndentifiers;
	}

	@Override
	public List<String> getColumnIdentifiers() {
		return columnIdentifier;
	}

	@Override
	public List<String> getRowIdentifiers() {
		return rowIdentifiers;
	}

	@Override
	public String toString() {
		final AsString as = new AsString();
		return as.toString(this);
	}

}
