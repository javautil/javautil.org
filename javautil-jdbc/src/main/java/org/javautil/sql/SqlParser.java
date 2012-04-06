package org.javautil.sql;

import java.util.LinkedHashSet;

public interface SqlParser {
	public LinkedHashSet<String> getBinds(final String sqlText);
}