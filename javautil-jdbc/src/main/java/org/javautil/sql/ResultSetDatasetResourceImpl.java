package org.javautil.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.javautil.dataset.ResultSetDataset;

/**
 * Basic implementation of QueryResource that uses a java.sql.Connection to
 * return a ResultSetDataSet.
 * 
 * @author jjs
 */
public class ResultSetDatasetResourceImpl extends BaseQueryResourceImpl
 {

	public ResultSetDataset getDataset() {
		ResultSet rset = null;
		try {
			rset = executeQuery();
			return new ResultSetDataset(rset);
		} catch (final SQLException e) {
			throw new RuntimeException(e);
		}
	}

	
	

}
