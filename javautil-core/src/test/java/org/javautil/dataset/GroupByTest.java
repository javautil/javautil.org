package org.javautil.dataset;

import java.util.Set;

import org.apache.log4j.Logger;
import org.javautil.dataset.testdata.TrailingNullsDataset;
import org.junit.Test;

// todo jjs this looks good from a visual check now we have to have the tests that verify 
public class GroupByTest extends BaseTest {
	// TODO need lots of more tests
	private static Logger logger = Logger.getLogger(GroupByTest.class);

	@Test
	public void test1() {
		final Dataset ds = TrailingNullsDataset.getDataset();
		final GroupBy groupByState = new GroupBy(ds, "STATE");
		groupByState.getGroupedBy();
		logger.debug(groupByState);
		// TODO what is this testing
	}

	@Test
	public void test2() {
		final Dataset ds = TrailingNullsDataset.getDataset();
		final GroupBy groupByState = new GroupBy(ds, "STATE", "CITY");
		groupByState.getGroupedBy();
		logger.debug(groupByState);
	}

	@Test
	public void test3() {
		final Dataset ds = TrailingNullsDataset.getDataset();
		final GroupBy groupByState = new GroupBy(ds, "STATE", "CITY");
		final Set<GroupedBy> grouped = groupByState.getGroupedBy();
		for (final GroupedBy gb : grouped) {
			final double fine = GroupedByOperations.sum(gb, "TICKETS");
			logger.debug(gb.getGroupColumns() + " " + fine);
		}
	}

	@Test
	public void test4() {
		final Dataset ds = TrailingNullsDataset.getDataset();
		final GroupBy groupByStateCity = new GroupBy(ds, "STATE", "CITY");
		GroupedByOperations.sum(groupByStateCity, "TICKETS", "total tickets");

		final Set<GroupedBy> grouped = groupByStateCity.getGroupedBy();
		for (final GroupedBy gb : grouped) {
			// double fine = GroupedByOperations.sum(gb, "TICKETS");
			logger.debug(gb.getGroupColumns() + " "); // + fine);
		}
	}

	@Test
	public void test5() {
		final Dataset ds = TrailingNullsDataset.getDataset();
		final GroupBy groupByStateCity = new GroupBy(ds, "STATE", "CITY");
		GroupedByOperations.sum(groupByStateCity, "TICKETS", "total tickets");
		final SortIndex si = new SortIndex(2, false);
		groupByStateCity.sort(si);
		final Set<GroupedBy> grouped = groupByStateCity.getGroupedBy();

		for (final GroupedBy gb : grouped) {
			logger.debug(gb.getGroupColumns() + " "); // + fine);
		}

	}
}
