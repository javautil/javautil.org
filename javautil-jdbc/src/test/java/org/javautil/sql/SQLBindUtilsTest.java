package org.javautil.sql;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.apache.log4j.BasicConfigurator;
import org.javautil.io.ClassPathResourceResolver;
import org.javautil.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class SQLBindUtilsTest {

	private static ResourceLoader loader;

	@BeforeClass
	public static void setupClass() throws Exception {
		BasicConfigurator.configure();
		loader = new ClassPathResourceResolver("query");
		((ClassPathResourceResolver) loader).afterPropertiesSet();
	}

	@Test
	public void testGetBinds() throws Exception {
		final Resource resource = loader.getResource("friends_like.sql");
		final String sql = IOUtils.readStringFromStream(resource
				.getInputStream());
		final LinkedHashSet<String> binds = SQLBindUtils.getBinds(sql);
		Assert.assertEquals("search_text", binds.toArray()[0]);
		Assert.assertEquals("number_of_rows", binds.toArray()[1]);
	}

	@Test
	public void testParameterIndexesForBinds() throws Exception {
		final Resource resource = loader.getResource("friends_like.sql");
		final String sql = IOUtils.readStringFromStream(resource
				.getInputStream());
		final Map<String, List<Integer>> indexes = SQLBindUtils
				.parameterIndexesForBinds(sql);
		final List<Integer> searchTextIndexes = indexes.get("search_text");
		Assert.assertTrue(searchTextIndexes.contains(1));
		Assert.assertTrue(searchTextIndexes.contains(2));
		Assert.assertTrue(searchTextIndexes.contains(3));
		Assert.assertTrue(searchTextIndexes.contains(4));
		Assert.assertTrue(searchTextIndexes.contains(5));
		Assert.assertTrue(searchTextIndexes.contains(6));
		Assert.assertTrue(searchTextIndexes.contains(7));
		Assert.assertTrue(searchTextIndexes.contains(8));
		Assert.assertTrue(searchTextIndexes.contains(9));
		final List<Integer> numberOfRowsIndexes = indexes.get("number_of_rows");
		Assert.assertTrue(numberOfRowsIndexes.contains(10));
		Assert.assertTrue(numberOfRowsIndexes.contains(11));
		Assert.assertTrue(numberOfRowsIndexes.contains(12));
	}
}
