package org.javautil.sql;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Contains information about a sql statement as text. Holds the statement bind
 * variable names that can be used as names for bind parameter values. The binds
 * variable names are case insensitive and are kept in lower case. Holds a map
 * containing lists of the one-based indexes (the base of one is used as per the
 * jdbc spec) that the bind variables can be found at.
 * 
 * @author bcm
 */
public class SQLBindInfo implements SqlStatementBindMeta {

	/**
	 * A human friendly identifier or name
	 */
	private String statementName;

	/**
	 * The sql text, along with all surrounding comments (if desired)
	 */
	private final String sqlText;

	/**
	 * The indexes of the bind variables in the sql text
	 */
	private final Map<String, List<Integer>> bindIndexes;

	/**
	 * All the bind variables in the sql text
	 */
	private final Set<String> bindVariableNames;

	/**
	 * Incremented and appended to name for auto generated names
	 */
	private static long nextQueryIndex = 1;

	/**
	 * True when the name of the query was generated
	 */
	private boolean nameGenerated = false;

	/**
	 * Constructs a QueryInfo using a user defined name. This is the preferred
	 * constructor to call when the query is provided references a known
	 * resource or table.
	 * 
	 * @param name
	 * @param sqlText
	 */
	public SQLBindInfo(final String name, final String sqlText) {
		if (name == null) {
			throw new IllegalArgumentException("name is null");
		}
		if (sqlText == null) {
			throw new IllegalArgumentException("sqlText is null");
		}
		this.statementName = name;
		this.sqlText = sqlText;
		// so that all binds will be indexed in lowercase
		final String sql = sqlText.toLowerCase();
		this.bindVariableNames = SQLBindUtils.getBinds(sql);
		this.bindIndexes = SQLBindUtils.parameterIndexesForBinds(sql);
	}

	/**
	 * Constructs a QueryInfo using a generated name. This constructor should
	 * generally be avoided when possible to produce more informative debug
	 * messages.
	 * 
	 * @param sqlText
	 */
	public SQLBindInfo(final String sqlText) {
		this(null,sqlText);
		if (sqlText == null) {
			throw new IllegalArgumentException("sqlText is null");
		}
		this.statementName = generateQueryName();
	}

	/* (non-Javadoc)
	 * @see org.javautil.sql.SqlStatementBindMeta#getName()
	 */
	@Override
	public String getStatementName() {
		return statementName;
	}

	/* (non-Javadoc)
	 * @see org.javautil.sql.SqlStatementBindMeta#getSqlText()
	 */
	@Override
	public String getSqlText() {
		return sqlText;
	}

	/* (non-Javadoc)
	 * @see org.javautil.sql.SqlStatementBindMeta#getBindNames()
	 */
	@Override
	public Set<String> getBindNames() {
		return bindVariableNames;
	}

	/**
	 * Used internally to generate a query name.
	 * 
	 * @return generatedQueryName
	 */
	private String generateQueryName() {
		return "Unnamed Query " + (nextQueryIndex++);
	}

	/**
	 * Returns a map containing the sql prepared statement indexes. The key is
	 * the bind variable name (in lower case), and the value is a list of the
	 * one-based indexes (the base of one is used as per the jdbc spec) that the
	 * bind variable is found at.
	 * 
	 * @return bindIndex
	 */
	public Map<String, List<Integer>> getBindIndexes() {
		return bindIndexes;
	}

	/* (non-Javadoc)
	 * @see org.javautil.sql.SqlStatementBindMeta#isNameGenerated()
	 */
	//@Override
	public boolean isNameGenerated() {
		return nameGenerated;
	}

	/* (non-Javadoc)
	 * @see org.javautil.sql.SqlStatementBindMeta#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder s = new StringBuilder();
		s.append(statementName);
		s.append(":\n");
		s.append(sqlText);
		if (getBindNames().size() > 0) {
			s.append("\n-- Required binds: ");
			s.append(Arrays.toString(getBindNames().toArray()));
		}
		return s.toString();
	}

	@Override
	public boolean hasBindParameters() {
		return getBindNames().size() > 0;
	}

}
