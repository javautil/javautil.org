package org.javautil.document.renderer;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

import org.javautil.document.Document;
import org.javautil.document.DocumentRegion;

public class DocumentRenderStatusImpl implements DocumentRenderStatus {

	private DocumentRegion currentRegion;

	private final Document document;

	private final Map<DocumentRegion, Boolean> renderStatus = new HashMap<DocumentRegion, Boolean>();

	private final Map<DocumentRegion, Dimension> dimensions = new HashMap<DocumentRegion, Dimension>();

	private final Map<DocumentRegion, Point> topLeftCoordinates = new HashMap<DocumentRegion, Point>();

	public DocumentRenderStatusImpl(final Document document) {
		this.document = document;
	}

	protected void checkRegion(final DocumentRegion documentRegion) {
		if (documentRegion == null) {
			throw new IllegalStateException("documentRegion is null");
		}
		if (!document.getRegions().contains(documentRegion)) {
			throw new IllegalStateException("documentRegion is not in "
					+ "document: " + document);
		}
	}

	@Override
	public void setTopLeftCoordinate(final DocumentRegion documentRegion,
			final Point coordinate) {
		checkRegion(documentRegion);
		topLeftCoordinates.put(documentRegion, coordinate);
	}

	@Override
	public void setDimension(final DocumentRegion documentRegion,
			final Dimension size) {
		checkRegion(documentRegion);
		dimensions.put(documentRegion, size);
	}

	@Override
	public Point getBottomRightCoordinate(final DocumentRegion documentRegion) {
		checkRegion(documentRegion);
		Point ret = null;
		final Rectangle rect = getRegionBounds(documentRegion);
		if (ret != null) {
			final Dimension size = rect.getSize();
			ret = new Point(rect.x + size.width, rect.y + size.height);
		}
		return ret;
	}

	@Override
	public void setCurrentRegionComplete() {
		renderStatus.put(currentRegion, Boolean.TRUE);
	}

	@Override
	public boolean isRegionRendered(final DocumentRegion documentRegion) {
		checkRegion(documentRegion);
		final Boolean status = renderStatus.get(documentRegion);
		return status == null ? false : status.booleanValue();
	}

	@Override
	public Dimension getDimension(final DocumentRegion documentRegion) {
		checkRegion(documentRegion);
		return dimensions.get(documentRegion);
	}

	@Override
	public Rectangle getRegionBounds(final DocumentRegion documentRegion) {
		checkRegion(documentRegion);
		final Point coordinate = topLeftCoordinates.get(documentRegion);
		final Dimension dimension = dimensions.get(documentRegion);
		Rectangle rect = null;
		if (coordinate != null && dimension != null) {
			rect = new Rectangle(coordinate, dimension);
		}
		return rect;
	}

	@Override
	public Point getTopLeftCoordinate(final DocumentRegion documentRegion) {
		checkRegion(documentRegion);
		return topLeftCoordinates.get(documentRegion);
	}

	@Override
	public DocumentRegion getCurrentRegion() {
		return currentRegion;
	}

	@Override
	public void setCurrentRegion(final DocumentRegion currentRegion) {
		checkRegion(currentRegion);
		this.currentRegion = currentRegion;
	}

}
