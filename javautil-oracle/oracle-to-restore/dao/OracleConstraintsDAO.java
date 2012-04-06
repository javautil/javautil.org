package com.dbexperts.oracle.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dbexperts.oracle.DatabaseMetaData.OracleConstraint;
import com.dbexperts.oracle.dto.OracleTableColumn;

public class OracleConstraintsDAO {

static final String newline = System.getProperty("line.separator");
static final String columnList = 
    "    owner,\n" +
    "    constraint_name,\n" +
    "    constraint_type,\n" +
    "    table_name,\n" +
    "    search_condition,\n" +
    "    r_owner,\n" +
    "    r_constraint_name,\n" +
    "    delete_rule,\n" +
    "    status,\n" +
    "    deferrable,\n" +
    "    deferred,\n" +
    "    validated,\n" +
    "    generated,\n" +
    "    bad,\n" +
    "    rely,\n" +
    "    last_change,\n" +
    "    index_owner,\n" +
    "    index_name,\n" +
    "    invalid,\n" +
    "    view_related\n" ;

public static String getSelectText(boolean useDba) {
    StringBuilder b = new StringBuilder();
    b.append("select ");
    b.append(newline);
    b.append(columnList);
    b.append(" from ");
    String viewName = useDba ? "dba" : "all" + "_constraints ";
    b.append(viewName);
    b.append("where owner = :owner and \n");
    b.append("      table_name = :table_name ");
    b.append(" order by constraint_name ");
    return b.toString();
}

public static List<OracleConstraint> getForTable(Connection conn,String owner, String tableName, boolean useDba, boolean upperCaseArgs) throws SQLException {
    if (conn == null) {
	throw new IllegalArgumentException("conn is null");
    }
    if (owner == null) {
	throw new IllegalArgumentException("owner is null");

    }
    if (tableName == null) {
	throw new IllegalArgumentException("tableName is null");
    }
    ArrayList<OracleConstraint> constraints = new ArrayList<OracleConstraint>();
    String myOwner = upperCaseArgs ? owner.toUpperCase() : owner;
    String myTableName = upperCaseArgs ? tableName.toUpperCase() : tableName;
    String selectText = getSelectText(useDba);
    PreparedStatement ps = conn.prepareStatement(selectText);
    ps.setString(1, owner);
    ps.setString(2, tableName);
    ResultSet rset = null;
    try {
	rset = ps.executeQuery();
    } catch (SQLException sqe) {
	throw new SQLException("while processing '" + selectText + "'" + sqe.getMessage());
    }
    while (rset.next()) {
	OracleConstraint otc = new OracleConstraint();
	getRow(rset,otc);
	constraints.add(otc);
    }
    ps.close();
    return constraints;
}
public static void getRow(ResultSet rset, OracleConstraint row)
throws java.sql.SQLException
{
    String columnName = null;

    try {
	row.setOwner(rset.getString(columnName = "OWNER"));
	row.setConstraintName(rset.getString(columnName = "CONSTRAINT_NAME"));
	row.setConstraintType(rset.getString(columnName = "CONSTRAINT_TYPE"));
	row.setTableName(rset.getString(columnName = "TABLE_NAME"));
	row.setSearchCondition(rset.getString(columnName = "SEARCH_CONDITION"));
	row.setROwner(rset.getString(columnName = "R_OWNER"));
	row.setRConstraintName(rset.getString(columnName = "R_CONSTRAINT_NAME"));
	row.setDeleteRule(rset.getString(columnName = "DELETE_RULE"));
	row.setStatus(rset.getString(columnName = "STATUS"));
	row.setDeferrable(rset.getString(columnName = "DEFERRABLE"));
	row.setDeferred(rset.getString(columnName = "DEFERRED"));
	row.setValidated(rset.getString(columnName = "VALIDATED"));
	row.setGenerated(rset.getString(columnName = "GENERATED"));
	row.setBad(rset.getString(columnName = "BAD"));
	row.setRely(rset.getString(columnName = "RELY"));
	row.setLastChange(rset.getTimestamp(columnName = "LAST_CHANGE"));
	row.setIndexOwner(rset.getString(columnName = "INDEX_OWNER"));
	row.setIndexName(rset.getString(columnName = "INDEX_NAME"));
	row.setInvalid(rset.getString(columnName = "INVALID"));
	row.setViewRelated(rset.getString(columnName = "VIEW_RELATED"));
    }
    catch (java.sql.SQLException s) {
	throw new java.sql.SQLException("error processing column" + columnName + "\n" + s.getMessage());
    }
} 

    public static List<OracleConstraint> getImportedKeyConstraints(Collection<OracleConstraint> constraints,String schema, String tableName) {
	if (constraints == null) {
	    throw new IllegalArgumentException("constraints is null");
	}
	List<OracleConstraint> cons = new ArrayList<OracleConstraint>();
	for (OracleConstraint con : constraints) {
	    if (con.getOwner().equals(schema) || schema == null) {
		if (con.getTableName().equals(tableName) || tableName == null) {
		    if (con.getConstraintType().equals("R")) {
			cons.add(con);
		    }
		}
	    }
	}
	return cons;
    }
}  // end of class
