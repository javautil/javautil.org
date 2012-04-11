package org.javautil.poi;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.javautil.dataset.render.typewriter.TypewriterBehavior;
import org.javautil.dataset.render.typewriter.TypewriterContent;
import org.javautil.dataset.render.typewriter.TypewriterRenderer;
import org.javautil.document.DocumentBuilder;
import org.javautil.document.DocumentRegion;
import org.javautil.document.SimpleRegion;
import org.javautil.document.layout.AbsoluteLayout;
import org.javautil.document.renderer.BshRenderTemplate;
import org.javautil.document.style.DocumentStyles;
import org.javautil.document.style.SimpleBorder;
import org.javautil.document.style.Style;
import org.javautil.document.style.StyleImpl;
import org.junit.Test;

public class POIWorkbookRendererTest {

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Test
	public void performDocumentTest() throws Exception {
		final File tempFile = File.createTempFile("poi", ".xls");

		final DocumentBuilder documentBuilder = new DocumentBuilder();
		documentBuilder.setDeleteExistingOutputFile(true);
		documentBuilder.setContentsTypeClass(POIWorkbookRenderer.class);
		documentBuilder.setOutputFile(tempFile);
		final List<DocumentRegion> regions = new ArrayList<DocumentRegion>();
		final SimpleRegion documentRegion = new SimpleRegion();
		final BshRenderTemplate template = new BshRenderTemplate();
		final StringWriter s = new StringWriter();
		s.append("renderer.addData(8990, \"default\");\n");
		s.append("renderer.addData(8991, \"default\");\n");
		s.append("renderer.addData(8992, \"default\");\n");
		s.append("renderer.addData(8993, \"default\");\n");
		s.append("renderer.addData(8994, \"default\");\n");
		s.append("renderer.addData(8995, \"default\");\n");
		s.append("renderer.addData(8996, \"default\");\n");
		s.append("renderer.addData(8997, \"default\");\n");
		s.append("renderer.addData(8998, \"default\");\n");
		s.append("renderer.addData(8999, \"default\");\n");
		s.append("renderer.addData(9000, \"default\");\n");
		s.append("renderer.addData(\"OVER NINE THOUSAND\", \"default\");\n");
		template.setBshScript(s.toString());
		documentRegion.setRenderTemplate(template);
		documentRegion.setLayoutConstraints(new AbsoluteLayout());
		documentRegion.setName("testing");
		final DocumentStyles styles = new DocumentStyles();
		final StyleImpl style = new StyleImpl();
		style.setBorders(SimpleBorder.getInstance(1, Color.BLACK));
		styles.put("default", style);
		documentRegion.setDocumentStyles(styles);
		regions.add(documentRegion);
		documentBuilder.setRegions(regions);

		documentBuilder.afterPropertiesSet();

		final TypewriterContent<HSSFRow, HSSFCell> content = (TypewriterContent<HSSFRow, HSSFCell>) documentBuilder
				.getContents().getContent("test");
		Assert.assertEquals(SheetHelper.class, content.getClass());
		final HSSFRow row = content.getRowAt(0, false);
		Assert.assertNotNull(row);
		Assert.assertEquals(12, row.getLastCellNum());
		final HSSFCell cell = content.getCellAt(0, 11, false, false);
		Assert.assertNotNull(cell);
		Assert.assertEquals("OVER NINE THOUSAND", cell.getStringCellValue());
		// TODO add to integration test
		// Runtime.getRuntime().exec("ooffice " + tempFile.getPath());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void performSimpleTest() throws IOException {
		final File tempFile = File.createTempFile("poi", ".xls");
		final POIWorkbookRenderer renderer = new POIWorkbookRenderer();
		final TypewriterContent<HSSFRow, HSSFCell> content = renderer
				.getContent("test");
		Assert.assertEquals(SheetHelper.class, content.getClass());
		final Map<String, Style> styles = new HashMap<String, Style>();
		styles.put("default", new StyleImpl());
		content.setStylesByName(styles);
		final TypewriterRenderer typewriter = content.getRendererFactory()
				.getTypewriterRenderer(new TypewriterBehavior());
		typewriter.addData(8990, "default");
		typewriter.addData(8991, "default");
		typewriter.addData(8992, "default");
		typewriter.addData(8993, "default");
		typewriter.addData(8994, "default");
		typewriter.addData(8995, "default");
		typewriter.addData(8996, "default");
		typewriter.addData(8997, "default");
		typewriter.addData(8998, "default");
		typewriter.addData(8999, "default");
		typewriter.addData(8999, "default");
		typewriter.addData(9000, "default");
		typewriter.addData("OVER NINE THOUSAND", "default");
		Assert.assertEquals(13, content.getRowAt(0, false).getLastCellNum());
		Assert.assertEquals("OVER NINE THOUSAND",
				content.getCellAt(0, 12, false, false).getStringCellValue());
		renderer.getWorkbook().write(new FileOutputStream(tempFile));
		// Runtime.getRuntime().exec("ooffice " + tempFile.getPath());
	}

}
