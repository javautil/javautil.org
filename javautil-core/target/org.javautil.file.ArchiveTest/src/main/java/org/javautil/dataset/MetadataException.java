package org.javautil.dataset;

/**
 * Thrown when there is an inconsistent state between the metadata and the data.
 * 
 * This exception should be thrown in order to facilitate catching this error
 * and rethrowing an exception with additional context information that was not
 * available to the method throwing this exception.
 * 
 * @author jjs
 * 
 */
public class MetadataException extends RuntimeException {
	public MetadataException(final String s) {
		super(s);
	}

	public MetadataException(final Exception e) {
		super(e);
	}

	public MetadataException(final String string, final MetadataException me) {
		super(string, me);
	}
}
