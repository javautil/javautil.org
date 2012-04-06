package org.javautil.util;

import java.net.InetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author bcm
 * 
 */
public class ApplicationInfo implements Cloneable {

	private static Log logger = LogFactory.getLog(ApplicationInfo.class);

	private static String localHostname;

	static {
		try {
			InetAddress localhost = InetAddress.getLocalHost();
			localHostname = localhost.getHostName();
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public static String getLocalHostname() {
		return localHostname;
	}

	private String applicationName = null;

	private String userName = null;

	private Object uniqueIdentifier = null;

	private String hostName = null;

	private String ipAddress = null;

	private String moduleName = null;

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Object getUniqueIdentifier() {
		return uniqueIdentifier;
	}

	public void setUniqueIdentifier(Object uniqueIdentifier) {
		this.uniqueIdentifier = uniqueIdentifier;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

}
