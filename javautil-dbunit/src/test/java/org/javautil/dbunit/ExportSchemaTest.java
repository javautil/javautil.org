package org.javautil.dbunit;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.dbunit.DatabaseUnitException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ExportSchemaContext.xml" })
public class ExportSchemaTest {
	
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private ExportSchema exportSchema;

	@Test
	public void test() throws FileNotFoundException, SQLException, DatabaseUnitException, IOException, ClassNotFoundException {
		assertNotNull(exportSchema);
		File outputFile = new File("target/exportSchemaTest.actual");
		logger.info("ouputFile " + outputFile);
		exportSchema.setOutputFile(outputFile);
		exportSchema.fullExport();
	}
}
