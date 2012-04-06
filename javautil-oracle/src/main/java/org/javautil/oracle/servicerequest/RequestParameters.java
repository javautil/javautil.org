package org.javautil.oracle.servicerequest;

import java.util.HashMap;

public class RequestParameters {

	private final HashMap<String, RequestParameter> argumentMap = new HashMap<String, RequestParameter>();

	public RequestParameters() {
	}

	public void add(final RequestParameter argument) {
		argumentMap.put(argument.getName(), argument);
	}

	public int size() {
		return argumentMap.size();
	}

}
