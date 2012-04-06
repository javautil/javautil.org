package org.javautil.exceptionprocessing;

import org.javautil.hibernate.CreateDatabaseSchema;
import org.junit.Test;

public class CreateDatabaseSchemaTest {
	// TODO this should have no dependency on condition identification

	// java -cp hibernate_classpaths org.hibernate.tool.hbm2ddl.SchemaExport
	// options mapping_files
	// TODO this program is unnessary see above
	// unless we are just testing
	// http://docs.jboss.org/hibernate/core/3.3/reference/en/html/toolsetguide.html#toolsetguide-s1-2
	@Test
	public void test() {
		final String argString = "-outputFile target/CreateDatabaseSchemaTest.output -mappingsDirectory=../javautil-conditionidentification/src/main/resource/org/javautil/exceptionprocessing/dto -configurationFile ../javautil-conditionidentification/etc/hibernate.cfg.xml -createDatabaseObjects";
		CreateDatabaseSchema.main(argString.split(" "));

	}

}