package com.dbexperts.oracle.cache;



import java.sql.SQLException;

import oracle.jdbc.OracleConnection;



public interface OracleCache {
    public CacheStatus getCacheStatus();

    public void load(OracleConnection conn) throws SQLException;

    public void refresh(OracleConnection conn) throws SQLException;
}
