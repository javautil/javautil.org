package org.javautil.hibernate.connectionprovider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.connection.ConnectionProvider;
import org.javautil.jdbc.H2SingletonInstance;

/**
 * 
 * @author bcm
 * 
 * @see http://www.hibernate.org/hib_docs/v3/api/org/hibernate/connection/
 *      ConnectionProvider.html
 */
public class H2MemConnectionProvider implements ConnectionProvider {

	private Connection connectionInstance = null;

	@Override
	public void close() throws HibernateException {
		try {
			if (connectionInstance != null && !connectionInstance.isClosed()) {
				connectionInstance.close();
			}
		} catch (final SQLException e) {
			throw new HibernateException(e);
		}
	}

	@Override
	public void closeConnection(final Connection conn) throws SQLException {
		conn.close();
	}

	@Override
	public void configure(final Properties arg0) throws HibernateException {
		// nothing to do here
	}

	@Override
	public synchronized Connection getConnection() throws SQLException {
		if (connectionInstance == null) {
			connectionInstance = H2SingletonInstance.getConnection();
		}
		return connectionInstance;
	}

	// todo jjs what does this mean?
	@Override
	public boolean supportsAggressiveRelease() {
		return false;
	}

}
