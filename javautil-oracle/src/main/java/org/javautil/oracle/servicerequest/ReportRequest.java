package org.javautil.oracle.servicerequest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

public class ReportRequest implements Runnable {
	private static final String className = "com.javautil.oracle.ReportRequest";

	private static final Logger logger = Logger.getLogger(className);
	private static HashMap<String, Integer> urlMap = new HashMap<String, Integer>();
	private String reportServerName = null;
	private String applicationPath = null;
	private String reportServerConfigurationEntry = null;
	private String reportServerUrl = null;
	private String reportServerPort = null;
	ArrayList<RequestParameter> arguments = new ArrayList<RequestParameter>();
	private static HashMap<String, String> reportParms = new HashMap<String, String>();

	String argArray[] = null;

	public ReportRequest(final RequestArgumentParser reportParser) {
		this.setParameters(reportParser);
	}

	public ReportRequest() {
	}

	public void addParameter(final RequestParameter parm) {
		arguments.add(parm);
	}

	public String getUrlString() throws java.io.UnsupportedEncodingException {
		checkState();
		final StringBuffer buff = new StringBuffer();

		final StringBuffer argList = new StringBuffer();

		boolean needAmp = false;
		final StringBuilder urlBuffer = new StringBuilder();

		needAmp = false;

		if (reportParms.size() > 0) {
			for (final String key : reportParms.keySet()) {
				if (needAmp) {
					argList.append("&");
				}
				argList.append(URLEncoder.encode(key, "UTF-8"));
				argList.append("=");
				argList.append(URLEncoder.encode(reportParms.get(key)));
				needAmp = true;
			}
		}
		urlBuffer.append("http://" + reportServerName + ":" + reportServerPort
				+ applicationPath + "?" + reportServerConfigurationEntry);

		if (argList != null) {
			urlBuffer.append("&").append(new String(argList));
		}

		buff.append("<reportUrlString>" + urlBuffer + "</reportUrlString>");
		logger.info(new String(buff));
		return urlBuffer.toString();
	}

	public void issue() throws java.net.MalformedURLException,
			java.io.IOException, java.lang.IllegalStateException {
		final StringBuffer buff = new StringBuffer(4096);

		final String urlString = getUrlString();
		// URL url = new URL(urlString);
		// URLConnection connection = url.openConnection();

		// BufferedReader in = new BufferedReader(new
		// InputStreamReader(connection.getInputStream()));
		final URL url = leaseURL(urlString);
		final BufferedReader in = new BufferedReader(new InputStreamReader(
				url.openStream()));
		String inputLine;

		while ((inputLine = in.readLine()) != null) {
			buff.append(inputLine);
		}
		in.close();
		returnURL(urlString);

		final String output = new String(buff);

		if (output.indexOf("ORA-") > -1 || output.indexOf("REP-") > -1) {
			logger.warn(output);
			throw new java.lang.IllegalStateException(
					"Unable to produce report");
		}

	}

	@Override
	public void run() {
		try {
			issue();
		} catch (final Exception e) {
		}
	}

	public void setParameters(final RequestArgumentParser parser) {
		for (final RequestParameter rq : parser.getParameters()) {
			arguments.add(rq);
		}
	}

	public void writeReport(final OutputStream out) throws java.io.IOException {
		int numRead = 0;
		InputStream in;
		final int buffsize = 16 * 1024;
		// int bytesRead = 0;
		final byte[] inB = new byte[buffsize];
		// URL u = new URL(getUrlString());
		final String urlString = getUrlString();
		final URL u = leaseURL(urlString);
		in = u.openStream();
		while (true) {
			// bytesRead +=
			numRead = in.read(inB, 0, buffsize);
			if (numRead < 0) {
				break;
			}

			String test = new String(inB);

			test = test.substring(0, numRead);

			if (test.indexOf("ORA-") > -1 || test.indexOf("REP-") > -1) {
				logger.warn(test);
				throw new java.lang.IllegalStateException(
						"Unable to produce report");
			}

			out.write(inB, 0, numRead);
		}
		in.close();
		returnURL(urlString);
		out.close();
	}

	/*
	 * static void forwardPdf(String url, java.io.OutputStream pw) {
	 * OutputStream out; int numRead = 0; InputStream in; PrintStream ps; byte[]
	 * inB = new byte[10240]; try { logger.debug("Report called using: " + url);
	 * //URL u = new URL(url); URL u = leaseURL(url); in = u.openStream(); while
	 * (true) { numRead = in.read(inB, 0, 10240); if (numRead < 0) break;
	 * 
	 * String test = new String(inB);
	 * 
	 * test = test.substring(0, numRead);
	 * 
	 * if (test.indexOf("ORA-") > -1 || test.indexOf("REP-") > -1) {
	 * logger.warn(test); throw new
	 * java.lang.IllegalStateException("Unable to produce report"); }
	 * pw.write(inB, 0, numRead); } in.close(); returnURL(url);
	 * 
	 * pw.flush(); } catch (Exception e) {
	 * logger.error("Exception called while calling report: " + url +
	 * e.getMessage()); e.printStackTrace(); } }
	 */

	private synchronized URL leaseURL(final String url)
			throws java.net.MalformedURLException {
		final Thread t = Thread.currentThread();

		// Parse the URL string to get the host/port
		final int i = url.indexOf("//") + 2;

		final String hostPort = url.substring(i, url.indexOf("/", i));

		Integer useCount = urlMap.get(hostPort);

		if (useCount == null) {
			urlMap.put(hostPort, new Integer(1));
		} else {
			if (useCount.intValue() > 15) {
				System.out.println("Maximum ReportRequest connections to '"
						+ hostPort + "' hit.  " + t.getName() + " (" + t
						+ ") Waiting for a free slot.");
			}
			while (useCount.intValue() > 15) {
				try {
					Thread.sleep(15000); // Wait 15 sec (1 sec/request and 15
											// requests...
				} catch (final java.lang.InterruptedException ie) {
				}
				useCount = urlMap.get(hostPort);
				if (useCount.intValue() > 15) {
					System.out.println("ReportRequest Thread " + t.getName()
							+ " (" + t.toString()
							+ ") still waiting for a free slot to '" + hostPort
							+ "'. " + useCount.intValue()
							+ " connections in use.");
				}

			}
			useCount = new Integer(useCount.intValue() + 1);
			urlMap.put(hostPort, useCount);
		}
		return new URL(url);
	}

	private synchronized void returnURL(final String url) {
		final int i = url.indexOf("//") + 2;

		final Thread t = Thread.currentThread();

		final String hostPort = url.substring(i, url.indexOf("/", i));

		Integer useCount = urlMap.get(hostPort);

		System.out.println("ReportRequest Thread " + t.getName() + " ("
				+ t.toString() + ") releasing slot to '" + hostPort + "'. "
				+ useCount.intValue() + " connections in use before release.");

		// if (useCount!=null) {
		if (useCount.intValue() > 0) {
			useCount = new Integer(useCount.intValue() - 1);
			urlMap.put(hostPort, useCount);
		}
		// }
	}

	public String getReportServerName() {
		return reportServerName;
	}

	public void setReportServerName(final String reportServerName) {
		this.reportServerName = reportServerName;
	}

	public String getApplicationPath() {
		return applicationPath;
	}

	public void setApplicationPath(final String applicationPath) {
		this.applicationPath = applicationPath;
	}

	public String getReportServerConfigurationEntry() {
		return reportServerConfigurationEntry;
	}

	public void setReportServerConfigurationEntry(
			final String reportServerConfigurationEntry) {
		this.reportServerConfigurationEntry = reportServerConfigurationEntry;
	}

	public String getReportServerUrl() {
		return reportServerUrl;
	}

	public void setReportServerUrl(final String reportServerUrl) {
		this.reportServerUrl = reportServerUrl;
	}

	public String getReportServerPort() {
		return reportServerPort;
	}

	public void setReportServerPort(final String reportServerPort) {
		this.reportServerPort = reportServerPort;
	}

	public HashMap<String, String> getReportParms() {
		return reportParms;
	}

	public void setReportParms(final HashMap<String, String> reportParms) {
		ReportRequest.reportParms = reportParms;
	}

	private void checkState() {
		if (reportServerName == null) {
			throw new java.lang.IllegalStateException(
					"reportServerName has not been set");
		}
		if (applicationPath == null) {
			throw new java.lang.IllegalStateException(
					"applicationPath has not been set");
		}
		if (reportServerConfigurationEntry == null) {
			throw new java.lang.IllegalStateException(
					"reportServerConfigurationEntry has not been set");
		}
		if (reportServerUrl == null) {
			throw new java.lang.IllegalStateException(
					"reportServerUrl has not been set");
		}
		if (reportServerPort == null) {
			throw new java.lang.IllegalStateException(
					"reportServerPort has not been set");
		}
	}
}
