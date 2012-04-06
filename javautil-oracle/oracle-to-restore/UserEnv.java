package com.dbexperts.oracle;

import java.sql.SQLException;

import oracle.jdbc.OracleConnection;

import com.dbexperts.oracle.dao.UserEnvS;
import com.dbexperts.persistence.PersistenceException;

public class UserEnv
	{
	

	
	public UserEnv(OracleConnection conn) throws SQLException, PersistenceException {
		UserEnvS.get(conn,this);

	}
	 private boolean isLocked ;
   /** Container for the data persisted in ACTION. */
     private String action = null;

   /** Container for the data persisted in AUTHENTICATED_IDENTITY. */
     private String authenticatedIdentity = null;

   /** Container for the data persisted in AUTHENTICATION_DATA. */
     private String authenticationData = null;

   /** Container for the data persisted in AUTHENTICATION_METHOD. */
     private String authenticationMethod = null;

   /** Container for the data persisted in BG_JOB_ID. */
     private String bgJobId = null;

   /** Container for the data persisted in CLIENT_IDENTIFIER. */
     private String clientIdentifier = null;

   /** Container for the data persisted in CLIENT_INFO. */
     private String clientInfo = null;

   /** Container for the data persisted in CURRENT_EDITION_ID. */
     private String currentEditionId = null;

   /** Container for the data persisted in CURRENT_EDITION_NAME. */
     private String currentEditionName = null;

   /** Container for the data persisted in CURRENT_SCHEMA. */
     private String currentSchema = null;

   /** Container for the data persisted in CURRENT_SCHEMA_AID. */
     private String currentSchemaAid = null;

   /** Container for the data persisted in DB_DOMAIN. */
     private String dbDomain = null;

   /** Container for the data persisted in DB_NAME. */
     private String dbName = null;

   /** Container for the data persisted in DB_UNIQUE_NAME. */
     private String dbUniqueName = null;

   /** Container for the data persisted in FG_JOB_ID. */
     private String fgJobId = null;

   /** Container for the data persisted in GLOBAL_CONTEXT_MEMORY. */
     private String globalContextMemory = null;

   /** Container for the data persisted in GLOBAL_UID. */
     private String globalUid = null;

   /** Container for the data persisted in HOST. */
     private String host = null;

   /** Container for the data persisted in IDENTIFICATION_TYPE. */
     private String identificationType = null;

   /** Container for the data persisted in INSTANCE. */
     private String instance = null;

   /** Container for the data persisted in INSTANCE_NAME. */
     private String instanceName = null;

   /** Container for the data persisted in IP_ADDRESS. */
     private String ipAddress = null;

   /** Container for the data persisted in ISDBA. */
     private String isdba = null;

   /** Container for the data persisted in LANG. */
     private String lang = null;

   /** Container for the data persisted in LANGUAGE. */
     private String language = null;

   /** Container for the data persisted in MODULE. */
     private String module = null;

   /** Container for the data persisted in NETWORK_PROTOCOL. */
     private String networkProtocol = null;

   /** Container for the data persisted in NLS_CALENDAR. */
     private String nlsCalendar = null;

   /** Container for the data persisted in NLS_CURRENCY. */
     private String nlsCurrency = null;

   /** Container for the data persisted in NSL_DATE_FORMAT. */
     private String nslDateFormat = null;

   /** Container for the data persisted in NLS_DATE_LANGUAGE. */
     private String nlsDateLanguage = null;

   /** Container for the data persisted in NLS_SORT. */
     private String nlsSort = null;

   /** Container for the data persisted in NLS_TERRITORY. */
     private String nlsTerritory = null;

   /** Container for the data persisted in OS_USER. */
     private String osUser = null;

   /** Container for the data persisted in SERVER_HOST. */
     private String serverHost = null;

   /** Container for the data persisted in SERVICE_NAME.  */
     private String serviceName = null;

   /** Container for the data persisted in SESSION_USER.  */
     private String sessionUser = null;

   /** Container for the data persisted in SESSION_USER_ID. */
     private String sessionUserId = null;

   /** Container for the data persisted in SESSION_ID. */
     private int sessionId;

   /** Container for the data persisted in SID. */
     private int sid;

   /** Container for the data persisted in TERMINAL. */
     private String terminal = null;

    /** Accessor set method for action.
     No validation provided in base method. */
    public void setAction(String val) {
        action = val;
    }

    /** Accessor get method for action. */
    public String getAction() {
        return action;
    }


    /** Accessor set method for authenticatedIdentity.
     No validation provided in base method. */
    public void setAuthenticatedIdentity(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        authenticatedIdentity = val;
    }

    /** Accessor get method for authenticatedIdentity. */
    public String getAuthenticatedIdentity() {
        return authenticatedIdentity;
    }


    /** Accessor set method for authenticationData.
     No validation provided in base method. */
    public void setAuthenticationData(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        authenticationData = val;
    }

    /** Accessor get method for authenticationData. */
    public String getAuthenticationData() {
        return authenticationData;
    }


    /** Accessor set method for authenticationMethod.
     No validation provided in base method. */
    public void setAuthenticationMethod(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        authenticationMethod = val;
    }

    /** Accessor get method for authenticationMethod. */
    public String getAuthenticationMethod() {
        return authenticationMethod;
    }


    /** Accessor set method for bgJobId.
     No validation provided in base method. */
    public void setBgJobId(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        bgJobId = val;
    }

    /** Accessor get method for bgJobId. */
    public String getBgJobId() {
        return bgJobId;
    }


    /** Accessor set method for clientIdentifier.
     No validation provided in base method. */
    public void setClientIdentifier(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        clientIdentifier = val;
    }

    /** Accessor get method for clientIdentifier. */
    public String getClientIdentifier() {
        return clientIdentifier;
    }


    /** Accessor set method for clientInfo.
     No validation provided in base method. */
    public void setClientInfo(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        clientInfo = val;
    }

    /** Accessor get method for clientInfo. */
    public String getClientInfo() {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        return clientInfo;
    }


    /** Accessor set method for currentEditionId.
     No validation provided in base method. */
    public void setCurrentEditionId(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        currentEditionId = val;
    }

    /** Accessor get method for currentEditionId. */
    public String getCurrentEditionId() {
        return currentEditionId;
    }


    /** Accessor set method for currentEditionName.
     No validation provided in base method. */
    public void setCurrentEditionName(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        currentEditionName = val;
    }

    /** Accessor get method for currentEditionName. */
    public String getCurrentEditionName() {
        return currentEditionName;
    }


    /** Accessor set method for currentSchema.
     No validation provided in base method. */
    public void setCurrentSchema(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        currentSchema = val;
    }

    /** Accessor get method for currentSchema. */
    public String getCurrentSchema() {
        return currentSchema;
    }


    /** Accessor set method for currentSchemaAid.
     No validation provided in base method. */
    public void setCurrentSchemaAid(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        currentSchemaAid = val;
    }

    /** Accessor get method for currentSchemaAid. */
    public String getCurrentSchemaAid() {
        return currentSchemaAid;
    }


    /** Accessor set method for dbDomain.
     No validation provided in base method. */
    public void setDbDomain(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        dbDomain = val;
    }

    /** Accessor get method for dbDomain. */
    public String getDbDomain() {
        return dbDomain;
    }


    /** Accessor set method for dbName.
     No validation provided in base method. */
    public void setDbName(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        dbName = val;
    }

    /** Accessor get method for dbName. */
    public String getDbName() {
        return dbName;
    }


    /** Accessor set method for dbUniqueName.
     No validation provided in base method. */
    public void setDbUniqueName(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        dbUniqueName = val;
    }

    /** Accessor get method for dbUniqueName. */
    public String getDbUniqueName() {
        return dbUniqueName;
    }


    /** Accessor set method for fgJobId.
     No validation provided in base method. */
    public void setFgJobId(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        fgJobId = val;
    }

    /** Accessor get method for fgJobId. */
    public String getFgJobId() {
        return fgJobId;
    }


    /** Accessor set method for globalContextMemory.
     No validation provided in base method. */
    public void setGlobalContextMemory(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        globalContextMemory = val;
    }

    /** Accessor get method for globalContextMemory. */
    public String getGlobalContextMemory() {
        return globalContextMemory;
    }


    /** Accessor set method for globalUid.
     No validation provided in base method. */
    public void setGlobalUid(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        globalUid = val;
    }

    /** Accessor get method for globalUid. */
    public String getGlobalUid() {
        return globalUid;
    }


    /** Accessor set method for host.
     No validation provided in base method. */
    public void setHost(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        host = val;
    }

    /** Accessor get method for host. */
    public String getHost() {
        return host;
    }


    /** Accessor set method for identificationType.
     No validation provided in base method. */
    public void setIdentificationType(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        identificationType = val;
    }

    /** Accessor get method for identificationType. */
    public String getIdentificationType() {
        return identificationType;
    }


    /** Accessor set method for instance.
     No validation provided in base method. */
    public void setInstance(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        instance = val;
    }

    /** Accessor get method for instance. */
    public String getInstance() {
        return instance;
    }


    /** Accessor set method for instanceName.
     No validation provided in base method. */
    public void setInstanceName(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        instanceName = val;
    }

    /** Accessor get method for instanceName. */
    public String getInstanceName() {
        return instanceName;
    }


    /** Accessor set method for ipAddress.
     No validation provided in base method. */
    public void setIpAddress(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        ipAddress = val;
    }

    /** Accessor get method for ipAddress. */
    public String getIpAddress() {
        return ipAddress;
    }


    /** Accessor set method for isdba.
     No validation provided in base method. */
    public void setIsdba(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        isdba = val;
    }

    /** Accessor get method for isdba. */
    public String getIsdba() {
        return isdba;
    }


    /** Accessor set method for lang.
     No validation provided in base method. */
    public void setLang(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        lang = val;
    }

    /** Accessor get method for lang. */
    public String getLang() {
        return lang;
    }


    /** Accessor set method for language.
     No validation provided in base method. */
    public void setLanguage(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        language = val;
    }

    /** Accessor get method for language. */
    public String getLanguage() {
        return language;
    }


    /** Accessor set method for module.
     No validation provided in base method. */
    public void setModule(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        module = val;
    }

    /** Accessor get method for module. */
    public String getModule() {
        return module;
    }


    /** Accessor set method for networkProtocol.
     No validation provided in base method. */
    public void setNetworkProtocol(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        networkProtocol = val;
    }

    /** Accessor get method for networkProtocol. */
    public String getNetworkProtocol() {
        return networkProtocol;
    }


    /** Accessor set method for nlsCalendar.
     No validation provided in base method. */
    public void setNlsCalendar(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        nlsCalendar = val;
    }

    /** Accessor get method for nlsCalendar. */
    public String getNlsCalendar() {
        return nlsCalendar;
    }


    /** Accessor set method for nlsCurrency.
     No validation provided in base method. */
    public void setNlsCurrency(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        nlsCurrency = val;
    }

    /** Accessor get method for nlsCurrency. */
    public String getNlsCurrency() {
        return nlsCurrency;
    }


    /** Accessor set method for nslDateFormat.
     No validation provided in base method. */
    public void setNslDateFormat(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        nslDateFormat = val;
    }

    /** Accessor get method for nslDateFormat. */
    public String getNslDateFormat() {
        return nslDateFormat;
    }


    /** Accessor set method for nlsDateLanguage.
     No validation provided in base method. */
    public void setNlsDateLanguage(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        nlsDateLanguage = val;
    }

    /** Accessor get method for nlsDateLanguage. */
    public String getNlsDateLanguage() {
        return nlsDateLanguage;
    }


    /** Accessor set method for nlsSort.
     No validation provided in base method. */
    public void setNlsSort(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        nlsSort = val;
    }

    /** Accessor get method for nlsSort. */
    public String getNlsSort() {
        return nlsSort;
    }


    /** Accessor set method for nlsTerritory.
     No validation provided in base method. */
    public void setNlsTerritory(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        nlsTerritory = val;
    }

    /** Accessor get method for nlsTerritory. */
    public String getNlsTerritory() {
        return nlsTerritory;
    }


    /** Accessor set method for osUser.
     No validation provided in base method. */
    public void setOsUser(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        osUser = val;
    }

    /** Accessor get method for osUser. */
    public String getOsUser() {
        return osUser;
    }


    /** Accessor set method for serverHost.
     No validation provided in base method. */
    public void setServerHost(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        serverHost = val;
    }

    /** Accessor get method for serverHost. */
    public String getServerHost() {
        return serverHost;
    }


    /** Accessor set method for serviceName.
     No validation provided in base method. */
    public void setServiceName(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        serviceName = val;
    }

    /** Accessor get method for serviceName. */
    public String getServiceName() {
        return serviceName;
    }


    /** Accessor set method for sessionUser.
     No validation provided in base method. */
    public void setSessionUser(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        sessionUser = val;
    }

    /** Accessor get method for sessionUser. */
    public String getSessionUser() {
        return sessionUser;
    }


    /** Accessor set method for sessionUserId.
     No validation provided in base method. */
    public void setSessionUserId(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        sessionUserId = val;
    }

    /** Accessor get method for sessionUserId. */
    public String getSessionUserId() {
        return sessionUserId;
    }


    /** Accessor set method for sessionId.
     No validation provided in base method. */
    public void setSessionId(int sessionId) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        this.sessionId = sessionId;
    }

    /** Accessor get method for sessionId. */
    public int getSessionId() {
        return sessionId;
    }


    /** Accessor set method for sid.
     No validation provided in base method. */
    public void setSid(int sid) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        this.sid = sid;
    }

    /** Accessor get method for sid. */
    public int getSid() {
        return sid;
    }


    /** Accessor set method for terminal.
     No validation provided in base method. */
    public void setTerminal(String val) {
    	if (isLocked) {
    		throw new UnsupportedOperationException("immutable");
    	}
        terminal = val;
    }

    /** Accessor get method for terminal. */
    public String getTerminal() {
        return terminal;
    }

    /**
     * Prevent any future modification.
     */
    public void lock() {
    	isLocked = true;
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
	    final String TAB = System.getProperty("line.separator");
	   
	    System.out.println("authentication '" + authenticationMethod + "'");
	    
	    String retValue  = "UserEnv ( "
	        + super.toString() + TAB
	        + "action = " + ((this.action == null) ? "null" : ("'" + this.action + "'")) + TAB
	        + "authenticatedIdentity = " + ((this.authenticatedIdentity == null) ? "null" : ("'" + this.authenticatedIdentity + "'")) + TAB
	        + "authenticationData = " + ((this.authenticationData == null) ? "null" : ("'" + this.authenticationData + "'")) + TAB
	        + "authenticationMethod = '" +  this.authenticationMethod + "'" + TAB
	        + "bgJobId = " + ((this.bgJobId == null) ? "null" : ("'" + this.bgJobId + "'")) + TAB
	        + "clientIdentifier = " + ((this.clientIdentifier == null) ? "null" : ("'" + this.clientIdentifier + "'")) + TAB
	        + "clientInfo = " + ((this.clientInfo == null) ? "null" : ("'" + this.clientInfo + "'")) + TAB
	        + "currentEditionId = " + ((this.currentEditionId == null) ? "null" : ("'" + this.currentEditionId + "'")) + TAB
	        + "currentEditionName = " + ((this.currentEditionName == null) ? "null" : ("'" + this.currentEditionName + "'")) + TAB
	        + "currentSchema = " + ((this.currentSchema == null) ? "null" : ("'" + this.currentSchema + "'")) + TAB
	        + "currentSchemaAid = " + ((this.currentSchemaAid == null) ? "null" : ("'" + this.currentSchemaAid + "'")) + TAB
	        + "dbDomain = " + ((this.dbDomain == null) ? "null" : ("'" + this.dbDomain + "'")) + TAB
	        + "dbName = " + ((this.dbName == null) ? "null" : ("'" + this.dbName + "'")) + TAB
	        + "dbUniqueName = " + ((this.dbUniqueName == null) ? "null" : ("'" + this.dbUniqueName + "'")) + TAB
	        + "fgJobId = " + ((this.fgJobId == null) ? "null" : ("'" + this.fgJobId + "'")) + TAB
	        + "globalContextMemory = " + ((this.globalContextMemory == null) ? "null" : ("'" + this.globalContextMemory + "'")) + TAB
	        + "globalUid = " + ((this.globalUid == null) ? "null" : ("'" + this.globalUid + "'")) + TAB
	        + "host = " + ((this.host == null) ? "null" : ("'" + this.host + "'")) + TAB
	        + "identificationType = " + ((this.identificationType == null) ? "null" : ("'" + this.identificationType + "'")) + TAB
	        + "instance = " + ((this.instance == null) ? "null" : ("'" + this.instance + "'")) + TAB
	        + "instanceName = " + ((this.instanceName == null) ? "null" : ("'" + this.instanceName + "'")) + TAB
	        + "ipAddress = " + ((this.ipAddress == null) ? "null" : ("'" + this.ipAddress + "'")) + TAB
	        + "isdba = " + ((this.isdba == null) ? "null" : ("'" + this.isdba + "'")) + TAB
	        + "lang = " + ((this.lang == null) ? "null" : ("'" + this.lang + "'")) + TAB
	        + "language = " + ((this.language == null) ? "null" : ("'" + this.language + "'")) + TAB
	        + "module = " + ((this.module == null) ? "null" : ("'" + this.module + "'")) + TAB
	        + "networkProtocol = " + ((this.networkProtocol == null) ? "null" : ("'" + this.networkProtocol + "'")) + TAB
	        + "nlsCalendar = " + ((this.nlsCalendar == null) ? "null" : ("'" + this.nlsCalendar + "'")) + TAB
	        + "nlsCurrency = " + ((this.nlsCurrency == null) ? "null" : ("'" + this.nlsCurrency + "'")) + TAB
	        + "nlsDateLanguage = " + ((this.nlsDateLanguage == null) ? "null" : ("'" + this.nlsDateLanguage + "'")) + TAB
	        + "nlsSort = " + ((this.nlsSort == null) ? "null" : ("'" + this.nlsSort + "'")) + TAB
	        + "nlsTerritory = " + ((this.nlsTerritory == null) ? "null" : ("'" + this.nlsTerritory + "'")) + TAB
	        + "nslDateFormat = " + ((this.nslDateFormat == null) ? "null" : ("'" + this.nslDateFormat + "'")) + TAB
	        + "osUser = " + ((this.osUser == null) ? "null" : ("'" + this.osUser + "'")) + TAB
	        + "serverHost = " + ((this.serverHost == null) ? "null" : ("'" + this.serverHost + "'")) + TAB
	        + "serviceName = " + ((this.serviceName == null) ? "null" : ("'" + this.serviceName + "'")) + TAB
	        + "sessionId = " + this.sessionId  + TAB
	        + "sessionUser = " + ((this.sessionUser == null) ? "null" : ("'" + this.sessionUser + "'")) + TAB
	        + "sessionUserId = " + ((this.sessionUserId == null) ? "null" : ("'" + this.sessionUserId + "'")) + TAB
	        + "sid = " + this.sid + TAB
	        + "terminal = " + ((this.terminal == null) ? "null" : ("'" + this.terminal + "'")) + TAB
	        + " )";
	 
	    return retValue;
	}
}
