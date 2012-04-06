package com.dbexperts.oracle.cache;
import oracle.jdbc.OracleConnection;



/**
Version:   $Id: Cacheable.java,v 1.1 2011/08/31 21:09:57 jjs Exp $
*/
/**
 * property entries
 * <ul>
 *    <li><b>com.diamond9.Diamond.logLabels</b><I>boolean</I>
 *    true - log format is ... [thread name]className(filename.java:lineNbr)
 *    </li>
 *    <li>
 *    <b>com.diamond9.Diamond.logStackTrace</b><I>boolean</I>
 *    </li>
 * </ul>
 */

public interface Cacheable {
	/**
	 * @param out
	 * @throws java.io.IOException
	 */
//	public void emitHtml(Writer out)
//	throws java.io.IOException;

	public CacheStatus getCacheStatus();

	public void refresh(OracleConnection dbc)
	throws java.sql.SQLException;

}
