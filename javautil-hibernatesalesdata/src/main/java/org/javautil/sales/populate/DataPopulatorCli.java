package org.javautil.sales.populate;

import java.sql.SQLException;

import org.javautil.commandline.CommandLineHandler;
import org.javautil.sales.database.schema.CreateSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class DataPopulatorCli {
	@Autowired
	private CreateSchema schemaCreator;

	/**
	 * @return the schemaCreator
	 */
	public CreateSchema getSchemaCreator() {
		return schemaCreator;
	}

	/**
	 * @param schemaCreator
	 *            the schemaCreator to set
	 */
	public void setSchemaCreator(final CreateSchema schemaCreator) {
		this.schemaCreator = schemaCreator;
	}

	void createSchema() {
		if (schemaCreator == null) {
			throw new IllegalStateException("schemaCreator is null");
		}
		schemaCreator.createDatabaseSchema();
	}

	public static void main(final String[] args) throws SQLException {
		final DataPopulatorCliArguments arguments = new DataPopulatorCliArguments();
		final CommandLineHandler clih = new CommandLineHandler(arguments);
		clih.evaluateArguments(args);
		final DataPopulatorCli populator = new DataPopulatorCli();
		final String fileName = arguments.getApplicationContextFile().getPath();
		final FileSystemXmlApplicationContext springContext = new FileSystemXmlApplicationContext(
				fileName);
		populator.schemaCreator = (CreateSchema) springContext
				.getBean("schemaCreator");
		populator.createSchema();
		final DataPopulator dpc = (DataPopulator) springContext
				.getBean("dataPopulator");
		dpc.populate();
	}
}
