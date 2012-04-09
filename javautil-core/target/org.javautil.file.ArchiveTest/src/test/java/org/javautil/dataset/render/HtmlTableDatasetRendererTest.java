package org.javautil.dataset.render;

import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import junit.framework.Assert;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.SimpleLayout;
import org.apache.log4j.WriterAppender;
import org.junit.BeforeClass;
import org.junit.Test;

public class HtmlTableDatasetRendererTest extends AbstractTableRendererTest {

	@BeforeClass
	public static void beforeClass() {
		final WriterAppender appender = new WriterAppender();
		appender.setWriter(new PrintWriter(System.out));
		appender.setLayout(new SimpleLayout());
		appender.setName(HtmlTableDatasetRenderer.class.getName());
		BasicConfigurator.configure(appender);
	}

	@SuppressWarnings("unchecked")
	static HtmlTableDatasetRenderer getRenderer() {
		return new HtmlTableDatasetRenderer();
	}

	static HtmlTableRendererRequest getRequest() {
		final HtmlTableRendererRequest rendererRequest = new HtmlTableRendererRequest(
				getSampleDataset());
		rendererRequest.setStreamResult(getStreamResult());
		return rendererRequest;
	}

	@Test
	public void testSimple() throws Exception {
		useBufferStreamResult();
		getRenderer().render(getRequest());
		System.out.println(getBuffer().toString());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testWithTagAttributes() throws Exception {
		useBufferStreamResult();
		final HtmlTableDatasetRenderer renderer = getRenderer();
		final Map<String, String> tableAttributes = new LinkedHashMap<String, String>();
		tableAttributes.put("cellpadding", "0");
		tableAttributes.put("cellspacing", "0");
		tableAttributes.put("style",
				"display: none; background-image: url('background.gif');");
		renderer.getTable().setTableTagAttributes(tableAttributes);
		renderer.render(getRequest());
		Assert.assertTrue(getBuffer().toString().indexOf(
				"<table cellpadding=\"0\" cellspacing=\"0\" style=\"display: none; "
						+ "background-image: url('background.gif');\">") > -1);
	}

	public static void main(final String[] args) throws Exception {
		useTempFileStreamResult();
		getRenderer().render(getRequest());
		final String browser = "/usr/bin/firefox";
		final String filePath = "file://" + getTempFile().getAbsolutePath();
		final String command = browser + " " + filePath;
		Runtime.getRuntime().exec(command);
		Thread.sleep(5000);
	}
}
