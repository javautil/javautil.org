package com.dbexperts.oracle.security;
import com.dbexperts.security.AuthenticationException;
public interface UserAuthentication {

	/**
	 * Returns the authentication ticket.
	 * @param username
	 * @param password
	 * @return
	 */
	public String authenticate(String username, String password) throws AuthenticationException;

}
