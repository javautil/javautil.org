package org.javautil.exceptionprocessing;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.javautil.datasources.DatasourceType;
import org.javautil.datasources.Datasources;
import org.javautil.jdbc.datasources.SimpleDatasourcesFactory;

//import oracle.jdbc.pool.OracleDataSource;

public class CreateDataSourceForJNDI {

	private final Logger logger = Logger.getLogger(getClass());
	private static CreateDataSourceForJNDI dsJndi;
	private Context ctx;

	// private DataSource datasource;

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		// TODO Auto-generated method stub
		if (dsJndi == null) {
			dsJndi = new CreateDataSourceForJNDI();
		}
		dsJndi.createDataSource();

	}

	CreateDataSourceForJNDI() {
		try {
			logger.info("Initializing JNDI Context...");
			// this location is for temp,need to define a directory service
			// and register it to JVM parameters to make sure all the projects
			// points to the same location.
			final String location = System.getProperty("user.dir");
			final Properties props = new Properties();
			props.put(Context.INITIAL_CONTEXT_FACTORY,
					"com.sun.jndi.fscontext.RefFSContextFactory");
			props.put(Context.PROVIDER_URL, "file://" + location);
			ctx = new InitialContext(props);
			logger.info("JNDI Context Initialized....");
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	public DataSource getDatasource(final String datasourceName) {
		return null; // todo write
	}

	public void registerDatasource(final String datasourceName,
			final DataSource datasource) {

	}

	public void registerDatasources(final Map<String, DataSource> datasources) {

	}

	public void createDataSource() {

		try {
			logger.info("Started Creating DataSource....");
			final Datasources ds = SimpleDatasourcesFactory.getDataSources();
			final List<DatasourceType> list = ds.getDatasource();
			for (final DatasourceType dst : list) {
				final DataSource datasource = SimpleDatasourcesFactory
						.getDataSource(dst.getName());
				SimpleDatasourcesFactory.getDataSource(dst.getName());
				ctx.bind(dst.getName(), datasource);
			}
			// OracleDataSource dsource = new OracleDataSource();
			// ctx.bind("vasu", dsource);

			// Properties props = new Properties();
			// FileInputStream fs = new FileInputStream(System
			// .getProperty("user.home")
			// + "/context.xml");
			// props.loadFromXML(fs);
			// ComboPooledDataSource dSource = new ComboPooledDataSource();
			// dSource.setDriverClass(props.getProperty("driverClass"));
			// dSource.setUser(props.getProperty("user"));
			// dSource.setPassword(props.getProperty("password"));
			// dSource.setJdbcUrl(props.getProperty("url"));
			// ctx.bind(props.getProperty("name"), dSource);
			// logger.info("DataSource created and bind to JNDI Context....");
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}

	}

}
