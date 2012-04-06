package org.javautil.jdbc.datasources;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javautil.logging.Events;

/**
 * 
 * 
 */
public class SystemOrEnvironmentDataSourceFactory {

	private static Log logger = LogFactory
			.getLog(SystemOrEnvironmentDataSourceFactory.class);

	private static String dataSourceName = null;

	private static final String parameterNameDatasourceName = "DATASOURCE";

	private static final String parameterNameLegacyDatasourceName = "datasource";

	static {
		dataSourceName = System.getProperty(parameterNameDatasourceName);
		if (dataSourceName == null) {
			dataSourceName = System.getenv(parameterNameDatasourceName);
		}
		if (dataSourceName == null) {
			dataSourceName = System
					.getProperty(parameterNameLegacyDatasourceName);
		}
		if (dataSourceName == null) {
			dataSourceName = System.getenv(parameterNameLegacyDatasourceName);
		}
		if (dataSourceName == null) {
			final String message = parameterNameDatasourceName
					+ " not defined as a system property or environment variable!";
			logger.error(message);
			throw new IllegalStateException(message);
		}
	}

	public DataSource getDataSource() {
		if (logger.isDebugEnabled() && Events.isRegistered("datasource")) {
			logger.debug("getting datasource '" + dataSourceName + "'");
		}
		return SimpleDatasourcesFactory.getDataSource(dataSourceName);
	}

}