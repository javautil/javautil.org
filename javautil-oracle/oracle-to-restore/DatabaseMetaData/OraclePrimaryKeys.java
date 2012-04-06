package com.dbexperts.oracle.DatabaseMetaData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dbexperts.jdbc.DatabaseMetaData.PrimaryKey;

public class OraclePrimaryKeys {

    private static final String query = 
	"select ac.constraint_name, aic.column_name, aic.column_position " +
	"from all_ind_columns aic, all_constraints ac " +
	"where aic.index_name = ac.constraint_name and " +
	"       ac.constraint_type = 'P' and " +
	"      aic.index_owner = ac.owner and" +
	"      aic.index_owner = :owner and " +
	"      ac.owner = :owner and " +
	"      aic.table_name = :table_name and " +
	"      ac.table_name = :table_name";
    	
    
    public static PrimaryKey getPrimaryKey(Connection conn, 
	    String schemaName, String tableName) throws SQLException {
	if (conn == null) {
	    throw new IllegalArgumentException("conn is null");
	}
	if (schemaName ==  null){
	    throw new IllegalArgumentException("schemaName is null");
	}
	if (tableName == null) {
	    throw new IllegalArgumentException("tableName is null");
	}
	PreparedStatement ps = conn.prepareStatement(query);
	ps.setString(1, schemaName);
	ps.setString(2, schemaName);
	ps.setString(3, tableName);
	ps.setString(4, tableName);
	PrimaryKey   pk = null;
	ResultSet rset = null;
	    try {
		rset = ps.executeQuery();
	    } catch (SQLException sqe) {
		throw new SQLException("while processing " + query + " " + sqe.getMessage());
	    }
	while (rset.next()) {
	   if (pk == null) {
	       String constraintName = rset.getString("CONSTRAINT_NAME");
	       pk = new PrimaryKey(schemaName, tableName, constraintName);
	   }
	   String columnName= rset.getString("COLUMN_NAME");
	   short pos = rset.getShort("COLUMN_POSITION");
	}
	ps.close();
	return pk;
    }

    
//    public static PrimaryKey getPrimaryKey(Connection conn, String catalog,
//	    String schemaName, String tableName) {
//	// TODO Auto-generated method stub
//	return null;
//    }

}
