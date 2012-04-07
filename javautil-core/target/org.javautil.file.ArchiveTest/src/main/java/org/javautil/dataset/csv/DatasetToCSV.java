package org.javautil.dataset.csv;

import java.io.File;
import java.io.IOException;

import org.javautil.dataset.Dataset;

/**
 * TODO document
 * 
 * This is for creating test output of datasets in unit testing.
 * 
 * 
 * 
 * 
 * @author jjs@dbexperts.com TODO what is this?
 * 
 */
public class DatasetToCSV {
	public static void writeDataset(final Dataset ds, final boolean writeDataset)
			throws IOException {
		if (!writeDataset) {
			throw new UnsupportedOperationException(
					"not supported at this time ");
		}
		final StackTraceElement[] callStack = Thread.currentThread()
				.getStackTrace();
		final String className = callStack[2].getClassName();
		final String methodName = callStack[2].getMethodName();
		final String fileName = "testData/" + className + "." + methodName
				+ ".csv";
		final File f = new File(new File(fileName).getParent());
		f.mkdirs();
		DatasetCsvMarshaller.writeToFileName(ds, fileName);
	}
}
