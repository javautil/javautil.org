package org.javautil.document.renderer;

import java.awt.Point;

import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javautil.dataset.Dataset;
import org.javautil.dataset.render.typewriter.TypewriterBehavior;
import org.javautil.dataset.render.typewriter.TypewriterOrientation;
import org.javautil.dataset.render.typewriter.TypewriterRenderer;
import org.javautil.dataset.render.typewriter.TypewriterRendererFactory;
import org.javautil.document.DocumentRegion;

import bsh.Interpreter;
import bsh.TargetError;

public class BshRenderTemplate implements RenderTemplate {

	private final Log logger = LogFactory.getLog(getClass());

	private String orientation = TypewriterOrientation.HORIZONTAL.toString()
			.toLowerCase();

	private String bshScript;

	@Override
	@SuppressWarnings("unchecked")
	public void render(final DocumentRegionRendererRequest request) {
		try {
			final Interpreter interpreter = new Interpreter();
			final DocumentRegion region = request.getDocumentRegion();
			if (region == null) {
				throw new IllegalStateException("region is null");
			}
			final Dataset dataset = region.getDataset();
			if (dataset == null) {
				logger.debug("dataset is null for region " + region.getName());
			} else {
				interpreter.set("dataset", dataset);
			}
			final TypewriterBehavior behavior = new TypewriterBehavior();
			if (orientation == null) {
				throw new IllegalStateException("orientation is null");
			}
			behavior.setOrientation(TypewriterOrientation.valueOf(orientation
					.toUpperCase()));
			final DocumentRenderStatus status = request.getStatus();
			final Point topLeftPosition = status.getTopLeftCoordinate(region);
			if (topLeftPosition == null) {
				throw new IllegalStateException("topLeftPosition was not set "
						+ "in request.status for region " + region.getName());
			}
			interpreter.set("content", request.getContent());
			behavior.setTopLeftPosition(topLeftPosition);
			final TypewriterRendererFactory factory = request
					.getRendererFactory();
			if (factory == null) {
				throw new IllegalStateException("factory is null");
			}
			final TypewriterRenderer typewriter = factory
					.getTypewriterRenderer(behavior);
			if (typewriter == null) {
				throw new IllegalStateException("typewriter is null");
			}
			interpreter.set("renderer", typewriter);
			if (logger.isDebugEnabled()) {
				logger.debug("interpreting bsh: " + getBshScript());
			}
			interpreter.eval(getBshScript());
			final StreamResult streamResult = request.getStreamResult();
			if (streamResult == null) {
				throw new IllegalArgumentException("streamResult is null");
			}

			// set status information on completion of the region
			status.setTopLeftCoordinate(region, typewriter.getBounds()
					.getLocation());
			status.setDimension(region, typewriter.getDimension());
			status.setCurrentRegionComplete();

		} catch (final Exception e) {
			Throwable t = e;
			if (e instanceof TargetError) {
				t = ((TargetError) e).getTarget();
				System.err.println(ExceptionUtils.getFullStackTrace(e));
				System.err.println(ExceptionUtils.getFullStackTrace(t));
			}
			throw new RuntimeException("error in bsh template: \n"
					+ getBshScript(), t);
		}
	}

	public String getBshScript() {
		return bshScript;
	}

	public void setBshScript(final String bshScript) {
		this.bshScript = bshScript;
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(final String orientation) {
		this.orientation = orientation;
	}

}
