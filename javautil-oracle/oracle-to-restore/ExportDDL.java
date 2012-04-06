package com.dbexperts.oracle;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.dbexperts.datasources.DataSourcesFactory;
import com.dbexperts.jdbc.InvalidDataSourceException;
import com.dbexperts.logging.LoggingConfigurator;

public class ExportDDL {

	private Connection conn;
	private Logger logger = Logger.getLogger(getClass());
	private PreparedStatement objectsStatement;
	private ResultSet objectsRset;

	private String objectsView = "dba_objects";
	private PreparedStatement ddlStatement;

	private String owner;
	private String objectName;
	private String objectType;
	private String baseDirName;
	private String dataSourceName = "dbexperts";
	private long startMillis;
	private long endMillis;
	public void process() throws SQLException, IOException {
		init();
		while (nextDatabaseObject()) {
			String ddl = getDDL();
			File f = getOutputFile();
			System.out.println(f.getAbsolutePath());
			System.out.println(ddl);
		}
		dispose();
	}
	
	private void dispose() throws SQLException {
		objectsStatement.close();
		ddlStatement.close();
		conn.close();
		
	}

	private void init() throws SQLException {
		LoggingConfigurator.configure();
		startMillis = System.currentTimeMillis();
		conn = DataSourcesFactory.getConnection(dataSourceName);
		prepareObjectsStatement();
		prepareDDLStatement();
	}

	private void prepareObjectsStatement() throws SQLException {
		String objectText = "select owner, object_name, object_type from " + objectsView
				+ " order by owner, object_type, object_name ";
		objectsStatement = conn.prepareStatement(objectText);
		objectsRset = objectsStatement.executeQuery();
	}

	private File getOutputFile() {
		String relativePath = owner + "/" + objectType + "/" + objectName + ".sql";
		String qualifiedPath;
		if (baseDirName == null) { 
			qualifiedPath  = relativePath;
		} else {
			qualifiedPath = baseDirName + "/" + relativePath;
		}
		File retval = new File(qualifiedPath);
		retval.mkdirs();
		return retval;
	}
	
	private void prepareDDLStatement() throws SQLException {
		String ddlText = "select dbms_metadata.get_ddl(object_type => ?, name => ?, schema => ?) from dual";
		ddlStatement = conn.prepareStatement(ddlText);
	}

	private boolean nextDatabaseObject() throws SQLException {
		boolean retval = objectsRset.next();
		if (retval) {
			owner = objectsRset.getString("owner");
			objectName = objectsRset.getString("object_name");
			objectType = objectsRset.getString("object_type");
		}
		return retval;
	}

	private String getDDL() throws IOException, SQLException {
		String type = objectType.replace(" ", "_");
		ddlStatement.setString(1, type);
		
		ddlStatement.setString(2, objectName);
		ddlStatement.setString(3, owner);
		ResultSet ddlRset = null;
		try {
			ddlRset = ddlStatement.executeQuery();
		} catch (SQLException e) {
			logger.error(e.getMessage());		
		}
		String p = owner + "/" + objectType + "/" + objectName + ".sql";
		// File f = new File(p);
		StringWriter w = new StringWriter();
		String retval = null;
		if (ddlRset != null && ddlRset.next()) {
			Clob c = ddlRset.getClob(1);
			InputStream is = c.getAsciiStream();

			int x;
			while ((x = is.read()) != -1) {
				w.write((char) x);
			}
			is.close();
			w.close();
			retval = w.toString();
		}

		return retval;
	}

	public static void main(String[] args) throws InvalidDataSourceException, SQLException, IOException {
		ExportDDL ed = new ExportDDL();
		ed.process();

	}
}
