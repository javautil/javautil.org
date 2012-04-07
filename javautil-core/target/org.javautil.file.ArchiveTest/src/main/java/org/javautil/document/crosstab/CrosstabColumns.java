package org.javautil.document.crosstab;

import java.util.List;

/**
 * @author jjs@javautil.org
 */
public interface CrosstabColumns {
	public List<String> getCellIdentifiers();

	public String getColumnIdentifier();

	public List<String> getRowIdentifiers();

}