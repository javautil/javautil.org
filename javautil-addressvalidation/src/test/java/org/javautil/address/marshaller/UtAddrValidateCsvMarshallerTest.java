/**
 * 
 */
package org.javautil.address.marshaller;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.log4j.Logger;
import org.javautil.address.USAddressStandardizer;
import org.javautil.address.UtAddressValidate;
import org.javautil.address.beans.AddressBean;
import org.javautil.address.interfaces.AuthoritativeAddress;
import org.javautil.address.interfaces.StandardizedAddress;
import org.javautil.address.service.usps.UspsServiceRequestHelper;
import org.javautil.address.usps.AddressValidationException;
import org.javautil.address.usps.UspsValidationTestData;
import org.javautil.file.InputStreamComparator;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author jjs
 * 
 */
public class UtAddrValidateCsvMarshallerTest {

	private final USAddressStandardizer standardizer = new USAddressStandardizer();
	private final UspsServiceRequestHelper authoritiver = new UspsServiceRequestHelper();
	private final UtAddrValidateCsvMarshaller marshaller = new UtAddrValidateCsvMarshaller();
	private static String pathName = "target/data";
	private final String filePathNameExpected = "src/test/resources/UtAddrValidateCsvMarshallerTest.expected.csv";
	private final String fileName2 = "UtAddrValidateCsvMarshallerTest.2.csv";
	private final Logger logger = Logger.getLogger(getClass());

	@BeforeClass
	public static void beforeClass() {
		File outputDir = new File(pathName);
		outputDir.mkdirs();
	}

	String getFilePath(String fileName) {
		fileName = pathName + "/" + fileName;
		return fileName;
	}

	@Test
	public void Test1() throws AddressValidationException,
			FileNotFoundException {
		// populateBean();
		UtAddressValidate reconstructedBean = null;
		// logger.debug("writing to file " + getFilePath(fileNameExpected));
		// createCsvFromBean(constructedBean, filePathNameExpected);
		// Create a bean from an existing file
		reconstructedBean = createBeanFromCsv(filePathNameExpected);
		// marshall the file to CSV
		createCsvFromBean(reconstructedBean, getFilePath(fileName2));
		FileInputStream fis1 = new FileInputStream(filePathNameExpected);
		FileInputStream fis2 = new FileInputStream(getFilePath(fileName2));
		// compare the two files
		InputStreamComparator comparator = new InputStreamComparator();
		int result = comparator.compare(fis1, fis2);
		assertEquals(0, result);

	}

	void createCsvFromBean(UtAddressValidate bean, String fileName)
			throws AddressValidationException {
		marshaller.setOutputFileName(fileName);
		marshaller.write(bean);
		marshaller.close();
	}

	UtAddressValidate populateBean() throws AddressValidationException {
		AddressBean raw = UspsValidationTestData.getAddress1();
		StandardizedAddress standard = standardizer.standardize(raw);
		AuthoritativeAddress authoritative = authoritiver.process(standard);
		StandardizedAddress authStandard = standardizer
				.standardize(authoritative);
		UtAddressValidate request = new UtAddressValidate();
		request.setRawAddress(raw);
		request.setStdAddress(standard);
		request.setAuthAddress(authoritative);
		request.setStdAuthAddress(authStandard);
		request.setUtAddrValidateNbr(1L);
		request.setRunNbr(2);
		request.setDataSrcNbr(3);
		request.setDataSrcPk(4L);
		request.setName("Test Company");

		return request;
	}

	UtAddressValidate createBeanFromCsv(String fileName) {
		marshaller.setInputFileName(fileName);
		marshaller.getHeadings(); // throw away
		UtAddressValidate record = marshaller.getNext();
		return record;
	}

	public void assertEqualRecs(UtAddressValidate a, UtAddressValidate b) {

	}
}
