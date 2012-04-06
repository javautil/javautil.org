package org.javautil.sql;

import java.sql.Connection;

import org.javautil.io.ClassPathResourceResolver;
import org.junit.Before;
import org.springframework.core.io.ResourceLoader;

public class ListOfMapsQueryResourceTest extends QueryResourceImplTest {


	@Before
	public void setup() throws Exception {
		Connection conn = getDatasource().getConnection();
		QueryResource resource = new ListOfMapsQueryResource();
		setResource(resource);
		resource.setConnection(conn);
		ResourceLoader loader = new ClassPathResourceResolver("query");
		resource.setResourceLoader(loader);
		resource.afterPropertiesSet();
	}

	
}
