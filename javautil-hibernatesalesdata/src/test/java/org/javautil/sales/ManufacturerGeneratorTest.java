package org.javautil.sales;

import org.javautil.sales.populate.Manufacturer;
import org.javautil.sales.populate.ManufacturerGenerator;
import org.junit.Test;

public class ManufacturerGeneratorTest {

	// todo review the absurdly large numbers used here?
	@Test
	public void test1() {
		final ManufacturerGenerator gen = new ManufacturerGenerator();
		gen.generateManufacturers();
		for (int i = 1; i < 1000; i++) {
			final Manufacturer m = gen.getManufacturer();
			m.incrementReference();
		}
		// for (Manufacturer m : gen.getManufacturersOrderedByCumm().values() )
		// {
		// String message = m.toString();
		// System.out.println(message);
		// }
	}

	// public static void main(String[] args) {
	// ManufacturerGeneratorTest mgt = new ManufacturerGeneratorTest();
	// mgt.test1();
	// }
	// todo last manufacturer is never referenced
}
