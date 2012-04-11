package org.javautil.jfm.mains.mvc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javautil.jdbc.datasources.SimpleDatasourcesFactory;
import org.javautil.jdbc.metadata.Table;
import org.javautil.jdbc.metadata.dao.TableDaoJdbc;
import org.javautil.jfm.mains.FreeMarkerGenerator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class SpringTableMetaDataFTLFormGenerator extends FreeMarkerGenerator {

	private DataSource dataSource;

	private Configuration configuration;

	private Table model;

	private final Log logger = LogFactory.getLog(getClass());

	// TODO check for copy/past
	public void process(
			final SpringTableMetaDataFormGeneratorArguments arguments)
			throws SQLException, IOException, TemplateException {
		dataSource = SimpleDatasourcesFactory.getDataSource(arguments
				.getDatasourceName());
		final Connection conn = dataSource.getConnection();
		final DatabaseMetaData dmd = conn.getMetaData();
		configuration = getConfiguration();
		final Reader reader = new FileReader(arguments.getTemplateFile());
		File outputDir = arguments.getOutputFile().getParentFile();
		outputDir.mkdirs();
		final OutputStream os = new FileOutputStream(arguments.getOutputFile());
		TableDaoJdbc dao = new TableDaoJdbc(conn, dmd,
				arguments.getSchemaName(), arguments.getCatalogName(),
				arguments.getTableName());
		Table table = dao.getTable(dmd, conn, arguments.getSchemaName(),
				arguments.getCatalogName(), arguments.getTableName(), "%");

		render(reader, os, table, arguments);
		conn.close();

		// ArrayList<ColumnAttributes> columns = (ArrayList<ColumnAttributes>)
		// table.getColumns();
		// JFMGenerator instance =
		// getInstance(SpringTableMetaDataFTLFormGenerator.class,
		// arguments);
		// FreeMarkerGenerator.generateToOutputFileArgument(instance,
		// arguments);
	}

	/**
	 * 
	 * @param templateReader
	 *            the template that creates the output template
	 * @param outputStream
	 *            the result
	 * @param table
	 * @throws IOException
	 * @throws TemplateException
	 */
	public void render(final Reader templateReader,
			final OutputStream outputStream, Table table,
			final SpringTableMetaDataFormGeneratorArguments args)
			throws IOException, TemplateException {
		Writer writer = null;
		try {
			writer = new OutputStreamWriter(outputStream);
			final Template template = new Template(this.toString(),
					templateReader, configuration);
			final Map<String, Object> map = new HashMap<String, Object>();
			map.put("bean", table);
			map.put("arguments", args);
			if (logger.isDebugEnabled()) {
				logger.debug(table.toString());
				logger.debug(formatMap(map));
			}
			template.process(map, writer);
		} finally {
			writer.close();
		}
	}

	public String formatMap(Map<String, Object> map) {
		StringBuilder sb = new StringBuilder();
		sb.append(map.toString());
		String retval = sb.toString();
		return retval;
	}

	// public void afterPropertiesSet() throws Exception {
	// if (configuration == null) {
	// logger.info("no freemarker configuration was specified, "
	// + " using default");
	// configuration = getDefaultConfiguration();
	// }
	// if (configuration.getTemplateLoader() != null) {
	// logger.warn("replaced configuration template loader");
	// }
	// ResourceLoader loader = new FileSystemResourceLoader();
	// configuration.setTemplateLoader(new MultiTemplateLoader(
	// new TemplateLoader[] { new ClassTemplateLoader(TemplateRenderer.class,
	// ""),
	// new SpringTemplateLoader(loader, "/") }));
	//
	// Map<String, String> autoImports = new HashMap<String, String>();
	// autoImports.put("jfm", "jfm.ftl");
	// configuration.setAutoImports(autoImports);
	// if (model == null) {
	// model = new HashMap<String, Object>();
	// }
	// }

	public Configuration getDefaultConfiguration() {
		final Configuration cfg = new Configuration();
		cfg.setDateFormat("MM/dd/yyyy");
		cfg.setDateTimeFormat("MM/dd/yyyy hh:mm aaa");
		cfg.setNumberFormat("#");
		return cfg;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(final Configuration configuration) {
		this.configuration = configuration;
	}

	public static void main(final String[] args) throws SQLException,
			IOException, TemplateException {
		final SpringTableMetaDataFormGeneratorArguments arguments = new SpringTableMetaDataFormGeneratorArguments();
		arguments.processArguments(args);
		final SpringTableMetaDataFTLFormGenerator instance = new SpringTableMetaDataFTLFormGenerator();
		instance.process(arguments);
	}

}
