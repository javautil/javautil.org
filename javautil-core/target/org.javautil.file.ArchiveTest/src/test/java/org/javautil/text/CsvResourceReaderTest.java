package org.javautil.text;

import org.junit.Test;

public class CsvResourceReaderTest {

	@Test
	public void test1() {
		new CsvResourceReader(this.getClass(),
				"/org/javautil/text/SampleColumnMetadata.csv");
	}

}
