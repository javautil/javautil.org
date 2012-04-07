package org.javautil.document.renderer;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import org.javautil.document.DocumentRegion;

public interface DocumentRenderStatus {

	public DocumentRegion getCurrentRegion();

	public void setCurrentRegion(DocumentRegion currentRegion);

	public void setCurrentRegionComplete();

	public boolean isRegionRendered(DocumentRegion documentRegion);

	public Rectangle getRegionBounds(DocumentRegion documentRegion);

	public void setTopLeftCoordinate(DocumentRegion documentRegion, Point point);

	public Point getTopLeftCoordinate(DocumentRegion documentRegion);

	public Point getBottomRightCoordinate(DocumentRegion documentRegion);

	public void setDimension(DocumentRegion documentRegion, Dimension dimension);

	public Dimension getDimension(DocumentRegion documentRegion);
}
