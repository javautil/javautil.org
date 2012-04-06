package com.dbexperts.oracle.servicerequest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;


public class ServiceRqstBaseS {
    static String selectText = "" +
            "SELECT\n" +
            "    service_rqst_cd,\n" +
            "    service_rqst_descr,\n" +
            "    classname\n" +
            "FROM service_rqst\n";
    /**
     * sql text for inserting all rows into the table
     */
    static String insertText = "insert into service_rqst " +
            "(\n" +
            "service_rqst_cd, \n" +
            "service_rqst_descr, \n" +
            "classname \n" +
            ")\n" +
            "select ?,?,? from dual";

    /**
     *  container for rows retrieved from fetches in fetched sequence
     */
    ArrayList<ServiceRqstBean> rows = new ArrayList<ServiceRqstBean>();
    /**
     *   HashMap based on obfuscated primary key
     */
    //HashMap map = new HashMap();
    /**
     *   this variable is employed to support for the efficiencies afforded by
     *   connection pooling while simultaneously providing a mechanism to
     *   minimize statement reparsing, which can be very expensive
     */
    boolean persistConnection = false;
    ResultSet rset = null;


    PreparedStatement selectStmt = null;
    PreparedStatement insertStmt = null;

    static void getRow(final ResultSet rset, final ServiceRqstBean row)
            throws java.sql.SQLException {
        String columnName = null;

        try {
            final String ServiceRqstCd = rset.getString(columnName = "service_rqst_cd");
            row.setServiceRqstCd(rset.wasNull() ? null : ServiceRqstCd);

            final String ServiceRqstDescr = rset.getString(columnName = "service_rqst_descr");
            row.setServiceRqstDescr(rset.wasNull() ? null : ServiceRqstDescr);

            final String Classname = rset.getString(columnName = "classname");
            row.setClassname(rset.wasNull() ? null : Classname);

        } catch (final java.sql.SQLException s) {
            throw new java.sql.SQLException("error processing column " + columnName + "\nstmt text " + selectText + "\n" + s.getMessage());
        }
    } // end of getRow

    public ServiceRqstBaseS() {
    }

    public void add(final ServiceRqstBean row) {
        rows.add(row);
    }

    public void clear() {
        rows.clear();
    }

    public void connectionPersistenceBegin() {
        persistConnection = true;
    }

    public void connectionPersistenceEnd()
            throws java.sql.SQLException {
        persistConnection = false;
        if (insertStmt != null) {
            insertStmt.close();
            insertStmt = null;
        }
        if (selectStmt != null) {
            selectStmt.close();
            selectStmt = null;
        }
    }
//
//    /** insert all tuples into persistent store */
//
//    public void insert(Connection dbc,Collection<ServiceRqstBean> rows)
//            throws java.sql.SQLException {
//        Iterator<ServiceRqstBean> it = rows.iterator();
//        while (it.hasNext()) {
//            ServiceRqstBean row = (ServiceRqstBean) it.next();
//            insertRow(row, dbc);
//        }
//    }

//    /**
//     * ConnectionPool safe method for persisting an instance of ServiceRqstBase
//     * if this is called repeatedly within a transaction, it is highly recommended
//     * that the method pairs connectionPersistenceBegin and connectionPersistenceEnd be called
//     * to reduce sql statement parsing.
//     */
//    public void insertRow(ServiceRqstBean row, Connection dbc)
//            throws java.sql.SQLException {
//        if (insertStmt == null || !persistConnection) {
//            insertStmt = dbc.prepareStatement(insertText);
//        }
//
//        insertStmt.setString(1, row.getServiceRqstCd());
//        insertStmt.setString(2, row.getServiceRqstDescr());
//        insertStmt.setString(3, row.getClassname());
//        insertStmt.executeUpdate();
//    }

    /** return the rows iterator */
    public Iterator<ServiceRqstBean> iterator() {
        return rows.iterator();
    }

    /** return the size of the rows container */
    public int size() {
        return rows.size();
    }

//    /** return all of the tuples in regularly formed xml */
//    public String toXml() {
//        StringBuffer buff = new StringBuffer(1024);
//        Iterator<ServiceRqstBean> it = rows.iterator();
//        buff.append("<ServiceRqstS>");
//        while (it.hasNext()) {
//            ServiceRqst tuple = (ServiceRqst) it.next();
//            buff.append(tuple.toXml());
//        }
//        buff.append("</ServiceRqstS>");
//        return new String(buff);
//    }

}  // end of class
