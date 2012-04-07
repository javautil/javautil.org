package org.javautil.document.layout;

import java.awt.Point;
import java.awt.Rectangle;

import org.javautil.document.DocumentRegion;
import org.javautil.document.renderer.DocumentRenderStatus;

public class RelativeLayout implements LayoutConstraints {

	private DocumentRegion leftOfRegion;

	private DocumentRegion rightOfRegion;

	private DocumentRegion belowRegion;

	private DocumentRegion aboveRegion;

	@Override
	public void performLayout(final DocumentRenderStatus status) {
		if (status == null) {
			throw new IllegalArgumentException("status is null");
		}
		final DocumentRegion region = status.getCurrentRegion();
		if (region == null) {
			throw new IllegalArgumentException("status does not have a "
					+ "currentRegion set");
		}
		// Rectangle leftOfBounds = null;
		Rectangle rightOfBounds = null;
		Rectangle belowBounds = null;

		if (rightOfRegion != null) {
			rightOfBounds = status.getRegionBounds(rightOfRegion);
			if (rightOfRegion == status.getCurrentRegion()) {
				throw new IllegalArgumentException("relative region "
						+ "cannot user rightOfRegion to reference itself");
			}
		}
		if (belowRegion != null) {
			belowBounds = status.getRegionBounds(belowRegion);
			if (belowRegion == status.getCurrentRegion()) {
				throw new IllegalArgumentException("relative region "
						+ "cannot user belowRegion to reference itself");
			}
		}

		if (aboveRegion != null) {
			throw new UnsupportedOperationException("aboveRegion is not "
					+ "yet implemented");
		}
		if (leftOfRegion != null) {
			throw new UnsupportedOperationException("leftOfRegion is not yet "
					+ "implemented");
		}

		int x = -1;
		int y = -1;
		if (rightOfBounds != null) {
			x = rightOfBounds.x + rightOfBounds.width;
			if (belowBounds == null) {
				y = rightOfBounds.y;
			}
		}
		if (belowBounds != null) {
			y = belowBounds.y + belowBounds.height;
			if (rightOfBounds == null) {
				x = belowBounds.x;
			}
		}
		if (x == -1 || y == -1) {
			throw new IllegalStateException("Unable to calculate relative "
					+ "layout, ensure that rightOfRegion and/or "
					+ "belowRegion are not null");
		}

		status.setTopLeftCoordinate(region, new Point(x, y));
	}

	public DocumentRegion getLeftOfRegion() {
		return leftOfRegion;
	}

	public void setLeftOfRegion(final DocumentRegion leftOfRegion) {
		this.leftOfRegion = leftOfRegion;
	}

	public DocumentRegion getRightOfRegion() {
		return rightOfRegion;
	}

	public void setRightOfRegion(final DocumentRegion rightOfRegion) {
		this.rightOfRegion = rightOfRegion;
	}

	public DocumentRegion getBelowRegion() {
		return belowRegion;
	}

	public void setBelowRegion(final DocumentRegion belowRegion) {
		this.belowRegion = belowRegion;
	}

	public DocumentRegion getAboveRegion() {
		return aboveRegion;
	}

	public void setAboveRegion(final DocumentRegion aboveRegion) {
		this.aboveRegion = aboveRegion;
	}

	static void checkRegion(final DocumentRegion region) {
		if (region == null) {
			throw new IllegalStateException("region is null");
		}
	}

	public static RelativeLayout leftOf(final DocumentRegion region) {
		checkRegion(region);
		final RelativeLayout layout = new RelativeLayout();
		layout.setLeftOfRegion(region);
		return layout;
	}

	public static RelativeLayout rightOf(final DocumentRegion region) {
		checkRegion(region);
		final RelativeLayout layout = new RelativeLayout();
		layout.setRightOfRegion(region);
		return layout;
	}

	public static RelativeLayout above(final DocumentRegion region) {
		checkRegion(region);
		final RelativeLayout layout = new RelativeLayout();
		layout.setAboveRegion(region);
		return layout;
	}

	public static RelativeLayout below(final DocumentRegion region) {
		checkRegion(region);
		final RelativeLayout layout = new RelativeLayout();
		layout.setBelowRegion(region);
		return layout;
	}

}