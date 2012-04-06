package org.javautil.jdbc.datasources;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.sql.DataSource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.javautil.datasources.DatasourceType;
import org.javautil.datasources.Datasources;
import org.javautil.datasources.Property;
import org.javautil.datasources.SystemProperty;

/**
 * 
 * 
 */
public class SimpleDatasourcesFactory {

	public static final String DATASOURCES_FILE = "DATASOURCES_FILE";

	private static SimpleDatasourcesFactory instance = new SimpleDatasourcesFactory();

	private final Logger logger = Logger.getLogger(getClass());
	private final Map<String, AbstractDataSource> dataSourceMap = new HashMap<String, AbstractDataSource>();

	private Map<String, DatasourceType> dataSourceTypeMap = null;

	public static DataSource getDataSource(final String dataSourceName) {
		final DataSource ds = instance.getDS(dataSourceName);
		return ds;
	}

	public static Datasources getDataSources() {
		return instance.getDatasources();
	}

	public static AbstractDataSource getDataSource(final String dataSourceName,
			final boolean testConnection) {
		final AbstractDataSource ds = instance.getDS(dataSourceName);
		if (testConnection) {
			ds.testConnection();
		}
		return ds;
	}

	public Collection<String> getDatasourceNames() {
		return dataSourceMap.keySet();
	}

	private SimpleDatasourcesFactory() {
		// no public instantiation
	}

	private String getDataSourcesFileMessage(final String resolve,
			final String path) {
		final File f = new File(path);
		final String message = "Using Datasources defined by " + resolve
				+ " is: '" + path + "' at '" + f.getAbsolutePath();
		return message;
	}

	/**
	 * Attempt to locate Datasources.xml file ' todo create a message which
	 * fully describes the search path todo create a path resolver
	 * 
	 * @returns
	 */
	public File getDatasourcesFile() {
		String location = System.getProperty(DATASOURCES_FILE);

		if (location != null) {
			logger.debug(getDataSourcesFileMessage("system property "
					+ DATASOURCES_FILE, location));
		} else {
			location = System.getenv(DATASOURCES_FILE);

			if (location != null) {
				logger.debug(getDataSourcesFileMessage("system environment "
						+ DATASOURCES_FILE, location));
			} else {
				location = System.getProperty("user.home") + "/datasources.xml";

				logger.warn(DATASOURCES_FILE
						+ " not found in environment or system properties.");
				logger.warn(getDataSourcesFileMessage(
						"Defaulting to datasources location ", location));
			}
		}

		final File DatasourcesFile = new File(location);
		if (!DatasourcesFile.exists()) {
			throw new IllegalStateException("File does not exist! " + location);
		}

		return DatasourcesFile;
	}

	private AbstractDataSource getDS(final String dataSourceName) {
		AbstractDataSource dataSource = dataSourceMap.get(dataSourceName);
		if (dataSource == null) {
			logger.debug("datasource for '" + dataSourceName
					+ "' is null; instantiating");
			dataSource = instantiate(dataSourceName);
			dataSourceMap.put(dataSourceName, dataSource);
		} else {
			if (logger.isTraceEnabled()) {
				logger.trace("found datasource '" + dataSourceName + "'");
			}
		}
		// testDataSource(dataSourceName, dataSource);
		return dataSource;
	}

	public void testDataSource(final String dataSourceName,
			final AbstractDataSource ds) {
		try {
			final Connection conn = ds.getConnection();
			conn.close();
		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IllegalStateException(dataSourceName
					+ " does not return connections");
		}

	}

	@SuppressWarnings("unchecked")
	public Datasources getDatasources() {
		final File datasourcesFile = getDatasourcesFile();
		try {
			logger.debug("Unmarshalling: " + datasourcesFile.getAbsolutePath());
			final Package jaxbJavaPkg = Datasources.class.getPackage();
			if (jaxbJavaPkg == null) {
				throw new IllegalStateException("JAXB Class has a null java "
						+ "package for \"org.javautil.datasources.Datasources"
						+ "\", class was loaded with ClassLoader \""
						+ Datasources.class.getClassLoader().getClass()
								.getName() + "\": toString(): "
						+ Datasources.class.getClassLoader().toString());
			}
			final String datasourcesPkg = jaxbJavaPkg.getName();
			final JAXBContext jc = JAXBContext.newInstance(datasourcesPkg);
			final Unmarshaller unmarshaller = jc.createUnmarshaller();
			final JAXBElement<Datasources> jaxb = (JAXBElement<Datasources>) unmarshaller
					.unmarshal(datasourcesFile);
			return jaxb.getValue();
		} catch (final JAXBException jex) {
			throw new IllegalStateException(
					"Unable to unmarshal Datasources file: "
							+ datasourcesFile.getAbsolutePath() + " "
							+ jex.getMessage(), jex);
		} catch (final Exception e) {
			throw new IllegalStateException("Unexpected error while "
					+ "attempting to unmarshal Datasources file: "
					+ datasourcesFile.getAbsolutePath(), e);

		}
	}

	public DatasourceType getDataSourceType(final String dataSourceName) {
		if (dataSourceTypeMap == null) {
			final Datasources dss = getDatasources();

			dataSourceTypeMap = new TreeMap<String, DatasourceType>();
			for (final DatasourceType dst : dss.getDatasource()) {
				final String name = validate(
						"datasource name cannot be null or zero length",
						dst.getName());
				if (dataSourceTypeMap.put(name, dst) != null) {
					throw new IllegalStateException(
							"Datasources file has two Datasources with the name "
									+ dst.getName());
				}
			}
		}
		return dataSourceTypeMap.get(dataSourceName);
	}

	@SuppressWarnings("unchecked")
	private AbstractDataSource instantiate(final String dataSourceName) {
		AbstractDataSource dataSource = dataSourceMap.get(dataSourceName);

		if (dataSource == null) {
			logger.debug("Searching for datasource: " + dataSourceName);
			final DatasourceType dst = getDataSourceType(dataSourceName);
			if (dst == null) {
				throw new IllegalArgumentException("no such datasource \""
						+ dataSourceName + "\"; valid datasource names: "
						+ Arrays.toString(dataSourceMap.keySet().toArray()));
			}

			try {
				final String className = validate(
						"datasource classname cannot be null or zero length",
						dst.getClassname());

				for (final SystemProperty sp : dst.getSystemProperty()) {
					logger.debug("Setting system property " + sp.getKey() + "="
							+ sp.getValue());
					System.setProperty(sp.getKey(), sp.getValue());
				}

				logger.debug("Attempting to instantiate: " + className);

				final Class<AbstractDataSource> clazz = (Class<AbstractDataSource>) Class
						.forName(className);
				dataSource = clazz.newInstance();

				logger.debug("Initializing: " + className);
				dataSource.setName(dataSourceName);
				logger.debug("dataSourceName " + dataSourceName);
				final List<Property> properties = dst.getProperty();
				for (final Property prop : properties) {
					final String key = prop.getKey();
					final String val = prop.getValue();
					// todo this will show passwords as we lost the non mapped
					// implementation due
					// todo this should be a prop resolver to get an atomic log
					// entry
					logger.debug("key '" + key + "' value '" + val + "'");
				}
				dataSource.setProperties(dst.getProperty());
				dataSource.assertRequiredProperties();
				dataSource.initialize();
			} catch (final Throwable t) {
				logger.error(t);
				throw new IllegalStateException(
						"Could not instantiate datasource: " + dataSourceName,
						t);
			}
		}
		return dataSource;
	}

	private String validate(final String message, String value) {
		value = (value == null ? null : value.trim());
		if (value == null || value.length() == 0) {
			throw new IllegalArgumentException(message);
		}
		return value;
	}

	/**
	 * Constructs a <code>String</code> with all attributes in name = value
	 * format.
	 * 
	 * @return a <code>String</code> representation of this object.
	 */
	@Override
	public String toString() {
		final String TAB = "    ";

		String retValue = "";

		retValue = "DatasourcesFactory ( " + super.toString() + TAB
				+ "logger = " + this.logger + TAB + "dataSourceMap = "
				+ this.dataSourceMap + TAB + "dataSourceTypeMap = "
				+ this.dataSourceTypeMap + TAB + " )";

		return retValue;
	}
}
