package com.dbexperts.oracle.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dbexperts.oracle.UserEnv;
import com.dbexperts.persistence.PersistenceException;

public class UserEnvS
{
	int insertBatchSize = 100;
	private Connection conn;

	private ResultSet rset = null;
	private PreparedStatement selectStmt = null;
	private static final String newline = System.getProperty("line.separator");
	static final String selectText = "" +
	"select " + newline +   //
	"        rtrim(sys_context('USERENV', 'ACTION'))                 action, " + newline +   //
	"        rtrim(sys_context('USERENV', 'AUTHENTICATED_IDENTITY')) authenticated_identity, " + newline +   //
	"        rtrim(sys_context('USERENV', 'AUTHENTICATION_DATA'))    authentication_data, " + newline +   //
	"        rtrim(sys_context('USERENV', 'AUTHENTICATION_METHOD'))  authentication_method, " + newline +   //
	"        rtrim(sys_context('USERENV', 'BG_JOB_ID'))              bg_job_id, " + newline +   //
	"        rtrim(sys_context('USERENV', 'CLIENT_IDENTIFIER'))      client_identifier, " + newline +   //
	"        rtrim(sys_context('USERENV', 'CLIENT_INFO'))            client_info, " + newline +   //
	"        rtrim(sys_context('USERENV', 'CURRENT_EDITION_ID'))   current_edition_id, " + newline +   //
	"        rtrim(sys_context('USERENV', 'CURRENT_EDITION_NAME'))  current_edition_name, " + newline +   //
	"        rtrim(sys_context('USERENV', 'CURRENT_SCHEMA'))        current_schema, " + newline +   //
	"        rtrim(sys_context('USERENV', 'CURRENT_SCHEMAID'))      current_schema_aid, " + newline +   //
	"        rtrim(sys_context('USERENV', 'DB_DOMAIN'))             db_domain, " + newline +   //
	"        rtrim(sys_context('USERENV', 'DB_NAME'))               db_name, " + newline +   //
	"        rtrim(sys_context('USERENV', 'DB_UNIQUE_NAME'))        db_unique_name, " + newline +   //
	"        rtrim(sys_context('USERENV', 'FG_JOB_ID'))             fg_job_id, " + newline +   //
	"        rtrim(sys_context('USERENV', 'GLOBAL_CONTEXT_MEMORY')) global_context_memory, " + newline +   //
	"        rtrim(sys_context('USERENV', 'GLOBAL_UID'))            global_uid, " + newline +   //
	"        rtrim(sys_context('USERENV', 'HOST'))                  host, " + newline +   //
	"        rtrim(sys_context('USERENV', 'IDENTIFICATION_TYPE'))   identification_type, " + newline +   //
	"        rtrim(sys_context('USERENV', 'INSTANCE'))              instance, " + newline +   //
	"        rtrim(sys_context('USERENV', 'INSTANCE_NAME'   ))      instance_name, " + newline +   //
	"        rtrim(sys_context('USERENV', 'IP_ADDRESS'))            ip_address, " + newline +   //
	"        rtrim(sys_context('USERENV', 'ISDBA'))                 isdba, " + newline +   //
	"        rtrim(sys_context('USERENV', 'LANG'))                  lang,        " + newline +   //
	"        rtrim(sys_context('USERENV', 'LANGUAGE'))              language, " + newline +   //
	"        rtrim(sys_context('USERENV', 'MODULE'))                module, " + newline +   //
	"        rtrim(sys_context('USERENV', 'NETWORK_PROTOCOL'))      network_protocol, " + newline +   //
	"        rtrim(sys_context('USERENV', 'NLS_CALENDAR'))          nls_calendar, " + newline +   //
	"        rtrim(sys_context('USERENV', 'NLS_CURRENCY'))          nls_currency, " + newline +   //
	"        rtrim(sys_context('USERENV', 'NLS_DATE_FORMAT'))       nsl_date_format, " + newline +   //
	"        rtrim(sys_context('USERENV', 'NLS_DATE_LANGUAGE'))     nls_date_language, " + newline +   //
	"        rtrim(sys_context('USERENV', 'NLS_SORT'))              nls_sort, " + newline +   //
	"        rtrim(sys_context('USERENV', 'NLS_TERRITORY'))         nls_territory, " + newline +   //
	"        rtrim(sys_context('USERENV', 'OS_USER'))               os_user, " + newline +   //
	"        rtrim(sys_context('USERENV', 'SERVER_HOST'))           server_host, " + newline +   //
	"        rtrim(sys_context('USERENV', 'SERVICE_NAME'))          service_name, " + newline +   //
	"        rtrim(sys_context('USERENV', 'SESSION_USER'))          session_user, " + newline +   //
	"        rtrim(sys_context('USERENV', 'SESSION_USERID'))        session_user_id, " + newline +   //
	"        rtrim(sys_context('USERENV', 'SESSIONID'))             session_id, " + newline +   //
	"        rtrim(sys_context('USERENV', 'SID'))                   sid, " + newline +   //
	"        rtrim(sys_context('USERENV', 'TERMINAL'))              terminal " + newline +   //
	"from dual";


	public static UserEnv get(Connection conn, UserEnv userEnv) throws SQLException, PersistenceException {
		Statement s = conn.createStatement();
		ResultSet rset;
		try {
		rset = s.executeQuery(selectText);
		} catch (SQLException sqe) {
			throw new PersistenceException(sqe,selectText);
		}
		rset.next();
		getRow(rset,userEnv);
		s.close();
		return userEnv;
		
		
	}

	private final static String zero = new String(new char[]  {0});
	
	/**
	 * At the time this was written the jdbc driver was returning a zero at the end of the string.
	 * @param in
	 * @return
	 */
	private static String patch (String in) {
		return in == null ? null : in.replaceAll(zero, "");
	}
	
    public static void getRow(ResultSet rset, UserEnv row)
    throws java.sql.SQLException
    {
        String columnName = null;

        try {
        	row.setAction(rset.getString(columnName = "ACTION"));
            row.setAuthenticatedIdentity(patch(rset.getString(columnName = "AUTHENTICATED_IDENTITY")));
            row.setAuthenticationData(patch(rset.getString(columnName = "AUTHENTICATION_DATA")));
            row.setAuthenticationMethod(patch(rset.getString(columnName = "AUTHENTICATION_METHOD")));
            row.setBgJobId(patch(rset.getString(columnName = "BG_JOB_ID")));
            row.setClientIdentifier(patch(rset.getString(columnName = "CLIENT_IDENTIFIER")));
            row.setClientInfo(patch(rset.getString(columnName = "CLIENT_INFO")));
            row.setCurrentEditionId(patch(rset.getString(columnName = "CURRENT_EDITION_ID")));
            row.setCurrentEditionName(patch(rset.getString(columnName = "CURRENT_EDITION_NAME")));
            row.setCurrentSchema(patch(rset.getString(columnName = "CURRENT_SCHEMA")));
            row.setCurrentSchemaAid(patch(rset.getString(columnName = "CURRENT_SCHEMA_AID")));
            row.setDbDomain(patch(rset.getString(columnName = "DB_DOMAIN")));
            row.setDbName(patch(rset.getString(columnName = "DB_NAME")));
            row.setDbUniqueName(patch(rset.getString(columnName = "DB_UNIQUE_NAME")));
            row.setFgJobId(patch(rset.getString(columnName = "FG_JOB_ID")));
            row.setGlobalContextMemory(patch(rset.getString(columnName = "GLOBAL_CONTEXT_MEMORY")));
            row.setGlobalUid(patch(rset.getString(columnName = "GLOBAL_UID")));
            row.setHost(patch(rset.getString(columnName = "HOST")));
            row.setIdentificationType(patch(rset.getString(columnName = "IDENTIFICATION_TYPE")));
            row.setInstance(patch(rset.getString(columnName = "INSTANCE")));
            row.setInstanceName(patch(rset.getString(columnName = "INSTANCE_NAME")));
            row.setIpAddress(patch(rset.getString(columnName = "IP_ADDRESS")));
            row.setIsdba(patch(rset.getString(columnName = "ISDBA")));
            row.setLang(patch(rset.getString(columnName = "LANG")));
            row.setLanguage(patch(rset.getString(columnName = "LANGUAGE")));
            row.setModule(patch(rset.getString(columnName = "MODULE")));
            row.setNetworkProtocol(patch(rset.getString(columnName = "NETWORK_PROTOCOL")));
            row.setNlsCalendar(patch(rset.getString(columnName = "NLS_CALENDAR")));
            row.setNlsCurrency(patch(rset.getString(columnName = "NLS_CURRENCY")));
            row.setNslDateFormat(patch(rset.getString(columnName = "NSL_DATE_FORMAT")));
            row.setNlsDateLanguage(patch(rset.getString(columnName = "NLS_DATE_LANGUAGE")));
            row.setNlsSort(patch(rset.getString(columnName = "NLS_SORT")));
            row.setNlsTerritory(patch(rset.getString(columnName = "NLS_TERRITORY")));
            row.setOsUser(patch(rset.getString(columnName = "OS_USER")));
            row.setServerHost(patch(rset.getString(columnName = "SERVER_HOST")));
            row.setServiceName(patch(rset.getString(columnName = "SERVICE_NAME")));
            row.setSessionUser(patch(rset.getString(columnName = "SESSION_USER")));
            row.setSessionUserId(patch(rset.getString(columnName = "SESSION_USER_ID")));
            row.setSessionId(rset.getInt(columnName = "SESSION_ID"));
            row.setSid(rset.getInt(columnName = "SID"));
            row.setTerminal(patch(rset.getString(columnName = "TERMINAL")));
        }

        
        
            catch (java.sql.SQLException s) {
                throw new java.sql.SQLException("error processing column" + columnName + "\n" + s.getMessage());
            }
        } // end of getRow 
	String getSelectText() {
		return selectText;
	}

	/**
	 * Constructs a <code>String</code> with all attributes
	 * in name = value format.
	 *
	 * @return a <code>String</code> representation 
	 * of this object.
	 */
	public String toString()
	{
	    final String TAB = "\n\r";
	   
	    String retValue  = "UserEnvS ( "
	        + super.toString() + TAB
	        + "conn = '" + this.conn + "'" + TAB
	        + "insertBatchSize = '" + this.insertBatchSize + "'" + TAB
	        + "rset = '" + this.rset + "'" + TAB
	        + "selectStmt = '" + this.selectStmt + "'" + TAB
	        + " )";
	
	    return retValue;
	}

}  // end of class
