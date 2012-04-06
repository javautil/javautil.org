//package com.dbexperts.oracle.DatabaseMetaData;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.dbexperts.jdbc.DatabaseMetaData.IndexColumn;
//import com.dbexperts.jdbc.DatabaseMetaData.IndexColumns;
//import com.dbexperts.jdbc.DatabaseMetaData.JDBCIndexColumns;
//
//public class OracleIndexColumns extends JDBCIndexColumns implements IndexColumns {
//// todo delete
//    public OracleIndexColumns(Connection conn, OracleTable table) throws SQLException {
//	if (conn == null) {
//	    throw new IllegalArgumentException("conn is null");
//	}
//	if (table == null) {
//	    throw new IllegalArgumentException("table is null");
//	}
//	
//	List<OracleIndexColumn> columns = IndexColumnsDAO.getForTable(conn, table.getSchemaName(), table.getTableName());
//	setIndexColumns(columns);
////	for (OracleIndexColumn column : columns) {
////	    
////	}
//    }
//
//   
//
//}
