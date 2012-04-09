package org.javautil.dataset.render.typewriter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javautil.document.style.Style;
import org.javautil.text.StringRenderSupport;

// TODO Can  we have a hint of what R and C are
public abstract class AbstractTypewriterContentSupport<R, C> extends
		StringRenderSupport implements TypewriterContent<R, C>,
		TypewriterContents<R, C> {

	private boolean createMissingCells = false;

	private boolean createMissingRows = false;

	private final Log logger = LogFactory.getLog(getClass());

	private Map<String, Style> stylesByName = new LinkedHashMap<String, Style>();

	private TypewriterRendererFactory rendererFactory = null;

	@Override
	public Map<String, Style> getStylesByName() {
		return stylesByName;
	}

	@Override
	public void setStylesByName(final Map<String, Style> stylesByName) {
		this.stylesByName = stylesByName;
	}

	public abstract void setCellData(C cell, Object data, Style style);

	public abstract void setCellFormula(C cell, String formula, Style style);

	@Override
	public abstract void write(OutputStream outputStream) throws IOException;

	@Override
	public void setBlankCellAt(final int rowIndex, final int columnIndex,
			final Style style) {
		if (logger.isTraceEnabled()) {
			logger.trace("blank at (" + rowIndex + ", " + columnIndex + ")");
		}
		final C cell = getCellAt(rowIndex, columnIndex, true, true);
		setCellData(cell, "", style);
	}

	@Override
	public void setCellAt(final int rowIndex, final int columnIndex,
			final Object data, final Style style) {
		if (logger.isTraceEnabled()) {
			logger.trace(data + " at (" + rowIndex + ", " + columnIndex + ")");
		}
		final C cell = getCellAt(rowIndex, columnIndex, true, true);
		setCellData(cell, data, style);
	}

	@Override
	public void setFormulaCellAt(final int rowIndex, final int columnIndex,
			final String formula, final Style style) {
		if (logger.isTraceEnabled()) {
			logger.trace(formula + " at (" + rowIndex + ", " + columnIndex
					+ ")");
		}
		final C cell = getCellAt(rowIndex, columnIndex, true, true);
		setCellFormula(cell, formula, style);
	}

	@Override
	public TypewriterRendererFactory getRendererFactory() {
		if (rendererFactory == null) {
			final TypewriterRendererFactoryImpl<R, C> factory = new TypewriterRendererFactoryImpl<R, C>(
					this);
			factory.setCreateMissingCells(isCreateMissingCells());
			factory.setCreateMissingRows(isCreateMissingRows());
			rendererFactory = factory;
		}
		return rendererFactory;
	}

	@Override
	public TypewriterContent<R, C> getContent(final String identifier) {
		return this;
	}

	public boolean isCreateMissingCells() {
		return createMissingCells;
	}

	public void setCreateMissingCells(final boolean createMissingCells) {
		this.createMissingCells = createMissingCells;
	}

	public boolean isCreateMissingRows() {
		return createMissingRows;
	}

	public void setCreateMissingRows(final boolean createMissingRows) {
		this.createMissingRows = createMissingRows;
	}

}