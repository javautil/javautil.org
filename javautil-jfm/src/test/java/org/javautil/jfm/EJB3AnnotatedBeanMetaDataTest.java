package org.javautil.jfm;

import javax.persistence.Entity;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
// TODO this is not a test
public class EJB3AnnotatedBeanMetaDataTest {

	private EJB3AnnotatedBeanMetadata metaData;

	@Before
	public void setup() throws Exception {
		metaData = new EJB3AnnotatedBeanMetadata();
		metaData.setJavaClass(MySimpleEJB3Bean.class);
		metaData.afterPropertiesSet();
	}

	@Test
	public void noTest() {
		
	}
	// TODO bcm fix this
	 @Test
	 public void testGetName() {
	 Assert.assertEquals("my_simple_ejb3_bean", metaData.getName());
	 }

	@Entity(name = "my_simple_ejb3_bean")
	class MySimpleEJB3Bean {
		String firstName;
		String lastName;

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(final String firstName) {
			this.firstName = firstName;
		}
	}

}
