package org.javautil.jdbc.metadata.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.javautil.file.FileComparator;
import org.javautil.jdbc.metadata.Schema;
import org.javautil.jdbc.metadata.containers.DatabaseTables;
import org.javautil.jdbc.metadata.renderer.SchemaToXml;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/test/resources/H2-target.xml" })
public class JdbcSchemaDAOTest {
	@Autowired
	private DataSource dataSource;
	/**
	 * Set to BOOLEAN.TRUE to create the actual file, review and then revert
	 */
	private final Boolean createExpected = Boolean.FALSE;
	private final String expectedDir = "testdata/expected/";
	private final String actualDir = "target/actual/";
	private final Logger logger = Logger.getLogger(getClass());

	public File getOutputFile(String name, boolean createExpected) {
		String pathName;
		String dirName = createExpected ? expectedDir : actualDir;
		File dirPath = new File(dirName);
		dirPath.mkdirs();
		pathName = dirName + getClass().getName() + "." + name;
		File file = new File(pathName);
		logger.debug("File name: '" + file.getPath() + "'");
		return new File(pathName);
	}

	// TODO this convenience should be moved to source
	// TODO in SchemaToElement
	private void dump(Schema schema, String name, boolean createExpected)
			throws IOException {

		SchemaToXml ste = new SchemaToXml(schema, true);
		String xml = ste.getAsString(schema, name, true);
		FileWriter os = new FileWriter(getOutputFile(name, createExpected));
		os.write(xml);
		os.flush();
		os.close();
	}

	@Test
	public void test() throws SQLException, IOException {
		String fileNamePart = "test";
		Connection conn = dataSource.getConnection();
		SchemaDaoJdbc dao = new SchemaDaoJdbc(conn, "PUBLIC", "TEST", "%");
		dao.populateTables();
		Schema schema = dao.getSchema();
		DatabaseTables tables = schema.getTables();
		assertNotNull(tables);
		assertEquals(7, tables.size());

		dump(schema, fileNamePart, createExpected);
		if (!createExpected) {
			FileComparator fc = new FileComparator();
			File expected = getOutputFile(fileNamePart, false);
			File actual = getOutputFile(fileNamePart, true);
			int result = fc.compare(expected, actual);
			assertEquals(0, result);
		}
	}
}
