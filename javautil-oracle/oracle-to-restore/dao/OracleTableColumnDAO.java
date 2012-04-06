package com.dbexperts.oracle.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dbexperts.oracle.dto.OracleTableColumn;

public class OracleTableColumnDAO
{
	int insertBatchSize = 100;
	private Connection conn;
	private static final String newline = System.getProperty("line.separator");
	private ResultSet rset = null;
	private PreparedStatement selectStmt = null;
	static final String columnList = "" +
        "    owner,\n" +
        "    table_name,\n" +
        "    column_name,\n" +
        "    data_type,\n" +
        "    data_type_mod,\n" +
        "    data_type_owner,\n" +
        "    data_length,\n" +
        "    data_default,\n" + 
        "    data_precision,\n" +
        "    data_scale,\n" +
        "    nullable,\n" +
        "    column_id,\n" +
        "    default_length,\n" +
        "    num_distinct,\n" +
        "    low_value,\n" +
        "    high_value,\n" +
        "    density,\n" +
        "    num_nulls,\n" +
        "    num_buckets,\n" +
        "    last_analyzed,\n" +
        "    sample_size,\n" +
        "    character_set_name,\n" +
        "    char_col_decl_length,\n" +
        "    global_stats,\n" +
        "    user_stats,\n" +
        "    avg_col_len,\n" +
        "    char_length,\n" +
        "    char_used,\n" +
        "    v80_fmt_image,\n" +
        "    data_upgraded,\n" +
        "    histogram\n";

	public static String getSelectText(boolean useDba) {
	    StringBuilder b = new StringBuilder();
	    b.append("select ");
	    b.append(newline);
	    b.append(columnList);
	    b.append("from ");
	    String viewName = useDba ? "dba" : "all" + "_tab_columns";
	    b.append(viewName);
	    b.append(" where owner = :owner and \n");
	    b.append("      table_name = :table_name ");
	    b.append(" order by column_id");
	    return b.toString();
	}
	
//	public static List<OracleTableColumn> getForTableNoUpper(Connection conn,String owner, String tableName) {
//	    return getForTable(conn,owner, tableName, false);
//	}
	
	
	public static List<OracleTableColumn> getForTable(Connection conn,String owner, String tableName, boolean useDba, boolean upperCaseArgs) throws SQLException {
	    if (conn == null) {
		throw new IllegalArgumentException("conn is null");
	    }
	    if (owner == null) {
		throw new IllegalArgumentException("owner is null");
		
	    }
	    if (tableName == null) {
		throw new IllegalArgumentException("tableName is null");
	    }
	    ArrayList<OracleTableColumn> columns = new ArrayList<OracleTableColumn>();
	    String myOwner = upperCaseArgs ? owner.toUpperCase() : owner;
	    String myTableName = upperCaseArgs ? tableName.toUpperCase() : tableName;
	    String selectText = getSelectText(useDba);
	    PreparedStatement ps = conn.prepareStatement(selectText);
	    ps.setString(1, myOwner);
	    ps.setString(2, myTableName);
	    ResultSet rset = null;
	    try {
		rset = ps.executeQuery();
	    } catch (SQLException sqe) {
		throw new SQLException("while processing '" + selectText + "' " + sqe.getMessage());
	    }
	    while (rset.next()) {
		OracleTableColumn otc = new OracleTableColumn();
		getRow(rset,otc);
		columns.add(otc);
	    }
	    ps.close();
	    return columns;
	}

    public static void getRow(ResultSet rset, OracleTableColumn row)
    throws java.sql.SQLException
    {
        String columnName = null;

        try {
            row.setSchema(rset.getString(columnName = "OWNER"));
            row.setTableName(rset.getString(columnName = "TABLE_NAME"));
            row.setColumnName(rset.getString(columnName = "COLUMN_NAME"));
            row.setDataType(rset.getString(columnName = "DATA_TYPE"));
            row.setDataTypeMod(rset.getString(columnName = "DATA_TYPE_MOD"));
            row.setDataTypeOwner(rset.getString(columnName = "DATA_TYPE_OWNER"));

            row.setCharLength(rset.getInt(columnName = "DATA_LENGTH"));

            row.setPrecision(rset.getInt(columnName = "DATA_PRECISION"));

            row.setScale(rset.getInt(columnName = "DATA_SCALE"));
            String nullable = (rset.getString(columnName = "NULLABLE"));
            row.setNullable(nullable);

            row.setColumnNumber(rset.getInt(columnName = "COLUMN_ID"));

            row.setDefaultLength(rset.getInt(columnName = "DEFAULT_LENGTH"));

            row.setNumDistinct(rset.getInt(columnName = "NUM_DISTINCT"));
//            row.setLowValue(rset.getObject(columnName = "LOW_VALUE"));
//            row.setHighValue(rset.getObject(columnName = "HIGH_VALUE"));

            row.setDensity(rset.getBigDecimal(columnName = "DENSITY"));

            row.setNumNulls(rset.getLong(columnName = "NUM_NULLS"));

            row.setNumBuckets(rset.getInt(columnName = "NUM_BUCKETS"));
            row.setLastAnalyzed(rset.getTimestamp(columnName = "LAST_ANALYZED"));

            row.setSampleSize(rset.getLong(columnName = "SAMPLE_SIZE"));
            row.setCharacterSetName(rset.getString(columnName = "CHARACTER_SET_NAME"));

            row.setCharColDeclLength(rset.getInt(columnName = "CHAR_COL_DECL_LENGTH"));
            row.setGlobalStats(rset.getString(columnName = "GLOBAL_STATS"));
            row.setUserStats(rset.getString(columnName = "USER_STATS"));

            row.setAvgColLen(rset.getInt(columnName = "AVG_COL_LEN"));

            row.setCharLength(rset.getInt(columnName = "CHAR_LENGTH"));
            row.setCharUsed(rset.getString(columnName = "CHAR_USED"));
            row.setV80FmtImage(rset.getString(columnName = "V80_FMT_IMAGE"));
            row.setDataUpgraded(rset.getString(columnName = "DATA_UPGRADED"));
            row.setHistogram(rset.getString(columnName = "HISTOGRAM"));
        }
            catch (java.sql.SQLException s) {
                throw new java.sql.SQLException("error processing column" + columnName + "\n" + s.getMessage());
            }
        } // end of getRow 
//	String getSelectText() {
//		return selectText;
//	}

   
}  // end of class
