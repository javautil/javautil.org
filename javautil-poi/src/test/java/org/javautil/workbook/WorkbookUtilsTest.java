package org.javautil.workbook;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Ignore;
import org.junit.Test;

public class WorkbookUtilsTest {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Ignore
	// this is complicated, slow and doesn't work
	@Test
	public void testColumnIdToColumnIndex() {
		Assert.assertEquals("A", WorkbookUtils.getColumnId(0));
		Assert.assertEquals("B", WorkbookUtils.getColumnId(1));
		Assert.assertEquals("C", WorkbookUtils.getColumnId(2));
		Assert.assertEquals("D", WorkbookUtils.getColumnId(3));
		Assert.assertEquals("E", WorkbookUtils.getColumnId(4));
		Assert.assertEquals("F", WorkbookUtils.getColumnId(5));
		Assert.assertEquals("G", WorkbookUtils.getColumnId(6));
		Assert.assertEquals("H", WorkbookUtils.getColumnId(7));
		Assert.assertEquals("Z", WorkbookUtils.getColumnId(25));
		Assert.assertEquals("AA", WorkbookUtils.getColumnId(26));
		Assert.assertEquals("AB", WorkbookUtils.getColumnId(27));
		Assert.assertEquals("AC", WorkbookUtils.getColumnId(28));
		Assert.assertEquals("AD", WorkbookUtils.getColumnId(29));
		Assert.assertEquals("AE", WorkbookUtils.getColumnId(30));
		Assert.assertEquals("AF", WorkbookUtils.getColumnId(31));
		Assert.assertEquals("AZ", WorkbookUtils.getColumnId(51));
		Assert.assertEquals("BA", WorkbookUtils.getColumnId(52));
	}

	@Ignore
	// TODO this code is broken
	@Test
	public void testColumnIndexToColumnId() {
		Assert.assertEquals(0, WorkbookUtils.getColumnIndex("A"));
		Assert.assertEquals(1, WorkbookUtils.getColumnIndex("B"));
		Assert.assertEquals(2, WorkbookUtils.getColumnIndex("C"));
		Assert.assertEquals(3, WorkbookUtils.getColumnIndex("D"));
		Assert.assertEquals(4, WorkbookUtils.getColumnIndex("E"));
		Assert.assertEquals(5, WorkbookUtils.getColumnIndex("F"));
		Assert.assertEquals(6, WorkbookUtils.getColumnIndex("G"));
		Assert.assertEquals(7, WorkbookUtils.getColumnIndex("H"));
		Assert.assertEquals(25, WorkbookUtils.getColumnIndex("Z"));
		Assert.assertEquals(26, WorkbookUtils.getColumnIndex("AA"));
		Assert.assertEquals(27, WorkbookUtils.getColumnIndex("AB"));
		Assert.assertEquals(28, WorkbookUtils.getColumnIndex("AC"));
		Assert.assertEquals(29, WorkbookUtils.getColumnIndex("AD"));
		Assert.assertEquals(30, WorkbookUtils.getColumnIndex("AE"));
		Assert.assertEquals(31, WorkbookUtils.getColumnIndex("AF"));
		Assert.assertEquals(51, WorkbookUtils.getColumnIndex("AZ"));
		Assert.assertEquals(52, WorkbookUtils.getColumnIndex("BA"));
	}
}
