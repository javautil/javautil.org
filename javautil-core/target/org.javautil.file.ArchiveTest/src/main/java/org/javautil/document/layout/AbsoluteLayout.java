package org.javautil.document.layout;

import java.awt.Point;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javautil.document.DocumentRegion;
import org.javautil.document.renderer.DocumentRenderStatus;

public class AbsoluteLayout implements LayoutConstraints {

	private int x = 0;

	private int y = 0;

	private Point topLeftCoordinate;

	private final Log logger = LogFactory.getLog(getClass());

	public AbsoluteLayout() {
	}

	public AbsoluteLayout(final Point topLeftCoordinate) {
		this.topLeftCoordinate = topLeftCoordinate;
	}

	@Override
	public void performLayout(final DocumentRenderStatus status) {
		final DocumentRegion region = status.getCurrentRegion();
		if (region == null) {
			throw new IllegalStateException("status does not have a "
					+ "currentRegion set");
		}
		if (topLeftCoordinate == null) {
			topLeftCoordinate = new Point(x, y);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("setting topLeftCoordinate for region "
					+ region.getName() + " to " + topLeftCoordinate);
		}
		status.setTopLeftCoordinate(region, getTopLeftCoordinate());
	}

	public Point getTopLeftCoordinate() {
		return topLeftCoordinate;
	}

	public void setTopLeftCoordinate(final Point topLeftCoordinate) {
		this.topLeftCoordinate = topLeftCoordinate;
	}

	public int getX() {
		return x;
	}

	public void setColumn(final int column) {
		setX(column);
	}

	public void setX(final int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setRow(final int row) {
		setY(row);
	}

	public void setY(final int y) {
		this.y = y;
	}

}
