package org.javautil.jdbc;

public interface InstrumentedConnection {

	void setModule(String name);

	void setClientInfo(String info);

	void setAction(String action);
}
