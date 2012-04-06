package org.javautil.document.crosstab;

import java.util.List;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
public class CrosstabColumnsImpl implements CrosstabColumns {
	private final List<String> cellIndentifiers;
	private final String columnIdentifier;
	private final List<String> rowIdentifiers;

	public CrosstabColumnsImpl(final List<String> rowIdentifiers,
			final String columnIdentifier, final List<String> cellIdentifiers) {
		this.rowIdentifiers = rowIdentifiers;
		this.columnIdentifier = columnIdentifier;
		this.cellIndentifiers = cellIdentifiers;
	}

	@Override
	public List<String> getCellIdentifiers() {
		return cellIndentifiers;
	}

	@Override
	public String getColumnIdentifier() {
		return columnIdentifier;
	}

	@Override
	public List<String> getRowIdentifiers() {
		return rowIdentifiers;
	}
}
