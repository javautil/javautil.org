package org.javautil.xml;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import javax.xml.transform.stream.StreamResult;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javautil.dataset.ColumnMetadata;
import org.javautil.dataset.DataType;
import org.javautil.dataset.DatasetMetadataImpl;
import org.javautil.dataset.MatrixDataset;
import org.junit.BeforeClass;
import org.junit.Test;

public class XmlRendererTest {

	private static MatrixDataset simpleDataset;

	private final Log logger = LogFactory.getLog(getClass());

	@BeforeClass
	public static void setup() {
		final DatasetMetadataImpl m = new DatasetMetadataImpl();
		m.addColumn(new ColumnMetadata("yr", 1, DataType.INTEGER, 8, 0, null));
		m.addColumn(new ColumnMetadata("per", 2, DataType.INTEGER, 1, 0, null));
		m.addColumn(new ColumnMetadata("mth", 3, DataType.INTEGER, 2, 0, null));
		m.addColumn(new ColumnMetadata("sold", 3, DataType.DOUBLE, 8, 0, null));
		simpleDataset = new MatrixDataset(m);
		simpleDataset.addRow(asList(2008, 1, 1, 1000));
		simpleDataset.addRow(asList(2008, 1, 3, 1500));
		simpleDataset.addRow(asList(2008, 2, 4, 2000));
		simpleDataset.addRow(asList(2008, 3, 7, 3000));
		simpleDataset.addRow(asList(2008, 4, 10, 4000));
	}

	private final String simpleXml = "<resultset>" + //
			"<row yr=\"2008\" per=\"1\" mth=\"1\" sold=\"1000\"/>" + //
			"<row yr=\"2008\" per=\"1\" mth=\"3\" sold=\"1500\"/>" + //
			"<row yr=\"2008\" per=\"2\" mth=\"4\" sold=\"2000\"/>" + //
			"<row yr=\"2008\" per=\"3\" mth=\"7\" sold=\"3000\"/>" + //
			"<row yr=\"2008\" per=\"4\" mth=\"10\" sold=\"4000\"/>" + //
			"</resultset>";

	private final String oneBreakXml = "<resultset>" + //
			"<yr value=\"2008\">" + // //
			"<row per=\"1\" mth=\"1\" sold=\"1000\"/>" + //
			"<row per=\"1\" mth=\"3\" sold=\"1500\"/>" + //
			"<row per=\"2\" mth=\"4\" sold=\"2000\"/>" + //
			"<row per=\"3\" mth=\"7\" sold=\"3000\"/>" + //
			"<row per=\"4\" mth=\"10\" sold=\"4000\"/>" + //
			"</yr>" + //
			"</resultset>";

	private final String twoBreaksXml = "<resultset>" + //
			"<yr value=\"2008\">" + //
			"<mth value=\"1\">" + //
			"<row per=\"1\" sold=\"1000\"/>" + //
			"</mth>" + //
			"<mth value=\"3\">" + //
			"<row per=\"1\" sold=\"1500\"/>" + //
			"</mth>" + //
			"<mth value=\"4\">" + //
			"<row per=\"2\" sold=\"2000\"/>" + //
			"</mth>" + //
			"<mth value=\"7\">" + //
			"<row per=\"3\" sold=\"3000\"/>" + //
			"</mth>" + //
			"<mth value=\"10\">" + //
			"<row per=\"4\" sold=\"4000\"/>" + //
			"</mth>" + //
			"</yr>" + //
			"</resultset>";

	@Test
	public void testSimple() throws Exception {
		final XmlRenderer writer = new XmlRenderer();
		final XmlRendererRequestImpl request = new XmlRendererRequestImpl();
		final StringWriter testWriter = new StringWriter();
		final StreamResult result = new StreamResult(testWriter);
		request.setStreamResult(result);
		request.setDataset(simpleDataset);
		writer.setRequest(request);
		writer.process();
		logger.debug(testWriter.toString().replaceAll("<", "\n\\<"));
		Assert.assertEquals(simpleXml, testWriter.toString());
	}

	@Test
	public void testOneBreak() throws Exception {
		final XmlRenderer writer = new XmlRenderer();
		final XmlRendererRequestImpl request = new XmlRendererRequestImpl();
		final StringWriter testWriter = new StringWriter();
		final StreamResult result = new StreamResult(testWriter);
		request.setStreamResult(result);
		request.setBreaks(Arrays.asList(new String[] { "yr" }));
		request.setDataset(simpleDataset);
		writer.setRequest(request);
		writer.process();
		logger.debug(testWriter.toString().replaceAll("<", "\n\\<"));
		Assert.assertEquals(oneBreakXml, testWriter.toString());
	}

	@Test
	public void testTwoBreaks() throws Exception {
		final XmlRenderer writer = new XmlRenderer();
		final XmlRendererRequestImpl request = new XmlRendererRequestImpl();
		final StringWriter testWriter = new StringWriter();
		final StreamResult result = new StreamResult(testWriter);
		request.setStreamResult(result);
		request.setBreaks(Arrays.asList(new String[] { "yr", "mth" }));
		request.setDataset(simpleDataset);
		writer.setRequest(request);
		writer.process();
		logger.debug(testWriter.toString().replaceAll("<", "\n\\<"));
		Assert.assertEquals(twoBreaksXml, testWriter.toString());
	}

	protected static List<Object> asList(final Object... objects) {
		return Arrays.asList(objects);
	}

}
