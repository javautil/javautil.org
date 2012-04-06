package org.javautil.jfm;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import junit.framework.Assert;

import org.javautil.jfm.mains.FreeMarkerGenerator;
import org.junit.Ignore;
import org.junit.Test;

public class FreeMarkerGeneratorTest {
	// TODO fix, this doesn't work
	@Ignore
	@Test
	public void testRenderMySimpleBeanGetJavaClassSimpleName() throws Exception {
		final String template = "${bean.simpleName}";
		final String expected = "MySimpleBean";
		inMemoryRenderTest(MySimpleBean.class, template, expected);
	}

	protected void inMemoryRenderTest(final Class<? extends Object> javaClass,
			final String template, final String expected) throws Exception {
		final FreeMarkerGenerator freemarkerGenerator = new FreeMarkerGenerator();
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		final StringReader templateReader = new StringReader(template);
		freemarkerGenerator.setModel(FreeMarkerGenerator.createModel(javaClass,
				null));
		freemarkerGenerator.setTemplateReader(templateReader);
		freemarkerGenerator.afterPropertiesSet(); // initializing bean
		freemarkerGenerator.generate(outputStream);
		Assert.assertEquals(expected, new String(outputStream.toByteArray()));
	}

	/**
	 * Used by the TemplateRendererTest unit test as a basic pojo class.
	 * 
	 * @author bcm
	 */
	class MySimpleBean {
		private String myStringProperty;

		public String getMyStringProperty() {
			return myStringProperty;
		}

		public void setMyStringProperty(final String myStringProperty) {
			this.myStringProperty = myStringProperty;
		}
	}

}
