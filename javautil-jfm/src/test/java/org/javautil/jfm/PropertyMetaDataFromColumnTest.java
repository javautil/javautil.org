package org.javautil.jfm;

import junit.framework.Assert;

import org.javautil.dataset.ColumnMetadata;
import org.junit.Before;
import org.junit.Test;

public class PropertyMetaDataFromColumnTest {

	private PropertyMetadataFromColumn metaData;

	private ColumnMetadata columnMetaData;

	@Before
	public void setup() {
		columnMetaData = new ColumnMetadata();
		columnMetaData.setColumnName("VP_CST_NBR");
		columnMetaData.setColumnIndex(0);
		columnMetaData.setComments("The Customer Unique Id");
		columnMetaData.setHeading("Customer No");
		columnMetaData.setLabel("Existing Customer No");
		metaData = new PropertyMetadataFromColumn();
		metaData.setColumn(columnMetaData);
	}

	@Test
	public void testGetName() {
		Assert.assertEquals(columnMetaData.getColumnName(), metaData.getName());
	}

	@Test
	public void testGetHeading() {
		Assert.assertEquals(columnMetaData.getHeading(), metaData.getHeading());
	}

	@Test
	public void testGetLabel() {
		Assert.assertEquals(columnMetaData.getLabel(), metaData.getLabel());
	}

	@Test
	public void testGetDescription() {
		Assert.assertEquals(columnMetaData.getComments(),
				metaData.getDescription());
	}

}
