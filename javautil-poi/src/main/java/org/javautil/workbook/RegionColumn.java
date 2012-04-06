package org.javautil.workbook;

import org.javautil.document.style.Style;

public interface RegionColumn {

	public abstract Integer getColumnNumber();

	public abstract Integer getSourceColumnIndex();

	public abstract String getColumnName();

	public abstract String getHeading();

	public abstract Style getStyle();

}
