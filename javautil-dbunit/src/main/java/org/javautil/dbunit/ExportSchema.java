package org.javautil.dbunit;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

public class ExportSchema extends AbstractExporter
{
	
	
	public void fullExport() throws SQLException, DatabaseUnitException, FileNotFoundException, IOException {
		afterPropertiesSet();
        // full database export
        IDataSet fullDataSet = getIConnection().createDataSet();
        FlatXmlDataSet.write(fullDataSet, getOutputStream());
        dispose();
		
	}


}
     