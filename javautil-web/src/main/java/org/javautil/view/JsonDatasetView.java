package org.javautil.view;

import java.io.OutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.javautil.dataset.Dataset;
import org.javautil.dataset.DatasetIterator;
import org.javautil.dataset.DatasetMetadata;
import org.javautil.sql.ListOfMapsQueryResource;
import org.javautil.sql.QueryResource;
//import org.javautil.sql.QueryResourceImpl;
import org.javautil.web.HttpRequestUtils;
import org.springframework.core.io.ResourceLoader;

/**
 * A view that emits a JSON object that is derived from a query. The query is
 * converted to a list if there is one resultset column or to a list of maps.
 * The JSON is derived from the java object using GSON, which can fail fast in
 * the case of data that does not readily translate to JSON.
 * 
 * http://code.google.com/p/google-gson/
 * 
 * @author bcm
 */
public class JsonDatasetView extends AbstractJsonView {

	private ResourceLoader resourceLoader;

	private SessionFactory sessionFactory;

	/**
	 * When true, the root JSON node is rendered as an array with simple scalar
	 * values if there is only one column. By default, java objects are rendered
	 * by JSON as array of objects (hashes). When false, the root node of the
	 * JSON is always an array of objects (hashes).
	 */
	private boolean renderSingleColumnAsScalarArray = true;

	/**
	 * Prepares a java Object suitable for rendering as JSON.
	 * 
	 * @param connection
	 * @param queryParameters
	 * @return
	 */
	protected Object renderJsonDataset(Connection connection,
			Map<String, Object> queryParameters) {
		ListOfMapsQueryResource resource = null;
		try {
			resource = new ListOfMapsQueryResource();
			resource.setResourceLoader(getResourceLoader());
			resource.setConnection(connection);
			resource.setParameters(queryParameters);
			resource.setQueryResourceName(getBeanName() + ".sql");
			return getJsonDataStructure(resource);
		} finally {
			if (resource != null) {
				resource.destroy();
			}
		}
	}

	protected Object getJsonDataStructure(QueryResource resource) {
		return getJsonDataStructure(resource.getDataset());
	}

	@SuppressWarnings("unchecked")
	protected Object getJsonDataStructure(Dataset dataset) {
		if (logger.isDebugEnabled()) {
			logger.debug(dataset.getDatasetIterator().getRowCount()
					+ " rows in the dataset");
		}
		Object ret = null;
		DatasetMetadata metadata = dataset.getMetadata();
		if (isRenderSingleColumnAsScalarArray()
				&& metadata.getColumnCount() == 1) {
			// when there is only one column, we return only a list
			List<Object> list = new ArrayList<Object>();
			DatasetIterator iterator = dataset.getDatasetIterator();
			while (iterator.next()) {
				Object value = iterator.getObject(0);
				list.add(value);
			}
			ret = list;
		} else {
			// otherwise we return a list of maps to gson
			ret = dataset;
		}
		return ret;
	}

	@SuppressWarnings( { "unchecked", "deprecation" })
	protected Object prepareJsonModel(HttpServletRequest request)
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
			Map parameters = HttpRequestUtils.getSingleParameters(request);
			session = sessionFactory.openSession();
			connection = session.connection();
			return renderJsonDataset(connection, parameters);
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

	public boolean isRenderSingleColumnAsScalarArray() {
		return renderSingleColumnAsScalarArray;
	}

	public void setRenderSingleColumnAsScalarArray(
			boolean renderSingleColumnAsScalarArray) {
		this.renderSingleColumnAsScalarArray = renderSingleColumnAsScalarArray;
	}

}
