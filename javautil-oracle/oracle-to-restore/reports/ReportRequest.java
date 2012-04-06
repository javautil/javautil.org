package com.dbexperts.oracle.reports;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;



public class ReportRequest {
//implements Runnable {
//    private static final String className = "com.javautil.oracle.ReportRequest";
//
//    private static final Logger logger = Logger.getLogger(className);
//    private static HashMap<String,Integer> urlMap = new HashMap<String,Integer>();
//    String reportServerName = null;
//    String applicationPath = null;
//    String reportServerConfigurationEntry = null;
//    private Map <String,String> arguments ;
//
//    String argArray [] = null;
//
//    public ReportRequest(Map<String,String> arguments) {
//        this.setParameters(arguments);
//    }
//
//
//    public void addParameter(RequestParameter parm) {
//        arguments.add(parm);
//    }
//
//    public String getUrlString()
//            throws java.io.UnsupportedEncodingException {
//        StringBuilder buff = new StringBuilder();
//
//
//
//        boolean needAmp = false;
//     
//        String reportServerPort = "80";
//        String urlString = null;
//
//
//
//           
//
//            urlString = arguments.get("reportServerURL");
//             
//            applicationPath = arguments.get("applicationPath");
//           
//            reportServerName = arguments.get("reportServer");
//            reportServerConfigurationEntry = arguments.get("reportServerConfigurationEntry");
//  
//      
//                reportServerPort = arguments.get("reportServerPort");
//                buff.append("reportServerPort: " + reportServerPort + "\n");
//
//   
//
//            urlString = "http://" + reportServerName + ":" + reportServerPort + applicationPath + "?" + reportServerConfigurationEntry + "&";
//        
//        urlString = "http://" + reportServerName + ":" + reportServerPort + applicationPath + "?" + reportServerConfigurationEntry + "&";
//        urlString = urlString + new String(argList);
//
//   
//        return urlString;
//
//    }
//
//    public String encodePair(String name, String value) {
//    	StringBuilder b = new StringBuilder();
//        b.append(URLEncoder.encode(name, "UTF-8"));
//        b.append("=");
//        b.append(URLEncoder.encode("\"" + value + "\"", "UTF-8"));
//    }
//    
//    
//    public void issue()
//            throws java.net.MalformedURLException, java.io.IOException, java.lang.IllegalStateException {
//        StringBuffer buff = new StringBuffer(4096);
//        logger.entering(className, "issue");
//        String urlString = getUrlString();
//        //URL url = new URL(urlString);
//        //URLConnection connection = url.openConnection();
//
//        //BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//        URL url = leaseURL(urlString);
//        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
//        String inputLine;
//
//        while ((inputLine = in.readLine()) != null) {
//            buff.append(inputLine);
//        }
//        in.close();
//        returnURL(urlString);
//
//        String output = new String(buff);
//
//        if (output.indexOf("ORA-") > -1 || output.indexOf("REP-") > -1) {
//            logger.warning(output);
//            throw new java.lang.IllegalStateException("Unable to produce report");
//        }
//
//        // logger.info(new String (buff));
//        logger.exiting(className, "issue");
//
//    }
//
//    public void run() {
//        try {
//            issue();
//        } catch (Exception e) {
//        }
//    }
//
//    public void setParameters(Map<String,String> parser) {
//    	for (RequestParameter rq : parser.getParameters()) {
//            arguments.add(rq);
//        }
//    }
//
//    public void writeToFile(BufferedOutputStream out)
//            throws java.io.IOException {
//        int numRead = 0;
//        InputStream in;
//        int buffsize = 16 * 1024;
//// int bytesRead =  0;
//        byte[] inB = new byte[buffsize];
//        //URL u = new URL(getUrlString());
//        String urlString = getUrlString();
//        URL u = leaseURL(urlString);
//        in = u.openStream();
//        while (true) {
//// bytesRead +=
//            numRead = in.read(inB, 0, buffsize);
//            if (numRead < 0)
//                break;
//
//            String test = new String(inB);
//
//            test = test.substring(0, numRead);
//
//            if (test.indexOf("ORA-") > -1 || test.indexOf("REP-") > -1) {
//                logger.warning(test);
//                throw new java.lang.IllegalStateException("Unable to produce report");
//            }
//
//            out.write(inB, 0, numRead);
//        }
//        in.close();
//        returnURL(urlString);
//        out.close();
//    }
//
//    /*
//    static void forwardPdf(String url, java.io.OutputStream pw) {
//        OutputStream out;
//        int numRead = 0;
//        InputStream in;
//        PrintStream ps;
//        byte[] inB = new byte[10240];
//        try {
//            logger.fine("Report called using: " + url);
//            //URL u = new URL(url);
//            URL u = leaseURL(url);
//            in = u.openStream();
//            while (true) {
//                numRead = in.read(inB, 0, 10240);
//                if (numRead < 0)
//                    break;
//
//                String test = new String(inB);
//
//                test = test.substring(0, numRead);
//
//                if (test.indexOf("ORA-") > -1 || test.indexOf("REP-") > -1) {
//                    logger.warning(test);
//                    throw new java.lang.IllegalStateException("Unable to produce report");
//                }
//                pw.write(inB, 0, numRead);
//            }
//            in.close();
//            returnURL(url);
//
//            pw.flush();
//        } catch (Exception e) {
//            logger.severe("Exception called while calling report: " + url + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//    */
//
//    private synchronized URL leaseURL(String url)
//    throws java.net.MalformedURLException
//    {
//        Thread t = Thread.currentThread();
//
//        // Parse the URL string to get the host/port
//        int i = url.indexOf("//") + 2;
//
//        String hostPort = url.substring(i, url.indexOf("/",i));
//
//        Integer useCount = urlMap.get(hostPort);
//
//        if (useCount==null) {
//            urlMap.put(hostPort,new Integer(1));
//        } else {
//            if (useCount.intValue()>15) {
//                System.out.println("Maximum ReportRequest connections to '" + hostPort + "' hit.  " + t.getName() + " (" + t + ") Waiting for a free slot.");
//            }
//            while (useCount.intValue()>15) {
//                try {
//                    Thread.sleep(15000); // Wait 15 sec (1 sec/request and 15 requests...
//                } catch (java.lang.InterruptedException ie) {
//                }
//                useCount = urlMap.get(hostPort);
//                if (useCount.intValue()>15) {
//                    System.out.println("ReportRequest Thread " + t.getName() + " (" + t.toString() + ") still waiting for a free slot to '" + hostPort + "'. " + useCount.intValue() + " connections in use.");
//                }
//
//            }
//            useCount = new Integer(useCount.intValue() + 1);
//            urlMap.put(hostPort,useCount);
//        }
//        return new URL(url);
//    }
//
//    private synchronized void returnURL(String url) {
//        int i = url.indexOf("//") + 2;
//
//        Thread t = Thread.currentThread();
//
//        String hostPort = url.substring(i, url.indexOf("/",i));
//
//        Integer useCount = urlMap.get(hostPort);
//
//        System.out.println("ReportRequest Thread " + t.getName() + " (" + t.toString() + ") releasing slot to '" + hostPort + "'. " + useCount.intValue() + " connections in use before release.");
//
//  //      if (useCount!=null)   {
//            if (useCount.intValue() > 0 ) {
//                useCount = new Integer(useCount.intValue() - 1);
//                urlMap.put(hostPort,useCount);
//            }
//    //    }
//    }
}

