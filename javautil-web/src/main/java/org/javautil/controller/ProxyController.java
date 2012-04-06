package org.javautil.controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javautil.io.IOUtils;
import org.javautil.web.HttpRequestUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * Provides a proxied response of a remote server's output. The remote server is
 * specified by setting the bean property remoteUri. The translation of the
 * local uri to the remote uri can be configured by setting various bean
 * properties. Note that by default, the local uri, parameters, and response
 * headers are proxied. If this is not desired, additional configuration must be
 * made.
 * 
 * @author bcm
 */
public class ProxyController extends AbstractController implements
		InitializingBean {

	private Log logger = LogFactory.getLog(getClass());

	/**
	 * The value of additionalParameters can be either a list or an object. If
	 * the value or the list value is not a string, it will be converted to one
	 * using String.valueOf().
	 */
	private Map<String, Object> additionalParameters;

	/**
	 * When parameter names in the list appear in the
	 */
	private List<String> stripParameterNames;

	/**
	 * The server or remote address that is to be proxied.
	 */
	private String remoteUri;

	/**
	 * When true, the local uri that was used as a request for this controller
	 * is appended to the remoteUri before proxying.
	 */
	private boolean proxyRequestUri = true;

	/**
	 * When not null, this prefix is removed from the local uri before the
	 * remoteUri is appended. This is only useful when the proxyRequestUri
	 * parameter is set to true; it is ignored otherwise.
	 */
	private String stripPrefix;

	/**
	 * When true, the request parameters in the url are sent to the remoteUri.
	 */
	private boolean proxyRequestParameters = true;

	/**
	 * When true, the http request headers that were set in the remoteUri are
	 * also set on the response from this controller.
	 */
	private boolean proxyHttpRequestHeaders = true;

	/**
	 * When true, will add a trailing slash to the remoteUri before proxying the
	 * local uri.
	 */
	private boolean addTrailingSlash = true;

	/**
	 * Needed to implement spring AbstracController.
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// get the remote url, with all desired modifications
		URL remoteUrl = buildRemoteURL(request);

		if (logger.isDebugEnabled()) {
			logger.debug("remoteUrl is " + remoteUrl.toExternalForm());
		}

		// establish remote connection
		URLConnection connection = remoteUrl.openConnection();
		boolean isHttpConnection = HttpURLConnection.class
				.isAssignableFrom(HttpURLConnection.class);

		// proxy http/https headers
		if (proxyHttpRequestHeaders && isHttpConnection) {
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			proxyHttpHeaders(httpConnection, response);
		}
		boolean isHttpSuccess = isHttpConnection
				&& ((HttpURLConnection) connection).getResponseCode() % 200 < 100;

		// send the body of the proxied content
		if (!isHttpConnection || isHttpSuccess) {
			IOUtils.pump(connection.getInputStream(), response
					.getOutputStream());
		}

		// we don't need a model and view, as the response was already written
		return null;
	}

	/**
	 * Needed to implement InitializingBean
	 */
	public void afterPropertiesSet() throws Exception {
		if (remoteUri == null) {
			throw new IllegalArgumentException("remoteUri is null");
		}
	}

	/**
	 * Returns the desired a remote url, suitable for proxying.
	 * 
	 * @param request
	 * @return url
	 * @throws MalformedURLException
	 */
	public URL buildRemoteURL(HttpServletRequest request)
			throws MalformedURLException {

		StringBuilder remoteAddress = new StringBuilder(remoteUri);

		// append the actual requested url to the proxied request
		if (proxyRequestUri) {
			if (isAddTrailingSlash()
					&& remoteAddress.charAt(remoteAddress.length() - 1) != '/') {
				remoteAddress.append("/");
			}
			String localUri = request.getRequestURI();
			// remove the stripPrefix if specified
			if (stripPrefix != null && localUri.startsWith(stripPrefix)) {
				localUri = localUri.substring(stripPrefix.length());
			}
			remoteAddress.append(localUri);
		}

		Map<String, List<String>> parameters = new LinkedHashMap<String, List<String>>();
		// append custom parameters
		if (additionalParameters != null) {
			appendCustomParameters(parameters);
		}
		// append local parameters, do not allow overriding of a custom
		// parameters
		if (proxyRequestParameters) {
			appendLocalUrlParameters(parameters, request);
		}

		// add the build parameter map to the url as request parameters
		try {
			if (parameters.keySet().size() > 0) {
				boolean needsQ = remoteAddress.indexOf("?") == -1;
				for (String parmName : parameters.keySet()) {
					List<String> values = parameters.get(parmName);
					if (values.size() == 0) {
						values = new ArrayList<String>();
						values.add("");
					}
					for (String value : values) {
						remoteAddress.append(needsQ ? "?" : "&");
						remoteAddress.append(parmName);
						if (value != null && !"".equals(value)) {
							remoteAddress.append("=");
							remoteAddress.append(URLEncoder.encode(value,
									"UTF-8"));
						}
						needsQ = false;
					}
				}
			}
		} catch (Exception e) {
			throw new IllegalStateException("error while bulding url", e);
		}

		URL remoteUrl = new URL(remoteAddress.toString());
		return remoteUrl;
	}

	/**
	 * Reads the parameters from the request and appends them to the parameter
	 * map.
	 * 
	 * @param parameters
	 * @param request
	 */
	protected void appendLocalUrlParameters(
			Map<String, List<String>> parameters, HttpServletRequest request) {
		Map<String, List<String>> urlParams = HttpRequestUtils
				.buildParameterMap(request);
		for (String parameterName : urlParams.keySet()) {
			boolean isOverriden = additionalParameters != null
					&& additionalParameters.containsKey(parameterName);
			boolean isIgnored = stripParameterNames != null
					&& stripParameterNames.contains(parameterName);
			if (!isOverriden && !isIgnored) {
				parameters.put(parameterName, urlParams.get(parameterName));
			}
		}
	}

	/**
	 * Adds the custom parameters specified as bean properties to the parameter
	 * map.
	 * 
	 * @param parameters
	 */
	@SuppressWarnings("unchecked")
	protected void appendCustomParameters(Map<String, List<String>> parameters) {
		for (String parameterName : additionalParameters.keySet()) {
			Object rawParameter = additionalParameters.get(parameterName);
			List<String> parameterValues = new ArrayList<String>();
			if (rawParameter != null) {
				if (Collection.class.isAssignableFrom(rawParameter.getClass())) {
					Collection<Object> collection = (Collection<Object>) rawParameter;
					for (Object objectValue : collection) {
						parameterValues.add(String.valueOf(objectValue));
					}
				} else {
					parameterValues.add(String.valueOf(rawParameter));
				}
			}
			parameters.put(parameterName, parameterValues);
		}
	}

	/**
	 * Proxies http headers from a connection to another response.
	 * 
	 * @param connection
	 * @param response
	 * @throws IOException
	 */
	protected void proxyHttpHeaders(HttpURLConnection connection,
			HttpServletResponse response) throws IOException {
		int responseCode = 500;
		try {
			responseCode = connection.getResponseCode();
		} catch (IOException e) {
			logger.error(e);
		}
		response.setStatus(responseCode);
		int last = connection.getHeaderFields().size();
		for (int i = 0; i < last; i++) {
			String name = connection.getHeaderFieldKey(i);
			String value = connection.getHeaderField(name);
			if (name == null && value == null) {
				break;
			}
			if (name != null) {
				response.setHeader(name, value);
			}
		}
	}

	public Map<String, Object> getAdditionalParameters() {
		return additionalParameters;
	}

	public void setAdditionalParameters(Map<String, Object> additionalParameters) {
		this.additionalParameters = additionalParameters;
	}

	public List<String> getStripParameterNames() {
		return stripParameterNames;
	}

	public void setStripParameterNames(List<String> stripParameterNames) {
		this.stripParameterNames = stripParameterNames;
	}

	public String getRemoteUri() {
		return remoteUri;
	}

	public void setRemoteUri(String remoteUri) {
		this.remoteUri = remoteUri;
	}

	public boolean isProxyRequestUri() {
		return proxyRequestUri;
	}

	public void setProxyRequestUri(boolean proxyRequestUri) {
		this.proxyRequestUri = proxyRequestUri;
	}

	public String getStripPrefix() {
		return stripPrefix;
	}

	public void setStripPrefix(String stripPrefix) {
		this.stripPrefix = stripPrefix;
	}

	public boolean isProxyRequestParameters() {
		return proxyRequestParameters;
	}

	public void setProxyRequestParameters(boolean proxyRequestParameters) {
		this.proxyRequestParameters = proxyRequestParameters;
	}

	public boolean isProxyHttpRequestHeaders() {
		return proxyHttpRequestHeaders;
	}

	public void setProxyHttpRequestHeaders(boolean proxyHttpRequestHeaders) {
		this.proxyHttpRequestHeaders = proxyHttpRequestHeaders;
	}

	/**
	 * @return the addTrailingSlash
	 */
	public boolean isAddTrailingSlash() {
		return addTrailingSlash;
	}

	/**
	 * @param addTrailingSlash
	 *            the addTrailingSlash to set
	 */
	public void setAddTrailingSlash(boolean addTrailingSlash) {
		this.addTrailingSlash = addTrailingSlash;
	}

}
