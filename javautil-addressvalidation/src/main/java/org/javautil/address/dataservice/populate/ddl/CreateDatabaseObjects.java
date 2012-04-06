/**
 * 
 */
package org.javautil.address.dataservice.populate.ddl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * <p>CreateDatabaseObjects class.</p>
 *
 * @author siyer TODO replace with maven task
 *         http://mojo.codehaus.org/sql-maven-plugin/examples/execute.html
 * @version $Id: CreateDatabaseObjects.java,v 1.3 2012/03/04 12:31:15 jjs Exp $
 */
public class CreateDatabaseObjects {

	String createTableStmt = " " + "CREATE TABLE UT_ADDR_VALIDATE ( "
			+ "UT_ADDR_VALIDATE_NBR  NUMBER(18)	NOT NULL, "
			+ "RUN_NBR               NUMBER(9)	NOT NULL, "
			+ "DATA_SRC_NBR          NUMBER(9)	NOT NULL, "
			+ "DATA_SRC_PK           NUMBER(18)	NOT NULL, "
			+ "NAME                  VARCHAR2(40), "
			+ "RAW_ADDR_1            VARCHAR2(35), "
			+ "RAW_ADDR_2            VARCHAR2(35), "
			+ "RAW_ADDR_3            VARCHAR2(35), "
			+ "RAW_CITY              VARCHAR2(25), "
			+ "RAW_STATE_CD          VARCHAR2(5), "
			+ "RAW_CNTRY_CD          VARCHAR2(3), "
			+ "RAW_POSTAL_CD         VARCHAR2(10), "
			+ "STD_PO_BOX            VARCHAR2(20), "
			+ "STD_STREET_NAME       VARCHAR2(35), "
			+ "STD_STREET_NBR        VARCHAR2(12), "
			+ "STD_STREET_TYPE       VARCHAR2(12), "
			+ "STD_SUBUNIT_CD        VARCHAR2(20), "
			+ "STD_SUBUNIT_TYPE      VARCHAR2(10), "
			+ "STD_ADDR_1            VARCHAR2(40), "
			+ "STD_ADDR_2            VARCHAR2(40), "
			+ "STD_CITY              VARCHAR2(40), "
			+ "STD_STATE_CD          VARCHAR2(4), "
			+ "STD_POSTAL_CD         VARCHAR2(10), "
			+ "AUTH_ADDR_1           VARCHAR2(50), "
			+ "AUTH_ADDR_2           VARCHAR2(50), "
			+ "AUTH_CITY             VARCHAR2(40), "
			+ "AUTH_STATE_CD         VARCHAR2(4), "
			+ "AUTH_POSTAL_CD        VARCHAR2(10), "
			+ "AUTH_RQST_CD          VARCHAR2(1), "
			+ "STD_AUTH_ADDR_1       VARCHAR2(40), "
			+ "STD_AUTH_ADDR_2       VARCHAR2(40), "
			+ "STD_AUTH_CITY         VARCHAR2(40), "
			+ "STD_AUTH_STATE_CD     VARCHAR2(4), "
			+ "STD_AUTH_POSTAL_CD    VARCHAR2(10), "
			+ "STD_TS                DATE, "
			+ "STD_ERR_MSG           VARCHAR2(256), "
			+ "AUTH_ERR_MSG          VARCHAR2(256), "
			+ "STD_AUTH_ERR_MSG      VARCHAR2(256), "
			+ "SRC_COMMENT           VARCHAR2(10), "
			+ "SRC_INVALID_FLG       VARCHAR2(1), "
			+ "AUTH_LATITUDE         NUMBER(9,6), "
			+ "AUTH_LONGITUDE        NUMBER(9,6) " + ") ";

	String alterTableStmt1 = " " + "ALTER TABLE UT_ADDR_VALIDATE ADD "
			+ "CONSTRAINT UT_ADDR_VALIDATE_PK " + "PRIMARY KEY "
			+ "(UT_ADDR_VALIDATE_NBR) ";

	String alterTableStmt2 = " " + "ALTER TABLE UT_ADDR_VALIDATE ADD "
			+ "CONSTRAINT UAV_DATA_SRC_UK "
			+ "UNIQUE (DATA_SRC_NBR, DATA_SRC_PK) ";

	/**
	 * <p>Constructor for CreateDatabaseObjects.</p>
	 *
	 * @param conn a {@link java.sql.Connection} object.
	 * @throws java.sql.SQLException if any.
	 */
	public CreateDatabaseObjects(final Connection conn) throws SQLException {
		CallableStatement cs = conn.prepareCall(createTableStmt);
		cs.execute();
		cs = conn.prepareCall(alterTableStmt1);
		cs.execute();
		cs = conn.prepareCall(alterTableStmt2);
		cs.execute();
		cs.close();
	}
}
