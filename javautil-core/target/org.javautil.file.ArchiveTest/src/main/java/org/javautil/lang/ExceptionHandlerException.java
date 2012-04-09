package org.javautil.lang;

import java.io.IOException;

public class ExceptionHandlerException extends RuntimeException {

	public ExceptionHandlerException(IOException e) {
		super(e);
	}

}
