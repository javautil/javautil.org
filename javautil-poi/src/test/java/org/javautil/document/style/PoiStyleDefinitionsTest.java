package org.javautil.document.style;

import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/poiDocumentStyles.xml" })
public class PoiStyleDefinitionsTest {

	@Autowired
	protected ApplicationContext appContext;

	private Set<StyleDefinition> definitions;

	@Before
	public void prepareStyleDefinitions() {
		Assert.assertNotNull(appContext);
		definitions = StyleUtil.getStyleDefinitionsFromContext(appContext);
	}

	@Test
	public void testLoadStyleDefinitions() {
		Assert.assertEquals(14, definitions.size());
	}

}
