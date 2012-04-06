package org.javautil.dataset;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.javautil.dataset.csv.DatasetMetadataMarshallerCsv;
import org.javautil.file.FileHelper;

public abstract class BaseTest {

	private final Logger logger = Logger.getLogger(getClass());

	public void assertExpected(final Dataset dataset) throws IOException {
		final StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
		final String writeExpected = System.getenv("WRITE_EXPECTED_DATA");
		if ("TRUE".equals(writeExpected)) {

			writeExpected(dataset, ste);
		} else {
			ensureExpected(dataset, ste);
		}
	}

	void ensureExpected(final Dataset dataset, final StackTraceElement ste)
			throws IOException {
		final String expectedfileName = getExpectedDataFileName(ste);
		final String actualFileName = getActualDataFileName(ste);

		final String expectedDataFileName = "src/test/resources/"
				+ expectedfileName + ".data.csv";
		final String expectedMetadataFileName = "src/test/resources/"
				+ expectedfileName + ".meta.csv";
		final String actualDataFileName = "src/test/resources/"
				+ actualFileName + ".data.csv";
		final String actualMetadataFileName = "src/test/resources/"
				+ actualFileName + ".meta.csv";
		// logger.debug("writing to " + expectedDataFileName);
		// logger.debug("writing to " + expectedMetadataFileName);
		final FileOutputStream dataOut = new FileOutputStream(
				actualDataFileName);
		final FileOutputStream metaOut = new FileOutputStream(
				actualMetadataFileName);

		// DatasetMetadataMarshallerCsv marshaller = new
		// DatasetMetadataMarshallerCsv();
		DatasetMetadataMarshallerCsv.write(dataset, dataOut, metaOut);
		dataOut.close();
		metaOut.close();
		new FileInputStream(actualMetadataFileName);
		new FileInputStream(expectedMetadataFileName);
		FileHelper.fileContentsMatch(new File(expectedMetadataFileName),
				new File(actualMetadataFileName));
		FileHelper.fileContentsMatch(new File(expectedDataFileName), new File(
				actualDataFileName));
		// InputStreamComparator comparator = new InputStreamComparator();
		// Assert.assertEquals(0,comparator.compare(expectedIn,actualIn));
		// actualIn.close();
		// expectedIn.close();
		//
		// actualIn = new FileInputStream(actualDataFileName);
		// expectedIn = new FileInputStream(expectedDataFileName);
		//
		// Assert.assertEquals(0,comparator.compare(expectedIn,actualIn));
		// actualIn.close();
		// expectedIn.close();
	}

	void writeExpected(final Dataset dataset, final StackTraceElement ste)
			throws IOException {
		final String fileName = getExpectedDataFileName(ste);

		final String dataFileName = "src/test/resources/" + fileName
				+ ".data.csv";
		final String metadataFileName = "src/test/resources/" + fileName
				+ ".meta.csv";
		logger.debug("writing to " + dataFileName);
		logger.debug("writing to " + metadataFileName);
		final FileOutputStream dataOut = new FileOutputStream(dataFileName);
		final FileOutputStream metaOut = new FileOutputStream(metadataFileName);

		// DatasetMetadataMarshallerCsv marshaller = new
		// DatasetMetadataMarshallerCsv();
		DatasetMetadataMarshallerCsv.write(dataset, dataOut, metaOut);
		dataOut.close();
		metaOut.close();
	}

	public void assertExpected(final ColumnMetadata columnMetadata,
			final StackTraceElement ste) {

	}

	String getExpectedDataFileName(final StackTraceElement invoker) {
		final String className = invoker.getClassName();
		if (logger.isDebugEnabled()) {
			logger.debug("className is " + className);
		}
		// final String simpleClassName = className.replaceAll(".*\\.", "");
		// final String classMethod = simpleClassName + "."
		// + invoker.getMethodName();
		final String classMethod = className + "." + invoker.getMethodName();
		final String fileName = classMethod + ".expected";
		if (logger.isDebugEnabled()) {
			logger.debug("expected file " + fileName);
		}
		return fileName;
	}

	String getActualDataFileName(final StackTraceElement invoker) {
		final String className = invoker.getClassName();
		if (logger.isDebugEnabled()) {
			logger.debug("className is " + className);
		}

		final String classMethod = className + "." + invoker.getMethodName();
		final String fileName = classMethod + ".actual";
		if (logger.isDebugEnabled()) {
			logger.debug("expected file " + fileName);
		}
		return fileName;
	}

}
