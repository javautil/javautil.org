package org.javautil.hibernate;

import org.hibernate.cfg.Configuration;

public class ConfigurationHelper {

	public Configuration getConfiguration() {
		final Configuration configuration = new Configuration();
		configuration.buildSettings();

		return configuration;
	}

}
