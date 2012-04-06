/**
 * 
 */
package org.javautil.address.dataservice.populate.ddl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * <p>DropDatabaseObjects class.</p>
 *
 * @author siyer
 * @version $Id: DropDatabaseObjects.java,v 1.2 2012/03/04 12:31:15 jjs Exp $
 */
public class DropDatabaseObjects {

	String dropTableStmt = "drop table ut_addr_validate";

	/**
	 * <p>Constructor for DropDatabaseObjects.</p>
	 *
	 * @param conn a {@link java.sql.Connection} object.
	 * @throws java$sql$SQLException if any.
	 */
	public DropDatabaseObjects(final Connection conn) throws SQLException {
		final CallableStatement cs = conn.prepareCall(dropTableStmt);
		try {
			cs.execute();
		} catch (final SQLException s) {
			throw new RuntimeException(s);
		}
		cs.close();
	}

}
