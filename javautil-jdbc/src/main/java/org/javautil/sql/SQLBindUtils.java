package org.javautil.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Utility methods useful for operating on SQL text and jdbc statements.
 * 
 * @author bcm
 */
public class SQLBindUtils  {

	private static Log logger = LogFactory.getLog(SQLBindUtils.class);

	/**
	 * A regular expression pattern string for finding bind variables inside of
	 * a block of sql text.
	 */
	public static final String SQL_BIND_VARIABLE_PATTERN_STRING = "(\\:{1}+[A-z_0-9]+)";

	/**
	 * The compiled pattern from SQL_BIND_VARIABLE_PATTERN_STRING.
	 */
	public static final Pattern SQL_BIND_VARIABLE_PATTERN = Pattern
			.compile(SQL_BIND_VARIABLE_PATTERN_STRING);

	private SQLBindUtils() {
		// prevent instantiation
	}

	/**
	 * Returns a list of bind variables, in the order that first appeared. The
	 * binds are parsed by using the regular expression
	 * SQL_BIND_VARIABLE_PATTERN.
	 * 
	 * TODO need a comment stripper this finds binds in comments
	 * 
	 * @param sqlText
	 * @return binds
	 */
	public static LinkedHashSet<String> getBinds(final String sqlText) {
		final LinkedHashSet<String> binds = new LinkedHashSet<String>();
		final Matcher m = SQL_BIND_VARIABLE_PATTERN.matcher(sqlText);
		while (m.find()) {
			final String bind = sqlText.substring(m.start() + 1, m.end());
			if (!binds.contains(bind)) {
				binds.add(bind);
			}
		}
		return binds;
	}

	/**
	 * Replaces the sql text with the colon prefixed bind variables replace with
	 * question marks in place of the bind variables names.
	 * 
	 * @param sqlText
	 * @return preparable sql text
	 */
	public static String asPreparableString(String sqlText) {
		Matcher m = SQL_BIND_VARIABLE_PATTERN.matcher(sqlText);
		while (m.find()) {
			sqlText = sqlText.substring(0, m.start()) + "?"
					+ sqlText.substring(m.end(), sqlText.length());
			m = SQL_BIND_VARIABLE_PATTERN.matcher(sqlText);
		}
		return sqlText;
	}

	/**
	 * Provides binds for use with asPreparableString() to bind a
	 * PreparedStatement. More specifically, this reads the bind variables from
	 * the sqlText and returns the one-based integer indexes at which the
	 * parameters appear.
	 * 
	 * Example: select name from pets where age > :age and age < :age and
	 * pet_type = :pet_type;
	 * 
	 * would return a map containing: { 'age': [1, 2]; 'pet_type': [3] }
	 * 
	 * @param _sqlText
	 * @return binds indexes
	 */
	public static Map<String, List<Integer>> parameterIndexesForBinds(
			final String sqlText) {
		StringBuffer sql = new StringBuffer(sqlText);
		// sort by length, to prevent substring matches while indexing binds
		final List<String> unsortedBinds = new ArrayList<String>(
				getBinds(sqlText));
		// note that the order of the this set is important
		final List<String> sortedBinds = new ArrayList<String>();
		while (unsortedBinds.size() > 0) {
			int largestKeyLen = 0;
			int keyNdx = 0;
			int ndx = 0;
			// we find binds for variables having the longest strings first
			for (final String keyName : unsortedBinds) {
				if (keyName.length() > largestKeyLen) {
					largestKeyLen = keyName.length();
					keyNdx = ndx;
				}
				ndx++;
			}
			final String newKey = unsortedBinds.get(keyNdx);
			sortedBinds.add(newKey);
			unsortedBinds.remove(keyNdx);
			largestKeyLen = 0;
		}
		final Map<String, List<Integer>> indexesByBind = new HashMap<String, List<Integer>>();
		// store a sql comment with the bind containing the sorted index
		final String prefix = "/*BIND_NDX_";
		final String suffix = "*/?";
		// replace the binds with their sortedIndex
		final Iterator<String> it = sortedBinds.iterator();
		int bindNdx = 0;
		while (it.hasNext()) {
			final String bindParameterName = it.next();
			final String replaceValue = prefix + bindNdx + suffix;
			final String patternText = "(\\:" + bindParameterName
					+ ")([^\\w_]|$)";
			final Pattern pattern = Pattern.compile(patternText);
			Matcher matcher = pattern.matcher(sql);
			while (matcher.find()) {
				// replace the bind variable name with /*BIND_NDX_I*/?
				sql = sql.replace(matcher.start(1), matcher.end(1),
						replaceValue);
				matcher = pattern.matcher(sql);
			}
			bindNdx++;
		}
		// now find the new exact string indexes of /*BIND_NDX_I*/?
		final Pattern bindFind = Pattern
				.compile("\\/\\*BIND\\_NDX\\_(\\d)*\\*\\/\\?");
		final Matcher m = bindFind.matcher(sql);
		int parmNdx = 1;
		while (m.find()) {
			final String _bindNdx = sql.substring(m.start() + prefix.length(),
					m.end() - suffix.length());
			bindNdx = Integer.parseInt(_bindNdx);
			if (logger.isTraceEnabled()) {
				logger.trace("found sql bind parameter: index=" + parmNdx
						+ ", variable name=" + sortedBinds.get(bindNdx)
						+ " (order=" + bindNdx + ")");
			}
			final String bindName = sortedBinds.get(bindNdx).toLowerCase();
			List<Integer> indexes = indexesByBind.get(bindName);
			if (indexes == null) {
				indexes = new ArrayList<Integer>();
				indexesByBind.put(bindName, indexes);
			}
			indexes.add(Integer.valueOf(parmNdx++));
		}
		return indexesByBind;
	}

}
