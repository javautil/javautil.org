package org.javautil.address.service;

import static org.junit.Assert.assertEquals;

import org.javautil.commandline.CommandLineHandler;
import org.junit.Test;

public class AddressValidationServiceArgumentsTest {

	@Test
	public void test1() {
		final String[] parms = { "-runNbr", "1", "-uspsAcct", "INVALID",
				"-noDatasource" };

		final AddressValidationServiceArguments args = new AddressValidationServiceArguments();
		final CommandLineHandler clh = new CommandLineHandler(args);
		clh.evaluateArguments(parms);
		assertEquals(new Integer(1), args.getRunNbr());
		assertEquals("INVALID", args.getUspsAcct());

	}

	@Test
	public void testFile() {
		final String[] parms = { "-runNbr", "1", "-uspsAcct", "INVALID",
				"-noDatasource", "-outputFileName", "outputFile",
				"-inputFileName", "inputFile" };

		final AddressValidationServiceArguments args = new AddressValidationServiceArguments();
		final CommandLineHandler clh = new CommandLineHandler(args);
		clh.evaluateArguments(parms);
		assertEquals(new Integer(1), args.getRunNbr());
		assertEquals("INVALID", args.getUspsAcct());
		assertEquals("outputFile", args.getOutputFileName());
		assertEquals("inputFile", args.getInputFileName());
	}

}
