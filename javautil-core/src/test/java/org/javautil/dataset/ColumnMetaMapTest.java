/**
 * 
 */
package org.javautil.dataset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class ColumnMetaMapTest {

	@Test
	public void getColumnNameIndexMaptest() {
		final ColumnMetadata cm1 = new ColumnMetadata();
		final ColumnMetadata cm2 = new ColumnMetadata();

		cm1.setColumnName("TEST_COL_1");
		cm1.setColumnIndex(0);
		cm2.setColumnName("TEST_COL_2");
		cm2.setColumnIndex(1);

		final List<ColumnMetadata> cmList = new ArrayList<ColumnMetadata>();
		cmList.add(cm1);
		cmList.add(cm2);

		final Map<String, Integer> columnIndexMap = ColumnMetaMap
				.getColumnNameIndexMap(cmList);
		Assert.assertEquals(columnIndexMap.size(), 2);
		Assert.assertEquals(columnIndexMap.get("TEST_COL_1").intValue(), 0);
		Assert.assertEquals(columnIndexMap.get("TEST_COL_2").intValue(), 1);
	}
}
