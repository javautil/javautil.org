package org.javautil.datasources;

import java.io.FileInputStream;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;

//import oracle.jdbc.pool.OracleDataSource;

public class CreateDatasourceJNDI {

	private final Logger logger = Logger.getLogger(getClass());
	private static CreateDatasourceJNDI dsJndi;
	private Context ctx;

	// private DataSource datasource;

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		// TODO Auto-generated method stub
		if (dsJndi == null) {
			dsJndi = new CreateDatasourceJNDI();
		}
		dsJndi.createDatasource();

	}

	CreateDatasourceJNDI() {
		try {
			logger.info("Initializing JNDI Context...");
			// TODO this location is for temporary,need to define a directory
			// service
			// and register it to JVM parameters to make sure all the projects
			// points to the same location.
			final String location = System.getProperty("user.dir");
			logger.info("Location .." + location);
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

	public void createDatasource() {

		try {
			logger.info("Binding Datasource to JNDI....");
			// Datasources ds = DatasourcesFactory.getDataSources();
			//
			// List<DatasourceType> list = ds.getDatasource();
			//
			// for (DatasourceType dst : list) {
			// DataSource datasource = DatasourcesFactory.getDataSource(dst
			// .getName());
			// DatasourcesFactory.getDataSource(dst.getName());
			// ctx.bind(dst.getName(), datasource);
			// }

			final Properties props = new Properties();
			final FileInputStream fs = new FileInputStream(
					System.getProperty("user.home") + "/context.xml");
			props.loadFromXML(fs);
			final String[] datasource = props.getProperty("datasources").split(
					",");
			for (final String element : datasource) {
				final ComboPooledDataSource dSource = new ComboPooledDataSource();
				dSource.setDriverClass(props.getProperty(element + ".driver"));
				dSource.setUser(props.getProperty(element + ".user"));
				dSource.setPassword(props.getProperty(element + ".password"));
				dSource.setJdbcUrl(props.getProperty(element + ".url"));
				ctx.rebind(element, dSource);
			}
			logger.info("Total number of Datasources bind to JNDI are .. "
					+ datasource.length);

		} catch (final Exception e) {
			throw new RuntimeException(e);
		}

	}

}
