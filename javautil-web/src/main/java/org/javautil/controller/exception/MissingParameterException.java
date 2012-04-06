package org.javautil.controller.exception;

/**
 * Exception thrown when a controller or view rejects a request because of a missing
 * request parameter.
 * 
 * @author bcm
 */
public class MissingParameterException extends RuntimeException implements MalformedRequestException {

	private static final long serialVersionUID = -6593522514136217322L;

	private String parameterName;
	
	public MissingParameterException(String parameterName) {
		super("parameter " + parameterName + " is required");
		this.parameterName = parameterName;
	}

	public String getParameterName() {
		return parameterName;
	}
	
}
