package org.javautil.hibernate.cfg;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.cfg.SettingsFactory;

public class AbstractDefaultConfiguration extends Configuration {

	private final Log logger = LogFactory.getLog(getClass());

	public AbstractDefaultConfiguration() {
		super();
	}

	public AbstractDefaultConfiguration(final SettingsFactory settingsFactory) {
		super(settingsFactory);
	}

	@Override
	public SessionFactory buildSessionFactory() throws HibernateException {
		final String property = Environment.CURRENT_SESSION_CONTEXT_CLASS;
		if (getProperty(property) == null) {
			setProperty(property, "thread");
			logger.info("set property " + property + " --> thread");
		}
		return super.buildSessionFactory();
	}

}
