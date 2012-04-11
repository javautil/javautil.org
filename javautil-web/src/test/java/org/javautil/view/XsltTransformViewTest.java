package org.javautil.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javautil.io.ClassPathResourceResolver;
import org.javautil.sales.test.AbstractH2SmallSalesTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class XsltTransformViewTest extends AbstractH2SmallSalesTest {

	private final Log logger = LogFactory.getLog(getClass());

	private final ResourceLoader resourceLoader = new ClassPathResourceResolver(
			"sales");

	@Test
	public void testSimpleRender() throws Exception {
		XsltTransformView view = new XsltTransformView();
		view.setBeanName("salesperson_options");
		view.setStylesheetName("html_select");
		view.setResourceLoader(resourceLoader);
		view.setSessionFactory(getSessionFactory());
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		Map<String, Object> parms = new HashMap<String, Object>();
		view.render(parms, request, response);
		byte[] contentBytes = response.getContentAsByteArray();
		Assert.assertTrue(contentBytes.length > 0);
		String content = new String(contentBytes);
		logger.info("XSLT transformed XmlDataset: " + content);
		Assert.assertTrue(content.trim().length() > 0);
	}

	@Test
	public void testRenderWithXsltParameters() throws Exception {
		XsltTransformView view = new XsltTransformView();
		view.setBeanName("salesperson_options");
		view.setStylesheetName("html_select");
		view.setResourceLoader(resourceLoader);
		view.setSessionFactory(getSessionFactory());
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("xsl:multiple", "true");
		MockHttpServletResponse response = new MockHttpServletResponse();
		Map<String, Object> parms = new HashMap<String, Object>();
		view.render(parms, request, response);
		byte[] contentBytes = response.getContentAsByteArray();
		Assert.assertTrue(contentBytes.length > 0);
		String content = new String(contentBytes);
		logger.info("XSLT transformed XmlDataset: " + content);
		Assert.assertTrue(content.trim().length() > 0);
		Assert.assertTrue(content.indexOf("multiple") > -1);
	}

	@Test
	public void testRenderWith2Transforms() throws Exception {
		XsltTransformView view = new XsltTransformView();
		view.setBeanName("salesperson_options");
		List<String> stylesheetNames = new ArrayList<String>();
		stylesheetNames.add("html_select");
		stylesheetNames.add("html_page");
		view.setStylesheetNames(stylesheetNames);
		view.setResourceLoader(resourceLoader);
		view.setSessionFactory(getSessionFactory());
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		Map<String, Object> parms = new HashMap<String, Object>();
		view.render(parms, request, response);
		byte[] contentBytes = response.getContentAsByteArray();
		Assert.assertTrue(contentBytes.length > 0);
		String content = new String(contentBytes);
		logger.info("XSLT transformed XmlDataset: " + content);
		Assert.assertTrue(content.trim().length() > 0);
		Assert.assertTrue(content.indexOf("<html") > -1);
		Assert.assertTrue(content.indexOf("<head") > -1);
		Assert.assertTrue(content.indexOf("<body") > -1);
		Assert.assertTrue(content.indexOf("<select") > -1);
		Assert.assertTrue(content.indexOf("<option") > -1);
	}

}
