package org.javautil.sql;

import java.sql.Connection;

import org.javautil.io.ClassPathResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ResourceLoader;

public class ResultSetDatasetQueryTest extends QueryResourceImplTest {


	@Before
	public void setup() throws Exception {
		Connection conn = getDatasource().getConnection();
		QueryResource resource = new ResultSetDatasetResourceImpl();
		setResource(resource);
		resource.setConnection(conn);
		ResourceLoader loader = new ClassPathResourceResolver("query");
		resource.setResourceLoader(loader);
		resource.afterPropertiesSet();
	}

	@Test(expected = IllegalStateException.class)
	public void testNoConnection() throws Exception {
		QueryResource resource = new DisassociatedResultSetDatasetQueryResource();
		resource.afterPropertiesSet();
	}

}
