//package com.dbexperts.oracle.DatabaseMetaData;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.Map;
//import java.util.TreeMap;
//
//public class OracleTableIndexes {
//    private Map <String,OracleIndex> indexByName = new TreeMap<String,OracleIndex>();
//    
//    public OracleTableIndexes(Connection conn, OracleTable table) throws SQLException {
//	if (conn == null) {
//	    throw new IllegalArgumentException("conn is null");
//	}
//	if (table == null) {
//	    throw new IllegalArgumentException("table is null");
//	}
//	
//	List<OracleIndex> indexes = OracleIndexDAO.getIndexesForTable(conn,table);
//	for (OracleIndex index : indexes) {
//	    indexByName.put(index.getIndexName(),index);
//	}
//	
//	List<OracleIndexColumn> columns = IndexColumnsDAO.getForTable(conn, table.getSchemaName(), table.getTableName());
//	for (OracleIndexColumn column : columns) {
//	    OracleIndex index = indexByName.get(column.getIndexName());
//	    if (index == null) {
//		throw new IllegalStateException("Cannot locate index for indexColumn " + column);
//	    }
//	    index.addIndexColumn(column);
//	}
//    }
//
////    @Override
////    public ArrayList<Exception> getExceptions() {
////	// TODO Auto-generated method stub
////	return null;
////    }
////
////    @Override
////    public List<IndexColumn> getIndexColumns() {
////	// TODO Auto-generated method stub
////	return null;
////}
//}
