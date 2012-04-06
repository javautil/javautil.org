package org.javautil.jdbc.datasources;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * Uses the C3P0 connection pooling library to maintain a connection pool.
 * 
 * todo figure out how remove all of these dependencies from this project needs
 * a ConnectionCacheProvider
 * 
 * @see http://www.mchange.com/projects/c3p0/index.html
 */
public class C3P0Datasource extends AbstractDataSource {

	private final Logger logger = Logger.getLogger(getClass());

	private final ComboPooledDataSource cpds = new ComboPooledDataSource();

	@Override
	protected void initialize() {
		cpds.setDescription(getName());
		cpds.setUser(getProperty(USER));
		cpds.setPassword(getProperty(PASSWORD));
		cpds.setJdbcUrl(getProperty(URL));
		try {
			cpds.setDriverClass(getProperty(DRIVER));
		} catch (final PropertyVetoException e) {
			throw new RuntimeException(e);

		}

	}

	@Override
	public Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			conn = cpds.getConnection();
		} catch (final SQLException sqe) {
			final StringBuilder sb = new StringBuilder();
			sb.append("user ").append(getProperty(USER));
			sb.append(" URL ").append(getProperty(URL));
			sb.append(" driver ").append(getProperty(DRIVER));
			logger.error(sqe.getMessage() + sb.toString());
			throw new SQLException(sb.toString(), sqe);
		}
		return conn;
	}

	/**
	 * Constructs a <code>String</code> with all attributes in name = value
	 * format.
	 * 
	 * @return a <code>String</code> representation of this object.
	 */
	@Override
	public String toString() {
		final String TAB = "    ";
		String retValue = "";
		retValue = getClass().getName() + " ( " + super.toString() + TAB
				+ "logger = " + this.logger + TAB + "cpds = " + this.cpds + TAB
				+ cpds.getDescription() + TAB + cpds.getUser() 
				+ TAB // + cpds.getPassword()
				+ TAB + cpds.getJdbcUrl() + TAB + cpds.getDriverClass() + " )";
		return retValue;
	}

}
