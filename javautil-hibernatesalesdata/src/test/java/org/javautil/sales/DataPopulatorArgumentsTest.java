package org.javautil.sales;

import org.javautil.sales.populate.DataPopulatorArguments;
import org.junit.Test;

public abstract class DataPopulatorArgumentsTest {
	// todo implement the system exit security bas
	@Test
	public void test1() {
		// todo bcm is the class resolver now broken ??
		final String cmd = "-salesp 10 ";
		final DataPopulatorArguments dpa = new DataPopulatorArguments();
		dpa.processArguments(cmd.split(" "));
	}

	@Test
	public void test2() {
		final String cmd = "-snake 10 ";
		final DataPopulatorArguments dpa = new DataPopulatorArguments();
		dpa.processArguments(cmd.split(" "));
	}

	// public static void main(String[] args) {
	// DataPopulatorArgumentsTest test = new DataPopulatorArgumentsTest();
	// test.test1();
	// }
}
