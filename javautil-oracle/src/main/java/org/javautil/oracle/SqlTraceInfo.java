package org.javautil.oracle;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
public class SqlTraceInfo {

	// todo jjs what the hell is this doing here?
	private static Set<String> queryNames = new HashSet<String>();
	static {
		queryNames.add("mfr_org.sql");
		queryNames.add("sales/mfr_cat.cursor.sql");
	}

	// TODO add Query name method to manipulate queryNames object

	public boolean isTracing(final String queryName) {
		return queryNames.contains(queryName);
	}

	public void setTraceForQueryName(final String queryName) {
		queryNames.add(queryName);
	}

	public void clearTraceForQueryName(final String queryName) {
		queryNames.remove(queryName);
	}
}
