package org.javautil.dataset.render;

import org.javautil.dataset.DatasetException;

public class DatasetRenderException extends DatasetException {

	private static final long serialVersionUID = 2843176325124265154L;

	public DatasetRenderException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public DatasetRenderException(final String message) {
		super(message);
	}

	public DatasetRenderException(final Throwable cause) {
		super(cause);
	}

}
