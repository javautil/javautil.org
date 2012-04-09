package org.javautil.dataset;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.javautil.data.testdata.TestData;
import org.javautil.io.IOUtils;
import org.junit.Assert;
import org.springframework.core.io.DefaultResourceLoader;

/**
 * Validates the contents of the ByteArrayOutputStream match the contents of the
 * matching file for the invoking class and method.
 * 
 * For example, if MatrixDataSetTests.test1 call validateResult, then the
 * expected file would be: MatrixDataSetTests.test1.expected.csv
 * 
 * TODO throw this out.
 * 
 */
public class ResultValidator {
	public Logger logger = Logger.getLogger(getClass());

	String getTestDataFileName(final StackTraceElement invoker) {
		final String className = invoker.getClassName();
		logger.debug("className is " + className);
		final String simpleClassName = className.replaceAll(".*\\.", "");
		final String classMethod = simpleClassName + "."
				+ invoker.getMethodName();
		final String fileName = classMethod + ".expected.csv";
		if (logger.isDebugEnabled()) {
			logger.debug("expected file " + fileName);
		}
		return fileName;
	}

	public byte[] expectedResults(final StackTraceElement invoker)
			throws IOException {
		final String fileName = getTestDataFileName(invoker);

		final DefaultResourceLoader resolver = new DefaultResourceLoader(
				TestData.class.getClassLoader());
		final String pathName = "org/javautil/data/testdata/" + fileName;
		final InputStream inputStream = resolver.getResource(pathName)
				.getInputStream();
		logger.debug("testing against " + fileName);
		if (inputStream == null) {
			throw new IllegalArgumentException("expected result file: '"
					+ fileName + "' does not exist");
		}
		final ByteArrayOutputStream validBaos = new ByteArrayOutputStream();
		IOUtils.pump(inputStream, validBaos);
		inputStream.close();

		final byte[] expectedResults = validBaos.toByteArray();
		return expectedResults;
	}

	public void validateResult(final StackTraceElement invoker,
			final byte[] testArray) {
		try {
			final byte[] validArray = expectedResults(invoker);

			if (!Arrays.equals(testArray, validArray)) {
				// TODO restore
				// if (logger.isDebugEnabled()) {
				final StringBuilder b = new StringBuilder();
				b.append("expected:\n");
				b.append(new String(validArray));
				b.append("actual:\n");
				b.append(new String(testArray));
				b.toString();
				logger.debug(b.toString());
				// }
			}

			Assert.assertEquals(
					"Result size is different from expected result size.",
					validArray.length, testArray.length);

			Assert.assertTrue(
					"Result bytes are different from expected result bytes.",
					Arrays.equals(testArray, validArray));

		} catch (final IOException ioex) {
			Assert.fail(ioex.getMessage());
		}
	}

}
