package org.javautil.dataset.render.typewriter;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javautil.document.style.Style;

public class TypewriterRendererImpl<R, C> implements TypewriterRenderer {

	private final Log logger = LogFactory.getLog(getClass());

	private TypewriterContent<R, C> content;

	private TypewriterBehavior behavior;

	private int maxX = 0;

	private int maxY = 0;

	private int columnIndex = 0;

	private int rowIndex = 0;

	private boolean createMissingRows = true;

	private boolean createMissingCells = true;

	private boolean vertical = false;

	@Override
	public void addStyles(final Map<String, Style> styles) {
		final Map<String, Style> originalStyles = content.getStylesByName();
		for (final String styleName : styles.keySet()) {
			final Style newStyle = styles.get(styleName);
			final Style oldStyle = originalStyles.get(styleName);
			if (oldStyle == null) {
				originalStyles.put(styleName, newStyle);
			} else if (newStyle != oldStyle) {
				throw new IllegalStateException("cannot add style [" + newStyle
						+ "] with name " + styleName + ", another style "
						+ "already exists with the same name");
			}
		}
	}

	public Style getNamedStyle(final String styleName) {
		final Style style = content.getStylesByName().get(styleName);
		if (style == null) {
			throw new IllegalArgumentException("no style named: " + styleName);
		}
		return style;
	}

	public TypewriterRendererImpl() {
	}

	public TypewriterRendererImpl(final TypewriterContent<R, C> content,
			final TypewriterBehavior behavior) {
		this.content = content;
		this.behavior = behavior;
		initialize();
	}

	protected void initialize() {
		// content
		if (content == null) {
			throw new IllegalArgumentException("content is null");
		}
		initializeBehavior(behavior);
		maxX = behavior.getTopLeftPosition().x;
		maxY = behavior.getTopLeftPosition().y;
	}

	protected void initializeBehavior(final TypewriterBehavior behavior) {
		// behavior orientation
		if (behavior.getOrientation() == TypewriterOrientation.HORIZONTAL) {
			if (logger.isDebugEnabled()) {
				logger.debug("using horizontal typewriter render");
			}
		} else if (behavior.getOrientation() == TypewriterOrientation.VERTICAL) {
			if (logger.isDebugEnabled()) {
				vertical = true;
				logger.debug("using vertical typewriter render");
			}
		} else {
			throw new IllegalArgumentException("unknown behavior.orientation "
					+ behavior.getOrientation());
		}
		// behavior position
		if (behavior.getTopLeftPosition() != null) {
			final Point topLeftPosition = behavior.getTopLeftPosition();
			if (logger.isDebugEnabled()) {
				logger.debug("moving to position: " + topLeftPosition);
			}
			createMissingRowsAndCells(topLeftPosition.y, topLeftPosition.x);
			setColumnIndex(topLeftPosition.x);
			setRowIndex(topLeftPosition.y);
		}
	}

	@Override
	public void setColumnIndex(final int columnIndex) {
		if (this.columnIndex == columnIndex) {
			if (logger.isWarnEnabled()) {
				logger.warn("setColumnIndex was called for the current "
						+ "column: " + columnIndex);
			}
		} else {
			if (logger.isTraceEnabled()) {
				logger.trace("now rendering cells for column: " + columnIndex);
			}
			this.columnIndex = columnIndex;
		}
		this.columnIndex = columnIndex;
		createMissingCells(rowIndex, columnIndex);
	}

	@Override
	public void setRowIndex(final int rowIndex) {
		if (this.rowIndex == rowIndex) {
			if (logger.isWarnEnabled()) {
				logger.warn("setRowIndex was called for the current row: "
						+ rowIndex);
			}
		} else {
			if (logger.isTraceEnabled()) {
				logger.trace("now rendering cells for row: " + rowIndex);
			}
			this.rowIndex = rowIndex;
		}
		this.rowIndex = rowIndex;
		createMissingRows(rowIndex);
		createMissingCells(rowIndex, columnIndex);
	}

	protected void createMissingRows(final int y2) {
		if (isCreateMissingRows()) {
			for (int y = 0; y <= y2; y++) {
				content.getRowAt(y, true);
			}
		}
	}

	protected void createMissingRowsAndCells(final int y2, final int x2) {
		if (isCreateMissingRows()) {
			for (int y = 0; y <= y2; y++) {
				content.getRowAt(y, true);
				createMissingCells(y, x2);
			}
		}
	}

	protected void createMissingCells(final int y, final int x2) {
		if (isCreateMissingCells()) {
			for (int x = 0; x <= x2; x++) {
				content.getCellAt(y, x, false, true);
			}
		}
	}

	protected int incrementColumn() {
		columnIndex++;
		if (columnIndex > maxX) {
			maxX = columnIndex;
		}
		return columnIndex;
	}

	protected int incrementRow() {
		createMissingRows(++rowIndex);
		if (rowIndex > maxY) {
			maxY = rowIndex;
		}
		return rowIndex;
	}

	@Override
	public void addBlank(final String styleName) {
		final Style style = getNamedStyle(styleName);
		content.setBlankCellAt(rowIndex, columnIndex, style);
		if (vertical) {
			incrementRow();
		} else {
			incrementColumn();
		}
	}

	@Override
	public void addData(final Object data, final String styleName) {
		final Style style = getNamedStyle(styleName);
		content.setCellAt(rowIndex, columnIndex, data, style);
		if (vertical) {
			incrementRow();
		} else {
			incrementColumn();
		}
	}

	@Override
	public void addFormula(final String formula, final String styleName) {
		content.setFormulaCellAt(rowIndex, columnIndex, formula,
				getNamedStyle(styleName));
		if (vertical) {
			incrementRow();
		} else {
			incrementColumn();
		}
	}

	@Override
	public void nextLine() {
		if (vertical) {
			incrementColumn();
			rowIndex = behavior.getTopLeftPosition().y;
			createMissingCells(rowIndex, columnIndex);
		} else {
			incrementRow();
			columnIndex = behavior.getTopLeftPosition().x;
			createMissingCells(rowIndex, columnIndex);
		}
	}

	@Override
	public void skip(final int numberOfCells) {
		for (int i = 0; i < numberOfCells; i++) {
			if (vertical) {
				incrementRow();
			} else {
				incrementColumn();
			}
			content.getCellAt(rowIndex, columnIndex, false, true);
		}
	}

	@Override
	public Dimension getDimension() {
		final Point home = behavior.getTopLeftPosition();
		return new Dimension(maxX - home.x, maxY - home.y);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(behavior.getTopLeftPosition(), getDimension());
	}

	@Override
	public int getColumnIndex() {
		return columnIndex;
	}

	@Override
	public int getRowIndex() {
		return rowIndex;
	}

	public TypewriterContent<R, C> getContent() {
		return content;
	}

	public void setContent(final TypewriterContent<R, C> content) {
		this.content = content;
	}

	@Override
	public TypewriterBehavior getBehavior() {
		return behavior;
	}

	@Override
	public void setBehavior(final TypewriterBehavior behavior) {
		if (behavior != this.behavior) {
			initializeBehavior(this.behavior);
		}
		this.behavior = behavior;
	}

	public boolean isCreateMissingRows() {
		return createMissingRows;
	}

	public void setCreateMissingRows(final boolean createMissingRows) {
		this.createMissingRows = createMissingRows;
	}

	public boolean isCreateMissingCells() {
		return createMissingCells;
	}

	public void setCreateMissingCells(final boolean createMissingCells) {
		this.createMissingCells = createMissingCells;
	}

}
