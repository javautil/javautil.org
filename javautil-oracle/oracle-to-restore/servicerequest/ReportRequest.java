package com.dbexperts.oracle.servicerequest;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;



public class ReportRequest implements Runnable {
    private static final String className = "com.javautil.oracle.ReportRequest";

    private static final Logger logger = Logger.getLogger(className);
    private static HashMap<String,Integer> urlMap = new HashMap<String,Integer>();
    String reportServerName = null;
    String applicationPath = null;
    String reportServerConfigurationEntry = null;
    ArrayList<RequestParameter> arguments = new ArrayList<RequestParameter>();

    String argArray [] = null;

    public ReportRequest(final RequestArgumentParser reportParser) {
        this.setParameters(reportParser);
    }


    public void addParameter(final RequestParameter parm) {
        arguments.add(parm);
    }

    public String getUrlString()
            throws java.io.UnsupportedEncodingException {
        final StringBuffer buff = new StringBuffer();

        final StringBuffer argList = new StringBuffer();

        boolean needAmp = false;
        final Iterator<RequestParameter> it = arguments.iterator();
        String reportServerPort = "80";
        String urlString = null;

        while (it.hasNext()) {
            final RequestParameter arg = it.next();

            logger.debug("ReportRequest.getUrlString() was passed '"+arg.getName()+"':'"+arg.getValue()+"'");

            if (arg.getName().equals("reportServerURL")) {
                urlString = arg.getValue();
            } else if (arg.getName().equals("applicationPath")) {
                applicationPath = arg.getValue();
                buff.append("applicationPath: " + applicationPath + "\n");
            } else if (arg.getName().equals("reportServer")) {
                reportServerName = arg.getValue();
                buff.append("reportServer: " + reportServerName + "\n");

            } else if (arg.getName().equals("reportServerConfigurationEntry")) {
                reportServerConfigurationEntry = arg.getValue();
                buff.append("reportServerConfigurationEntry: " + reportServerConfigurationEntry + "\n");

            } else if (arg.getName().equals("reportServerPort")) {
                reportServerPort = arg.getValue();
                buff.append("reportServerPort: " + reportServerPort + "\n");

            } else {
                if (needAmp) {
                    argList.append("&");
                }
                argList.append(URLEncoder.encode(arg.getName(), "UTF-8"));
                argList.append("=");
                argList.append(URLEncoder.encode("\"" + arg.getValue() + "\"", "UTF-8"));
                buff.append(arg.getName() + ": " + arg.getValue() + "\n");
                needAmp = true;
            }
        }

        if (urlString==null) {
            if (applicationPath == null) {
                throw new java.lang.IllegalStateException("applicationPath is null");
            }
            if (reportServerName == null) {
                throw new java.lang.IllegalStateException("reportServer is null");
            }

            urlString = "http://" + reportServerName + ":" + reportServerPort + applicationPath + "?" + reportServerConfigurationEntry + "&";
        }

        urlString = urlString + new String(argList);

        buff.append("<reportUrlString>" + urlString + "</reportUrlString>");
        logger.info(new String(buff));
        return urlString;

    }

    public void issue()
            throws java.net.MalformedURLException, java.io.IOException, java.lang.IllegalStateException {
        final StringBuffer buff = new StringBuffer(4096);

        final String urlString = getUrlString();
        //URL url = new URL(urlString);
        //URLConnection connection = url.openConnection();

        //BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        final URL url = leaseURL(urlString);
        final BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            buff.append(inputLine);
        }
        in.close();
        returnURL(urlString);

        final String output = new String(buff);

        if (output.indexOf("ORA-") > -1 || output.indexOf("REP-") > -1) {
            logger.warn(output);
            throw new java.lang.IllegalStateException("Unable to produce report");
        }



    }

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

    public void writeToFile(final BufferedOutputStream out)
            throws java.io.IOException {
        int numRead = 0;
        InputStream in;
        final int buffsize = 16 * 1024;
// int bytesRead =  0;
        final byte[] inB = new byte[buffsize];
        //URL u = new URL(getUrlString());
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
                throw new java.lang.IllegalStateException("Unable to produce report");
            }

            out.write(inB, 0, numRead);
        }
        in.close();
        returnURL(urlString);
        out.close();
    }

    /*
    static void forwardPdf(String url, java.io.OutputStream pw) {
        OutputStream out;
        int numRead = 0;
        InputStream in;
        PrintStream ps;
        byte[] inB = new byte[10240];
        try {
            logger.debug("Report called using: " + url);
            //URL u = new URL(url);
            URL u = leaseURL(url);
            in = u.openStream();
            while (true) {
                numRead = in.read(inB, 0, 10240);
                if (numRead < 0)
                    break;

                String test = new String(inB);

                test = test.substring(0, numRead);

                if (test.indexOf("ORA-") > -1 || test.indexOf("REP-") > -1) {
                    logger.warn(test);
                    throw new java.lang.IllegalStateException("Unable to produce report");
                }
                pw.write(inB, 0, numRead);
            }
            in.close();
            returnURL(url);

            pw.flush();
        } catch (Exception e) {
            logger.error("Exception called while calling report: " + url + e.getMessage());
            e.printStackTrace();
        }
    }
    */

    private synchronized URL leaseURL(final String url)
    throws java.net.MalformedURLException
    {
        final Thread t = Thread.currentThread();

        // Parse the URL string to get the host/port
        final int i = url.indexOf("//") + 2;

        final String hostPort = url.substring(i, url.indexOf("/",i));

        Integer useCount = urlMap.get(hostPort);

        if (useCount==null) {
            urlMap.put(hostPort,new Integer(1));
        } else {
            if (useCount.intValue()>15) {
                System.out.println("Maximum ReportRequest connections to '" + hostPort + "' hit.  " + t.getName() + " (" + t + ") Waiting for a free slot.");
            }
            while (useCount.intValue()>15) {
                try {
                    Thread.sleep(15000); // Wait 15 sec (1 sec/request and 15 requests...
                } catch (final java.lang.InterruptedException ie) {
                }
                useCount = urlMap.get(hostPort);
                if (useCount.intValue()>15) {
                    System.out.println("ReportRequest Thread " + t.getName() + " (" + t.toString() + ") still waiting for a free slot to '" + hostPort + "'. " + useCount.intValue() + " connections in use.");
                }

            }
            useCount = new Integer(useCount.intValue() + 1);
            urlMap.put(hostPort,useCount);
        }
        return new URL(url);
    }

    private synchronized void returnURL(final String url) {
        final int i = url.indexOf("//") + 2;

        final Thread t = Thread.currentThread();

        final String hostPort = url.substring(i, url.indexOf("/",i));

        Integer useCount = urlMap.get(hostPort);

        System.out.println("ReportRequest Thread " + t.getName() + " (" + t.toString() + ") releasing slot to '" + hostPort + "'. " + useCount.intValue() + " connections in use before release.");

  //      if (useCount!=null)   {
            if (useCount.intValue() > 0 ) {
                useCount = new Integer(useCount.intValue() - 1);
                urlMap.put(hostPort,useCount);
            }
    //    }
    }
}

