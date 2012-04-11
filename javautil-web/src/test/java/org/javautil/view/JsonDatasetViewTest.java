package org.javautil.view;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javautil.io.ClassPathResourceResolver;
import org.javautil.sales.test.AbstractH2SmallSalesTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class JsonDatasetViewTest extends AbstractH2SmallSalesTest {

	private final Log logger = LogFactory.getLog(getClass());

	private final ResourceLoader resourceLoader = new ClassPathResourceResolver(
			"sales");

	@Test
	public void testSimpleRender() throws Exception {
		JsonDatasetView view = new JsonDatasetView();
		view.setBeanName("salesperson");
		view.setResourceLoader(resourceLoader);
		view.setSessionFactory(getSessionFactory());
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		view.render(new HashMap<String, Object>(), request, response);
		byte[] contentBytes = response.getContentAsByteArray();
		Assert.assertTrue(contentBytes.length > 0);
		String content = new String(contentBytes);
		logger.info("JsonDataset: " + content);
		content = content.trim();
		Assert.assertTrue(content.length() > 0);
		Assert.assertTrue(content.startsWith("[{"));
		Assert.assertTrue(content.endsWith("}]"));
		// 5 salespersons
		Assert.assertEquals(5, content.split("\\},").length);
	}

	@Test
	public void testSingleColumnRender() throws Exception {
		JsonDatasetView view = new JsonDatasetView();
		view.setBeanName("salesperson_names");
		view.setResourceLoader(resourceLoader);
		view.setSessionFactory(getSessionFactory());
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		view.render(new HashMap<String, Object>(), request, response);
		byte[] contentBytes = response.getContentAsByteArray();
		Assert.assertTrue(contentBytes.length > 0);
		String content = new String(contentBytes);
		logger.info("JsonDataset: " + content);
		content = content.trim();
		Assert.assertTrue(content.length() > 0);
		Assert.assertTrue(content.startsWith("["));
		Assert.assertTrue(content.endsWith("]"));
		// 5 salespersons
		Assert.assertEquals(5, content.split("\\\",").length);
	}

}
