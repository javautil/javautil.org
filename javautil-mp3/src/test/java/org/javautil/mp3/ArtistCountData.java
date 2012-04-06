package org.javautil.mp3;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.javautil.dataset.ColumnMetadata;
import org.javautil.dataset.DataType;
import org.javautil.dataset.Dataset;
import org.javautil.dataset.DatasetMetadataImpl;
import org.javautil.dataset.MatrixDataset;
import org.javautil.dataset.MutableDatasetMetadata;
import org.javautil.dataset.csv.DatasetMetadataUnmarshallerCsv;
import org.junit.Ignore;
import org.junit.Test;

public class ArtistCountData {
	// TODO move to core

	public Dataset getData() throws IOException {
		final String fileName = "target/artists.csv";
		final InputStream is = new FileInputStream(fileName);
		final MutableDatasetMetadata meta = new DatasetMetadataImpl();
		meta.addColumn(new ColumnMetadata("Artist Name", DataType.STRING));
		meta.addColumn(new ColumnMetadata("Count", DataType.INTEGER));
		final MatrixDataset dataset = new MatrixDataset(meta);
		final DatasetMetadataUnmarshallerCsv unmarshaller = new DatasetMetadataUnmarshallerCsv();
		unmarshaller.getDataset(meta, is);
		is.close();
		return dataset;
	}

	@Ignore
	@Test
	public void getDataTest() throws IOException {
		getData();
	}
}
