package com.dbexperts.oracle.servicerequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;





public class ServiceRqstS extends ServiceRqstBaseS {
    private static HashMap<String,ServiceRqstBean> services = new HashMap<String,ServiceRqstBean>();

    public ServiceRqstS(final Connection dbc)
            throws java.sql.SQLException {
        getAll(dbc);
    }


    public synchronized void getAll(final Connection dbc)
            throws java.sql.SQLException {
        PreparedStatement stmt = null;
        ResultSet rset = null;
        clear();
        stmt = dbc.prepareStatement(selectText);
        rset = stmt.executeQuery();
        while (rset.next()) {
            final ServiceRqstBean row = new ServiceRqstBean();
            getRow(rset, row);
            add(row);
        }
    }

    @Override
	public synchronized void add(final ServiceRqstBean service) {
        super.add(service);
        services.put(service.getServiceRqstCd(), service);
    }

    ServiceRqstBean get(final Connection dbc, final String serviceName)
            throws java.sql.SQLException {
        ServiceRqstBean rc = null;
        rc = services.get(serviceName);
        if (rc == null) {
            getAll(dbc);
        }
        return services.get(serviceName);
    }
}
