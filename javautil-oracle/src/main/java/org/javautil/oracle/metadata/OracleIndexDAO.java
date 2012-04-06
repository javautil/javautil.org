//package com.dbexperts.oracle.DatabaseMetaData;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.dbexperts.oracle.DatabaseMetaData.OracleIndex;
//
//public class OracleIndexDAO
//{
////	int insertBatchSize = 100;
////	private Connection conn;
////
////	private ResultSet rset = null;
////	private PreparedStatement selectStmt = null;
//	static final String columnList = 
//        "    owner,\n" +
//        "    index_name,\n" +
//        "    index_type,\n" +
//        "    table_owner,\n" +
//        "    table_name,\n" +
//        "    table_type,\n" +
//        "    uniqueness,\n" +
//        "    compression,\n" +
//        "    prefix_length,\n" +
//        "    tablespace_name,\n" +
//        "    ini_trans,\n" +
//        "    max_trans,\n" +
//        "    initial_extent,\n" +
//        "    next_extent,\n" +
//        "    min_extents,\n" +
//        "    max_extents,\n" +
//        "    pct_increase,\n" +
//        "    pct_threshold,\n" +
//        "    include_column,\n" +
//        "    freelists,\n" +
//        "    freelist_groups,\n" +
//        "    pct_free,\n" +
//        "    logging,\n" +
//        "    blevel,\n" +
//        "    leaf_blocks,\n" +
//        "    distinct_keys,\n" +
//        "    avg_leaf_blocks_per_key,\n" +
//        "    avg_data_blocks_per_key,\n" +
//        "    clustering_factor,\n" +
//        "    status,\n" +
//        "    num_rows,\n" +
//        "    sample_size,\n" +
//        "    last_analyzed,\n" +
//        "    degree,\n" +
//        "    instances,\n" +
//        "    partitioned,\n" +
//        "    temporary,\n" +
//        "    generated,\n" +
//        "    secondary,\n" +
//        "    buffer_pool,\n" +
//        "    user_stats,\n" +
//        "    duration,\n" +
//        "    pct_direct_access,\n" +
//        "    ityp_owner,\n" +
//        "    ityp_name,\n" +
//        "    parameters,\n" +
//        "    global_stats,\n" +
//        "    domidx_status,\n" +
//        "    domidx_opstatus,\n" +
//        "    funcidx_status,\n" +
//        "    join_index,\n" +
//        "    iot_redundant_pkey_elim,\n" +
//        "    dropped\n";
//
//	public static List<OracleIndex> getIndexesForTable(Connection conn, OracleTable table) throws SQLException {
//	    ArrayList<OracleIndex> indexes = new ArrayList<OracleIndex>();
//	    if (conn == null ) {
//		throw new IllegalArgumentException("conn is null");
//	    }
//	    if (table == null) {
//		throw new IllegalArgumentException("table is null");
//	    }
//	    String owner = table.getSchemaName();
//	    String tableName = table.getTableName();
//	    String sqlText = "select " + columnList + " from all_indexes " + 
//	                     " where owner  = :owner  and " +
//	                     " table_name = :tableName";
//	    PreparedStatement ps = conn.prepareStatement(sqlText);
//	    ps.setString(1, owner);
//	    ps.setString(2, tableName);
//	    ResultSet rset = ps.executeQuery();
//	    while (rset.next()) {
//		OracleIndex index = new OracleIndex();
//		getRow(rset,index);
//		indexes.add(index);
//	    }
//	    ps.close();
//	    return indexes;
//	    
//	}
//
//
//	
//    
//    public static void getRow(ResultSet rset, OracleIndex row)
//    throws java.sql.SQLException
//    {
//        String columnName = null;
//
//        try {
//            row.setOwner(rset.getString(columnName = "OWNER"));
//            row.setIndexName(rset.getString(columnName = "INDEX_NAME"));
//            row.setIndexType(rset.getString(columnName = "INDEX_TYPE"));
//            row.setTableOwner(rset.getString(columnName = "TABLE_OWNER"));
//            row.setTableName(rset.getString(columnName = "TABLE_NAME"));
//            row.setTableType(rset.getString(columnName = "TABLE_TYPE"));
//            row.setUniqueness(rset.getString(columnName = "UNIQUENESS"));
//            row.setCompression(rset.getString(columnName = "COMPRESSION"));
//
//            row.setPrefixLength(rset.getInt(columnName = "PREFIX_LENGTH"));
//            row.setTablespaceName(rset.getString(columnName = "TABLESPACE_NAME"));
//
//            row.setIniTrans(rset.getInt(columnName = "INI_TRANS"));
//
//            row.setMaxTrans(rset.getInt(columnName = "MAX_TRANS"));
//
//            row.setInitialExtent(rset.getInt(columnName = "INITIAL_EXTENT"));
//
//            row.setNextExtent(rset.getInt(columnName = "NEXT_EXTENT"));
//
//            row.setMinExtents(rset.getInt(columnName = "MIN_EXTENTS"));
//
//            row.setMaxExtents(rset.getInt(columnName = "MAX_EXTENTS"));
//
//            row.setPctIncrease(rset.getInt(columnName = "PCT_INCREASE"));
//
//            row.setPctThreshold(rset.getInt(columnName = "PCT_THRESHOLD"));
//
//            row.setIncludeColumn(rset.getInt(columnName = "INCLUDE_COLUMN"));
//
//            row.setFreelists(rset.getInt(columnName = "FREELISTS"));
//
//            row.setFreelistGroups(rset.getInt(columnName = "FREELIST_GROUPS"));
//
//            row.setPctFree(rset.getInt(columnName = "PCT_FREE"));
//            row.setLogging(rset.getString(columnName = "LOGGING"));
//
//            row.setBlevel(rset.getInt(columnName = "BLEVEL"));
//
//            row.setLeafBlocks(rset.getInt(columnName = "LEAF_BLOCKS"));
//
//            row.setDistinctKeys(rset.getInt(columnName = "DISTINCT_KEYS"));
//
//            row.setAvgLeafBlocksPerKey(rset.getInt(columnName = "AVG_LEAF_BLOCKS_PER_KEY"));
//
//            row.setAvgDataBlocksPerKey(rset.getInt(columnName = "AVG_DATA_BLOCKS_PER_KEY"));
//
//            row.setClusteringFactor(rset.getInt(columnName = "CLUSTERING_FACTOR"));
//            row.setStatus(rset.getString(columnName = "STATUS"));
//
//            row.setNumRows(rset.getInt(columnName = "NUM_ROWS"));
//
//            row.setSampleSize(rset.getInt(columnName = "SAMPLE_SIZE"));
//            row.setLastAnalyzed(rset.getTimestamp(columnName = "LAST_ANALYZED"));
//            row.setDegree(rset.getString(columnName = "DEGREE"));
//            row.setInstances(rset.getString(columnName = "INSTANCES"));
//            row.setPartitioned(rset.getString(columnName = "PARTITIONED"));
//            row.setTemporary(rset.getString(columnName = "TEMPORARY"));
//            row.setGenerated(rset.getString(columnName = "GENERATED"));
//            row.setSecondary(rset.getString(columnName = "SECONDARY"));
//            row.setBufferPool(rset.getString(columnName = "BUFFER_POOL"));
//            row.setUserStats(rset.getString(columnName = "USER_STATS"));
//            row.setDuration(rset.getString(columnName = "DURATION"));
//
//            row.setPctDirectAccess(rset.getInt(columnName = "PCT_DIRECT_ACCESS"));
//            row.setItypOwner(rset.getString(columnName = "ITYP_OWNER"));
//            row.setItypName(rset.getString(columnName = "ITYP_NAME"));
//            row.setParameters(rset.getString(columnName = "PARAMETERS"));
//            row.setGlobalStats(rset.getString(columnName = "GLOBAL_STATS"));
//            row.setDomidxStatus(rset.getString(columnName = "DOMIDX_STATUS"));
//            row.setDomidxOpstatus(rset.getString(columnName = "DOMIDX_OPSTATUS"));
//            row.setFuncidxStatus(rset.getString(columnName = "FUNCIDX_STATUS"));
//            row.setJoinIndex(rset.getString(columnName = "JOIN_INDEX"));
//            row.setIotRedundantPkeyElim(rset.getString(columnName = "IOT_REDUNDANT_PKEY_ELIM"));
//            row.setDropped(rset.getString(columnName = "DROPPED"));
//        }
//            catch (java.sql.SQLException s) {
//                throw new java.sql.SQLException("error processing column" + columnName + "\n" + s.getMessage());
//            }
//        } 
//  
//}  // end of class
