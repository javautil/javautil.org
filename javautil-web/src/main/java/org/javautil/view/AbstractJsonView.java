package org.javautil.view;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.google.gson.Gson;

public abstract class AbstractJsonView extends AbstractView {

	public AbstractJsonView() {
		super();
		setContentType("application/json");
	}

	protected abstract Object prepareJsonModel(HttpServletRequest request)
			throws Exception;

	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedOutputModel(Map model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter writer = response.getWriter();
		
		Object jsonModel = prepareJsonModel(request);
		Gson gson = new Gson();
		String json = gson.toJson(jsonModel);
		
		
		response.setContentType(response.getContentType());
		response.setContentLength(json.length());
		writer.write(json);
	}

}
