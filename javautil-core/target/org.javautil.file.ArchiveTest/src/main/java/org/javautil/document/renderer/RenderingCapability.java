package org.javautil.document.renderer;

import org.javautil.document.MimeType;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
public class RenderingCapability {
	private final MimeType mimeType;

	public RenderingCapability(final MimeType mimeType) {
		if (mimeType == null) {
			throw new IllegalArgumentException("mimeType is null");
		}
		this.mimeType = mimeType;
	}

	/**
	 * @return the mimeType
	 */
	public MimeType getMimeType() {
		return mimeType;
	}

	public boolean canRender(final DatasetRendererRequest state) {
		boolean retval = true;

		if (!mimeType.equals(state.getMimeType())) {
			retval = false;
		}

		return retval;
	}
}
