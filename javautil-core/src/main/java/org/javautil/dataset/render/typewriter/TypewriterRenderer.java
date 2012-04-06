package org.javautil.dataset.render.typewriter;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Map;

import org.javautil.document.style.Style;

public interface TypewriterRenderer {

	public TypewriterBehavior getBehavior();

	public void setBehavior(TypewriterBehavior behavior);

	public void nextLine();

	public int getRowIndex();

	public void setRowIndex(int rowIndex);

	public int getColumnIndex();

	public void setColumnIndex(int columnIndex);

	public void skip(int numberOfCells);

	public void addBlank(String styleName);

	public void addData(Object data, String styleName);

	public void addFormula(String formula, String styleName);

	public void addStyles(Map<String, Style> styles);

	public Dimension getDimension();

	public Rectangle getBounds();

}
