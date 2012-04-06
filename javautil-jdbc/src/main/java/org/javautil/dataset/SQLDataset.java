package org.javautil.dataset;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.javautil.io.ClassPathResourceResolver;
import org.javautil.io.IOUtils;
import org.javautil.sql.QueryHelper;

/**
 * 
 * @author jjs@javautil.org
 * 
 */
public class SQLDataset {

	private static final Logger logger = Logger.getLogger(SQLDataset.class);

	/**
	 * Get a dataSet from a relational database.
	 * 
	 * usage example
	 * 
	 * if a .sql file is in the source folder under
	 * org.javautil.resources.myproject.sql called toad.sql and there is a SQL
	 * class in the package call with
	 * 
	 * <pre>
	 * &lt;code&gt;
	 * Connection conn = DatasourcesFactory.getConnection(&quot;myconn&quot;); 
	 * Map&lt;String,Object&gt;
	 *     parms = new Map&lt;String,Object&gt;(); parms.put(&quot;genus&quot;,&quot;bufo&quot;);
	 *     getDataFromResource(conn,SQL.class,&quot;toad.sql&quot;,parms);
	 * &lt;/code&gt;
	 * </pre>
	 * 
	 * todo demonstrate with a unit test
	 * 
	 * @param conn
	 * @param resourceClass
	 * @param resourceName
	 * @param binds
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	static public Dataset getDataFromResource(final Connection conn,
			final Class<?> resourceClass, final String resourceName,
			final Map<String, Object> binds) throws SQLException, IOException {
		if (conn == null) {
			throw new IllegalArgumentException("conn is null");
		}
		// todo for rest of arguments all are required

		Dataset ds = null;
		final ClassPathResourceResolver resolver = new ClassPathResourceResolver(
				resourceClass);
		try {
			resolver.afterPropertiesSet();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
		final String queryText = IOUtils.readStringFromStream(resolver
				.getResource(resourceName).getInputStream());
		QueryHelper queryHelper = null;
		try {
			queryHelper = new QueryHelper(resourceName, conn, queryText);
			final ResultSet rset = queryHelper.executeQuery(binds, false);
			ds = DisassociatedResultSetDataset.getDataset(rset);
		} finally {
			if (queryHelper != null) {
				queryHelper.destroy();
			}
		}
		return ds;
	}

	static public Dataset getDataFromResource(final Connection conn,
			final Class<?> resourceClass, final String resourceName)
			throws SQLException, IOException {
		return getDataFromResource(conn, resourceClass, resourceName,
				new HashMap<String, Object>());
	}

	static public Dataset getData(final Connection conn,
			final String queryName, final String sqlText) throws SQLException {
		return getData(conn, queryName, sqlText, new HashMap<String, Object>());
	}

	// todo figure a good unchecked exception to Call
	static public Dataset getData(final Connection conn,
			final String queryName, final String sqlText,
			final Map<String, Object> binds) {
		final Logger logger = Logger.getLogger(SQLDataset.class);
		if (conn == null) {
			throw new IllegalArgumentException("conn is null");
		}

		final long startTime = System.currentTimeMillis();
		final QueryHelper sqlHelper = new QueryHelper(queryName, conn, sqlText);
		MutableDataset ds;
		try {
			final ResultSet rset = sqlHelper.executeQuery(binds, false);
			ds = DisassociatedResultSetDataset.getDataset(rset);
			sqlHelper.destroy();
		} catch (final SQLException sqe) {
			// todo need to specify the query, figure which binds to dump, if
			// this
			// should be done at all
			throw new RuntimeException(sqe);
		}
		final long endTime = System.currentTimeMillis();
		if (logger.isDebugEnabled()) {
			logger.debug("elapsed time (millis) : " + (endTime - startTime));
		}
		return ds;

	}

	// /**
	// * Obtains a DataSet using the given procedure call.
	// *
	// * @return
	// * @throws DataSetException
	// */
	// public static MutableDataset getDatasetFromCall(Connection conn, String
	// query, Map<String, Object> parms)
	// throws DatasetException {
	// try {
	//
	// try {
	// logger.debug("Executing Query:\n" + query);
	// CallableStatement cstat = conn.prepareCall(query);
	// try {
	// cstat.registerOutParameter(1, OracleTypes.CURSOR);
	// cstat.execute();
	// ResultSet rset = (ResultSet) cstat.getObject(1);
	// try {
	// return DisassociatedResultSetDataset.getDataset(rset);
	// } finally {
	// rset.close();
	// }
	// } finally {
	// cstat.close();
	// }
	// } finally {
	// conn.close();
	// }
	// } catch (Exception ex) {
	// throw new DatasetException("while executing '" + query + "'" + ex);
	// }
	// }

}
