package org.javautil.jfm.mains;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javautil.jfm.ClassUtils;
import org.javautil.jfm.IntrospectiveBeanMetadata;
import org.javautil.jfm.JFMGenerator;
import org.javautil.jfm.TemplateRenderer;
import org.javautil.jfm.model.JFMModelBuilder;
import org.springframework.beans.factory.InitializingBean;

import freemarker.template.TemplateException;

public class FreeMarkerGenerator implements JFMGenerator, InitializingBean {

	private static Log logger = LogFactory.getLog(FreeMarkerGenerator.class);

	private Map<String, Object> model;

	private Reader templateReader;

	public static void main(final String[] args) throws Exception {
		final FreeMarkerGeneratorArguments arguments = new FreeMarkerGeneratorArguments();
		arguments.processArgs(args);
		final JFMGenerator instance = getInstance(FreeMarkerGenerator.class,
				arguments);
		FreeMarkerGenerator.generateToOutputFileArgument(instance, arguments);
	}

	private static JFMGenerator loadGeneratorInstance(
			final Class<? extends Object> clazz) throws InstantiationException,
			IllegalAccessException {
		if (!JFMGenerator.class.isAssignableFrom(clazz)) {
			throw new IllegalArgumentException("class " + clazz.getName()
					+ " does not implement " + JFMGenerator.class.getName());
		}
		final JFMGenerator generator = (JFMGenerator) clazz.newInstance();
		return generator;
	}

	public static JFMGenerator getInstance(final Class<? extends Object> clazz,
			final FreeMarkerGeneratorArguments arguments) throws Exception {
		// load the bean class
		Class<? extends Object> beanClass;
		if (arguments.getJavaClassFile() != null) {
			beanClass = ClassUtils.loadClassFromFile(arguments
					.getJavaClassFile());
		} else if (arguments.getJavaClassName() != null) {
			beanClass = Class.forName(arguments.getJavaClassName());
		} else {
			throw new IllegalStateException("either javaClassName or "
					+ "javaClassFile is required");
		}
		final JFMGenerator generator = loadGeneratorInstance(clazz);
		generator.setModel(createModel(beanClass, arguments));
		// setup the template reader
		if (arguments.getTemplateFile() != null) {
			final File template = arguments.getTemplateFile();
			generator.setTemplateReader(new FileReader(template));
		} else if (arguments.getTemplateResourceName() != null) {
			final String resourceName = arguments.getTemplateResourceName();
			final URL url = ClassLoader.getSystemResource(resourceName);
			final Reader reader = new InputStreamReader(url.openStream());
			generator.setTemplateReader(reader);
		} else {
			throw new IllegalStateException("either templateFile or "
					+ "templateResourceName is required");
		}
		generator.afterPropertiesSet(); // initializing bean
		return generator;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (templateReader == null) {
			throw new IllegalStateException("templateReader is null");
		}
		if (model == null) {
			throw new IllegalStateException("model is null");
		}
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> createModel(
			final Class<? extends Object> javaClass, final Object arguments)
			throws Exception {
		final Map<String, Object> model = new HashMap<String, Object>();
		final IntrospectiveBeanMetadata helper = new IntrospectiveBeanMetadata(
				javaClass);
		helper.afterPropertiesSet(); // initializing bean
		model.put("bean", helper);
		logger.debug("adding model object " + javaClass.getSimpleName()
				+ " as named variable \"bean\"");
		Object args = null;
		if (arguments != null) {
			args = new JFMModelBuilder().buildModelObject(arguments);
		}
		logger.debug("adding model object " + args
				+ " as named variable \"arguments\"");
		model.put("arguments", args);
		return model;
	}

	@Override
	public void generate(final OutputStream outputStream)
			throws TemplateException, IOException {
		final TemplateRenderer templateRenderer = new TemplateRenderer();
		try {
			templateRenderer.setModel(model);
			templateRenderer.afterPropertiesSet(); // initializing bean
			templateRenderer.render(templateReader, outputStream);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (templateReader != null) {
				templateReader.close();
			}
		}
	}

	public static void generateToOutputFileArgument(
			final JFMGenerator generator,
			final FreeMarkerGeneratorArguments arguments) throws IOException,
			TemplateException {
		final File output = arguments.getOutputFile();
		output.getParentFile().mkdirs();
		if (!output.exists() && !output.createNewFile()) {
			throw new IllegalStateException("could not create output file");
		}
		final OutputStream outputStream = new FileOutputStream(output, false);
		try {
			generator.generate(outputStream);
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
			if (generator.getTemplateReader() != null) {
				generator.getTemplateReader().close();
			}
		}
		logger.info(output.getAbsolutePath() + " written");
	}

	@Override
	public Reader getTemplateReader() {
		return templateReader;
	}

	@Override
	public void setTemplateReader(final Reader templateReader) {
		this.templateReader = templateReader;
	}

	@Override
	public Map<String, Object> getModel() {
		return model;
	}

	@Override
	public void setModel(final Map<String, Object> model) {
		this.model = model;
	}

}
