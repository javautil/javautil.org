package org.javautil;

import java.io.InputStream;
import java.util.Properties;

import org.javautil.io.ResourceUtils;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.Resource;

public class SpringRunner {
	// TODO what is this and why is it not in the test tree.
	// TODO oh yeah so other projects can use it
	public static void main(final String[] args) throws Exception {

		if (args.length < 1) {
			System.err.println("at least one argument (application"
					+ " context location) is required; (examples: "
					+ "file:///tmp/spring.xml, classpath:/javax/Class)");
		}
		if (args.length > 2) {
			System.err.println("more than two arguments are not supported");
		}

		// configure external properties placeholder
		final PropertyPlaceholderConfigurer cfg = new PropertyPlaceholderConfigurer();
		final Properties props = new Properties();
		if (args.length > 1) {
			InputStream in = null;
			try {
				in = ResourceUtils.asResource(args[1]).getInputStream();
				props.load(in);
			} finally {
				if (in != null) {
					in.close();
				}
			}
		}
		cfg.setProperties(props);

		// set placeholder values in spring context using properties
		XmlBeanFactory beanFactory = null;
		final Resource appContextResource = ResourceUtils.asResource(args[0]);
		beanFactory = new XmlBeanFactory(appContextResource);
		cfg.postProcessBeanFactory(beanFactory);

		// start the context
		beanFactory.preInstantiateSingletons();
	}

}