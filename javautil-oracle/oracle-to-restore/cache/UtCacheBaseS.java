package com.dbexperts.oracle.cache;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
/**
Version:   $Id: UtCacheBaseS.java,v 1.1 2011/08/31 21:09:58 jjs Exp $
*/
public class UtCacheBaseS
{
    static String selectText = "" +
         "SELECT\n" +
        "    object_name,\n" +
        "    change_time,\n" +
        "    cache_change_nbr\n" +
        "FROM ut_cache\n";
    /**
    * sql text for inserting all rows into the table
    */
    static String insertText = "insert into ut_cache "+
        "(\n"+
             "object_name, \n"+
             "change_time, \n"+
             "cache_change_nbr \n"+
         ")\n"+
        "select ?,?,? from dual";
    /**
     *  container for rows retrieved from fetches in fetched sequence
     */
    ArrayList<UtCache> rows = new ArrayList<UtCache>();




    static void getRow(final ResultSet rset, final UtCache row)
    throws java.sql.SQLException
    {
        String columnName = null;

        try {
        final String ObjectName = rset.getString(columnName = "object_name");
        row.setObjectName(rset.wasNull() ? null : ObjectName);

        final Timestamp ChangeTime = rset.getTimestamp(columnName = "change_time");
        row.setChangeTime(rset.wasNull() ? null : ChangeTime);

        final int CacheChangeNbr = rset.getInt(columnName = "cache_change_nbr");
       // row.setCacheChangeNbr(rset.wasNull() ? null : new Integer(CacheChangeNbr));

        }
        catch (final java.sql.SQLException s)
        {
            throw new java.sql.SQLException("error processing column" + columnName + "\n" + s.getMessage());
        }
    } // end of getRow
    public UtCacheBaseS()
    {
    }


    public void add(final UtCache row)
    {
       rows.add(row);
    }
    public void clear()
    {
       rows.clear();
    }



    /** return the rows iterator */
    public Iterator<UtCache> iterator()
    {
        return rows.iterator();
    }




    /** return the size of the rows container */
    public int size()
    {
        return rows.size();
    }
}  // end of class
