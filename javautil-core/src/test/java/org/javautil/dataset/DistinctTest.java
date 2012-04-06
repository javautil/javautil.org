package org.javautil.dataset;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.javautil.collections.Tuple;
import org.javautil.dataset.operations.Distinct;
import org.javautil.dataset.testdata.TrailingNullsDataset;
import org.junit.Test;

public class DistinctTest {
	// todo need lots of more tests
	@Test
	public void test1() {
		final Dataset<Object> ds = TrailingNullsDataset.getDataset();
		final Set<Tuple<?>> distinctState = new Distinct().getDistinctTuples(
				ds, "STATE");
		final Set<Tuple<Comparable<?>>> expected = new HashSet<Tuple<Comparable<?>>>();
		expected.add(new Tuple<Comparable<?>>(new Comparable[] { "TX" }));
		expected.add(new Tuple<Comparable<?>>(new Comparable[] { null }));
		assertTrue(expected.equals(distinctState));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test2() {
		final Dataset ds = TrailingNullsDataset.getDataset();
		final Set<Tuple<?>> distinctStateCity = new Distinct()
				.getDistinctTuples(ds, "STATE", "CITY");
		final Set<Tuple<Comparable<?>>> expected = new HashSet<Tuple<Comparable<?>>>();
		expected.add(new Tuple<Comparable<?>>(
				new Comparable[] { "TX", "DALLAS" }));
		expected.add(new Tuple<Comparable<?>>(new Comparable[] { "TX",
				"HOUSTON" }));
		expected.add(new Tuple<Comparable<?>>(new Comparable[] { "TX", null }));
		expected.add(new Tuple<Comparable<?>>(new Comparable[] { null, null }));
		assertTrue(expected.equals(distinctStateCity));
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void test3() {
		TrailingNullsDataset.getDataset();
		new Distinct().getDistinctTuples(null, "STATE", "CITY");
		// TODO needs tets
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void test4() {
		final Dataset ds = TrailingNullsDataset.getDataset();
		new Distinct().getDistinctTuples(ds, null);
		// TODO needs test

	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void test5() {
		final Dataset ds = TrailingNullsDataset.getDataset();
		new Distinct().getDistinctTuples(ds);
		// TODO needs test

	}
}
