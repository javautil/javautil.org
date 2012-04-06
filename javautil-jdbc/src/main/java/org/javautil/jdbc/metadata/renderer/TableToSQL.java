package org.javautil.jdbc.metadata.renderer;
//package org.javautil.jdbc.metadata;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//import org.javautil.lang.StringUtils;
//
//
//
////// todo this is redundant with respect to the code in oracle table
////public class TableToSQL {
////    private static final String newline = System.getProperty("line.separator");
////    private static final String statementEnd = ";" + newline + newline;
////    private IndexToSQL indexFormatter = new IndexToSQL();
////    private boolean reservedWordsUpperCase = true;
////    private boolean attributesLowerCase = true;
////    private static final String createTable = "CREATE TABLE ";
////    private static SQLGenerator gen = new SQLGenerator();
////    
////    public String toSQLString(Table table) {
////	StringBuilder b = new StringBuilder();
////	b.append(getCreateStatement(table));
////	b.append(statementEnd);
////
////	ForeignKeys importedKeys = table.getImportedKeys();
////
////	if (importedKeys != null) {
////
////	    final List<String> imported = getForeignKeysDDL(table, table
////		    .getImportedKeys().getValues());
////	    for (final String imp : imported) {
////		b.append(imp);
////		b.append(statementEnd);
////	    }
////	}
////	// final Map<String, Index> indexesByIndexName =
////	// table.getIndexesByName();
////	for (Index index : table.getIndexesByName().values()) {
////	    String sql = indexFormatter.toSQLString(index);
////	    b.append(sql);
////	    b.append(";");
////	    b.append(newline);
////	}
////	// todo need column comments
////	return b.toString();
////    }
////
////    public String getCreateTableText() {
////	return createTable;
////    }
////
////    public String getAttribute(String text) {
////	String retval = null;
////	if (text != null) {
////	    retval = attributesLowerCase ? text.toLowerCase() : text
////		    .toUpperCase();
////	}
////	return retval;
////    }
////
////    public String getReserved(String text) {
////	String retval = null;
////	if (text != null) {
////	    retval = reservedWordsUpperCase ? text.toUpperCase() : text
////		    .toLowerCase();
////	}
////	return retval;
////    }
//
//    // todo restore after getting jdbc mapper 
////    public String getCreateStatement(Table table) {
////
////	final StringBuilder b = new StringBuilder();
////	String ct = getReserved(getCreateTableText());
////
////	// b.append("create table ");
////	b.append(ct);
////	b.append(getAttribute(table.getTableName()));
////	b.append(" (");
////	b.append(newline);
////	boolean first = true;
////
////	for (final ColumnAttributes column : table.getColumns()) {
////	    if (!first) {
////		b.append(",");
////		b.append(newline);
////	    }
////	    b.append("   ");
////	    String attributeCase = getAttribute(column.getColumnName());
////	    String attributePadded = StringUtils.rightPad(attributeCase, 32);
////	    b.append(attributePadded);
////	    b.append(" ");
////	    b.append(getReserved(column.getSqlType()));
////	    if (!column.isNotNullable()) {
////		b.append(getReserved(" not null"));
////	    }
////	    first = false;
////	}
////	b.append(newline);
////	b.append(")");
////	final String result = b.toString();
////	// logger.info(result);
////	return result;
////    }
//
////    public static ArrayList<String> getForeignKeysDDL(Table table,
////	    Collection<ForeignKey> keys) {
////
////	final ArrayList<String> sqlTexts = new ArrayList<String>();
////
////	if (table.getImportedKeys() != null) {
////	    for (final ForeignKeyInterface fk : keys) {
////		final StringBuilder fkb = new StringBuilder();
////		fkb.append(gen.getReserved(" alter table "));
////		fkb.append( "alter table ");
////		fkb.append(gen.getEscapedPaddedAttribute(table.getTableName()));
////		fkb.append(gen.getReserved(" add constraint "));
////		fkb.append(fk.getFkName());
////		fkb.append(gen.getReserved(" foreign key ("));
////	//	fkb.append(newline);
////
////		boolean first = true;
////		for (final ForeignKeyColumn fkc : fk.getColumns()) {
////		    if (!first) {
////			fkb.append(",");
////			//fkb.append(newline);
////		    }
////		    fkb.append("   ");
////		    fkb.append(gen.getAttribute(fkc.getFkcolumnName()));
////		    first = false;
////		}
////	//	fkb.append(newline);
////		fkb.append(") references  ");
////		// todo should write an escaper that wraps in quotes if it is
////		// not all chars $ or _
////		fkb.append(gen.getAttribute(fk.getPktableName()));
////		fkb.append("(");
////		first = true;
////		for (final ForeignKeyColumn fkc : fk.getColumns()) {
////		    if (!first) {
////			fkb.append(", ");
////	//		fkb.append(newline);
////		    }
////		  //  fkb.append(" ");
////		    fkb.append(gen.getAttribute(fkc.getPkcolumnName()));
////		    first = false;
////		}
////		fkb.append(")");
////		final String result = fkb.toString();
////
////		sqlTexts.add(result);
////	    }
////	}
////	return sqlTexts;
////    }
//
//    /*
//     * (non-Javadoc)
//     * 
//     * @see com.dbexperts.jdbc.DatabaseMetaData.Table#getCreatePrimaryKey()
//     */
//    public String getCreatePrimaryKey(Table table) {
//	final StringBuilder b = new StringBuilder();
//	String result = null;
//	final PrimaryKey pk = table.getPrimaryKey();
//	if (pk != null) {
//	    b.append("alter table ");
//	    b.append(table.getTableName());
//	    b.append(" add constraint ");
//	    b.append(pk.getPrimaryKeyName());
//	    b.append(" primary key (\n");
//
//	    boolean first = true;
//	    for (final String columnName : pk.getColumnNames()) {
//		if (!first) {
//		    b.append(",\n");
//		}
//		b.append("   ");
//		b.append(columnName);
//		first = false;
//	    }
//	    b.append("\n)");
//	    result = b.toString();
//	}
//	return result;
//    }
// }
