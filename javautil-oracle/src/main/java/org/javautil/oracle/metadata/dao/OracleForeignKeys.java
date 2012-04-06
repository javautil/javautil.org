package org.javautil.oracle.metadata.dao;
//package com.dbexperts.oracle.DatabaseMetaData;
//
//import java.sql.Connection;
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.List;
//
//import com.dbexperts.jdbc.DatabaseMetaData.ForeignKey;
//import com.dbexperts.jdbc.DatabaseMetaData.ForeignKeyInterface;
//import com.dbexperts.jdbc.DatabaseMetaData.ForeignKeys;
//import com.dbexperts.jdbc.DatabaseMetaData.JDBCForeignKeys;
//import com.dbexperts.oracle.dao.OracleConstraintsDAO;
//
//public class OracleForeignKeys extends JDBCForeignKeys implements ForeignKeys  {
//    // todo finish this off
////    select
////    ac.OWNER,
////    ac.CONSTRAINT_NAME,
////    ac.CONSTRAINT_TYPE,
////    ac.TABLE_NAME,
////    ac.SEARCH_CONDITION,
////    ac.R_OWNER,
////    ac.R_CONSTRAINT_NAME,
////    ac.DELETE_RULE,
////    ac.STATUS,
////    ac.DEFERRABLE,
////    ac.DEFERRED,
////    ac.VALIDATED,
////    ac.GENERATED,
////    ac.BAD,
////    ac.RELY,
////    ac.LAST_CHANGE,
////    ac.INDEX_OWNER,
////    ac.INDEX_NAME,
////    ac.INVALID,
////    ac.VIEW_RELATED,
////    acr.TABLE_NAME pk_table_name,
////    acr.SEARCH_CONDITION pk_search_conditioni,
////    acr.DELETE_RULE pk_delete_rule,
////    acr.STATUS pk_status,
////    acr.DEFERRABLE pk_deferrablle,
////    acr.DEFERRED pk_deferred,
////    acr.VALIDATED pk_validated,
////    acr.GENERATED pk_generated,
////    acr.BAD pk_bad,
////    acr.RELY pk_rely,
////    acr.LAST_CHANGE pk_last_change,
////    acr.INDEX_OWNER pk_index_owner,
////    acr.INDEX_NAME pk_index_name,
////    acr.INVALID pk_invalid,
////    acr.VIEW_RELATED pk_view_related
////   from all_constraints ac,
////        all_constraints acr
////   where  ac.r_constraint_name = acr.constraint_name and
////          ac.owner = acr.owner and
// //         ac.constraint_type = 'R';
//
////    Retrieves a description of the foreign key columns that reference the given table's primary key columns (the foreign keys exported by a table). They are ordered by FKTABLE_CAT, FKTABLE_SCHEM, FKTABLE_NAME, and KEY_SEQ.
////    public static final short 	attributeNoNulls 	0
////    public static final short 	attributeNullable 	1
////    public static final short 	attributeNullableUnknown 	2
////    public static final int 	bestRowNotPseudo 	1
////    public static final int 	bestRowPseudo 	2
////    public static final int 	bestRowSession 	2
////    public static final int 	bestRowTemporary 	0
////    public static final int 	bestRowTransaction 	1
////    public static final int 	bestRowUnknown 	0
////    public static final int 	columnNoNulls 	0
////    public static final int 	columnNullable 	1
////    public static final int 	columnNullableUnknown 	2
////    public static final int 	importedKeyCascade 	0
////    public static final int 	importedKeyInitiallyDeferred 	5
////    public static final int 	importedKeyInitiallyImmediate 	6
////    public static final int 	importedKeyNoAction 	3
////    public static final int 	importedKeyNotDeferrable 	7
////    public static final int 	importedKeyRestrict 	1
////    public static final int 	importedKeySetDefault 	4
////    public static final int 	importedKeySetNull 	2
////    public static final int 	procedureColumnIn 	1
////    public static final int 	procedureColumnInOut 	2
////    public static final int 	procedureColumnOut 	4
////    public static final int 	procedureColumnResult 	3
////    public static final int 	procedureColumnReturn 	5
////    public static final int 	procedureColumnUnknown 	0
////    public static final int 	procedureNoNulls 	0
////    public static final int 	procedureNoResult 	1
////    public static final int 	procedureNullable 	1
////    public static final int 	procedureNullableUnknown 	2
////    public static final int 	procedureResultUnknown 	0
////    public static final int 	procedureReturnsResult 	2
////    public static final int 	sqlStateSQL99 	2
////    public static final int 	sqlStateXOpen 	1
////    public static final short 	tableIndexClustered 	1
////    public static final short 	tableIndexHashed 	2
////    public static final short 	tableIndexOther 	3
////    public static final short 	tableIndexStatistic 	0
////    public static final int 	typeNoNulls 	0
////    public static final int 	typeNullable 	1
////    public static final int 	typeNullableUnknown 	2
////    public static final int 	typePredBasic 	2
////    public static final int 	typePredChar 	1
////    public static final int 	typePredNone 	0
////    public static final int 	typeSearchable 	3
////    public static final int 	versionColumnNotPseudo 	1
////    public static final int 	versionColumnPseudo 	2
// //   public static final int 	versionColumnUnknown 	0
////    Each foreign key column description has the following columns:
////
////       1. PKTABLE_CAT String => primary key table catalog (may be null)
////       2. PKTABLE_SCHEM String => primary key table schema (may be null)
////       3. PKTABLE_NAME String => primary key table name
////       4. PKCOLUMN_NAME String => primary key column name
////       5. FKTABLE_CAT String => foreign key table catalog (may be null) being exported (may be null)
////       6. FKTABLE_SCHEM String => foreign key table schema (may be null) being exported (may be null)
////       7. FKTABLE_NAME String => foreign key table name being exported
////       8. FKCOLUMN_NAME String => foreign key column name being exported
////       9. KEY_SEQ short => sequence number within foreign key
////      10. UPDATE_RULE short => What happens to foreign key when primary is updated:
////              * importedNoAction - do not allow update of primary key if it has been imported
////              * importedKeyCascade - change imported key to agree with primary key update
////              * importedKeySetNull - change imported key to NULL if its primary key has been updated
////              * importedKeySetDefault - change imported key to default values if its primary key has been updated
////              * importedKeyRestrict - same as importedKeyNoAction (for ODBC 2.x compatibility) 
////      11. DELETE_RULE short => What happens to the foreign key when primary is deleted.
////              * importedKeyNoAction - do not allow delete of primary key if it has been imported
////              * importedKeyCascade - delete rows that import a deleted key
////              * importedKeySetNull - change imported key to NULL if its primary key has been deleted
////              * importedKeyRestrict - same as importedKeyNoAction (for ODBC 2.x compatibility)
////              * importedKeySetDefault - change imported key to default if its primary key has been deleted 
////      12. FK_NAME String => foreign key name (may be null)
////      13. PK_NAME String => primary key name (may be null)
////      14. DEFERRABILITY short => can the evaluation of foreign key constraints be deferred until commit
////              * importedKeyInitiallyDeferred - see SQL92 for definition
////              * importedKeyInitiallyImmediate - see SQL92 for definition
////              * importedKeyNotDeferrable - see SQL92 for definition 
//
//    public OracleForeignKeys(Connection conn, String schemaName,
//	    String tableName, List<OracleConstraint> constraints) {
//	if (conn == null ) {
//	    throw new IllegalArgumentException("conn is null");
//	}
//	if (schemaName == null) {
//	    throw new IllegalArgumentException("schemaName is null");
//	}
//	if (tableName == null) {
//	    throw new IllegalArgumentException("tableName is null");
//	}
//	if (constraints == null) {
//	    throw new IllegalArgumentException("constraints is null");
//	}
//	List<OracleConstraint> cons = OracleConstraintsDAO.getImportedKeyConstraints(constraints,schemaName, tableName);
////	for (OracleConstraint con : cons) {
//// 	ForeignKeyInterface fk = new ForeignKey();
////	   fk.setColumns(final ArrayList<ForeignKeyColumn> columns);
////	   fk.setDeleteRule(con.getDeleteRule());
////	   fk.setFkName(cons.);
////	   fk.setFktableCat(final String fktableCat);
////	   fk.setFktableName(final String fktableName);
////	   fk.setFktableSchem(final String fktableSchem);
////	   fk.setPkName(con.getRConstraintName());
////	//   fk.setPktableCat();
////	   fk.setPktableName(con.get);
////	   fk.setPktableSchem(final String pktableSchem);
////	   fk.setUpdateRule(final short updateRule);
////	}
//	}
//
////    public OracleForeignKeys(Connection conn, String catalog,
////	    String schemaName, String tableName, boolean b) {
////	// TODO Auto-generated constructor stub
////    }
//
////    @Override
////    public void addForeignKey(ForeignKey key) {
////	// TODO Auto-generated method stub
////
////    }
////
////    @Override
////    public String getSchemaPattern() {
////	// TODO Auto-generated method stub
////	return null;
////    }
////
////    @Override
////    public String getTable() {
////	// TODO Auto-generated method stub
////	return null;
////    }
////
////    @Override
////    public Collection<ForeignKey> getValues() {
////	// TODO Auto-generated method stub
////	return null;
////    }
////
////    @Override
////    public Iterator<ForeignKey> iterator() {
////	// TODO Auto-generated method stub
////	return null;
////    }
////
////    @Override
////    public void setSchemaPattern(String schemaPattern) {
////	// TODO Auto-generated method stub
////
////    }
////
////    @Override
////    public void setTable(String table) {
////	// TODO Auto-generated method stub
////
////    }
//
//}
