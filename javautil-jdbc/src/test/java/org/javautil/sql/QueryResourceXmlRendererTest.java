package org.javautil.sql;

import java.util.HashMap;
import java.util.Map;

import org.javautil.dataset.Dataset;
import org.junit.Ignore;

@Ignore
public class QueryResourceXmlRendererTest {
	
	public Dataset getDataset() {
		QueryResource resource = new DisassociatedResultSetDatasetQueryResource();
		final Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("name", "Betty");
		resource.setParameters(parms);
		resource.setQueryResourceName("friends_where_name.sql");
		final Dataset dataset = resource.getDataset();
		return dataset;
	}

	public void test() {
		QueryResourceXmlRenderer renderer = new QueryResourceXmlRenderer();
		
	}

}
