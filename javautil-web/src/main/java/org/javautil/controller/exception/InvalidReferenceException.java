package org.javautil.controller.exception;

/**
 * Exception thrown when the object referenced by the value of a request parameter is not null.
 * 
 * @author bcm
 */
public class InvalidReferenceException extends RuntimeException implements
		MalformedRequestException {

	private static final long serialVersionUID = -6593522514136217322L;

	private String parameterName = null;
	
	private Object parameterValue = null;
	
	public InvalidReferenceException(String parameterName,
			Object parameterValue) {
		super("parameter " + parameterName + " value " + parameterValue
				+ " is not a valid reference value");
		this.parameterName = parameterName;
		this.parameterValue = parameterValue;
	}

	public String getParameterName() {
		return parameterName;
	}

	public Object getParameterValue() {
		return parameterValue;
	}

}
