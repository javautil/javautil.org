package org.javautil.view;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.hibernate.Session;
import org.javautil.web.HttpRequestUtils;
import org.javautil.xml.XSLTUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * A view that uses a QueryResourceXmlRenderer to emit and transform xml. The
 * stylesheet is loaded using the ResourceLoader that loaded the sql query.
 * 
 * @author bcm
 */
public class XsltTransformView extends XmlDatasetView implements
		InitializingBean, URIResolver {

	private List<String> stylesheetNames;

	public XsltTransformView() {
		setContentType("text/plain");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (stylesheetNames == null) {
			throw new IllegalStateException("stylesheetNames is null");
		}
		if (stylesheetNames.size() == 0) {
			throw new IllegalArgumentException("no stylesheetNames specified");
		}
	}

	protected void renderXsltTransform(StreamSource xml, String stylesheetName,
			Map<String, Object> parms, StreamResult result) throws IOException,
			TransformerConfigurationException, ParserConfigurationException,
			SAXException, TransformerException {
		Resource stylesheet = getResourceLoader().getResource(
				stylesheetName + ".xsl");
		InputSource xsl = new InputSource(stylesheet.getInputStream());
		XSLTUtils.transform(this, xml, xsl, parms, result);
	}

	@SuppressWarnings(value = { "unchecked", "deprecation" })
	@Override
	protected void renderMergedOutputModel(Map model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (getResourceLoader() == null) {
			throw new IllegalArgumentException("resourceLoader is null");
		}
		if (getSessionFactory() == null) {
			throw new IllegalArgumentException("sessionFactory is null");
		}
		Connection connection = null;
		Session session = null;
		
		// render xml from dataset
		ByteArrayOutputStream buffer = null;
		try {			
			session = getSessionFactory().openSession();
			connection = session.connection();
			buffer = new ByteArrayOutputStream();
			renderXmlDataset(model, connection, buffer);
		} finally {
			if (connection != null) {
				connection.rollback();
			}
			if (session != null) {
				session.close();
			}
		}
		
		// prepare xslt transform parameters 
		byte[] bytes = buffer.toByteArray();
		StreamSource xml = new StreamSource(new ByteArrayInputStream(bytes));
		if (logger.isDebugEnabled()) {
			logger.debug("xml before transform: " + new String(bytes));
		}
		Map parms = HttpRequestUtils.getSingleParametersLike("xsl:", request,
				true);
		if (logger.isDebugEnabled()) {
			for (Object parm : parms.keySet()) {
				String value = (String) parms.get(parm);
				logger.debug("parameter " + parm + " was set to " + value);
			}
		}

		// perform xslt transforms
		if (stylesheetNames.size() == 0) {
			throw new IllegalArgumentException("no stylesheetNames specified");
		}
		buffer = null;
		StreamSource input = xml;
		StreamResult result = null;
		for (int i = 0; i < stylesheetNames.size(); i++) {
			if (buffer != null) {
				bytes = buffer.toByteArray();
				input = new StreamSource(new ByteArrayInputStream(bytes));
				buffer = null;
			}
			if (i == stylesheetNames.size() - 1) {
				result = new StreamResult(response.getOutputStream());
			} else {
				buffer = new ByteArrayOutputStream();
				result = new StreamResult(buffer);
			}
			renderXsltTransform(input, stylesheetNames.get(i), parms, result);
		}

	}

	public Source resolve(String href, String base) throws TransformerException {
		try {
			Resource resource = getResourceLoader().getResource(href);
			return new StreamSource(resource.getInputStream());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<String> getStylesheetNames() {
		return stylesheetNames;
	}

	public void setStylesheetName(String stylesheetName) {
		List<String> stylesheetNames = new ArrayList<String>();
		stylesheetNames.add(stylesheetName);
		this.stylesheetNames = stylesheetNames;
	}

	public void setStylesheetNames(List<String> stylesheetNames) {
		this.stylesheetNames = stylesheetNames;
	}
}
