package org.javautil.sql;

import java.sql.Connection;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.javautil.dataset.Dataset;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * 
 * @author jjs
 *
 */
public interface QueryResource {

	public Configuration getConfiguration();

	public Resource getConfigurationResource();

	public String getQueryText();

	public Dataset getDataset();

	public void setConnection(Connection conn);

	public void setResourceLoader(ResourceLoader loader);

	public void afterPropertiesSet();

	public void setQueryResourceName(String string);

	public void setParameters(Map<String, Object> parms);

}
