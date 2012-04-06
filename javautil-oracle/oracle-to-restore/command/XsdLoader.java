package com.dbexperts.oracle.command;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.SQLException;

import oracle.jdbc.OracleConnection;

import com.dbexperts.oracle.datasource.OracleDataSourceEnvironment;

public class XsdLoader {

	private static final String newline = System.getProperty("line.separator");
	public static void loadXsd(final OracleConnection conn, final String location, final File xsdFile) throws IOException, SQLException {
		if (conn == null) {
			throw new IllegalArgumentException("conn is null");
		}
		if (location == null) {
			throw new IllegalArgumentException("location is null");
		}
		if (xsdFile == null) {
			throw new IllegalArgumentException("xsdFile is null");
		}
		if (!xsdFile.canRead() ) {
			throw new IllegalArgumentException("can't read " + xsdFile.getAbsolutePath());
		}
		final long size = xsdFile.length();
		final FileReader fr = new FileReader(xsdFile);
		final int sizeInt = (int) size;

		final char[] xsd = new char[sizeInt];
		fr.read(xsd);
		fr.close();
		final String xsdText = new String(xsd);
		loadXsd(conn,location,xsdText);


	}

	public static void loadXsd(final OracleConnection conn, final String location, final String xsd) throws SQLException {
		if (conn == null) {
			throw new IllegalArgumentException ("conn is null");
		}
		if (location == null) {
			throw new IllegalArgumentException("location is null");
		}
		if (xsd == null) {
			throw new IllegalArgumentException("xsd is null");
		}
		final String sql =
			"begin " + newline +
			"  dbms_xmlschema.registerSchema(?,?); " + newline +
			"end";
		final CallableStatement cs = conn.prepareCall(sql);
		cs.setString(1, location);
		cs.setString(2,xsd);
		cs.execute();
	}


	// todo write with good command wrapper
	public static void main(final String[] args) throws SQLException, IOException {
		if (args.length != 2) {
			System.err.println("usage location xsdFile");
		}
		final String location = args[0];
		final String xsdFileName = args[1];
		final File  xsdFile = new File(xsdFileName);
		final OracleDataSourceEnvironment odse = new OracleDataSourceEnvironment();
		odse.getDataSource();
		final OracleConnection conn = odse.getConnection();
		XsdLoader.loadXsd(conn, location, xsdFile);
		conn.commit();
		conn.close();
		odse.getDataSource().close();

	}
}
