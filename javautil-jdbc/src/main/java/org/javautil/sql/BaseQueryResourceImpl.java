package org.javautil.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.javautil.dataset.ResultSetDataset;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
*/
public  abstract class BaseQueryResourceImpl extends AbstractQueryAndPropertiesResource
		implements InitializingBean, DisposableBean {

	

	public QueryHelper getQueryHelper() {
		return queryHelper;
	}



	public void setQueryHelper(QueryHelper queryHelper) {
		this.queryHelper = queryHelper;
	}

	private Connection connection;

	private Map<String, Object> parameters;


	private QueryHelper queryHelper;

	@Override
	public void afterPropertiesSet() {
		if (connection == null) {
			throw new IllegalStateException("connection is null");
		}
	}


	public ResultSet executeQuery() throws SQLException {
		if (queryHelper != null) {
			queryHelper.destroy();
		}
		queryHelper = new QueryHelper(getConnection(), getQueryText());
		ResultSet rset = queryHelper.executeQuery(getParameters(), false);
		return rset;
	}
	

	protected ResultSetDataset getResultSetDatasetInternal() {
		ResultSet rset = null;
		try {
			if (queryHelper != null) {
				queryHelper.destroy();
			}
			queryHelper = new QueryHelper(getConnection(), getQueryText());
			rset = queryHelper.executeQuery(getParameters(), false);
			return new ResultSetDataset(rset);
		} catch (final SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void destroy() {
		try {
			if (queryHelper != null) {
				queryHelper.destroy();
				queryHelper = null;
			}
		} catch (final SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(final Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(final Connection connection) {
		this.connection = connection;
	}

}
