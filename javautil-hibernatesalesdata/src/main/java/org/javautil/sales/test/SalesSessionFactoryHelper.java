package org.javautil.sales.test;

import java.io.IOException;
import java.io.InputStream;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.javautil.hibernate.cfg.DatasourceConfiguration;
import org.javautil.io.ClassPathResourceResolver;
import org.javautil.jdbc.datasources.H2InMemoryDataSource;

/**
 * Creates a SessionFactory for the test hbm files in the sales testing package.
 * 
 * @author bcm
 */
public class SalesSessionFactoryHelper {

	public static final String HBM_MAPPING_PKG = "org/javautil/sales/dto";

	private final ClassPathResourceResolver resolver = new ClassPathResourceResolver(
			HBM_MAPPING_PKG);

	/**
	 * Gets an inputstream for a resource in the hibernate mapping package.
	 * 
	 * @param resourceName
	 * @return inputstream
	 * @throws IOException
	 */
	protected InputStream getResource(final String resourceName)
			throws IOException {
		return resolver.getResource(resourceName).getInputStream();
	}

	/**
	 * Note that we add InputStream objects to the configuration instead of
	 * classes because this method is useful for testing outside of this
	 * project. If we configured hibernate's session factory to load the
	 * entities in a local directory containing the hbm files, it would not work
	 * outside of this project, since the hbms will be in the jar file.
	 * 
	 * @return
	 */

	// TODO document this wonderful hack
	public SessionFactory getSessionFactory() {
		final Configuration cfg = new DatasourceConfiguration(
				new H2InMemoryDataSource());
		cfg.setProperty(Environment.DIALECT, "org.hibernate.dialect.H2Dialect");
		try {
			resolver.afterPropertiesSet();
			cfg.addInputStream(getResource("Customer.hbm.xml"));
			cfg.addInputStream(getResource("CustomerSaleProduct.hbm.xml"));
			cfg.addInputStream(getResource("GttNumber.hbm.xml"));
			cfg.addInputStream(getResource("Product.hbm.xml"));
			cfg.addInputStream(getResource("ProductEtl.hbm.xml"));
			cfg.addInputStream(getResource("Sale.hbm.xml"));
			cfg.addInputStream(getResource("Salesperson.hbm.xml"));
			// classes are added automatically from the same classpath locations
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
		cfg.setProperty(Environment.HBM2DDL_AUTO, "create");
		return cfg.buildSessionFactory();
	}
}
