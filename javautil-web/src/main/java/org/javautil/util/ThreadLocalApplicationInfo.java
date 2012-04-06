package org.javautil.util;

/**
 * 
 * @author bcm
 * 
 */

public class ThreadLocalApplicationInfo {

	private ThreadLocalApplicationInfo() {
	}

	private static ThreadLocal<ApplicationInfo> appInfos = new ThreadLocal<ApplicationInfo>();

	public static ApplicationInfo getApplicationInfo() {
		return appInfos.get();
	}

	public static void setApplicationInfo(ApplicationInfo appInfo) {
		appInfos.set(appInfo);
	}

}
