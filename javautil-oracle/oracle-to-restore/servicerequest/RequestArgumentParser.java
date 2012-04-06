package com.dbexperts.oracle.servicerequest;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @todo configure verbose cascade from xml in Machine
 * @author jim
 *
 */
public class RequestArgumentParser {

    //private static final Logger logger = Logger.getLogger(RequestArgumentParser.class.getName());
    private final StringBuffer buff = new StringBuffer(1024);
    private int start = 0;
    private int tokenPosition;
    private boolean done = false;
    private final ArrayList<RequestParameter> arguments = new ArrayList<RequestParameter>();
    private final String args;

    private boolean verbose = true;

    public RequestArgumentParser(final String args) {
        this.args = args;
        if (args != null) {
            try {
                while (!done) {
                    processArgument();
                }
            } catch (final java.lang.Exception e) {
                e.printStackTrace();
                throw new java.lang.IllegalArgumentException(e.getMessage() + "\n" + " encountered while processing\n" + args + "\nAt position: " + start + "\n" + new String(buff));
            }
        }
    }

    public RequestArgumentParser(final String args, final boolean verbose) {
        this.verbose = true;
        this.args = args;
        if (this.args != null) {
            try {
                while (!done) {
                    processArgument();
                }
            } catch (final Exception i) {
                throw new java.lang.IllegalArgumentException(i.getMessage() + "\n" +
                        " encountered while processing\n" + args +
                        "\nAt position: " + start + "\n" + new String(buff));
            }
        }
    }

    public String getParameter(final String parameterName) {
        String rc = null;
        final Iterator<RequestParameter> it = arguments.iterator();
        while (it.hasNext()) {
            final RequestParameter rq = it.next();
            if (rq.getName().equals(parameterName)) {
                rc = rq.getValue();
            }
        }
        return rc;
    }

    public ArrayList<RequestParameter> getParameters() {
        return arguments;
    }


    public RequestParameter getRequestParameter(final String parameterName) {
        RequestParameter rc = null;
        final Iterator<RequestParameter> it = arguments.iterator();
        while (it.hasNext()) {
            final RequestParameter rq = it.next();
            if (rq.getName().equals(parameterName)) {
                rc = rq;
            }
        }
        return rc;
    }

    @Override
	public String toString() {
        final StringBuffer buff = new StringBuffer(1024);
        Iterator<RequestParameter> argIt = arguments.iterator();
        int maxParmLength = 0;
        buff.append("<arguments>\n");
        // get maximum parameter name length make it purty
        argIt = arguments.iterator();
        String spaces = "                                                                                           ";
        while (argIt.hasNext()) {
            final RequestParameter rq = argIt.next();
            final int length = rq.getName().length();
            if (length > maxParmLength) {
                maxParmLength = length;
            }
        }

        // expand as necessary
        while (spaces.length() < maxParmLength) {
            spaces = spaces + spaces;
        }
        argIt = arguments.iterator();
        while (argIt.hasNext()) {
            final RequestParameter rq = argIt.next();
            final int leftPadLength = maxParmLength - rq.getName().length();
            buff.append(spaces.substring(0, leftPadLength));
            buff.append(rq.toXml());
            buff.append("\n");
        }
        buff.append("</arguments>\n");
        return new String(buff);
    }

    public String toXml() {
        final StringBuffer buff = new StringBuffer(1024);
        Iterator<RequestParameter> argIt = arguments.iterator();
        int maxParmLength = 0;
        buff.append("<arguments>\n");
        // get maximum parameter name length make it purty
        argIt = arguments.iterator();
        String spaces = "                                                                                           ";
        while (argIt.hasNext()) {
            final RequestParameter rq = argIt.next();
            final int length = rq.getName().length();
            if (length > maxParmLength) {
                maxParmLength = length;
            }
        }

        // expand as necessary
        while (spaces.length() < maxParmLength) {
            spaces = spaces + spaces;
        }
        argIt = arguments.iterator();
        while (argIt.hasNext()) {
            final RequestParameter rq = argIt.next();
            final int leftPadLength = maxParmLength - rq.getName().length();
            buff.append(spaces.substring(0, leftPadLength));
            buff.append(rq.toXml());
            buff.append("\n");
        }
        buff.append("</arguments>\n");
        return new String(buff);
    }

    private void processArgument() {
        String name;
        String type;
        String valueLengthString = null;
        //int valueLength;
        String value;
        final String separator = ",";

        tokenPosition = args.indexOf(separator, start);

        name = args.substring(start, tokenPosition);
        start = tokenPosition + 1;


        tokenPosition = args.indexOf(separator, start);


        type = args.substring(start, tokenPosition);
        start = tokenPosition + 1;



        if (type.equals("V")) {
            tokenPosition = args.indexOf(separator, start);

            valueLengthString = args.substring(start, tokenPosition);
            if (valueLengthString == null || valueLengthString.equals("")) {
            } else {
                start = tokenPosition + 1;
                if (verbose) {
                    buff.append("valueLengthString: " + valueLengthString + "\n");
                }
                tokenPosition = Integer.parseInt(valueLengthString) + start;
            }
        } else {
            tokenPosition = args.indexOf(separator, start);
        }

        if (tokenPosition > 0) {
            value = args.substring(start, tokenPosition);
        } else {
            done = true;
            value = args.substring(start);
        }
        start = tokenPosition + 1;
        if (start >= args.length()) {
            done = true;
        }


        arguments.add(new RequestParameter(name, value, type));
    }

}
