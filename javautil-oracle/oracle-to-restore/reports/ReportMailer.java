//package com.javautil.OracleServices;
//
////import com.javautil.oracle.ReportRequest;
//import com.javautil.jdbc.DataSource;
//import com.javautil.oracle.serviceRequest.ReportRequest;
//import com.javautil.oracle.serviceRequest.RequestArgumentParser;
//import com.javautil.oracle.serviceRequest.RequestThread;
//import com.javautil.property.PropertyManagement;
//import com.javautil.property.PropertyManager;
//import com.javautil.util.Mailer;
////import com.javautil.util.RequestArgumentParser;
////import com.javautil.util.RequestThread;
//
//import java.io.BufferedOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.util.ArrayList;
//import java.util.logging.Logger;
//
///**
// */
// //@todo restore this to work under javautil
//public class ReportMailer extends RequestThread {
//   
//    private  Logger logger = Logger.getLogger(this.getClass().getName());
//    private static ArrayList requests = new ArrayList();
//    private String tempFileName = null;
//
//    /**
//     *
//     * @param request
//     * @exception java.io.IOException
//     * @exception javax.mail.MessagingException
//     */
//    synchronized private void processRequest(String request)
//            throws java.io.IOException, javax.mail.MessagingException {
//        String reportArguments = getParameter("report");
//        System.out.println("report request " + reportArguments);
//        RequestArgumentParser reportParser = new RequestArgumentParser(reportArguments, true);
//        String body = getBody(request);
//        createReportFile(reportParser);
//        RequestArgumentParser bodyParser = new RequestArgumentParser(getArguments());
//
//        sendMessage(bodyParser, body);
//        System.out.println("report has been sent");
//    }
//
//    String getBody(String request) {
//        return new String("");
//    }
//
//    /**
//     * Perpetually waiting for processRequest() invocations
//     */
//    public void run() {
//        try {
//            processRequest(arguments);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//
//     */
//    private void createReportFile(RequestArgumentParser reportParser)
//            throws java.io.IOException {
//       PropertyManagement properties = getPropertyManager();
//       String tempDir = properties.getProperty("temporaryDirectory");
//        //ReportRequest report = new ReportRequest(reportParser,getPropertyManager());
//        ReportRequest report = new ReportRequest(reportParser);
//
//        File outFile = null;
//        if (tempDir != null) {
//            File dir = new File(tempDir);
//            outFile = File.createTempFile("fax", ".pdf", dir);
//        } else {
//            outFile = File.createTempFile("fax", ".pdf");
//        }
//        tempFileName = outFile.getAbsolutePath();
//
//        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outFile));
//        report.writeToFile(out);
//    }
//
//    public void sendMessage(RequestArgumentParser parser, String body)
//            throws javax.mail.MessagingException, java.io.UnsupportedEncodingException {
//	String cc = null;
//        PropertyManagement properties = getPropertyManager();
//        String smtpServer = properties.getMandatoryProperty("smtpServer");
//        Mailer mailer = new Mailer(smtpServer);
//        mailer.setFrom(parser.getParameter("fromAddress"));
//        mailer.addRecipient(parser.getParameter("toAddress"));
//	cc = parser.getParameter("cc1");
//	if (cc != null && !cc.equals("")) {
//	    mailer.addCC(cc);
//	}
//	cc = parser.getParameter("cc2");
//	if (cc != null && !cc.equals("")) {
//	    mailer.addCC(cc);
//	}
//	cc = parser.getParameter("bcc1");
//	if (cc != null && !cc.equals("")) {
//	    mailer.addBCC(cc);
//	}
//	cc = parser.getParameter("bcc2");
//	if (cc != null && !cc.equals("")) {
//	    mailer.addBCC(cc);
//	}
//	//mailer.addCC(parser.getParameter("cc1"));
//	//mailer.addCC(parser.getParameter("cc2"));
//	//mailer.addBCC(parser.getParameter("bcc1"));
//	//mailer.addBCC(parser.getParameter("bcc2"));
//        mailer.setSubject(parser.getParameter("subject"));
//        mailer.setMessage(body);
//        logger.finer("attaching " + tempFileName);
//        mailer.addAttachment(tempFileName);
//        System.out.println(mailer.toString());
//        mailer.send();
//    }
//
//	public boolean isArgumentListValid() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	public void setDataSource(DataSource dataSource) {
//		// TODO Auto-generated method stub
//		
//	}
//
//
//}
