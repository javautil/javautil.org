package org.javautil.mp3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;
import org.javautil.commandline.CommandLineHandler;
import org.javautil.mp3.persistence.Mp3OutputStreamPersistence;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Mp3MetadataExtractorCsvCli {
	private static final Logger logger = Logger
			.getLogger(Mp3MetadataExtractorCli.class);
	// TODO extractor needs to be populated via injection, how to do, from
	// commandline, configuration file, environment?

	private MetadataExtractor extractor = new MetadataExtractor();

	/**
	 * @param extractor
	 *            the extractor to set
	 */
	public void setExtractor(final MetadataExtractor extractor) {
		this.extractor = extractor;
	}

	/**
	 * @return the extractor
	 */
	public MetadataExtractor getExtractor() {
		return extractor;
	}

	private Mp3MetadataExtractorArguments arguments = new Mp3MetadataExtractorArguments();

	public Mp3MetadataExtractorCsvCli(
			final Mp3MetadataExtractorArguments arguments) {
		this.arguments = arguments;
		final File outputFile = arguments.getOutputFile();
		logger.info("writing to " + outputFile);
	}

	// // TODO this needs to be somewhere else
	// void instantiateObjects() {
	// final CsvPersister persister = new CsvPersister();
	// FileOutputStream fos;
	// try {
	// final File outputFile = arguments.getOutputFile();
	// if (outputFile == null) {
	// throw new IllegalArgumentException("outputFile is null");
	// }
	// fos = new FileOutputStream(arguments.getOutputFile());
	// } catch (final FileNotFoundException e) {
	// throw new RuntimeException(e);
	// }
	// final Writer w = new OutputStreamWriter(fos);
	// persister.setWriter(w);
	//
	// extractor.setPersistence(persister);
	// extractor.setDirectories(arguments.getDirectory());
	// }
	//
	// void process() {
	// extractor.process();
	// }

	// TODO make the program not terminate if it finds a zero length file.
	public static void main(final String[] args) throws FileNotFoundException {
		final Mp3MetadataExtractorArguments arguments = new Mp3MetadataExtractorArguments();
		final CommandLineHandler clh = new CommandLineHandler(arguments);
		ApplicationContext applicationContext = null;
		clh.evaluateArguments(args);
		if (arguments.getApplicationContextFile() != null) {
			final String fileName = arguments.getApplicationContextFile()
					.getPath();
			logger.debug("using context file: ' " + fileName + "'");
			applicationContext = new FileSystemXmlApplicationContext(fileName);

		} else {
			applicationContext = new ClassPathXmlApplicationContext(
					"ExtractorTestH2HibernateApplicationContext.xml"); // change
		}
		final MetadataExtractor extractor = (MetadataExtractor) applicationContext
				.getBean("extractor");
		extractor.setDirectories(arguments.getDirectory());
		final Mp3OutputStreamPersistence persistence = (Mp3OutputStreamPersistence) applicationContext
				.getBean("persistence");
		final FileOutputStream fos = new FileOutputStream(
				arguments.getOutputFile());
		persistence.setOutputStream(fos);
		extractor.process();
	}
}
