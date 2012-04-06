package org.javautil.jdbc.metadata;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.javautil.oracle.dao.ObjectSourceDAO;
import org.javautil.text.StringUtils;

public class DDL {
	private List<String> lines = new ArrayList<String>();
	private String sql;
	private DatabaseObject object;
	private Table table;
	private static final String newline = System.getProperty("line.separator");
	private Logger logger = Logger.getLogger(getClass());

	public DDL(DatabaseObject object) {
		if (object == null) {
			throw new IllegalArgumentException("object is null");
		}
		this.object = object;
	}

	public DDL(Table table, String sql) {
	//	this.object = object;
		this.table = table;
		this.sql = sql;
	}

	public DDL(DatabaseObject object, Connection conn) throws SQLException {
		this(object);
		if (conn == null) {
			throw new IllegalArgumentException("conn is null");
		}
		if (object.getDatabaseObjectType() != null) {
			if (object.getDatabaseObjectType().hasSource()) {
				lines = ObjectSourceDAO.getForObject(conn, false, object);
			} else {
				logger.debug("no source for " + object.toString());
			}
		} else {
			logger.debug("no database object type");
		}
	}

	public void addLine(String line) {
		lines.add(line);
	}

	public List<String> getStrings() {
		return lines;
	}

	public List<String> getRightTrimmedLines() {
		ArrayList<String> trimmed = new ArrayList<String>();
		for (int i = 0; i < lines.size(); i++) {
			trimmed.add(StringUtils.trimRight(lines.get(i)));
		}
		return trimmed;
	}

	public String getTrimmedText() {
		StringBuffer b = new StringBuffer();
		for (String line : lines) {
			b.append(StringUtils.trimRight(line));
			b.append(newline);
		}
		return b.toString();
	}

	public String getSQL() {
		String retval = null;

		if (sql != null) {
			retval = sql;
		} else {
			retval = getTrimmedText();
		}
		return retval;
	}

	public void setSQL(String val) {
		sql = val;
	}
}
