//package org.javautil.ejb3;

// TODO this should be moved to jfm
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.io.Reader;
//import java.io.Writer;
//import java.net.URL;
//import java.sql.Connection;
//import java.util.HashMap;
//import org.javautil.jdbc.JdbcJavaNameImpl;
//import org.javautil.jdbc.datasources.DatasourcesFactory;
//import org.javautil.jdbc.metadata.JDBCTable;
//
//import java.util.Map;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
////	import org.javautil.jfm.TemplateRenderer;
////	import org.javautil.jfm.model.JFMModelBuilder;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.core.io.FileSystemResourceLoader;
//import org.springframework.core.io.ResourceLoader; //import org.springframework.ui.freemarker.SpringTemplateLoader;
//
//import freemarker.cache.ClassTemplateLoader;
//import freemarker.cache.MultiTemplateLoader;
//import freemarker.cache.TemplateLoader;
//import freemarker.template.Configuration;
//import freemarker.template.Template;
//import freemarker.template.TemplateException;
//
//public class Ejb3Generator implements
//// JFMGenerator,
//
//		InitializingBean {
//
//	private static Log logger = LogFactory.getLog(Ejb3Generator.class);
//
//	private Map<String, Object> model;
//
//	private Reader templateReader;
//
//	private Configuration configuration;
//
//	private Writer writer;
//
//	private Ejb3GeneratorArguments arguments;
//
//	private JdbcJavaNameImpl namer = new JdbcJavaNameImpl();
//
//	public void process(Ejb3GeneratorArguments arguments) throws IOException {
//			this.arguments = arguments;
//			templateReader = new FileReader(arguments.getTemplateFile());
//		//	String outputPath = arguments.getSourceDirectory() + "/" + arguments
//			
//			writer = new FileWriter(getOutputFile());
//		}
//
//	public File getOutputFile() {
//		String sourceDir = arguments.getSourceDirectory().getAbsolutePath();
//		String packagePath = arguments.getPackageName().replace(".", "/");
//	//	String className = arguments.getTableName();
//		String className = namer.attributeNameInitCap(arguments.getTableName());
//		String path = sourceDir + "/" + packagePath + "/" + className + ".java";
//		File f = new File(path);
//		f.mkdirs();
//		return f;
//	}
//
//	public static void main(String[] args) throws Exception {
//		Ejb3GeneratorArguments arguments = new Ejb3GeneratorArguments();
//		arguments.processArguments(args);
//		Ejb3Generator instance = new Ejb3Generator();
//		arguments.processArguments(args);
//		instance.process(arguments);
//
//	}
//
//	
//	private JDBCTable getTable() {
//		Connection conn = DatasourcesFactory.getDataSource(arguments.getDatasourceName());
//		
//		JDBCTable table =new JDBCTable(Connection conn, final String schemaName, final String catalogName,
//				final String tableName )
//	}
//	// private static JFMGenerator loadGeneratorInstance(Class<? extends Object>
//	// clazz) {
//	// if (!JFMGenerator.class.isAssignableFrom(clazz)) {
//	// throw new IllegalArgumentException("class " + clazz.getName() +
//	// " does not implement " + JFMGenerator.class.getName());
//	// }
//	// JFMGenerator generator;
//	// try {
//	// generator = (JFMGenerator) clazz.newInstance();
//	// } catch (Exception e) {
//	// throw new RuntimeException("while instantiating " +
//	// clazz.getCanonicalName(), e);
//	// }
//	// return generator;
//	// }
//
//	// /**
//	// * This seems goofy seeks like there should be
//	// *
//	// * @param clazz
//	// * @param arguments
//	// * @return
//	// * @throws Exception
//	// */
//	// public static JFMGenerator getInstance(Class<? extends Object> clazz,
//	// FreeMarkerGeneratorArguments arguments) throws Exception {
//	// // load the bean class
//	// Class<? extends Object> beanClass;
//	// if (arguments.getJavaClassFile() != null) {
//	// beanClass = ClassUtils.loadClassFromFile(arguments.getJavaClassFile());
//	// } else if (arguments.getJavaClassName() != null) {
//	// beanClass = Class.forName(arguments.getJavaClassName());
//	// } else {
//	// throw new IllegalStateException("either javaClassName or " +
//	// "javaClassFile is required");
//	// }
//	// JFMGenerator generator = loadGeneratorInstance(clazz);
//	// generator.setModel(createModel(beanClass, arguments));
//	// // setup the template reader
//	// if (arguments.getTemplateFile() != null) {
//	// File template = arguments.getTemplateFile();
//	// generator.setTemplateReader(new FileReader(template));
//	// } else if (arguments.getTemplateResourceName() != null) {
//	// String resourceName = arguments.getTemplateResourceName();
//	// URL url = ClassLoader.getSystemResource(resourceName);
//	// Reader reader = new InputStreamReader(url.openStream());
//	// generator.setTemplateReader(reader);
//	// } else {
//	// throw new IllegalStateException("either templateFile or " +
//	// "templateResourceName is required");
//	// }
//	// generator.afterPropertiesSet(); // initializing bean
//	// return generator;
//	// }
//	//
//	// public void afterPropertiesSet() throws Exception {
//	// if (templateReader == null) {
//	// throw new IllegalStateException("templateReader is null");
//	// }
//	// if (model == null) {
//	// throw new IllegalStateException("model is null");
//	// }
//	// }
//
//	// @SuppressWarnings("unchecked")
//	// public static Map<String, Object> createModel(Class<? extends Object>
//	// javaClass, Object arguments) throws Exception {
//	// Map<String, Object> model = new HashMap<String, Object>();
//	// IntrospectiveBeanMetadata helper = new
//	// IntrospectiveBeanMetadata(javaClass);
//	// helper.afterPropertiesSet(); // initializing bean
//	// model.put("bean", helper);
//	// logger.debug("adding model object " + javaClass.getSimpleName() +
//	// " as named variable \"bean\"");
//	// Object args = null;
//	// if (arguments != null) {
//	// args = new JFMModelBuilder().buildModelObject(arguments);
//	// }
//	// logger.debug("adding model object " + args +
//	// " as named variable \"arguments\"");
//	// model.put("arguments", args);
//	// return model;
//	// }
//
//	// public void generate(OutputStream outputStream) throws TemplateException,
//	// IOException {
//	// TemplateRenderer templateRenderer = new TemplateRenderer();
//	// try {
//	// templateRenderer.setModel(model);
//	// templateRenderer.afterPropertiesSet(); // initializing bean
//	// templateRenderer.render(templateReader, outputStream);
//	// } catch (Exception e) {
//	// throw new RuntimeException(e);
//	// } finally {
//	// if (templateReader != null) {
//	// templateReader.close();
//	// }
//	// }
//	// }
////
////	public static void generateToOutputFileArgument(JFMGenerator generator, FreeMarkerGeneratorArguments arguments)
////			throws IOException, TemplateException {
////		File output = arguments.getOutputFile();
////		output.getParentFile().mkdirs();
////		if (!output.exists() && !output.createNewFile()) {
////			throw new IllegalStateException("could not create output file");
////		}
////		OutputStream outputStream = new FileOutputStream(output, false);
////		try {
////			generator.generate(outputStream);
////		} finally {
////			// if (outputStream != null) {
////			// outputStream.close();
////			// }
////			if (generator.getTemplateReader() != null) {
////				generator.getTemplateReader().close();
////			}
////		}
////		logger.info(output.getAbsolutePath() + " written");
////	}
//
//	public Reader getTemplateReader() {
//		return templateReader;
//	}
//
//	public void setTemplateReader(Reader templateReader) {
//		this.templateReader = templateReader;
//	}
//
//	public Map<String, Object> getModel() {
//		return model;
//	}
//
//	public void setModel(Map<String, Object> model) {
//		this.model = model;
//	}
//
//	/**
//	 * 
//	 * @param templateReader
//	 *            the template that creates the output template
//	 * @param outputStream
//	 *            the result
//	 * @throws IOException
//	 * @throws TemplateException
//	 */
//	public void render(Reader templateReader, OutputStream outputStream) throws IOException, TemplateException {
//		Writer writer = null;
//		try {
//			writer = new OutputStreamWriter(outputStream);
//			Template template = new Template(this.toString(), templateReader, configuration);
//			template.process(getModel(), writer);
//		} finally {
//			if (writer != null) {
//				writer.close();
//			}
//		}
//	}
//
//	// public void afterPropertiesSet() throws Exception {
//	// if (configuration == null) {
//	// logger.info("no freemarker configuration was specified, "
//	// + " using default");
//	// configuration = getDefaultConfiguration();
//	// }
//	// if (configuration.getTemplateLoader() != null) {
//	// logger.warn("replaced configuration template loader");
//	// }
//	// ResourceLoader loader = new FileSystemResourceLoader();
//	// configuration.setTemplateLoader(new MultiTemplateLoader(
//	// new TemplateLoader[] { new ClassTemplateLoader(TemplateRenderer.class,
//	// ""),
//	// new SpringTemplateLoader(loader, "/") }));
//	//
//	// Map<String, String> autoImports = new HashMap<String, String>();
//	// autoImports.put("jfm", "jfm.ftl");
//	// configuration.setAutoImports(autoImports);
//	// if (model == null) {
//	// model = new HashMap<String, Object>();
//	// }
//	// }
//
//	public Configuration getDefaultConfiguration() {
//		Configuration cfg = new Configuration();
//		cfg.setDateFormat("MM/dd/yyyy");
//		cfg.setDateTimeFormat("MM/dd/yyyy hh:mm aaa");
//		cfg.setNumberFormat("#");
//		return cfg;
//	}
//
//	public Configuration getConfiguration() {
//		return configuration;
//	}
//
//	public void setConfiguration(Configuration configuration) {
//		this.configuration = configuration;
//	}
//
//	public void afterPropertiesSet() throws Exception {
//		// TODO Auto-generated method stub
//
//	}
//
//	// public Map<String, Object> getModel() {
//	// return model;
//	// }
//	//
//	// public void setModel(Map<String, Object> model) {
//	// this.model = model;
//	// }
//
// }
