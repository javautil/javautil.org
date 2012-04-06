package org.javautil.hibernate.persist;

import java.io.File;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.javautil.hibernate.cfg.DatasourceConfiguration;
import org.javautil.jdbc.datasources.H2InMemoryDataSource;

/**
 * An hibernate SessionFactory creator for use with an in memory H2 database.
 * Automatically creates H2 tables for all beans in a specified hbm directory.
 * 
 * @author bcm
 */
public class H2InMemorySessionFactory {

	/**
	 * Creates a hibernate session factory instance.
	 * 
	 * @param directory
	 *            containing hibernate hbm.xml files
	 * @return a hibernate SessionFactory
	 */
	public static SessionFactory getInstance(final File hbmDirectory) {
		final Configuration cfg = new DatasourceConfiguration(
				new H2InMemoryDataSource());
		cfg.setProperty(Environment.DIALECT, "org.hibernate.dialect.H2Dialect");
		cfg.addDirectory(hbmDirectory);
		cfg.setProperty(Environment.HBM2DDL_AUTO, "create");
		return cfg.buildSessionFactory();
	}

}
