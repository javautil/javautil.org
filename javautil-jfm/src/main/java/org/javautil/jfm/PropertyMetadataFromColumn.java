package org.javautil.jfm;

import org.javautil.dataset.ColumnMetadata;
import org.springframework.beans.factory.InitializingBean;

/**
 * Implementation of PropertyMetaData backed by a ColumnMetadata object.
 * 
 * @author bcm-javautil
 */
public class PropertyMetadataFromColumn implements InitializingBean,
		PropertyMetadata {

	private ColumnMetadata column;

	public ColumnMetadata getColumn() {
		return column;
	}

	public void setColumn(final ColumnMetadata column) {
		this.column = column;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (column == null) {
			throw new IllegalStateException("column is null");
		}
	}

	@Override
	public String getDescription() {
		return column.getComments();
	}

	@Override
	public String getHeading() {
		return column.getHeading();
	}

	@Override
	public String getLabel() {
		return column.getLabel();
	}

	@Override
	public String getName() {
		return column.getColumnName();
	}

}
