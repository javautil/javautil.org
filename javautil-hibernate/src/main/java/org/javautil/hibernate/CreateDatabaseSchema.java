package org.javautil.hibernate;

import java.io.File;
import java.io.FilenameFilter;

import org.apache.log4j.Logger;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.javautil.commandline.CommandLineHandler;

public class CreateDatabaseSchema {

	private final Logger logger = Logger.getLogger(getClass());

	private CreateDatabaseSchemaArgs argumentBean;

	private void afterPropertiesSet() {
		if (argumentBean == null) {
			throw new IllegalStateException("argumentBean is null");
		}
	}

	public void createSchema(final File configurationFile,
			final String outputFilename, final boolean createDatabaseObjects,
			final File mappingDirectory, final String delimiter) {
		if (mappingDirectory == null) {
			throw new IllegalArgumentException("mappingDirectory is null");
		}
		if (configurationFile == null) {
			throw new IllegalArgumentException("configurationFile is null");
		}
		if (outputFilename == null) {
			throw new IllegalArgumentException("outputFileName is null");
		}
		final Configuration cfg = new Configuration();
		logger.info("using configuration file: " + configurationFile.getName());
		final Configuration configured = cfg.configure(configurationFile);
		addFiles(configured, mappingDirectory);

		configured.buildSessionFactory();

		createSchema(configured, outputFilename, createDatabaseObjects,
				delimiter);
	}

	public void addFiles(final Configuration configuration,
			final File mappingDirectory) {
		if (configuration == null) {
			throw new IllegalArgumentException("configuration is null");
		}
		if (mappingDirectory != null) {
			new HbmFilter();
			final String[] mappingFiles = mappingDirectory
					.list(new HbmFilter());
			for (final String fileName : mappingFiles) {
				final String fullName = mappingDirectory + "/" + fileName;
				configuration.addFile(fullName);
				logger.info("adding file " + fullName);
			}
		}
	}

	public void createSchema(final Configuration cfg, final String filename,
			final boolean createDatabaseObjects, final String delimiter) {

		try {
			logger.debug("Starting process...");
			final SchemaExport schema = new SchemaExport(cfg);
			schema.setFormat(true);
			schema.setDelimiter(delimiter);
			boolean createScript = false;
			if (filename != null) {
				schema.setOutputFile(filename);
				createScript = true;
			}
			schema.drop(true, true);
			schema.create(createScript, createDatabaseObjects);
			logger.debug("Ending process...");

		} catch (final Throwable ex) {
			throw new RuntimeException(ex);
		}
	}

	private void setArgumentBean(final CreateDatabaseSchemaArgs argumentBean) {
		this.argumentBean = argumentBean;
	}

	public void process() {
		afterPropertiesSet();
		createSchema(argumentBean.getConfigurationFile(), argumentBean
				.getOutputFile().getName(),
				argumentBean.isCreateDatabaseObjects(),
				argumentBean.getMappingsDirectory(),
				argumentBean.getDelimiter());
	}

	public static void main(final String[] args) {
		final CreateDatabaseSchemaArgs argProcessor = new CreateDatabaseSchemaArgs();
		final CommandLineHandler clh = new CommandLineHandler(argProcessor);
		clh.evaluateArguments(args);
		final CreateDatabaseSchema hbm2ddl = new CreateDatabaseSchema();
		hbm2ddl.setArgumentBean(argProcessor);
		hbm2ddl.process();
		// hbm2ddl.createSchema("-outputFile exceptionScript.sql");
	}

	class HbmFilter implements FilenameFilter {

		@Override
		public boolean accept(final File dir, final String name) {
			final boolean hbmFile = name.endsWith("hbm.xml");
			return hbmFile;
		}

	}

}
