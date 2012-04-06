//package com.dbexperts.oracle.DatabaseMetaData;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.dbexperts.jdbc.DatabaseMetaData.IndexColumn;
//import com.dbexperts.oracle.DatabaseMetaData.OracleIndexColumn;
//
//public class IndexColumnsDAO
//{
//	int insertBatchSize = 100;
//	private Connection conn;
//
//	private ResultSet rset = null;
//	private PreparedStatement selectStmt = null;
//	static final String selectText = "" +
//         "SELECT\n" +
//        "    index_owner,\n" +
//        "    index_name,\n" +
//        "    table_owner,\n" +
//        "    table_name,\n" +
//        "    column_name,\n" +
//        "    column_position,\n" +
//        "    column_length,\n" +
//        "    char_length,\n" +
//        "    descend\n" +
//        "FROM ALL_IND_COLUMNS\n";
//
//	public static List<IndexColumn> getForTable(Connection conn, String tableOwner, String tableName) throws SQLException {
//	    if (conn == null) {
//		throw new IllegalArgumentException("conn is null");
//	    }
//	    if (tableOwner == null) {
//		throw new IllegalArgumentException("tableOwner is null");
//	    }
//	    if (tableName == null) {
//		throw new IllegalArgumentException("tableName is null");
//	    }
//	    String text =          
//		"SELECT\n" +  //
//	        "    index_owner,\n" +  //
//	        "    index_name,\n" + //
//	        "    table_owner,\n" + //
//	        "    table_name,\n" + //
//	        "    column_name,\n" + //
//	        "    column_position,\n" + //
//	        "    column_length,\n" + //
//	        "    char_length,\n" + //
//	        "    descend\n" + //
//	        "FROM ALL_IND_COLUMNS " + //
//	        "where table_owner = :owner and table_name = :table_name " + //
//	        "order by index_name, column_position ";
//	    PreparedStatement ps = conn.prepareStatement(text);
//	    ps.setString(1, tableOwner);
//	    ps.setString(2,tableName);
//	    ResultSet rs = ps.executeQuery();
//	    while (rs.next()) {
//		// todo complete
//	    }
//	    ArrayList<IndexColumn> cols = new ArrayList<IndexColumn>();
//	    return cols;
//	    
//	}
//	
//
//    public static void getRow(ResultSet rset, OracleIndexColumn row)
//    throws java.sql.SQLException
//    {
//        String columnName = null;
//
//        try {
//            row.setIndexOwner(rset.getString(columnName = "INDEX_OWNER"));
//            row.setIndexName(rset.getString(columnName = "INDEX_NAME"));
//            row.setTableOwner(rset.getString(columnName = "TABLE_OWNER"));
//            row.setTableName(rset.getString(columnName = "TABLE_NAME"));
//            row.setColumnName(rset.getString(columnName = "COLUMN_NAME"));
//
//            row.setColumnPosition(rset.getInt(columnName = "COLUMN_POSITION"));
//
//            row.setColumnLength(rset.getInt(columnName = "COLUMN_LENGTH"));
//
//            row.setCharLength(rset.getInt(columnName = "CHAR_LENGTH"));
//            row.setDescend(rset.getString(columnName = "DESCEND"));
//        }
//            catch (java.sql.SQLException s) {
//                throw new java.sql.SQLException("error processing column" + columnName + "\n" + s.getMessage());
//            }
//        } // end of getRow 
//
//
//   
//}  // end of class
