package org.javautil.jdbc;

import java.sql.ResultSet;

public interface ResultSetPopulable {
    public void setValues(ResultSet rset) throws java.sql.SQLException;
}
