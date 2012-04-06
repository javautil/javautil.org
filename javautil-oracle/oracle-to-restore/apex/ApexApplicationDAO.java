package com.dbexperts.oracle.apex;

import java.sql.Connection;
import java.util.Collection;

public interface ApexApplicationDAO {

    public abstract Collection<ApexApplicationBean> getApplications(
	    Connection conn);

}