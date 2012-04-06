package org.javautil.security;

import java.security.Permission;

/**
 * 
 * @author bcm
 */
public class NoExitSecurityManager extends SecurityManager {

	// todo what is the point of this
	@Override
	public void checkPermission(final Permission perm) {
		// does nothing
	}

	@Override
	public void checkExit(final int status) {
		super.checkExit(status); // first call on preventing.
		throw new NoExitException(status);
	}
};
