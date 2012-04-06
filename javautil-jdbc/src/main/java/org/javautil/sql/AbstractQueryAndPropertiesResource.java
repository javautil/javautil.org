package org.javautil.sql;

import java.io.IOException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javautil.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public abstract class AbstractQueryAndPropertiesResource implements
		QueryResource {

	private ResourceLoader resourceLoader;

	private String queryResourceName;

	private final Log logger = LogFactory.getLog(getClass());

	public PropertiesConfiguration getPropertiesConfiguration() {
		try {
			final PropertiesConfiguration cfg = new PropertiesConfiguration();
			if (getConfigurationResource().exists()) {
				cfg.load(getConfigurationResource().getInputStream());
			}
			return cfg;
		} catch (final ConfigurationException e) {
			throw new RuntimeException(e);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Configuration getConfiguration() {
		return getPropertiesConfiguration();
	}

	@Override
	public Resource getConfigurationResource() {
		if (getResourceLoader() == null) {
			throw new IllegalStateException("resourceLoader is null");
		}
		if (getQueryResourceName() == null) {
			throw new IllegalStateException("queryResourceName is null");
		}
		return getResourceLoader().getResource(
				getQueryResourceName() + ".properties");
	}

	@Override
	public String getQueryText() {
		if (getResourceLoader() == null) {
			throw new IllegalStateException("resourceLoader is null");
		}
		if (getQueryResourceName() == null) {
			throw new IllegalStateException("queryResourceName is null");
		}
		try {
			final Resource resource = getResourceLoader().getResource(
					getQueryResourceName());
			if (logger.isDebugEnabled()) {
				logger.debug("reading query text from resource: "
						+ resource.getDescription());
			}
			return IOUtils.readStringFromStream(resource.getInputStream());
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	public ResourceLoader getResourceLoader() {
		return resourceLoader;
	}

	public void setResourceLoader(final ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	public String getQueryResourceName() {
		return queryResourceName;
	}

	public void setQueryResourceName(final String queryResourceName) {
		this.queryResourceName = queryResourceName;
	}

}
