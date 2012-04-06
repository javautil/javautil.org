package org.javautil.jdbc.metadata.renderer;
//package org.javautil.jdbc.metadata;
// todo replace with new version from my house
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.ResourceBundle;
//
//import javax.sql.DataSource;
//
//import oracle.jdbc.OracleConnection;
//
//import org.apache.log4j.Logger;
//import org.javautil.commandline.CommandLineHandler;
//
//import com.dbexperts.datasources.DataSourcesFactory;
//import com.dbexperts.declarativeCLI.CommonParametersHelper;
//import com.dbexperts.declarativeCLI.OracleDictionaryExporterParameters;
//import com.dbexperts.jdbc.DataSources;
//import com.dbexperts.oracle.WrappedOracleConnection;
//public class DatabaseMetadataExporter {
//
//    private final Logger logger = Logger.getLogger(this.getClass().getName());
//
//    // private Document doc;
//    private TableToSQL tableSQL =new TableToSQL();
//    private Schema schema;
//    private CommonParametersHelper parmHelper;
//    private OracleDictionaryExporterParameters parms;
//    private DataSources dataSources;
//    private Connection conn;
//    public static final String revision = "$Revision: 1.1 $";
//
//    private void processParameters(OracleDictionaryExporterParameters parms)
//    throws SQLException, IOException, NonexistantTableException {
//	dataSources = DataSourcesFactory.getDataSources();
//	parmHelper = new CommonParametersHelper(parms);
//	this.parms = parms;
//	DataSource ds = getDataSource();
//
//	for (String schemaName : parms.getSchemaName()) {
//	    processSchema(ds,schemaName);
//	}
//
//	logger.debug("done");
//    }
//
//    public void processSchema(DataSource ds, String schemaName) throws SQLException, IOException, NonexistantTableException {
//
//	conn = ds.getConnection();
//	WrappedOracleConnection wconn = WrappedOracleConnection.getInstance((OracleConnection) conn);
//	wconn.enableTraceDetailUnlimited();
//	schema = SchemaFactory.getSchema(conn, schemaName); 
//	if (logger.isDebugEnabled()) {
//	    
//	}
//
//	writeToDirectory(parms.getOutputDirectory());
//	conn.close();
//	logger.debug("done");
//    }
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
//    public File getOutputFile(File baseDirectory, String schemaName, String objectTypeName,
//	    String fileName, boolean lowerCase) {
//	if (schemaName == null) {
//	    throw new IllegalArgumentException("schemaName is null");
//	}
//	if (objectTypeName == null) {
//	    throw new IllegalArgumentException("objectTypeName is null");
//	}
//	File base = baseDirectory == null ? new File(".") : baseDirectory;
//	String targetDirectoryName = null;
//
//	    targetDirectoryName = baseDirectory.getPath() + "/" +  schemaName + "/" + objectTypeName;
//
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
//    private void writeStringToFile(File directory, String schemaName, String objectTypeName, String fileName, boolean lowerCase,
//	    String payload) throws IOException {
//	File f = getOutputFile(directory, schemaName , objectTypeName, fileName + ".sql",
//		lowerCase);
//	FileWriter fw = new FileWriter(f);
//	
//
//	fw.write(payload);
//	fw.close();
//
//    }
//
//    private void writeToFile(File directory, String objectType, boolean lowerCase,
//	    DatabaseObject dbo) throws IOException {
//	File f = getOutputFile(directory, dbo.getOwner(), objectType, dbo.getName() + ".sql",
//		lowerCase);
//	FileWriter fw = new FileWriter(f);
//	DDL ddl = dbo.getDDL();
//	fw.write(dbo.getDDL().getSQL());
//	
//    }
//
//    public void writeToDirectory(File directory) throws IOException {
//	if (directory == null) {
//	    throw new IllegalArgumentException("directory is null");
//	}
//
//	if (schema.canGetViewSource()) {
//	    for (DatabaseObject dbo : schema.getViews().values()) {
//
//		String text = schema.getViewSource(dbo.getName());
//		DDL ddl = new DDL(dbo);
//		dbo.setDDL(ddl);
//		ddl.setSQL(text);
//		
//		writeToFile(directory,"view",true,dbo);
//	    }
//	}
//	
//	if (schema.canGetFunctions()) {
//	    for (DatabaseObject dbo : schema.getFunctions().values()) {
//		writeToFile(directory,"functions",true,dbo);
//	    } 
//	} else {
//	    logger.info("schema canGetFunctions() is false");
//	}
//
//	if (schema.canGetProcedures()) {
//	    for (DatabaseObject dbo : schema.getProcedures().values()) {
//		writeToFile(directory,"procedures",true,dbo);
//	    }
//	}
//	if (schema.canGetPackageSpecifications()) {
//	    for (DatabaseObject dbo : schema.getPackageSpecifications().values()) {
//		writeToFile(directory,"packageSpecification",true,dbo);
//	    }
//	}
//	if (schema.canGetPackageBodies()) {
//	    for (DatabaseObject dbo : schema.getPackageBodies().values()) {
//		writeToFile(directory,"packageBody",true,dbo);
//	    }
//	}
//	if (schema.canGetTriggers()) {
//	    for (DatabaseObject dbo : schema.getTriggers().values()) {
//		writeToFile(directory,"trigger",true,dbo);
//	    }
//	}
//	
//	for (Table table : schema.getSchemaTables().getTables()) {
//	    String tableText = tableSQL.toSQLString(table);
//	    writeStringToFile(directory,table.getSchemaName(),"table",table.getTableName(), true,tableText);
//	}
//    }
//
//    public static void main(String[] javaArgs) throws Exception {
//	OracleDictionaryExporterParameters parms = new OracleDictionaryExporterParameters();
//
//	DatabaseMetadataExporter invocation = new DatabaseMetadataExporter();
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
// }
