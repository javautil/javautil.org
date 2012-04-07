package org.javautil.dataset.testdata;

import org.javautil.collections.ListHelper;
import org.javautil.dataset.ColumnMetadata;
import org.javautil.dataset.DataType;
import org.javautil.dataset.DatasetMetadataImpl;
import org.javautil.dataset.MatrixDataset;
import org.javautil.dataset.MutableDataset;

public class TicketsDataset {
	private final DatasetMetadataImpl meta = new DatasetMetadataImpl() {
		{
			addColumn(new ColumnMetadata("STATE", 0, DataType.STRING, null,
					null, null, null, null, null));
			addColumn(new ColumnMetadata("CITY", 1, DataType.STRING, null,
					null, null, null, null, null));
			addColumn(new ColumnMetadata("MONTH", 2, DataType.INTEGER, null,
					null, null, null, null, null));
			addColumn(new ColumnMetadata("TICKETS", 3, DataType.DOUBLE, null,
					null, null, null, null, null));
		}
	};

	private final MatrixDataset tickets = new MatrixDataset(meta) {
		{
			addRow(ListHelper.toList("TX", "DALLAS", new Integer(1),
					new Double(42)));
			addRow(ListHelper.toList("TX", "DALLAS", new Integer(2),
					new Double(27)));
			addRow(ListHelper.toList("TX", "HOUSTON", new Integer(1),
					new Double(32)));
			addRow(ListHelper.toList("TX", "Quoted\"Text", new Integer(3),
					new Double(17)));

		}
	};

	public MutableDataset getDataset() {
		return tickets;
	}
}
