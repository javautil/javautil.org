package org.javautil.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author bcm
 * 
 *         TODO: write new class to work with sql binds and derive binds from
 *         query and pull them from the request as single values.
 * 
 */
public abstract class HttpRequestUtils {

	/**
	 * Translates the request parameters into a map that contains a list of
	 * values for each key.
	 * 
	 * @param request
	 * @return map
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, List<String>> buildParameterMap(
			HttpServletRequest request) {
		// accumulate our parameter map of values
		LinkedHashMap<String, List<String>> requestParameters = new LinkedHashMap<String, List<String>>();
		Map<String, Object> _params = request.getParameterMap();
		for (String paramName : _params.keySet()) {
			Object _paramValue = _params.get(paramName);
			ArrayList<String> paramValues = null;
			if (_paramValue instanceof String) {
				String paramValue = (String) _paramValue;
				paramValues = new ArrayList<String>(1);
				paramValues.add(paramValue);
			} else if (_paramValue instanceof String[]) {
				String[] paramValue = (String[]) _paramValue;
				paramValues = new ArrayList<String>(paramValue.length);
				for (int i = 0; i < paramValue.length; i++) {
					paramValues.add(paramValue[i]);
				}
			}
			requestParameters.put(paramName, paramValues);
		}
		return requestParameters;
	}

	/**
	 * Returns a Map of name value pairs from the request having the parameter
	 * name starting with prefix. The values of the returned map are always a
	 * java.util.List no matter how many input fields are on the form.
	 * 
	 * @param prefix
	 * @param request
	 * @return java.util.Map
	 */
	public static Map<String, List<String>> getParametersLike(String prefix,
			HttpServletRequest request) {
		return getParametersLike(prefix, request, false);
	}

	/**
	 * Gets parameters from the http request that start with a given prefix. The
	 * prefix is matched case-sensitive. Optionally the prefix can be stripped
	 * in the returned map. The returned map is a map containing the request
	 * parameter name and values as as a list.
	 * 
	 * @param prefix
	 * @param request
	 * @param stripPrefix
	 * @return map of parameters names/values list
	 */
	public static Map<String, List<String>> getParametersLike(String prefix,
			HttpServletRequest request, boolean stripPrefix) {
		Map<String, List<String>> parms = buildParameterMap(request);
		Map<String, List<String>> matches = new LinkedHashMap<String, List<String>>();
		for (String parmName : parms.keySet()) {
			if (parmName.startsWith(prefix)) {
				String parm = parmName;
				if (stripPrefix) {
					parm = parm.substring(parmName.length());
				}
				matches.put(parm, parms.get(parmName));
			}
		}
		return matches;
	}

	/**
	 * Gets parameters from the http request that start with a given prefix. The
	 * prefix is matched case-sensitive. Optionally the prefix can be stripped
	 * in the returned map. The returned map is a map containing the request
	 * parameter name and value. Fails fast when a parameter is repeated and
	 * starts with the prefix specified.
	 * 
	 * @param prefix
	 * @param request
	 * @param stripPrefix
	 * @return map of parameters names/values
	 */
	public static Map<String, String> getSingleParametersLike(String prefix,
			HttpServletRequest request, boolean stripPrefix) {
		Map<String, List<String>> parms = buildParameterMap(request);
		Map<String, String> matches = new LinkedHashMap<String, String>();
		for (String parmName : parms.keySet()) {
			if (parmName.startsWith(prefix)) {
				String parm = parmName;
				if (stripPrefix) {
					parm = parm.substring(prefix.length());
				}
				List<String> values = parms.get(parmName);
				if (values.size() > 1) {
					throw new IllegalStateException("parameter " + parmName
							+ " is present in the http request values "
							+ values.size() + " times, but was expected "
							+ "to appear at most once; values: "
							+ Arrays.toString(values.toArray()));
				}
				String value = values.size() == 0 ? null : values.get(0);
				matches.put(parm, value);
			}
		}
		return matches;
	}

	/**
	 * The returned map is a map containing the request parameter name and
	 * value. Fails fast when any parameter name is repeated.
	 * 
	 * @param request
	 * @return map of parameter names/values
	 */
	public static Map<String, String> getSingleParameters(
			HttpServletRequest request) {
		Map<String, List<String>> parms = buildParameterMap(request);
		Map<String, String> matches = new LinkedHashMap<String, String>();
		for (String parmName : parms.keySet()) {
			List<String> values = parms.get(parmName);
			if (values.size() > 1) {
				throw new IllegalStateException("parameter " + parmName
						+ " is present in the http request values "
						+ values.size() + " times, but was expected "
						+ "to appear at most once; values: "
						+ Arrays.toString(values.toArray()));
			}
			String value = values.size() == 0 ? null : values.get(0);
			matches.put(parmName, value);
		}
		return matches;
	}

	/**
	 * Gets the URI path and parameters for a given request.
	 * 
	 * @param request
	 * @return path
	 */
	public static String getRequestedPage(HttpServletRequest request) {
		// store the full URI and query for later reference
		String queryString = request.getQueryString();
		String page = request.getRequestURI();
		if (queryString != null && queryString.length() > 0) {
			page = request.getRequestURI() + "?" + queryString;
		} else {
			page = request.getRequestURI();
		}
		while (page.length() > 2 && page.charAt(0) == '/'
				&& page.charAt(1) == '/') {
			// tomcat 5.5 can have an incorrect double leading slash, remove it
			page = page.substring(1, page.length());
		}
		String contextPath = request.getContextPath();
		if (contextPath.length() > 0 && page.startsWith(contextPath)) {
			page = page.substring(contextPath.length(), page.length());
		}
		return page;
	}

}
