package org.javautil.document.layout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;
import org.javautil.dataset.ColumnMetadata;
import org.javautil.dataset.DataType;
import org.javautil.dataset.Dataset;
import org.javautil.dataset.DatasetMetadataImpl;
import org.javautil.dataset.MatrixDataset;
import org.javautil.dataset.render.Dom4jHtmlTable;
import org.javautil.document.Document;
import org.javautil.document.DocumentImpl;
import org.javautil.document.SimpleRegion;
import org.javautil.document.renderer.BshRenderTemplate;
import org.javautil.document.renderer.IncrementalDocumentRenderer;
import org.javautil.document.renderer.IncrementalRendererRequest;
import org.javautil.document.renderer.RenderTemplate;
import org.javautil.document.style.ColorUtil;
import org.javautil.document.style.DocumentStyles;
import org.javautil.document.style.StyleImpl;
import org.javautil.io.IOUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class IncrementalDocumentRendererTest {

	private final Log logger = LogFactory.getLog(getClass());

	private File tempFile;

	@BeforeClass
	public static void setup() {
		BasicConfigurator.configure();
	}

	@Before
	public void before() throws Exception {
		tempFile = File.createTempFile("renderer", ".xls");
		if (logger.isDebugEnabled()) {
			logger.debug("file: " + tempFile.getAbsolutePath());
		}
		tempFile.deleteOnExit();
	}

	@Test
	public void testRightOfRegionHtml() throws Exception {
		final SimpleRegion region1 = newTestRegion();
		region1.setLayoutConstraints(new AbsoluteLayout());
		region1.setName("region1");
		final SimpleRegion region2 = newTestRegion();
		region2.setLayoutConstraints(RelativeLayout.rightOf(region1));
		region2.setName("region2");
		region1.setRenderTemplate(getTestTemplate());
		region2.setRenderTemplate(getTestTemplate());
		final Document document = new DocumentImpl(region1, region2);
		performTest(getDom4jHtmlRequest(document));
	}

	@Test
	public void testBelowRegionHtml() throws Exception {
		final SimpleRegion region1 = newTestRegion();
		region1.setLayoutConstraints(new AbsoluteLayout());
		region1.setName("region1");
		final SimpleRegion region2 = newTestRegion();
		region2.setLayoutConstraints(RelativeLayout.below(region1));
		region2.setName("region2");
		region1.setRenderTemplate(getTestTemplate());
		region2.setRenderTemplate(getTestTemplate());
		final Document document = new DocumentImpl(region1, region2);
		performTest(getDom4jHtmlRequest(document));
	}

	private void performTest(final IncrementalRendererRequest request)
			throws Exception {
		final IncrementalDocumentRenderer r = new IncrementalDocumentRenderer();
		request.setStreamResult(getTestStreamResult());
		r.render(request);
		final String content = IOUtils
				.readStringFromStream(new FileInputStream(tempFile));
		System.out.println("--- begin html content ---");
		System.out.println(content);
		System.out.println("--- end html content ---");
	}

	private SimpleRegion newTestRegion() throws Exception {
		final SimpleRegion region = new SimpleRegion();
		region.setDataset(getTestDataset());
		region.setDocumentStyles(getTestStyles());
		region.setParameters(null);
		return region;
	}

	private RenderTemplate getTestTemplate() {
		final BshRenderTemplate bshTemplate = new BshRenderTemplate();
		bshTemplate.setBshScript(getBeanshellScript());
		return bshTemplate;
	}

	private IncrementalRendererRequest getDom4jHtmlRequest(
			final Document document) {
		final IncrementalRendererRequest request = new IncrementalRendererRequest();
		request.setDateFormatter(new SimpleDateFormat("yyyy-MM-dd"));
		request.setContents(new Dom4jHtmlTable());
		request.setDocument(document);
		return request;
	}

	private static Date toDate(final String date) throws ParseException {
		return new SimpleDateFormat("MM/dd/yyyy").parse(date);
	}

	private static ArrayList<Object> toList(final Object... objects) {
		final ArrayList<Object> list = new ArrayList<Object>();
		for (final Object object : objects) {
			list.add(object);
		}
		return list;
	}

	private DocumentStyles getTestStyles() {
		final DocumentStyles styles = new DocumentStyles();
		final StyleImpl style1 = new StyleImpl();
		style1.setName("style1");
		style1.setFontColor(ColorUtil.parseColor("#666"));
		style1.setBackgroundColor(ColorUtil.parseColor("#dfdfdf"));
		styles.put("style1", style1);
		return styles;
	}

	private String getBeanshellScript() {
		final StringWriter s = new StringWriter();
		final PrintWriter p = new PrintWriter(s);
		p.println("iterator = dataset.getDatasetIterator();");
		p.println("while (iterator.next()) {");
		p.println("  String name = iterator.getString(\"name\");");
		p.println("  Integer age = iterator.getInteger(\"age\");");
		p.println("  Double weight = iterator.getDouble(\"weight\");");
		p.println("  java.util.Date birthday = iterator.getDate(\"birthday\");");
		p.println("  renderer.addData(name, \"style1\");");
		p.println("  renderer.addData(age, \"style1\");");
		p.println("  renderer.addData(weight, \"style1\");");
		p.println("  renderer.addData(birthday, \"style1\");");
		p.println("  renderer.nextLine();");
		p.println("}");
		return s.toString();
	}

	private StreamResult getTestStreamResult() throws IOException {
		final StreamResult result = new StreamResult();
		result.setOutputStream(new FileOutputStream(tempFile));
		return result;
	}

	@SuppressWarnings("unchecked")
	private static Dataset getTestDataset() throws ParseException {
		final DatasetMetadataImpl meta = new DatasetMetadataImpl();
		meta.addColumn(new ColumnMetadata("name", 0, DataType.STRING, null,
				null, 32, null, null, null));
		meta.addColumn(new ColumnMetadata("weight", 1, DataType.DOUBLE, 9, 9,
				null, null, null, null));
		meta.addColumn(new ColumnMetadata("age", 2, DataType.INTEGER, 9, null,
				null, null, null, null));
		meta.addColumn(new ColumnMetadata("birthday", 3, DataType.DATE, null,
				null, null, null, null, null));
		final MatrixDataset dataset = new MatrixDataset(meta);
		dataset.addRow(toList("fifi", 11.25, 3, toDate("01/09/2005")));
		dataset.addRow(toList("socks", 13.5, 14, toDate("02/21/1994")));
		dataset.addRow(toList("dolche", 6.125, 2, toDate("08/02/2006")));
		dataset.addRow(toList("chewy", 65.33333, 6, toDate("08/02/2002")));
		return dataset;
	}
}
