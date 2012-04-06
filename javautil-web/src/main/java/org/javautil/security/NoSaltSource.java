package org.javautil.security;

import org.springframework.security.providers.dao.SaltSource;
import org.springframework.security.userdetails.UserDetails;

/**
 * An unsalted implementation of SaltSource, not recommended as a permanent salt solution.
 * 
 * @see org.springframework.security.providers.dao.salt.SystemWideSaltSource
 * @author bcm
 */
public class NoSaltSource implements SaltSource {

	private static final String NO_SALT = "";
	
	public Object getSalt(UserDetails user) {
		return NO_SALT;
	}

}
