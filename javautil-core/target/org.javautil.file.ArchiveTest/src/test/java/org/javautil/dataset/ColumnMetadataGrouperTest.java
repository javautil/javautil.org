/**
 * 
 */
package org.javautil.dataset;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 */
public class ColumnMetadataGrouperTest extends BaseTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void getGroupNametest() {
		final ColumnMetadata cm1 = new ColumnMetadata();
		final ColumnMetadata cm2 = new ColumnMetadata();

		cm1.setColumnName("TEST_COL_1");
		cm1.setColumnIndex(0);
		cm1.setGroupName("Group1");
		cm2.setColumnName("TEST_COL_2");
		cm2.setColumnIndex(1);
		cm2.setGroupName("Group2");

		final List<ColumnMetadata> cmList = new ArrayList<ColumnMetadata>();
		cmList.add(cm1);
		cmList.add(cm2);

		final ColumnMetadataGrouper grouper = new ColumnMetadataGrouper();
		final String[] groups = grouper.getGroupNames(cmList);
		Assert.assertEquals(groups.length, 2);
	}

	@Test
	public void getColumnNameIndexMaptest() {
		final ColumnMetadata cm1 = new ColumnMetadata();
		final ColumnMetadata cm2 = new ColumnMetadata();

		cm1.setColumnName("TEST_COL_1");
		cm1.setColumnIndex(0);
		cm1.setGroupName("Group1");
		cm2.setColumnName("TEST_COL_2");
		cm2.setColumnIndex(1);
		cm2.setGroupName("Group1");

		final Collection<ColumnMetadata> cmList = new ArrayList<ColumnMetadata>();
		cmList.add(cm1);
		cmList.add(cm2);

		new ColumnMetadataGrouper();
		final Map<String, Integer> columnIndexMap = ColumnMetadataGrouper
				.getColumnNameIndexMap(null, 1, cmList, "Group1");
		Assert.assertEquals(columnIndexMap.size(), 2);
		Assert.assertEquals(columnIndexMap.get("TEST_COL_1").intValue(), 1);
		Assert.assertEquals(columnIndexMap.get("TEST_COL_2").intValue(), 2);

	}

}
