package org.javautil.jdbc.metadata.renderer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;

/**
 * 
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 */
public class XmlSchemaToSql {
	public static final String revision = "$Revision: 1.1 $";
	private final Document doc;
	public TableXMLMarshaller dillon = new TableXMLMarshaller();
	private Connection conn;
	private boolean verbose = true;
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	public XmlSchemaToSql(final Connection conn, final Document doc) {
		if (conn == null) {
			throw new IllegalArgumentException("conn is null");
		}
		if (doc == null) {
			throw new IllegalArgumentException("doc is null");
		}
		this.conn = conn;
		this.doc = doc;
	}

	public XmlSchemaToSql(final Document doc) {
		this.doc = doc;
	}

	public void createForeignKeys() throws SQLException {
		checkConnection();
		final ArrayList<String> statements = getCreateForeignKeyStatements();
		for (final String statement : statements) {
			final PreparedStatement stmt = conn.prepareStatement(statement);
			if (verbose) {
				logger.info(statement);
			}
			stmt.execute();
			stmt.close();
		}
	}

	public void createPrimaryKeys() throws SQLException {
		checkConnection();
		final ArrayList<String> statements = getCreatePrimaryKeyStatements();
		for (final String statement : statements) {
			final PreparedStatement stmt = conn.prepareStatement(statement);
			if (verbose) {
				logger.info(statement);
			}
			stmt.execute();
			stmt.close();
		}
	}

	public void createTables() throws SQLException {
		checkConnection();
		final ArrayList<String> statements = getCreateTableStatements();
		for (final String statement : statements) {
			final PreparedStatement stmt = conn.prepareStatement(statement);
			if (verbose) {
				logger.info(statement);
			}
			stmt.execute();
			stmt.close();
		}
	}

	public ArrayList<String> getCreateForeignKeyStatements() {
		final Logger logger = Logger.getLogger(XmlSchemaToSql.class.getName());
		final ArrayList<String> sql = new ArrayList<String>();
		final List<Element> tables = getTableElements();
		for (final Element tableElement : tables) {
			// Table table = new Table(table);
			final ArrayList<String> texts = TableXMLMarshaller
					.getCreateForeignKeys(tableElement);
			if (texts == null) {
				final String tableName = TableXMLMarshaller
						.getTableName(tableElement);
				logger.warn("table has no foreign keys " + tableName);
			} else {
				// logger.info(texts);
				sql.addAll(texts);
			}
		}
		return sql;
	}

	public ArrayList<String> getCreatePrimaryKeyStatements() {
		final Logger logger = Logger.getLogger(XmlSchemaToSql.class.getName());
		final ArrayList<String> sql = new ArrayList<String>();
		final List<Element> tables = getTableElements();
		for (final Element tableElement : tables) {
			// Table table = new Table(table);
			final String text = TableXMLMarshaller
					.getCreatePrimaryKey(tableElement);

			if (text == null) {

				final String tableName = TableXMLMarshaller
						.getTableName(tableElement);
				logger.warn("table has no primary key " + tableName);
			} else {
				if (verbose) {
					logger.info(text);
				}
				sql.add(text);
			}
		}
		return sql;
	}

	public ArrayList<String> getCreateTableStatements() {
		final ArrayList<String> sql = new ArrayList<String>();
		final List<Element> tables = getTableElements();
		for (final Element table : tables) {
			final String text = TableXMLMarshaller.getCreateStatement(table);

			sql.add(text);
		}
		return sql;
	}

	@SuppressWarnings("unchecked")
	public List<Element> getTableElements() {
		final XPath xpathSelector = DocumentHelper.createXPath("//entity");
		final List<Element> results = xpathSelector.selectNodes(doc);
		return results;
	}

	public boolean isVerbose() {
		return verbose;
	}

	public void setVerbose(final boolean verbose) {
		this.verbose = verbose;
	}

	private void checkConnection() {
		if (conn == null) {
			throw new IllegalStateException("conn is null");
		}
	}

}
