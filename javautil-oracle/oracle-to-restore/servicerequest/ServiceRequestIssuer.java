package com.dbexperts.oracle.servicerequest;

import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.Iterator;

import oracle.jdbc.OracleConnection;



public class ServiceRequestIssuer {
    private String serviceName;
    private final ArrayList<ServiceRequestArgument> arguments = new ArrayList<ServiceRequestArgument>();

    // sql statements
    private final String newRequestText = " begin service_request.new_request(?); end; ";
    private final String addRequestText = " begin service_request.add_argument(?, ?);  end;";
    private final String issueRequestText = " begin service_request.issue_request;  end;";
    private CallableStatement newRequest = null;
    private CallableStatement addRequestDate = null;
    private CallableStatement addRequestDouble = null;
    private CallableStatement addRequestString = null;
    private CallableStatement issueRequest = null;


    //
    //private static final String

    public ServiceRequestIssuer() {
    }

    public ServiceRequestIssuer(final String serviceName) {
        this.serviceName = serviceName;
    }

    public void addArgument(final String name, final Double value) {
        arguments.add(new ServiceRequestArgument(name, value));
    }



    public void addArgument(final String name, final java.util.Date value) {
        arguments.add(new ServiceRequestArgument(name, value));
    }


    public void addArgument(final String name, final String value) {
        arguments.add(new ServiceRequestArgument(name, value));
    }


    public String getServiceName() {
        return serviceName;
    }

    public void issue(final OracleConnection dbc)
            throws java.sql.SQLException {
        newRequest = dbc.prepareCall(newRequestText);
        newRequest.setString(1, getServiceName());
        newRequest.executeUpdate();

        final Iterator<ServiceRequestArgument> it = arguments.iterator();
        while (it.hasNext()) {
            final ServiceRequestArgument rqst = it.next();
            switch (rqst.getType()) {
                case ServiceRequestArgument.DATE:
                    if (addRequestDate == null) {
                        addRequestDate = dbc.prepareCall(addRequestText);
                    }
                    final java.sql.Time stamp = new java.sql.Time(rqst.getDate().getTime());
                    addRequestString.setString(1, rqst.getName());
                    addRequestDate.setTime(2, stamp);
                    addRequestDate.executeUpdate();
                    break;
                case ServiceRequestArgument.STRING:
                    if (addRequestString == null) {
                        addRequestString = dbc.prepareCall(addRequestText);
                    }
                    addRequestString.setString(1, rqst.getName());
                    addRequestString.setString(2, rqst.getString());
                    addRequestString.executeUpdate();
                    break;
                case ServiceRequestArgument.DOUBLE:
                    if (addRequestDouble == null) {
                        addRequestDouble = dbc.prepareCall(addRequestText);
                    }
                    addRequestString.setString(1, rqst.getName());
                    addRequestDouble.setDouble(2, rqst.getDouble().doubleValue());
                    addRequestDouble.executeUpdate();
                    break;
            }
        }
        issueRequest = dbc.prepareCall(issueRequestText);
        issueRequest.executeUpdate();
    }

    public void setServiceName(final String serviceName) {
        if (this.serviceName != null && !this.serviceName.equals(serviceName)) {
            throw new java.lang.IllegalArgumentException("serviceName is immutable: attempted to change serviceName from '" + this.serviceName + " to '" + serviceName);
        }
        this.serviceName = serviceName;
    }


}

