package org.javautil.jfm;

import static junit.framework.Assert.assertEquals;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.javautil.file.FileComparator;
import org.javautil.jfm.mains.mvc.SpringTableMetaDataFTLFormGenerator;
import org.junit.BeforeClass;
import org.junit.Test;

import freemarker.template.TemplateException;

// TODO fix so that it doesn't exit using security
public class SpringTableMetaDataFormGeneratorTest {

	private static final String schema = " -dataSource=h2 -schema=PUBLIC -table=PRODUCT ";

	private static final String destinationDir = "target/actualData/";

	private static final String expectedDir = "src/test/expectedData/";

	private final Logger logger = Logger.getLogger(getClass());

	private String fileName;

	private final FileComparator fileComparator = new FileComparator();

	@BeforeClass
	public static void beforeClass() {
		// TODO bad location
		System.setProperty("DATASOURCES_FILE",
				"src/test/resources/DataSources.xml");
	}

	public void after() {

		assertEquals(
				0,
				fileComparator.compare(expectedDir + fileName, destinationDir
						+ fileName));
	}

	// TODO fix this test
	@Test
	public void test1() throws SQLException, IOException, TemplateException {
		fileName = "snack";
		final String args = "-templateFile=src/main/resources/tableList.ftl"
				+ schema + "-outputFile=" + destinationDir + fileName;
		logger.debug(args);
		SpringTableMetaDataFTLFormGenerator.main(args.split(" "));
		// TODO what does this test use FileComparator
	}

	// TODO fix this test
	@Test
	public void test2() throws SQLException, IOException, TemplateException {
		fileName = "snack2";
		final String args = "-templateFile=src/main/resources/tableList.ftl"
				+ schema + "-outputFile=" + destinationDir + fileName;
		SpringTableMetaDataFTLFormGenerator.main(args.split(" "));
		// TODO comapre output to input
	}

	// TODO fix this test
	@Test
	public void test3() throws SQLException, IOException, TemplateException {
		fileName = "snack3";
		final String args = "-templateFile=src/main/resources/SimpleInputForm.ftl"
				+ schema + "-outputFile=" + destinationDir + fileName;
		SpringTableMetaDataFTLFormGenerator.main(args.split(" "));
		// TODO compare output to input
	}

	// TODO fix this test
	@Test
	public void test4() throws SQLException, IOException, TemplateException {
		fileName = "product.ftl";
		final String args = "-templateFile=src/main/resources/SimpleInputForm.ftl"
				+ schema + "-outputFile=" + destinationDir + fileName;
		SpringTableMetaDataFTLFormGenerator.main(args.split(" "));
	}

	// TODO fix this test
	@Test
	public void test5() throws SQLException, IOException, TemplateException {
		fileName = "productInputForm.ftl";
		final String args = "-templateFile=src/main/resources/MetaDataInputFormTemplate.ftl"
				+ schema
				+ "-outputFile="
				+ destinationDir
				+ fileName
				+ " -htmlFormAction=/myContext/myServlet/myController/myView.html -htmlFormMethod=post -htmlTableClass=classtable";
		SpringTableMetaDataFTLFormGenerator.main(args.split(" "));
	}

	// TODO fix this test
	@Test
	public void test6() throws SQLException, IOException, TemplateException {
		fileName = "productInputForm2.ftl";
		final String args = "-templateFile=src/main/resources/MetaDataInputFormTemplate.ftl"
				+ schema
				+ "-outputFile="
				+ destinationDir
				+ fileName
				+ " -htmlFormAction=/myContext/myServlet/myController/myView.html"
				+ " -htmlFormMethod=post" + " -htmlTableClass=classtable";
		SpringTableMetaDataFTLFormGenerator.main(args.split(" "));
	}

	// TODO fix this test
	@Test
	public void test7() throws SQLException, IOException, TemplateException {
		// TODO this should be in resources
		fileName = "productInputForm3.ftl";
		final String args = "-templateFile=src/main/resources/MetaDataInputFormTemplate.ftl"
				+ schema
				+ "-outputFile="
				+ destinationDir
				+ fileName
				+ " -htmlFormAction=/myContext/myServlet/myController/myView.html"
				+ " -htmlFormMethod=post" + " -htmlTableClass=classtable";
		SpringTableMetaDataFTLFormGenerator.main(args.split(" "));
	}

	// TODO fix this test
	@Test
	public void test8() throws SQLException, IOException, TemplateException {
		fileName = "productInputFormTest8.ftl";
		final String args = "-templateFile=src/main/resources/MetaDataInputFormTemplate.ftl"
				+ schema
				+ "-outputFile="
				+ destinationDir
				+ fileName
				+ " -htmlFormAction=/myContext/myServlet/myController/myView.html"
				+ " -htmlFormMethod=post" + " -htmlTableClass=classtable";
		SpringTableMetaDataFTLFormGenerator.main(args.split(" "));
	}

	// TODO fix this test
	@Test
	public void test9() throws SQLException, IOException, TemplateException {
		fileName = "productList.ftl";
		final String args = "-templateFile=src/main/resources/MetaDataListTemplate.ftl"
				+ schema
				+ "-outputFile="
				+ destinationDir
				+ fileName
				+ " -htmlTableClass=classtable";
		SpringTableMetaDataFTLFormGenerator.main(args.split(" "));
	}

}
