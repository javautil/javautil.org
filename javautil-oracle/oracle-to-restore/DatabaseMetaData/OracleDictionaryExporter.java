//package com.dbexperts.oracle.DatabaseMetaData;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.ResourceBundle;
//
//import javax.sql.DataSource;
//
//import org.apache.log4j.Logger;
//import org.dom4j.Document;
//
//import com.custdata.commandline.CommandLineHandler;
//import com.dbexperts.datasources.DataSourcesFactory;
//import com.dbexperts.declarativeCLI.CommonParametersHelper;
//import com.dbexperts.declarativeCLI.OracleDictionaryExporterParameters;
//import com.dbexperts.jdbc.DataSources;
//import com.dbexperts.jdbc.DatabaseMetaData.DatabaseObject;
//import com.dbexperts.jdbc.DatabaseMetaData.DatabaseObjectType;
//import com.dbexperts.jdbc.DatabaseMetaData.NonexistantTableException;
//
//public class OracleDictionaryExporter {
//
//    private final Logger logger = Logger.getLogger(this.getClass().getName());
//
//    private Document doc;
//
//    private OracleSchema schema;
//    private CommonParametersHelper parmHelper;
//    private OracleDictionaryExporterParameters parms;
//    private DataSources dataSources;
//
//    private void processParameters(OracleDictionaryExporterParameters parms)
//	    throws SQLException, IOException, NonexistantTableException {
//	dataSources = DataSourcesFactory.getDataSources();
//	parmHelper = new CommonParametersHelper(parms);
//	this.parms = parms;
//	DataSource ds = getDataSource();
//
//	List<String> schemaName = parms.getSchemaName();
//	for
//	schema = new OracleSchema(ds, schemaName);
//	// todo add destinationDirectory
//	writeToDirectory(parms.getOutputDirectory());
//
//	logger.debug("done");
//    }
//
//    public static final String revision = "$Revision: 1.1 $";
//
//    /**
//     * stub for getting revision from build
//     */
//    public static String getBuildIdentifier() {
//	String[] words = revision.split(" ");
//	return words[1];
//    }
//
//    public DataSource getDataSource() {
//	// final DataSources dss = new DataSources();
//	logger.info(dataSources.toString());
//	DataSource ds = dataSources.getDataSource(parms.getDataSourceName());
//	return ds;
//    }
//
//    public Connection getConnection() throws SQLException {
//	DataSource ds = getDataSource();
//	Connection conn = ds.getConnection();
//	return conn;
//    }
//
//    public static void main(String[] javaArgs) throws Exception {
//	OracleDictionaryExporterParameters parms = new OracleDictionaryExporterParameters();
//
//	OracleDictionaryExporter invocation = new OracleDictionaryExporter();
//
//	CommandLineHandler cmd = new CommandLineHandler();
//
//	cmd.setResourceBundle(ResourceBundle
//		.getBundle("com.dbexperts.declarativeCLI.CommonOptions"));
//	cmd.setArguments(parms);
//	cmd.setApplicationVersion(getBuildIdentifier());
//
//	cmd.parse(javaArgs);
//
//	invocation.processParameters(parms);
//    }
//
//    public File getOutputFile(File baseDirectory, String subdirectory,
//	    String fileName, boolean lowerCase) {
//	File base = baseDirectory == null ? new File(".") : baseDirectory;
//	String targetDirectoryName = null;
//	if (subdirectory != null) {
//	    targetDirectoryName = baseDirectory.getPath() + "/" + subdirectory;
//	} else {
//	    targetDirectoryName = baseDirectory.getName();
//	}
//
//	File targetDirectory = new File(targetDirectoryName);
//	targetDirectory.mkdirs();
//	String targetFileName = lowerCase ? fileName.toLowerCase() : fileName;
//
//	String targetFilePathName = targetDirectoryName + "/" + targetFileName;
//	
//	logger.debug("targetFilePath " + targetFilePathName);
//	return new File(targetFilePathName);
//    }
//
//    private void writeToFile(File directory, String subdir, boolean lowerCase, DatabaseObject dbo) throws IOException {
//	File f = getOutputFile(directory, subdir, dbo.getName()
//		    + ".sql", lowerCase);
//	    FileWriter fw = new FileWriter(f);
//	    String ddl = null;
//
//	//    fw.write(dbo.getSource().getTrimmedText());
//	    // todo how much does this suck?
//	//    if (dbo.getDatabaseObjectType().hasSource()) {
//		fw.write(dbo.getDDL().getTrimmedText());
////	      }
////	    fw.flush();
////	    fw.close();
////	    long bytes = f.length();
////	    logger.debug("wrote " + bytes + " bytes to " + f.getAbsolutePath());
//    }
//    
//    public void writeToDirectory(File directory) throws IOException {
//	if (directory == null) {
//	    throw new IllegalArgumentException("directory is null");
//	}
//
//	for (DatabaseObject dbo : schema.getFunctions().values()) {
//	    writeToFile(directory,"functions",true,dbo);
//	}
//	for (DatabaseObject dbo : schema.getProcedures().values()) {
//	    writeToFile(directory,"procedures",true,dbo);
//	}
//	for (DatabaseObject dbo : schema.getPackageSpecifications().values()) {
//	    writeToFile(directory,"packageSpecification",true,dbo);
//	}
//	for (DatabaseObject dbo : schema.getPackageBodies().values()) {
//	    writeToFile(directory,"packageBody",true,dbo);
//	}
////	for (DatabaseObject dbo : schema.getTriggers().values()) {
////	    writeToFile(directory,"trigger",true,dbo);
////	}
//    }
//}
