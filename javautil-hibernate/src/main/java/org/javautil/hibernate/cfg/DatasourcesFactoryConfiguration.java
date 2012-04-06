package org.javautil.hibernate.cfg;

import org.javautil.jdbc.datasources.SimpleDatasourcesFactory;

/**
 * A configuration that requires a programmatic datasource instead of a
 * hibernate configured one.
 * 
 * Example: <code>
 * System.setProperty("DATASOURCES_FILE", new File("datasources.xml").getPath());
 * sessionFactory = new DatasourcesFactoryConfiguration("pfs").configure(HIBERNATE_CONFIGURATION_FILE).buildSessionFactory();
 * </code>
 * 
 * @author bcm
 */
public class DatasourcesFactoryConfiguration extends
		AbstractDefaultConfiguration {

	public DatasourcesFactoryConfiguration(final String datasourceName) {
		super(new DatasourceSettingsFactory(
				SimpleDatasourcesFactory.getDataSource(datasourceName)));
	}
}
