package org.javautil.proxy.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This class has the methods to validate the proxy request URL
 */
public class ProxyRequestRule {
	private List<Pattern> patterns; 
	
	private boolean useRegularExpressions = true;
	
	private List<String> disallowedParameters;
	
	private String stripPrefix;

	/**
	 * @return the disallowedParameters
	 */
	public List<String> getDisallowedParameters() {
		return disallowedParameters;
	}

	/**
	 * @param disallowedParameters the disallowedParameters to set
	 */
	public void setDisallowedParameters(List<String> disallowedParameters) {
		this.disallowedParameters = disallowedParameters;
	}

	/**
	 * @return the stripPrefix
	 */
	public String getStripPrefix() {
		return stripPrefix;
	}

	/**
	 * @param stripPrefix the stripPrefix to set
	 */
	public void setStripPrefix(String stripPrefix) {
		this.stripPrefix = stripPrefix;
	}

	/**
	 * @return the useRegularExpressions
	 */
	public boolean isUseRegularExpressions() {
		return useRegularExpressions;
	}

	/**
	 * @param useRegularExpressions the useRegularExpressions to set
	 */
	public void setUseRegularExpressions(boolean useRegularExpressions) {
		this.useRegularExpressions = useRegularExpressions;
	}

	/**
	 * @return the patterns
	 */
	public List<Pattern> getPatterns() {
		return patterns;
	}

	/**
	 * @param patterns the patterns to set
	 */
	public void setPatterns(List<Pattern> patterns) {
		this.patterns = patterns;
	}

	public void addPattern(Pattern p) {
		if (patterns == null) {
			patterns  = new ArrayList<Pattern>();
		}
		patterns.add(p);
		
	}
	
}
