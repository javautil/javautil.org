package org.javautil.dataset;

import org.junit.Test;

public class MappedDatasetRowTest {

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void constructorTest1() {
		new MappedDatasetRow(null, new Object[0], Integer.valueOf(1));
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void constructorTest2() {
		new MappedDatasetRow(new Object[0], null, 2);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void constructorTest3() {
		new MappedDatasetRow(new Object[0], new Object[0], null);
	}

}
