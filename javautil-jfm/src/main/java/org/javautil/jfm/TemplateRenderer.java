package org.javautil.jfm;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.ui.freemarker.SpringTemplateLoader;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TemplateRenderer implements InitializingBean {

	private Configuration configuration;

	private Map<String, Object> model;

	private final Log logger = LogFactory.getLog(getClass());

	/**
	 * 
	 * @param templateReader
	 *            the template that creates the output template
	 * @param outputStream
	 *            the result
	 * @throws IOException
	 * @throws TemplateException
	 */
	public void render(final Reader templateReader,
			final OutputStream outputStream) throws IOException,
			TemplateException {
		Writer writer = null;
		try {
			writer = new OutputStreamWriter(outputStream);
			final Template template = new Template(this.toString(),
					templateReader, configuration);
			template.process(getModel(), writer);
		} finally {
			writer.close();
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (configuration == null) {
			logger.info("no freemarker configuration was specified, "
					+ " using default");
			configuration = getDefaultConfiguration();
		}
		if (configuration.getTemplateLoader() != null) {
			logger.warn("replaced configuration template loader");
		}
		final ResourceLoader loader = new FileSystemResourceLoader();
		configuration.setTemplateLoader(new MultiTemplateLoader(
				new TemplateLoader[] {
						new ClassTemplateLoader(TemplateRenderer.class, ""),
						new SpringTemplateLoader(loader, "/") }));

		final Map<String, String> autoImports = new HashMap<String, String>();
		autoImports.put("jfm", "jfm.ftl");
		configuration.setAutoImports(autoImports);
		if (model == null) {
			model = new HashMap<String, Object>();
		}
	}

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

	public Map<String, Object> getModel() {
		return model;
	}

	public void setModel(final Map<String, Object> model) {
		this.model = model;
	}

}
