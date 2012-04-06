package org.javautil.jdbc.oracle;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.javautil.test.StandardTest;
import org.junit.Ignore;
import org.junit.Test;

public class TnsNamesTest extends StandardTest {

	private final TnsNames tnsNames = new TnsNames();

	// TODO fix this test
	@Ignore
	@Test
	public void testFile() {
		final File f = tnsNames.getOracleHomeTnsNamesFile();
		assertTrue(f.exists());
	}

	// public void testFile2() {
	// tnsNames.setTnsnamesFile(new File
	// ("src/test/resources/oracleHome/network/admin/tnsnames.ora"));
	// Collection<TnsnamesEntryBean> entries = tnsNames.getEntries();
	// }
}
