package org.javautil.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.javautil.controller.exception.InvalidParameterValueException;
import org.javautil.controller.exception.InvalidReferenceException;
import org.javautil.controller.exception.MissingParameterException;
import org.javautil.io.IOUtils;
import org.javautil.logging.ModelLogger;
import org.javautil.sql.SQLBlobInputStream;
import org.javautil.web.HttpRequestUtils;
import org.javautil.web.HttpResponseUtils;
import org.springframework.web.servlet.mvc.Controller;

public abstract class AbstractSpringController implements Controller {

	private SessionFactory sessionFactory;

	protected Session getSession() {
		return getSessionFactory().getCurrentSession();
	}

	protected void prohibit(String parameterName, Object parameterValue)
			throws InvalidParameterValueException {
		if (parameterName == null) {
			throw new IllegalArgumentException("parameterName is null");
		}
		if (parameterValue != null) {
			throw new InvalidParameterValueException(parameterName);
		}
	}

	protected void require(String parameterName, Object parameterValue)
			throws MissingParameterException {
		if (parameterName == null) {
			throw new IllegalArgumentException("parameterName is null");
		}
		if (parameterValue == null) {
			throw new MissingParameterException(parameterName);
		}
	}

	protected <T> T require(String parameterName, Object parameterValue,
			T resolvedReference) throws InvalidReferenceException,
			MissingParameterException {
		if (parameterName == null) {
			throw new IllegalArgumentException("parameterName is null");
		}
		require(parameterName, parameterValue);
		if (resolvedReference == null) {
			throw new InvalidReferenceException(parameterName, parameterValue);
		}
		return resolvedReference;
	}

	protected <T> void contains(String parameterName, Set<T> allowedValues,
			T parameterValue) throws InvalidParameterValueException {
		if (parameterName == null) {
			throw new IllegalArgumentException("parameterName is null");
		}
		if (parameterValue == null) {
			if (allowedValues.size() != 0) {
				throw new InvalidParameterValueException(parameterName);
			}
		} else {
			if (!allowedValues.contains(parameterValue)) {
				throw new InvalidParameterValueException(parameterName,
						parameterValue);
			}
		}
	}

	protected void setNoCacheHeaders(HttpServletResponse response) {
		HttpResponseUtils.setNoCacheHeaders(response);
	}

	protected void logModel(Log logger, HttpServletRequest request,
			Map<String, Object> model) {
		String pagePath = HttpRequestUtils.getRequestedPage(request);
		new ModelLogger(logger).info(pagePath, model);
	}

	protected void setDownloadHeaders(HttpServletResponse response,
			String fileName, Integer downloadSize) {
		HttpResponseUtils.setDownloadHeaders(response, fileName, downloadSize);
	}

	protected void setDownload(HttpServletResponse response,
			InputStream inputStream) throws IOException {
		IOUtils.pump(inputStream, response.getOutputStream());
	}

	protected void setDownload(HttpServletResponse response, Blob blob)
			throws IOException {
		setDownload(response, new SQLBlobInputStream(blob));
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
