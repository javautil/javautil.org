package com.dbexperts.oracle.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.dbexperts.oracle.DdScope;
import com.dbexperts.oracle.dto.OracleObject;

public class OracleObjectDAO
{
    	private Logger logger = Logger.getLogger(getClass());
	static final String columnList = "" +
        
        "    owner,\n" +
        "    object_name,\n" +
        "    subobject_name,\n" +
        "    object_id,\n" +
        "    data_object_id,\n" +
        "    object_type,\n" +
        "    created,\n" +
        "    last_ddl_time,\n" +
        "    timestamp,\n" +
        "    status,\n" +
        "    temporary,\n" +
        "    generated,\n" +
        "    secondary\n";

	/**
	 * Gets all of the objects 
	 * @param conn
	 * @param scope
	 * @param owner
	 * @return
	 * @throws SQLException
	 */
    public List<OracleObject> getForLikeOwner(Connection conn, DdScope scope, String owner) throws SQLException {
	ArrayList<OracleObject> objects = new ArrayList<OracleObject>();
	
	String selectText = "select " + columnList + " from " + scope.toString() + 
	 "_objects where owner like upper(:owner)";
	PreparedStatement ps = null;
	
	if (conn == null) {
	    throw new IllegalArgumentException("conn is null");
	}
	if (scope == null) {
	    throw new IllegalArgumentException("scope is null");
	}
	if ( owner == null ) {
	    throw new IllegalArgumentException("owner is null");
	}
	try {
	    ps = conn.prepareStatement(selectText);
	    ps.setString(1, owner);
	    ResultSet rset = ps.executeQuery();
	    while (rset.next()) {
		OracleObject ddo = new OracleObject();
		getRow(rset,ddo);
		objects.add(ddo);
		logger.debug(ddo.toString());
	    }
	    return objects;
	} catch (SQLException e) {
	    if (ps != null) {
	    try {
		ps.close();
	    } catch (SQLException e2) {
		// TODO Auto-generated catch block
		throw e2;
	    }
	    }
	    // TODO Auto-generated catch block
	    throw e;
	}
	
	
    }
	

    public static void getRow(ResultSet rset, OracleObject row)
    throws java.sql.SQLException
    {
        String columnName = null;

        try {
            row.setOwner(rset.getString(columnName = "OWNER"));
            row.setObjectName(rset.getString(columnName = "OBJECT_NAME"));
            row.setSubobjectName(rset.getString(columnName = "SUBOBJECT_NAME"));

            row.setObjectId(rset.getLong(columnName = "OBJECT_ID"));

            row.setDataObjectId(rset.getLong(columnName = "DATA_OBJECT_ID"));
            row.setObjectType(rset.getString(columnName = "OBJECT_TYPE"));
            row.setCreated(rset.getTimestamp(columnName = "CREATED"));
            row.setLastDdlTime(rset.getTimestamp(columnName = "LAST_DDL_TIME"));
            row.setTimestamp(rset.getString(columnName = "TIMESTAMP"));
            row.setStatus(rset.getString(columnName = "STATUS"));
            row.setTemporary(rset.getString(columnName = "TEMPORARY"));
            row.setGenerated(rset.getString(columnName = "GENERATED"));
            row.setSecondary(rset.getString(columnName = "SECONDARY"));
        }
            catch (java.sql.SQLException s) {
                throw new java.sql.SQLException("error processing column" + columnName + "\n" + s.getMessage());
            }
        } // end of getRow 
	

}  // end of class
