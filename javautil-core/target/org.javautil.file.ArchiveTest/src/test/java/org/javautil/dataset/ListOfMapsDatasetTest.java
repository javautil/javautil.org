package org.javautil.dataset;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class ListOfMapsDatasetTest {

	private ListOfMapsDataset dataset = null;

	@Before
	public void before() throws Exception {
		dataset = getListOfMapsDataset();
	}

	@Test
	public void testGetRow() throws Exception {
		final DatasetIterator iterator = dataset.getDatasetIterator();
		Assert.assertTrue(iterator.next());
	}

	@Test
	public void testSingleColumnGet() throws Exception {
		final Map<String, Object> row = dataset.get(0);
		Assert.assertEquals("fifi", row.get("name"));
	}

	@Test
	public void testStringColumnIterator() throws Exception {
		final DatasetIterator iterator = dataset.getDatasetIterator();
		iterator.next();
		Assert.assertEquals("fifi", iterator.getObject(0));
		iterator.next();
		Assert.assertEquals("socks", iterator.getObject(0));
		iterator.next();
		Assert.assertEquals("dolche", iterator.getObject(0));
		iterator.next();
		Assert.assertEquals("chewy", iterator.getObject(0));
		Assert.assertFalse(iterator.hasNext());
	}

	@Test
	public void testStringColumnIteratorByColumnName() throws Exception {
		final DatasetIterator iterator = dataset.getDatasetIterator();
		iterator.next();
		Assert.assertEquals("fifi", iterator.getObject("name"));
		iterator.next();
		Assert.assertEquals("socks", iterator.getObject("name"));
		iterator.next();
		Assert.assertEquals("dolche", iterator.getObject("name"));
		iterator.next();
		Assert.assertEquals("chewy", iterator.getObject("name"));
		Assert.assertFalse(iterator.hasNext());
	}

	@Test
	public void testIntegerColumnIterator() throws Exception {
		final DatasetIterator iterator = dataset.getDatasetIterator();
		iterator.next();
		Assert.assertEquals(3, iterator.getObject(2));
		iterator.next();
		Assert.assertEquals(14, iterator.getObject(2));
		iterator.next();
		Assert.assertEquals(2, iterator.getObject(2));
		iterator.next();
		Assert.assertEquals(6, iterator.getObject(2));
		Assert.assertFalse(iterator.hasNext());
	}

	@Test
	public void testDoubleColumnIterator() throws Exception {
		final DatasetIterator iterator = dataset.getDatasetIterator();
		iterator.next();
		Assert.assertEquals(11.25, iterator.getObject(1));
		iterator.next();
		Assert.assertEquals(13.5, iterator.getObject(1));
		iterator.next();
		Assert.assertEquals(6.125, iterator.getObject(1));
		iterator.next();
		Assert.assertEquals(65.33333, iterator.getObject(1));
		Assert.assertFalse(iterator.hasNext());
	}

	@Test
	public void testDatasetConstructor() throws Exception {
		dataset = new ListOfMapsDataset(dataset);
		testGetRow();
		testSingleColumnGet();
		testStringColumnIterator();
		testStringColumnIteratorByColumnName();
	}

	@Test
	public void testDatasetMetadataConstructor() throws Exception {
		dataset = new ListOfMapsDataset(dataset, dataset.getMetadata());
		testGetRow();
		testSingleColumnGet();
		testStringColumnIterator();
		testStringColumnIteratorByColumnName();
	}

	private static ListOfMapsDataset getListOfMapsDataset() throws Exception {
		return new ListOfMapsDataset(getTestDataset());
	}

	private static Dataset getTestDataset() throws ParseException {
		final DatasetMetadataImpl meta = new DatasetMetadataImpl();
		meta.addColumn(new ColumnMetadata("name", 0, DataType.STRING, null,
				null, 32, null, null, null));
		meta.addColumn(new ColumnMetadata("weight", 1, DataType.DOUBLE, 9, 9,
				null, null, null, null));
		meta.addColumn(new ColumnMetadata("age", 2, DataType.INTEGER, 9, null,
				null, null, null, null));
		meta.addColumn(new ColumnMetadata("birthday", 3, DataType.DATE, null,
				null, null, null, null, null));
		final MatrixDataset dataset = new MatrixDataset(meta);
		dataset.addRow(toList("fifi", 11.25, 3, toDate("01/09/2005")));
		dataset.addRow(toList("socks", 13.5, 14, toDate("02/21/1994")));
		dataset.addRow(toList("dolche", 6.125, 2, toDate("08/02/2006")));
		dataset.addRow(toList("chewy", 65.33333, 6, toDate("08/02/2002")));
		return dataset;
	}

	public static Date toDate(final String date) throws ParseException {
		return new SimpleDateFormat("MM/dd/yyyy").parse(date);
	}

	public static ArrayList<Object> toList(final Object... objects) {
		final ArrayList<Object> list = new ArrayList<Object>();
		for (final Object object : objects) {
			list.add(object);
		}
		return list;
	}

}
