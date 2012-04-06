package org.javautil.jfm;

import java.io.IOException;
import java.sql.SQLException;

import org.javautil.jfm.mains.mvc.SpringTableMetaDataFTLFormGenerator;
import org.junit.BeforeClass;
import org.junit.Test;

import freemarker.template.TemplateException;

// TODO fix so that it doesn't exit using security
public class SpringTableMetaDataFormGeneratorTest {

	private static final String schema = " -dataSource=hr -schema=HR -table=EMPLOYEES ";

	@BeforeClass
	public static void beforeClass() {
		// TODO bad location
		System.setProperty("DATASOURCES_FILE",
				"src/test/resources/DataSources.xml");
	}

	// TODO fix this test
	@Test
	public void test1() throws SQLException, IOException, TemplateException {

		final String args = "-templateFile=src/main/resources/tableList.ftl"
				+ schema + "-outputFile=/tmp/snack";
		SpringTableMetaDataFTLFormGenerator.main(args.split(" "));
		// TODO what does this test
	}

	// TODO fix this test
	@Test
	public void test2() throws SQLException, IOException, TemplateException {
		final String args = "-templateFile=src/main/resources/tableList.ftl"
				+ schema + "-outputFile=/tmp/snack2";
		SpringTableMetaDataFTLFormGenerator.main(args.split(" "));
		// TODO comapre output to input
	}

	// TODO fix this test
	@Test
	public void test3() throws SQLException, IOException, TemplateException {
		final String args = "-templateFile=src/main/resources/SimpleInputForm.ftl"
				+ schema + "-outputFile=/tmp/snack3";
		SpringTableMetaDataFTLFormGenerator.main(args.split(" "));
		// TODO compare output to input
	}

	// TODO fix this test
	@Test
	public void test4() throws SQLException, IOException, TemplateException {
		final String args = "-templateFile=src/main/resources/SimpleInputForm.ftl"
				+ schema + "-outputFile=/tmp/vp_dst.ftl";
		SpringTableMetaDataFTLFormGenerator.main(args.split(" "));
	}

	// TODO fix this test
	@Test
	public void test5() throws SQLException, IOException, TemplateException {
		final String args = "-templateFile=src/main/resources/MetaDataInputFormTemplate.ftl"
				+ schema
				+ "-outputFile=target/examples/mvc/vs_dst_form.ftl"
				+ " -htmlFormAction=/myContext/myServlet/myController/myView.html -htmlFormMethod=post -htmlTableClass=classtable";
		SpringTableMetaDataFTLFormGenerator.main(args.split(" "));
	}

	// TODO fix this test
	@Test
	public void test6() throws SQLException, IOException, TemplateException {
		final String args = "-templateFile=src/main/resources/MetaDataInputFormTemplate.ftl"
				+ schema
				+ " -outputFile=target/examples/mvc/user_tran_cst_form.ftl"
				+ " -htmlFormAction=/myContext/myServlet/myController/myView.html"
				+ " -htmlFormMethod=post" + " -htmlTableClass=classtable";
		SpringTableMetaDataFTLFormGenerator.main(args.split(" "));
	}

	// TODO fix this test
	@Test
	public void test7() throws SQLException, IOException, TemplateException {
		// TODO this should be in resources
		final String args = "-templateFile=src/main/resources/MetaDataInputFormTemplate.ftl"
				+ schema
				+ " -outputFile=target/examples/mvc/user_cst_mod_rqst.ftl"
				+ " -htmlFormAction=/myContext/myServlet/myController/myView.html"
				+ " -htmlFormMethod=post" + " -htmlTableClass=classtable";
		SpringTableMetaDataFTLFormGenerator.main(args.split(" "));
		// TODO no test here
	}

	// TODO fix this test
	@Test
	public void test8() throws SQLException, IOException, TemplateException {
		final String args = "-templateFile=src/main/resources/MetaDataInputFormTemplate.ftl"
				+ schema
				+ " -outputFile=target/examples/mvc/ut_user_rpt_grp_form.ftl"
				+ " -htmlFormAction=/myContext/myServlet/myController/myView.html"
				+ " -htmlFormMethod=post" + " -htmlTableClass=classtable";
		SpringTableMetaDataFTLFormGenerator.main(args.split(" "));
	}

	// TODO fix this test
	@Test
	public void test9() throws SQLException, IOException, TemplateException {
		final String args = "-templateFile=src/main/resources/MetaDataListTemplate.ftl"
				+ schema
				+ " -outputFile=target/examples/mvc/ut_user_rpt_grp_list.ftl"
				+ " -htmlTableClass=classtable";
		SpringTableMetaDataFTLFormGenerator.main(args.split(" "));
	}

}
