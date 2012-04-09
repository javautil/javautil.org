package org.javautil.dataset.csv;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.javautil.dataset.ColumnMetadata;
import org.javautil.dataset.DataType;
import org.javautil.dataset.Dataset;
import org.javautil.dataset.DatasetMetadata;
import org.javautil.dataset.DatasetMetadataImpl;
import org.javautil.dataset.MatrixDataset;
import org.javautil.dataset.MutableDataset;
import org.javautil.dataset.MutableDatasetMetadata;
import org.javautil.text.CsvReader;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 *         TODO jjs where are the tests? 
 *         
 *         TODO we also have a
 *         DatasetCsvUnmarshaller
 * 
 */
public class DatasetMetadataUnmarshallerCsv {
	
	private Logger logger = Logger.getLogger(getClass());

	// TODO move somewhere else
	// @SuppressWarnings("rawtypes")
	public MatrixDataset getDataset(MutableDatasetMetadata meta,
			final InputStream datastream) {
		MatrixDataset dataset = new MatrixDataset(meta);
		try {
			addRows(dataset, datastream);
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
		return dataset;
	}

	// TODO install or remove
//	 public Dataset getDataset(final InputStream csvStream,
//	 final InputStream metaStream) {
//	
//	 final List<ColumnMetadata> columnNamesList = getMetadata(metaStream);
//	 final MutableDatasetMetadata dm = new DatasetMetadataImpl(
//	 columnNamesList);
//	
//	 final MutableDataset dataset = new MatrixDataset(dm);
//	 //
//	 // get each of the rows from the csv input stream ,
//	 // create a collection of objects
//	 // call data
//	 //
//	 // dataset.appendRow(values);
//	 return dataset;
//	 }

	// TODO this should be in some Abstract Dataset class not here
	private List<DataType> getDataTypes(final Dataset dataset) {
		if (dataset == null) {
			throw new IllegalArgumentException("dataset is null");
		}
		final DatasetMetadata meta = dataset.getMetadata();
		final ArrayList<DataType> dataTypes = new ArrayList<DataType>();
		for (final ColumnMetadata column : meta.getColumnMetadata()) {
			dataTypes.add(column.getDataType());
		}
		return dataTypes;

	}

	public void addRows(final MutableDataset dataset, final InputStream is)
			throws IOException {
		final CsvReader reader = new CsvReader(is);

		List<Object> fields = null;
		reader.setDatatypes(getDataTypes(dataset));
		while ((fields = reader.readLineAsObjects()) != null) {
			if (logger.isDebugEnabled()) {
				logger.debug(fields);
			}
			dataset.appendRow(fields);
		}
	}

	public ArrayList<ColumnMetadata> getColumnMetadata(final InputStream meta)
			throws IOException {
		final ArrayList<ColumnMetadata> metalist = new ArrayList<ColumnMetadata>();
		final CsvReader reader = new CsvReader(meta);
		List<String> objects;
		while ((objects = reader.readLine()) != null) {
			final ColumnMetadata column = ColumnMetadata.getInstance(objects);
			metalist.add(column);
		}
		return metalist;
	}

	public MutableDatasetMetadata getMetadata(final InputStream meta)
			throws IOException {
		new DatasetMetadataImpl();
		final ArrayList<ColumnMetadata> columns = getColumnMetadata(meta);
		return new DatasetMetadataImpl(columns);
	}

}
