package org.javautil.view;

import javax.servlet.http.HttpServletRequest;

public class JsonView extends AbstractJsonView {

	private Object model;
	
	public JsonView() {		
	}

	public JsonView(Object model) {
		this.model = model;
	}
	
	protected Object prepareJsonModel(HttpServletRequest request)
			throws Exception {
		return model;
	}
	
}
