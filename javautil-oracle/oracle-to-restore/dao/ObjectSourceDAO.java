package com.dbexperts.oracle.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dbexperts.jdbc.DatabaseMetaData.DatabaseObject;
import com.dbexperts.jdbc.DatabaseMetaData.DatabaseObjectType;
import com.dbexperts.oracle.DatabaseMetaData.DdSourceLine;

public class ObjectSourceDAO
{

	
	
//	static final String selectText = "" +
//         "SELECT\n" +
//        "    owner,\n" +
//        "    name,\n" +
//        "    type,\n" +
//        "    line,\n" +
//        "    text\n" +
//        "FROM DBA_SOURCE\n";
	
	static final String columnList  =  "    owner, name, type, line, text " ;
   
	

	//PreparedStatement insertStmt = null;
    
	public static  List<String> getForObject(Connection conn, boolean useDbaView, DatabaseObject dbo) throws SQLException {
	    if (conn == null) {
		throw new IllegalArgumentException("conn is null");
	    }
	    DatabaseObjectType type =  dbo.getDatabaseObjectType();
	    if (!type.hasSource()) {
		throw new IllegalArgumentException("Source not applicable to type " + dbo.toString());
	    }
	    if (!dbo.isFullyQualified()) {
		throw new IllegalArgumentException("The database object is not fully qualified: " + dbo.toString() ); // todo test
	    }
	    ArrayList<String> retval = new ArrayList<String>();
	    String viewName = useDbaView ? "dba_" : "all_" + "source";
	    String sqlText = "select text from " + viewName + 
	    " where owner = :owner and \n" +  
	         "  name = :name and \n" +
	          " type = :type \n " + 
 	    " order by type, line  ";
	    PreparedStatement ps = conn.prepareStatement(sqlText);
	    ps.setString(1, dbo.getOwner());
	    ps.setString(2, dbo.getName());
	    String t = dbo.getDatabaseObjectType().getDbaSourceType();
	    
	    ps.setString(3, t);
	    
	    ResultSet rset = ps.executeQuery();
	    while (rset.next()) {
		String text = rset.getString(1); 
		retval.add(text);
	    }
	    ps.close();
	    return retval;
	}

    public static void getRow(ResultSet rset, DdSourceLine row)
    throws java.sql.SQLException
    {
        String columnName = null;

        try {
            row.setOwner(rset.getString(columnName = "OWNER"));
            row.setName(rset.getString(columnName = "NAME"));
            row.setType(rset.getString(columnName = "TYPE"));

            row.setLine(rset.getInt(columnName = "LINE"));
            row.setText(rset.getString(columnName = "TEXT"));
        }
            catch (java.sql.SQLException s) {
                throw new java.sql.SQLException("error processing column" + columnName + "\n" + s.getMessage());
            }
        } // end of getRow 
//	String getSelectText() {
//		return selectText;
//	}

   
}  // end of class
