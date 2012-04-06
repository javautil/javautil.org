package org.javautil.jdbc.datasources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.logging.ConsoleHandler;

import javax.xml.bind.PropertyException;
import javax.xml.transform.TransformerException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.javautil.file.FileHelper;
import org.javautil.jdbc.oracle.TnsNames;
import org.javautil.jdbc.oracle.TnsnamesEntryBean;
import org.javautil.xml.TransformerHelper;
import org.javautil.xml.XmlValidator;
import org.junit.Ignore;
import org.junit.Test;



public class TnsnamesTest {
	private final String test = null;
	private final Logger logger = Logger.getLogger(this.getClass().getName());


	@org.junit.Before
	public void beforeAll() {
	}

	// TODO fix this test
	// TODO create local tnsnames.ora and parse it and check results
	@Test
	public void getTnsNames() throws IOException
	// , PropertyException
	{
		final TnsNames tns = new TnsNames();
		tns.setTnsnamesFile(new File("src/test/resources/tnsnames.ora"));
		final File f = tns.getTnsNamesFile();
		final String msg = tns.getMessage();
		assertNotNull(msg, f);
		assertTrue(f.canRead());
		final Document tnsDoc = tns.getAsDocument();

		final OutputFormat formatter = OutputFormat.createPrettyPrint();

		final File tnsXmlFile = FileHelper
				.getWritableFileInTemp("tnsnames.xml");
		final FileWriter fw = new FileWriter(tnsXmlFile);
		final XMLWriter xw = new XMLWriter(fw, formatter);
		xw.write(tnsDoc);
		fw.close();
		logger.info("wrote " + tnsXmlFile.getAbsolutePath());
	}
	@Test
	public void getTnsNamesEntries() throws IOException
	{
		final TnsNames tns = new TnsNames();
		tns.setTnsnamesFile(new File("src/test/resources/tnsnames.ora"));
		Map<String,TnsnamesEntryBean> entries = tns.getEntries();
		assertNotNull(entries);
		assertEquals(4,entries.size());
		TnsnamesEntryBean bean = entries.get("ORA1010");
		assertNotNull(bean);
		assertEquals("stadq33.us.oracle.com",bean.getHostName());
		assertEquals(new Integer(1521),bean.getPort());
		assertEquals("ora1010.us.oracle.com",bean.getServiceName());
	}
	
	@Test
	public void getTnsNamesListEntries() throws IOException
	{
		final TnsNames tns = new TnsNames();
		tns.setTnsnamesFile(new File("src/test/resources/tnsnames-address-list.ora"));
		Map<String,TnsnamesEntryBean> entries = tns.getEntries();
		assertNotNull(entries);
		assertEquals(1,entries.size());
		TnsnamesEntryBean bean = entries.get("DWSUN42");
		assertNotNull(bean);
		assertEquals("dwsun42",bean.getHostName());
		assertEquals(new Integer(1521),bean.getPort());
		assertEquals("dev920.us.oracle.com",bean.getServiceName());
	}
	// TODO fix this test
//	@Ignore
//	@org.junit.Test
//	public void testDataSourcesSchemaCompliance() throws SQLException
//	// , PropertyException
//	{
//		final XmlValidator xv = new XmlValidator();
//
//		File ds = new File("unit-tests/dataSources.xml");
//
//		// we might be in a the output directory of the project, which is
//		// typically a subdirectory
//		// inside the project, so we want to check if the file exists one
//		// directory up also
//		final URL baseResource = getClass().getClassLoader().getResource(".");
//		final File parentFile = new File(baseResource.toString())
//				.getParentFile();
//		if (parentFile.isDirectory() && parentFile.exists()) {
//			final File projectDs = new File(parentFile, ds.getName());
//			if (projectDs.exists() && projectDs.isFile()) {
//				ds = projectDs;
//			}
//		}
//
//		logger.info("validating " + ds.getAbsolutePath());
//		try {
//			xv.validateFile(ds);
//		} catch (final DocumentException e) {
//			e.printStackTrace();
//			logger.error(e.getMessage());
//			fail(e.getMessage());
//		}
//
//		assertTrue(1 == 1);
//
//	}
	// TODO this doesn't belong here anywha
//	// TODO fix this test
//	@org.junit.Test
//	public void transformTnsNames() throws TransformerException, IOException {
//		// @todo get the temp dir and open it
//		final File f = new File("/tmp/tnsnames.xml");
//		final File out = new File("/tmp/DataSources.xml");
//		assert (!out.exists());
//		final TransformerHelper th = new TransformerHelper();
//		th.transform(f, "com/dbexperts/resources/TnsnamesToDataSources.xsl",
//				new File("/tmp/DataSources.xml"));
//		assert (out.exists());
//	}
}
