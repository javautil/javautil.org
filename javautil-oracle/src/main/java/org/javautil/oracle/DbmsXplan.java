package org.javautil.oracle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

/**
 * 
 * In order for this to work the connection provided must be to an Oracle
 * Database, but it does not have to be an OracleConnection.
 * 
 * The connected user must have priviliges on sys objects.
 * 
 * 
 * <pre>
 * grant select on v_$session to &amp;&amp;ConnectionUser; 
 * grant select on v_$sql_plan_statistics_all to &amp;&amp;ConnectionUser;
 * grant select on v_$sql to &amp;&amp;ConnectionUser;
 * </pre>
 * 
 * Grant succeeded.
 * 
 * 
 * @param conn
 * @return
 * @throws SQLException
 */
public class DbmsXplan {

	private final Logger logger = Logger.getLogger(getClass());
	private final String newline = System.getProperty("line.separator");

	// todo clean up
	// todo explain what needs to be done to use this
	/**
	 * 
	 * This returns the result of
	 * 
	 * <pre>
	 * select * from table(dbms_xplan.display_cursor(null,null,'ALLSTATS LAST'))
	 * 
	 * <pre>
	 * as a newline delimited string
	 * 
	 * &#064;param conn
	 * &#064;return
	 * @throws SQLException
	 */
	public String getExplainPlanForLastStatement(final Connection conn)
			throws SQLException {
		if (conn == null) {
			throw new IllegalArgumentException("conn is null");
		}
		final String text = "select * from table(dbms_xplan.display_cursor(null,null,'ALLSTATS LAST'))";
		final Statement s = conn.createStatement();
		final ResultSet rset = s.executeQuery(text);
		final int colCnt = rset.getMetaData().getColumnCount();
		logger.debug("colCnt " + colCnt);

		final StringBuilder b = new StringBuilder();
		while (rset.next()) {
			for (int i = 1; i <= colCnt; i++) {
				b.append(rset.getString(colCnt));
				b.append("\t");
			}
			b.append(newline);
		}
		return b.toString();

	}
}
