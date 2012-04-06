package org.javautil.mp3;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.javautil.dataset.ColumnMetadata;
import org.javautil.dataset.DataType;
import org.javautil.dataset.DatasetMetadataImpl;
import org.javautil.dataset.MutableDatasetMetadata;
import org.javautil.dataset.csv.DatasetCsvUnmarshaller;
import org.junit.Ignore;
import org.junit.Test;

public class ArtistCsvLoaderTest {
	@Ignore
	// TODO need to ensure that it was extracted or exists
	@Test
	public void test() throws IOException {

		final MutableDatasetMetadata meta = new DatasetMetadataImpl();
		meta.addColumn(new ColumnMetadata("Artist Name", DataType.STRING));
		meta.addColumn(new ColumnMetadata("Count", DataType.INTEGER));
		// MatrixDataset dataset = new MatrixDataset(meta);
		final InputStream artistStream = new FileInputStream(
				"target/artists.csv");

		final DatasetCsvUnmarshaller unmarshaller = new DatasetCsvUnmarshaller();
		unmarshaller.getDataset(meta, artistStream);
		artistStream.close();

	}
}
