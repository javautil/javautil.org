package org.javautil.exceptionprocessing;

import org.apache.log4j.Logger;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

// todo move to javautil-jdbc
public class CreateDataBaseSchema {

	private final Logger logger = Logger.getLogger(getClass());

	public void createSchema(final String filename) {

		final Configuration cfg = new Configuration();
		cfg.configure().buildSessionFactory();
		createSchema(cfg, filename);
	}

	public void createSchema(final Configuration cfg, final String filename) {

		try {
			logger.debug("Starting process...");
			final SchemaExport schema = new SchemaExport(cfg);
			schema.setOutputFile(filename);
			schema.create(/* script */true, /* export */true);
			logger.debug("Ending process...");

		} catch (final Throwable ex) {
			throw new RuntimeException(ex);
		}
	}

	public static void main(final String[] args) {
		final ExceptionRuleServiceImpl epctl = new ExceptionRuleServiceImpl();
		final Integer it = new Integer(1050464);
		final ExceptionProcessingServerArgs sagr = new ExceptionProcessingServerArgs();
		sagr.setRunNumber(it);
		epctl.process(sagr);
	}

}
