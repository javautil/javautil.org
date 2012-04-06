package org.javautil.proxy.util;

import java.util.Enumeration;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class ProxyParameterChecker {
	
	private ProxyRequestRule requestRule;
	
	@SuppressWarnings("unchecked")
	public void  checkParameters(HttpServletRequest request) {
		initializePatterns(requestRule);
		Enumeration<String> parmNames = request.getParameterNames();
		while  (parmNames.hasMoreElements()) {
			String name = parmNames.nextElement();
			for (Pattern p : requestRule.getPatterns()) {
				if (p.matcher(name).matches()) {
					// bad parameter found	
					throw new IllegalArgumentException("Bad parameter found in the URL");
				}
			}
		}
	}
	
	public void initializePatterns(ProxyRequestRule decorator) {
		if (decorator.getPatterns() == null  && decorator.isUseRegularExpressions()) {
			for (String text : decorator.getDisallowedParameters()) {
				Pattern p = Pattern.compile(text);
				decorator.addPattern(p);
			}
		}
	}

	/**
	 * @return the requestRule
	 */
	public ProxyRequestRule getRequestRule() {
		return requestRule;
	}

	/**
	 * @param requestRule the requestRule to set
	 */
	public void setRequestRule(ProxyRequestRule requestRule) {
		this.requestRule = requestRule;
	}

}
