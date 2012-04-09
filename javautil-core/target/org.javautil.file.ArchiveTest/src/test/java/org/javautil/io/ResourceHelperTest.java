package org.javautil.io;

import java.util.Map;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.io.Resource;

public class ResourceHelperTest {

	private final Log logger = LogFactory.getLog(getClass());

	private ResourceDirectory resourceDirectory;

	@BeforeClass
	public static void setupClass() {
		BasicConfigurator.configure();
	}

	@Before
	public void setup() throws Exception {
		resourceDirectory = new ClassPathResourceResolver("org/javautil");
		((ClassPathResourceResolver) resourceDirectory).afterPropertiesSet();
	}

	@Test
	public void testGetPathToResourceMapping() throws Exception {
		final ResourceHelper helper = new ResourceHelper(resourceDirectory);
		final Map<String, Resource> mapping = helper.getPathToResourceMapping();
		Assert.assertTrue(mapping.size() > 0);
	}

	@Test
	public void testGetPathToResourceMappingWithSameChildName()
			throws Exception {
		final ClassPathResourceResolver resourceDirectory = new ClassPathResourceResolver(
				"classpathResourceResolver");
		resourceDirectory.afterPropertiesSet();
		final ResourceHelper helper = new ResourceHelper(resourceDirectory);
		final Map<String, Resource> mapping = helper.getPathToResourceMapping();
		Assert.assertEquals(1, mapping.size());
	}
}
