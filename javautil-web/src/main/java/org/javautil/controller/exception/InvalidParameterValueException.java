package org.javautil.controller.exception;

/**
 * Exception thrown when the value of a request parameter is not valid.
 * 
 * @author bcm
 */
public class InvalidParameterValueException extends RuntimeException implements
		MalformedRequestException {

	private static final long serialVersionUID = -6593522514136217322L;

	private String parameterName = null;
	
	private Object parameterValue = null;
	
	public InvalidParameterValueException(String parameterName,
			Object parameterValue) {
		super("parameter " + parameterName + " cannot have value "
				+ parameterValue);
		this.parameterName = parameterName;
		this.parameterValue = parameterValue;
	}

	public InvalidParameterValueException(String parameterName) {
		super("parameter " + parameterName + " must have a null value");
		this.parameterName = parameterName;
	}

	public String getParameterName() {
		return parameterName;
	}

	public Object getParameterValue() {
		return parameterValue;
	}

}