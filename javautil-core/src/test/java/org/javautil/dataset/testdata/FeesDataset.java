package org.javautil.dataset.testdata;

import org.javautil.collections.ListHelper;
import org.javautil.dataset.ColumnMetadata;
import org.javautil.dataset.DataType;
import org.javautil.dataset.DatasetMetadataImpl;
import org.javautil.dataset.MatrixDataset;
import org.javautil.dataset.MutableDataset;

public class FeesDataset {
	private static DatasetMetadataImpl deferredAdjudicationMeta = new DatasetMetadataImpl() {
		{
			addColumn(new ColumnMetadata("STATE", 0, DataType.STRING, null,
					null, null, null, null, null));
			addColumn(new ColumnMetadata("CITY", 1, DataType.STRING, null,
					null, null, null, null, null));
			addColumn(new ColumnMetadata("MONTH", 2, DataType.INTEGER, null,
					null, null, null, null, null));
			addColumn(new ColumnMetadata("Fine", 3, DataType.DOUBLE, null,
					null, null, null, null, null));
			addColumn(new ColumnMetadata("Legal Fee", 4, DataType.DOUBLE, null,
					null, null, null, null, null));
		}
	};

	private static MatrixDataset fees = new MatrixDataset(
			deferredAdjudicationMeta) {
		{
			addRow(ListHelper.toList("TX", "DALLAS", new Integer(1),
					new Double(311), new Double(380)));
			addRow(ListHelper.toList("TX", "DALLAS", new Integer(2),
					new Double(321), null));
			addRow(ListHelper.toList("TX", "HOUSTON", new Integer(1),
					new Double(312), new Double(0)));
			addRow(ListHelper.toList("TX", "Southlake", new Integer(3),
					new Double(333), null));

		}
	};

	public static MutableDataset getDataset() {
		return fees;
	}
}
