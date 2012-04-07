package org.javautil.dataset;

import org.javautil.document.crosstab.CrosstabColumns;

public interface CrosstabbedDataset {
	/**
	 * The values used to crosstab this dataset TODO may not be accurate after
	 * adornment or injection
	 * 
	 * @return
	 */
	CrosstabColumns getCrosstabColumns();

	void setCrosstabColumns(CrosstabColumns crosstabColumns);
}
