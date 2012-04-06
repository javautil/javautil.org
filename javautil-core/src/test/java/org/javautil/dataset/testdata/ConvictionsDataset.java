package org.javautil.dataset.testdata;

import org.javautil.collections.ListHelper;
import org.javautil.dataset.ColumnMetadata;
import org.javautil.dataset.DataType;
import org.javautil.dataset.DatasetMetadataImpl;
import org.javautil.dataset.MatrixDataset;
import org.javautil.dataset.MutableDataset;
import org.javautil.util.Day;

public class ConvictionsDataset {
	public static MutableDataset getConvictions() {
		final DatasetMetadataImpl convictionMeta = new DatasetMetadataImpl() {
			{
				addColumn(new ColumnMetadata("STATE", 0, DataType.STRING, null,
						null, null, null, null, null));
				addColumn(new ColumnMetadata("CITY", 1, DataType.STRING, null,
						null, null, null, null, null));
				addColumn(new ColumnMetadata("Date", 2, DataType.DATE, null,
						null, null, null, null, null));
				addColumn(new ColumnMetadata("Days in Jail", 3,
						DataType.DOUBLE, null, null, null, null, null, null));
			}
		};

		final MatrixDataset mds = new MatrixDataset(convictionMeta);
		mds.addRow(ListHelper
				.toList("TX", "Southlake", new Day(2007, 12, 1), 3));
		mds.addRow(ListHelper.toList("TX", "Coppell", Day.today(), 3));
		return mds;

	}

	public MutableDataset getDataset() {
		return getConvictions();
	}
}
