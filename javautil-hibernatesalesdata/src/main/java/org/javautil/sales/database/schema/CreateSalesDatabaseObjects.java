package org.javautil.sales.database.schema;

import org.apache.log4j.Logger;
import org.javautil.hibernate.CreateDatabaseSchema;

public class CreateSalesDatabaseObjects {
	private static Logger logger = Logger
			.getLogger(CreateSalesDatabaseObjects.class);

	// TODO
	// TODO this should be dumping the full help
	// TODO set the bean properties
	public static void createDatabaseObjects() {
		final String argString = "-mappingsDirectory=./target/classes/org/javautil/sales/dto -config=etc/hibernate.cfg.xml -createDatabaseObjects -outputFile /tmp/outputfile.sql";
		CreateDatabaseSchema.main(argString.split(" "));
		logger.info("object creation complete");
	}

	public static void main(final String args[]) {
		createDatabaseObjects();
	}

}
