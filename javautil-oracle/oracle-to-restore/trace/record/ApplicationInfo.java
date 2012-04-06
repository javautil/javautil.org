/**
* @(#) AppName.java
*/

package com.dbexperts.oracle.trace.record;

/**
* APPNAME mod='%s' mh=%lu act='%s' ah=%lu
* ----------------------------------------------------------------------------
* APPNAME     Application name setting.  This only applies to Oracle 7.2
* and above.  This can be set by using the DBMS_APPLICATION_INFO
* package.  See Note 30366.1.
*
* mod         Module name.
* mh          Module hash value.
* act         Action.
* ah          Action hash value.
*/
public class ApplicationInfo extends AbstractRecord implements Record
{
	/**
	*
	*/
	private String moduleName;

	/**
	*
	*/
	private int modHash;

	/**
	*
	*/
	private int action;

	/**
	*
	*/
	private int actionHash;



	public RecordType getRecordType() {
		return RecordType.APP_NAME;
	}



	public int getModHash() {
		return modHash;
	}



	public String getModuleName() {
		return moduleName;
	}



	public int getAction() {
		return action;
	}



	public int getActionHash() {
		return actionHash;
	}
}
