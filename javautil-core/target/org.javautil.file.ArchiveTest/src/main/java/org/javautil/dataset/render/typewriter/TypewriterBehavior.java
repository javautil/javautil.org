package org.javautil.dataset.render.typewriter;

import java.awt.Point;

// TODO how is this behaviour
public class TypewriterBehavior {

	private Point topLeftPosition = new Point(0, 0);

	private TypewriterOrientation orientation = TypewriterOrientation.HORIZONTAL;

	public Point getTopLeftPosition() {
		return topLeftPosition;
	}

	public void setTopLeftPosition(final Point topLeftPosition) {
		this.topLeftPosition = topLeftPosition;
	}

	public TypewriterOrientation getOrientation() {
		return orientation;
	}

	public void setOrientation(final TypewriterOrientation orientation) {
		this.orientation = orientation;
	}

}
