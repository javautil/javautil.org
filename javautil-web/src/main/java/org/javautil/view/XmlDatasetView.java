package org.javautil.view;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.javautil.sql.DisassociatedResultSetDatasetQueryResource;
import org.javautil.sql.QueryResourceXmlRenderer;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.view.AbstractView;

/**
 * A view that uses a QueryResourceXmlRenderer to emit xml.
 * 
 * @author bcm
 */
public class XmlDatasetView extends AbstractView {

	private ResourceLoader resourceLoader;

	private SessionFactory sessionFactory;

	public XmlDatasetView() {
		setContentType("text/xml");
	}

	protected void renderXmlDataset(Map<String, Object> model,
			Connection connection, OutputStream outputstream)
			throws IOException {
		QueryResourceXmlRenderer renderer = new QueryResourceXmlRenderer();
		DisassociatedResultSetDatasetQueryResource resource = null;
		try {
			resource = new DisassociatedResultSetDatasetQueryResource();
			resource.setConnection(connection);
			resource.setParameters((Map<String, Object>) model);
			resource.setQueryResourceName(getBeanName() + ".sql");
			resource.setResourceLoader(resourceLoader);
			renderer.setQueryResource(resource);
			renderer.write(outputstream);
		} finally {
			if (resource != null) {
				resource.destroy();
			}
		}
	}

	@SuppressWarnings(value = { "unchecked", "deprecation" })
	@Override
	protected void renderMergedOutputModel(Map model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (resourceLoader == null) {
			throw new IllegalArgumentException("resourceLoader is null");
		}
		if (sessionFactory == null) {
			throw new IllegalArgumentException("sessionFactory is null");
		}
		Connection connection = null;
		Session session = null;
		OutputStream out = null;
		try {
			session = sessionFactory.openSession();
			connection = session.connection();
			out = response.getOutputStream();
			renderXmlDataset(model, connection, out);
		} finally {
			if (connection != null) {
				connection.rollback();
			}
			if (session != null) {
				session.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}

	public ResourceLoader getResourceLoader() {
		return resourceLoader;
	}

	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
