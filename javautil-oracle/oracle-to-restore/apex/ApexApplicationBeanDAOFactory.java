package com.dbexperts.oracle.apex;

import java.util.Collection;

public class ApexApplicationBeanDAOFactory {
 
    public static ApexApplicationDAO forIds(Collection<Integer> appIds) {
	ApexApplicationDAO rv = new ApexApplicationsForApplicationIdDAO(appIds);
	return rv;
    }
    
    public static ApexApplicationDAO forWorkspaceNameMask(Collection<String> workspaceNameMask) {
	ApexApplicationDAO rv = new ApexApplicationsForWorkspacesDAO(workspaceNameMask);
	return rv;
    }
}
