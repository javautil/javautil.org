package org.javautil.jdbc;

import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * 
 * @author bcm
 * 
 */
public interface DataSourceFactory {

	public DataSource getDataSource() throws SQLException;

}
