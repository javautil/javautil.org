package org.javautil.document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.javautil.dataset.ColumnMetadata;
import org.javautil.dataset.DataType;
import org.javautil.dataset.Dataset;
import org.javautil.dataset.DatasetMetadataImpl;
import org.javautil.dataset.MatrixDataset;
import org.junit.Before;
import org.junit.Test;

public class BreakHelperTest {

	private BreakHelper breaks;

	@Before
	public void setup() throws Exception {
		breaks = new BreakHelper();
		final Map<String, Integer> mapping = new HashMap<String, Integer>();
		mapping.put("yr", 0);
		mapping.put("per", 1);
		mapping.put("mth", 2);
		mapping.put("invoice_no", 3);
		mapping.put("customer_id", 4);
		mapping.put("customer_descr", 4);
		mapping.put("product_id", 5);
		mapping.put("product_descr", 6);
		mapping.put("cases", 7);
		mapping.put("dollars", 8);
		breaks.setColumnNameIndex(mapping);
		breaks.setBreaks(new ArrayList<String>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				add("yr");
				add("per");
				add("mth");
			}
		});
		breaks.afterPropertiesSet();
	}

	@Test
	public void testGetBreakLevelDataset() throws Exception {
		breaks = new BreakHelper();
		breaks.setLowerCase(true);
		final DatasetMetadataImpl metadata = new DatasetMetadataImpl();
		metadata.addColumn(new ColumnMetadata("yr", 1, DataType.INTEGER, 8, 0,
				null));
		metadata.addColumn(new ColumnMetadata("per", 2, DataType.INTEGER, 1, 0,
				null));
		metadata.addColumn(new ColumnMetadata("mth", 3, DataType.INTEGER, 2, 0,
				null));
		metadata.addColumn(new ColumnMetadata("dollars", 3, DataType.DOUBLE,
				14, 2, null));
		final Dataset dataset = new MatrixDataset(metadata);
		breaks.setDataset(dataset);
		breaks.setBreaks(new ArrayList<String>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				add("yr");
				add("per");
				add("mth");
			}
		});
		breaks.afterPropertiesSet();
		final Object[] currentRow = new Object[] { 2008, 4, 12, 35.50 };
		final Object[] previousRow = new Object[] { 2008, 4, 12, 42.97 };
		final int breakLevel = breaks.getBreakLevel(previousRow, currentRow);
		Assert.assertEquals(-1, breakLevel);
	}

	@Test
	public void testGetBreakLevelNoBreak() {
		final Object[] currentRow = new Object[] { 2008, 4, 12, "INV_90210",
				"12345", "Seven Eleven", "98765", "Mini Donuts, 6pk", 1, 35.50 };
		final Object[] previousRow = new Object[] { 2008, 4, 12, "INV_90210",
				"12345", "Seven Eleven", "98765", "Slim Jim, 10\"", 1, 42.97 };
		final int breakLevel = breaks.getBreakLevel(previousRow, currentRow);
		Assert.assertEquals(-1, breakLevel);
	}

	@Test
	public void testGetBreakLevelOnSameToStringButDifferentDataType() {
		// 2008 is a String and an Integer
		final Object[] currentRow = new Object[] { "2008", 4, 12, "INV_90210",
				"12345", "Seven Eleven", "98765", "Mini Donuts, 6pk", 1, 35.50 };
		final Object[] previousRow = new Object[] { 2008, 4, 12, "INV_90210",
				"12345", "Seven Eleven", "98765", "Slim Jim, 10\"", 1, 42.97 };
		final int breakLevel = breaks.getBreakLevel(previousRow, currentRow);
		Assert.assertEquals(1, breakLevel);
	}

	@Test
	public void testGetBreakLevelBreakOnFirstColumn() {
		final Object[] currentRow = new Object[] { 2007, 4, 11, "INV_90210",
				"12345", "Seven Eleven", "98765", "Mini Donuts, 6pk", 1, 35.50 };
		final Object[] previousRow = new Object[] { 2008, 4, 12, "INV_90210",
				"12345", "Seven Eleven", "98765", "Slim Jim, 10\"", 1, 42.97 };
		final int breakLevel = breaks.getBreakLevel(previousRow, currentRow);
		Assert.assertEquals(1, breakLevel);
	}

	@Test
	public void testGetBreakLevelBreakOnFirstSecondAndThirdColumns() {
		final Object[] currentRow = new Object[] { 2007, 3, 8, "INV_90210",
				"12345", "Seven Eleven", "98765", "Mini Donuts, 6pk", 1, 35.50 };
		final Object[] previousRow = new Object[] { 2008, 4, 12, "INV_90210",
				"12345", "Seven Eleven", "98765", "Slim Jim, 10\"", 1, 42.97 };
		final int breakLevel = breaks.getBreakLevel(previousRow, currentRow);
		Assert.assertEquals(1, breakLevel);
	}

	@Test
	public void testGetBreakLevelBreakOnSecondAndThirdColumn() {
		final Object[] currentRow = new Object[] { 2008, 3, 8, "INV_90210",
				"12345", "Seven Eleven", "98765", "Mini Donuts, 6pk", 1, 35.50 };
		final Object[] previousRow = new Object[] { 2008, 4, 12, "INV_90210",
				"12345", "Seven Eleven", "98765", "Slim Jim, 10\"", 1, 42.97 };
		final int breakLevel = breaks.getBreakLevel(previousRow, currentRow);
		Assert.assertEquals(2, breakLevel);
	}

	@Test
	public void testGetBreakLevelBreakOnThirdColumn() {
		final Object[] currentRow = new Object[] { 2008, 4, 11, "INV_90210",
				"12345", "Seven Eleven", "98765", "Mini Donuts, 6pk", 1, 35.50 };
		final Object[] previousRow = new Object[] { 2008, 4, 12, "INV_90210",
				"12345", "Seven Eleven", "98765", "Slim Jim, 10\"", 1, 42.97 };
		final int breakLevel = breaks.getBreakLevel(previousRow, currentRow);
		Assert.assertEquals(3, breakLevel);
	}
}
