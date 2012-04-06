package org.javautil.sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.javautil.text.StringHelper;


 /**
 * Contains information about a sql statement as text.
 * 
 *  Holds the statement bind
 * variable names that can be used as names for bind parameter values. The binds
 * variable names are case insensitive and are kept in lower case. Holds a map
 * containing lists of the one-based indexes (the base of one is used as per the
 * jdbc spec) that the bind variables can be found at.
 * 
 * @author jjs
 */
public class SqlStatementHelper implements SqlStatementBindMeta {
	//
	// Constants
	//
	private static final char APOS = '\'';
	private static final char QUOTE = '\"';
	private static final char COLON = ':';

	private static final int NORMAL = 0;
	private static final int OPEN_COMMENT = 1;
	private static final int CLOSE_COMMENT = 2;
	private static final int LINE_COMMENT = 3;
	private static final int OPEN_QUOTE = 4;
	private static final int CLOSE_QUOTE = 5;
	private static final int OPEN_APOS = 6;
	private static final int CLOSE_APOS = 7;
	private static final int EOL = 8;
	//
	// State
	//
	private boolean inApos = false;
	private boolean inQuote = false;
	private char[] inChars = null;
	private int index = 0;
	private int bindsCount = 0;
	private boolean convertBindNamesToLowerCase = true;
	private ArrayList<SqlBindParameter> bindParameters = new ArrayList<SqlBindParameter>();
	private Map<String, List<Integer>> bindIndexes;

	/**
	 * The raw SQL passed to the constructor.
	 */
	private String sqlText;

	/**
	 * The name of the SQL statement.
	 * 
	 * An identifier. Useful when queries are externalized to locate the source.
	 */
	private String statementName;

	/**
	 * The sql text with comments stripped.
	 */
	private StringBuilder noCommentBuff;
	/**
	 * The sql statement with bind parameters replaced with '?'.
	 * 
	 * Some jdbc drivers don't support named parameters.
	 */
	private StringBuilder convertedBindBuff;
	/**
	 * Order set of bind parameters found in statement
	 */
	private TreeSet<String> bindNames = null;

	private Logger logger = Logger.getLogger(getClass());
	private String noComments;

	// '
	//
	public SqlStatementHelper(String sqlText) {
		this(null, sqlText);
	}

	//
	public SqlStatementHelper(String statementName, String sqlText) {
		this.statementName = statementName;
		this.sqlText = sqlText;
		convertedBindBuff = new StringBuilder(sqlText.length());
		noCommentBuff = new StringBuilder(sqlText.length());
		inChars = StringHelper.getChars(sqlText);
	}

	/**
	 * Returns the statement with :bindNames converted to ? format.
	 * 
	 * Some jdbc drivers do not recognize named bind variables. This returns the
	 * text with "?" substituted for bind variables.
	 * 
	 * @return the convertedBindBuff
	 */
	public String getConvertedBindStatement() {
		return convertedBindBuff.toString();
	}

	private boolean isInitialBindChar(char c) {
		return Character.isLetter(c);
	}

	private boolean isBindChar(char c) {
		boolean retval = false;
		if (Character.isLetter(c) || Character.isDigit(c) || '_' == c) {
			retval = true;
		}
		return retval;
	}


	public void process() {

		while (moreInput()) {
			int charType = getCharType();
			switch (charType) {
			case OPEN_QUOTE:
				copyQuoted();
				break;
			case OPEN_APOS:
				copyAposed();
				break;
			case LINE_COMMENT:
				processLineComment();
				break;
			case OPEN_COMMENT:
				eatComment();
				break;
			case NORMAL:
				copyNoComment(true);
				break;
			case COLON:
				copyBind();
				break;
			}
			index++;
		}
		noComments = noCommentBuff.toString();
	}

	private void processLineComment() {
		logger.debug("processLineComment");
		boolean eol = false;
		while (!eol) {
			if (moreInput()) {
				copyConvertedBindBuf();
				if (getCharType() == EOL) {
					eol = true;
				} else {
					index++;
				}
			} else {
				eol = true;
			}
		}
		logger.debug("end processLineComment");
	}

	private void eatComment() {
		logger.debug("begin eating comment");
		boolean done = false;
		while (!done) {
			if (moreInput()) {
				copyConvertedBindBuf();
				if (getCharType() == CLOSE_COMMENT) {
					copyConvertedBindBuf('/');
					done = true;
				}
				index++;
			} else {
				done = true;
			}
		}
		if (logger.isDebugEnabled()){
		logger.debug("done eating comment at index " + index);
		}
	}

	private void copyNoComment(boolean copyConverted) {
		noCommentBuff.append(inChars[index]);
		if (copyConverted) {
			copyConvertedBindBuf();
		}
	}

	private void copyConvertedBindBuf(char c) {
		convertedBindBuff.append(c);
	}

	private void copyConvertedBindBuf() {
		copyConvertedBindBuf(inChars[index]);
	}

	private void copyNoComment(char c, boolean copyConverted) {
		noCommentBuff.append(c);
		if (copyConverted) {
			copyConvertedBindBuf(c);
		}
	}

	private boolean moreInput() {

		return index < inChars.length;
	}

	private void copyAposed() {
		boolean done = false;
		copyNoComment(APOS, true);
		index++;
		while (!done) {
			if (moreInput()) {
				copyNoComment(true);

				if (getCharType() == CLOSE_APOS) {
					done = true;
				} else {
					index++;
				}
			} else {
				done = true;
			}
		}

	}

	private void copyBind() {
		boolean done = false;
		copyConvertedBindBuf('?');
		copyNoComment(false);
		index++;
		StringBuilder bindName = new StringBuilder();

		while (!done) {
			if (moreInput()) {
				copyNoComment(false);
			
				if (!isBindChar(inChars[index])) {
					copyConvertedBindBuf();
					done = true;
				} else {
					bindName.append(inChars[index]);
					index++;
				}
			} else {
				done = true;
			}
		}
		String bindVariableName = convertBindNamesToLowerCase ? bindName
				.toString().toLowerCase() : bindName.toString();
		SqlBindParameter bp = new SqlBindParameter(++bindsCount,
				bindVariableName);
		bindParameters.add(bp);
	}

	private void copyQuoted() {
		boolean done = false;
		while (!done) {
			if (moreInput()) {
				copyNoComment(true);
				char c = inChars[index];
				if (c == QUOTE) {
					done = true;
				} else {
					index++;
				}
			}
		}
	}

	private int getCharType() {
		int retval = NORMAL;
		char c = inChars[index];
		switch (c) {
		case APOS:
			if (inApos) {
				inApos = false;
				retval = CLOSE_APOS;
			} else {
				inApos = true;
				retval = OPEN_APOS;
			}
			break;
		case QUOTE:
			if (inQuote) {
				inQuote = false;
				retval = CLOSE_QUOTE;
			} else {
				inQuote = true;
				retval = OPEN_QUOTE;
			}
			break;

		case '/':
			if (index < inChars.length - 1) {
				if (inChars[index + 1] == '*') {
					retval = OPEN_COMMENT; // should we support nesting
				} else {
					retval = NORMAL;
				}
			} else {
				retval = NORMAL;
			}
			break;
		case '*':
			if (index < inChars.length - 1) {
				if (inChars[index + 1] == '/') {
					retval = CLOSE_COMMENT; // should we support nesting
				} else {
					retval = NORMAL;
				}
			} else {
				retval = NORMAL;
			}
			break;
		case '-':
			if (index < inChars.length - 1) {
				if (inChars[index + 1] == '-') {
					retval = LINE_COMMENT; // should we support nesting
				} else {
					retval = NORMAL;
				}
			} else {
				retval = NORMAL;
			}
			break;
		case '\r':
		case '\n':
			retval = EOL;
			break;
		case ':':
			retval = COLON;
			break;
		}
		if (logger.isDebugEnabled()) {
		logger.debug("index " + index + " char " + inChars[index] + " " + retval);
		}
		return retval;

	}

	public String getNoComments() {
		return noComments;
	}

	/**
	 * @return the bindParameters
	 */
	public ArrayList<SqlBindParameter> getBindParameters() {
		return bindParameters;
	}

	@Override
	public String getStatementName() {
		return statementName;
	}

	@Override
	public String getSqlText() {
		return sqlText;
	}

	@Override
	public Set<String> getBindNames() {
		if (bindNames == null) {
			bindNames = new TreeSet<String>();
			for (SqlBindParameter parm : bindParameters) {
				bindNames.add(parm.getBindName());
			}
		}
		// TODO Auto-generated method stub
		return bindNames;
	}

	@Override
	public boolean hasBindParameters() {
		return getBindNames().size() > 0;
	}

	/**
	 * Returns a map containing the sql prepared statement indexes. The key is
	 * the bind variable name (in lower case), and the value is a list of the
	 * one-based indexes (the base of one is used as per the jdbc spec) that the
	 * bind variable is found at.
	 * 
	 * @return bindIndex
	 */
	@Override
	public Map<String, List<Integer>> getBindIndexes() {
		if (bindIndexes == null) {
			bindIndexes = new TreeMap<String, List<Integer>>();
			for (SqlBindParameter parm : bindParameters) {
				List<Integer> indexes = bindIndexes.get(parm.getBindName());
				if (indexes == null) {
					indexes = new ArrayList<Integer>();
					bindIndexes.put(parm.getBindName(), indexes);
				}
				indexes.add(parm.getIndex());
			}

		}
		return bindIndexes;
	}

}
