package org.javautil.dataset.csv;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.javautil.dataset.ColumnMetadata;
import org.javautil.dataset.Dataset;
import org.javautil.dataset.DatasetMetadataImpl;
import org.javautil.dataset.MatrixDataset;
import org.javautil.dataset.MutableDataset;
import org.javautil.dataset.MutableDatasetMetadata;
import org.javautil.text.CsvReader;
import org.javautil.dataset.DataType;


//import au.com.bytecode.opencsv.CSVReader;
/**
 * 
 * @author jjs@dbexperts.com
 * 
 * todo jjs where are the tests?
 * 
 */
public class DatasetCsvUnmarshaller {

	// TODO lose the crap, write unit tests
	public MatrixDataset getDataset(InputStream csvStream, InputStream metaStream) throws IOException {
		DatasetMetadataUnmarshallerCsv metaUnmarshaller =new DatasetMetadataUnmarshallerCsv();
		
		MutableDatasetMetadata dm = metaUnmarshaller.getMetadata(metaStream);
		return getDataset(dm,csvStream);
//		MatrixDataset dataset = new MatrixDataset(dm);
//		List<Object> values = null; // use Opencsv to get the values
//		CsvReader dataReader = new CsvReader(csvStream);
//		while ((values = dataReader.readLineAsObjects()) != null) {
//			dataset.appendRow(values);
//		}
//
//		return dataset;
	}
	// TODO 
//	private List<ColumnMetadata> getMetadata(InputStream meta) {
//		ArrayList<ColumnMetadata> metalist = new ArrayList<ColumnMetadata>();
//
//		Object[] objects = null;
////		 TODO: populate from csv
////		 TODO: need to write our own CSV stream reader because opencsv has
////		 implementation only for FileReader
//		 CsvReader reader = new CsvReader(meta);
//
//		 while (true) {
//		 int index = 0;
//		 ColumnMetadata cmd = new ColumnMetadata();
//		 cmd.setColumnName((String) objects[index]);
//		 index++;
//		//				
//		 if (index < objects.length && objects[index] != null) {
//		 cmd.setColumnIndex(Integer.parseInt((String)objects[index]));
//		 }
//		 if (index < objects.length && objects[2] != null) {
//		 DataType dt = DataType.getTypeByName((String) objects[2]);
//		 cmd.setDataType(dt);
//		 }
//		 if (index < objects.length && objects [3] != null) {
//		 cmd.setPrecision(new Integer((String) objects[3]));
//		 }
//		 if (index < objects.length && ) {
//			 
//		 }
//		//		
//		//				
////		 private Integer precision;
////		 private Integer scale;
////		 private Integer columnDisplaySize;
////		 private HorizontalAlignment horizontalAlignment;
////		 private String excelFormat;
////		 private String javaFormat;
////		 private String groupName;
//		//				
//		 }
//
//		// read each line of input from the input stream, creat a new
//		// ColumnMetadata add t5o list
//		return metalist;
//		// return list
//	}

	public MatrixDataset getDataset(MutableDatasetMetadata meta,
			InputStream csvStream) throws IOException {
		MatrixDataset dataset = new MatrixDataset(meta);
		CsvReader dataReader = new CsvReader(csvStream);
		List<Object> values = null; // use Opencsv to get the values
		while ((values = dataReader.readLineAsObjects()) != null) {
			dataset.appendRow(values);
		}

		return dataset;
	}
}
