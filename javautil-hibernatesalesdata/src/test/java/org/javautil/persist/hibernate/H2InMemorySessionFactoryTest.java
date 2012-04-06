package org.javautil.persist.hibernate;

import java.io.File;
import java.util.Map;

import junit.framework.Assert;

import org.hibernate.InvalidMappingException;
import org.hibernate.SessionFactory;
import org.javautil.hibernate.persist.H2InMemorySessionFactory;
import org.junit.Ignore;
import org.junit.Test;

/**
 * TODO this is testing Javautil-hibernate.
 * 
 * It is located here to prevent circular dependencies.
 * 
 * Unfortunately it makes the code coverage look bad
 * 
 * @author jjs
 * 
 */
public class H2InMemorySessionFactoryTest {

	// TODO Fix
	@Ignore
	@Test(expected = InvalidMappingException.class)
	public void testNoMappings() {
		// error because no mappings (*.hbm.xml) are in the project root
		H2InMemorySessionFactory.getInstance(new File("."));
	}

	// TODO fix

	// it is a real pain in the ... to have these files here, they conflict with
	// javautil-hibernatesales
	@Test
	@SuppressWarnings("unchecked")
	public void testWithMappings() {
		// success because 7 mappings (*.hbm.xml) are in the specified folder
		final SessionFactory sessionFactory = H2InMemorySessionFactory
				.getInstance(new File(
						"../javautil-hibernatesalesdata/src/main/resources/org/javautil/sales/dto"));
		final Map metadata = sessionFactory.getAllClassMetadata();
		Assert.assertEquals(7, metadata.size());
	}

}
