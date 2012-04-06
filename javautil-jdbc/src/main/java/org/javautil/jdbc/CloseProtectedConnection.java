package org.javautil.jdbc;

import java.sql.Connection;

/**
 * A WrappedConnection that cannot be closed by calling close().
 * 
 * @author bcm
 */
public class CloseProtectedConnection extends WrappedConnection {

	public CloseProtectedConnection(final Connection connection) {
		super(connection, null);
	}

	@Override
	public void close() {
		// do nothing
	}

}