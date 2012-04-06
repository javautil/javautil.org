package org.javautil.hibernate.cfg;

import javax.sql.DataSource;

public class DatasourceConfiguration extends AbstractDefaultConfiguration {

	public DatasourceConfiguration(final DataSource datasource) {
		super(new DatasourceSettingsFactory(datasource));
	}

}
