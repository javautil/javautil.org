package org.javautil.oracle.servicerequest;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.dom4j.Element;

//import org.javautil.j2ee.InitializationException;
//import org.javautil.jdbc.InvalidDataSourceException;

public interface ServiceProvider {
	// todo this should go away or be converted to a standard JaxbService
	public void initialize(Element arguments);

	// throws InitializationException;

	public void run();

	public void setDataSource(DataSource dataSource) throws SQLException;
	// , InvalidDataSourceException;
}
