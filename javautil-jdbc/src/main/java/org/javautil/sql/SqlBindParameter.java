package org.javautil.sql;

public class SqlBindParameter {
	/**
	 * The index of this bind variable in the statement relative 1.
	 */
	private int index;


	/**
	 * The name of this bind variable in the statement
	 */
	private String bindName;
	
	public SqlBindParameter(int index, String bindName) {
		super();
		this.index = index;
		this.bindName = bindName;
	}	
	/**
	 * @return the bindName
	 * TODO rename to BindParameter
	 */
	public String getBindName() {
		return bindName;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}


}
