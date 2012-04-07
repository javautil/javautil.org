package org.javautil.dataset.render.typewriter;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Map;

import org.javautil.document.style.Style;

public interface TypewriterContent<R, C> {

	public Map<String, Style> getStylesByName();

	public void setStylesByName(Map<String, Style> stylesByName);

	public R getRowAt(int rowIndex, boolean createRow);

	public C getCellAt(int rowIndex, int columnIndex, boolean createRow,
			boolean createCell);

	public void setBlankCellAt(int rowIndex, int columnIndex, Style style);

	public void setCellAt(int rowIndex, int columnIndex, Object data,
			Style style);

	public void setFormulaCellAt(int rowIndex, int columnIndex, String formula,
			Style style);

	public TypewriterRendererFactory getRendererFactory();

	public Dimension getDimension();

	public Rectangle getBounds();

}