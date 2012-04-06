package org.javautil.sql;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SqlStatementBindMeta {

	/**
	 * The name of the query resource or table that the sqlText is associated
	 * with. As a general rule, this name should be the same or very similar for
	 * queries that have the same sqlText.
	 * 
	 * @return name
	 */
	public abstract String getStatementName();

	/**
	 * The original sqlText that was used to construct the QueryInfo.
	 * 
	 * @return sqlText
	 */
	public abstract String getSqlText();

	/**
	 * The names of the bind variables that were found in the sqlText. These
	 * values are converted into lower case, and are listed in no guaranteed
	 * order.
	 * 
	 * @return bindVariableNames
	 */
	public abstract Set<String> getBindNames();
	
	/**
	 * 
	 * @return true if there is one or more bind parameters in the statement
	 */
	public boolean hasBindParameters();

//	/**
//	 * Returns true when the name is generated.
//	 * 
//	 * @return nameGenerated
//	 */
//	public abstract boolean isNameGenerated();

	/**
	 * A string representation of the query, suitable for a multiline debug
	 * message.
	 */
	public abstract String toString();

	public abstract Map<String, List<Integer>> getBindIndexes();

}