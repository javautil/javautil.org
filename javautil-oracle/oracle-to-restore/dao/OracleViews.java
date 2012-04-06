package com.dbexperts.oracle.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dbexperts.oracle.DatabaseMetaData.OracleView;

public class OracleViews
{
	
	static final String columns = "" +
        
        "    owner,\n" +
        "    view_name,\n" +
        "    text_length,\n" +
        "    text,\n" +
        "    type_text_length,\n" +
        "    type_text,\n" +
        "    oid_text_length,\n" +
        "    oid_text,\n" +
        "    view_type_owner,\n" +
        "    view_type,\n" +
        "    superview_name\n" ;
        
        
        
        public static List<OracleView> get(Connection conn, String schema, String viewName) throws SQLException {
            ArrayList<OracleView> views = new ArrayList<OracleView>();
            if (conn == null) {
        	throw new IllegalArgumentException("conn is null");
            }
            String text = "select " + columns + " from all_views " + 
                          "where (owner like :schema or :schema is null ) " +
                          " and (view_name like :view_name or :view_name is null) " +
                          "order by owner, view_name ";
            PreparedStatement ps =  conn.prepareStatement(text);
            ps.setString(1,schema);
            ps.setString(2,schema);
            ps.setString(3,viewName);
            ps.setString(4,viewName);
            ResultSet rset = ps.executeQuery();
            while (rset.next()) {
        	OracleView v = new OracleView();
        	getRow(rset,v);
        	views.add(v);
            }
            rset.close();
            ps.close();
            return views;
            
            
        }


	


    public static void getRow(ResultSet rset, OracleView row)
    throws java.sql.SQLException
    {
        String columnName = null;

        try {
            row.setOwner(rset.getString(columnName = "OWNER"));
            row.setViewName(rset.getString(columnName = "VIEW_NAME"));

            row.setTextLength(rset.getLong(columnName = "TEXT_LENGTH"));
            row.setText(rset.getString(columnName = "TEXT"));

            row.setTypeTextLength(rset.getLong(columnName = "TYPE_TEXT_LENGTH"));
            row.setTypeText(rset.getString(columnName = "TYPE_TEXT"));

            row.setOidTextLength(rset.getLong(columnName = "OID_TEXT_LENGTH"));
            row.setOidText(rset.getString(columnName = "OID_TEXT"));
            row.setViewTypeOwner(rset.getString(columnName = "VIEW_TYPE_OWNER"));
            row.setViewType(rset.getString(columnName = "VIEW_TYPE"));
            row.setSuperviewName(rset.getString(columnName = "SUPERVIEW_NAME"));
        }
            catch (java.sql.SQLException s) {
                throw new java.sql.SQLException("error processing column" + columnName + "\n" + s.getMessage());
            }
        } // end of getRow 
	
 
}  // end of class
