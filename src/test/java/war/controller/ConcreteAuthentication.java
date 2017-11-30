/**
 * Copyright (c) 2013, RMIT University, Australia.
 * All rights reserved.
 * 
 * This code is under the BSD license. See 'license.txt' for details.
 * Project hosted at: https://code.google.com/p/climate-smart-seaports/
 */
package war.controller;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Class used to emulate an authentication with Spring Security in order to be able 
 * to run some test cases as different user profiles. 
 * @author Guillaume Prevost
 */
public class ConcreteAuthentication extends AbstractAuthenticationToken {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * User of whom authentication is emulated
	 */
	private final UserDetails principal;

	/**
	 * Constructor of ConcreteAuthentication
	 * @param principal: user of whom authentication is emulated
	 */
	public ConcreteAuthentication(UserDetails principal) {
		super(principal.getAuthorities());
		this.principal = principal;
	}

	/**
	 * Getter for the user's credentials.
	 * @return empty string: This doesn't really matter since this class is used for unit tests purpose only
	 */
	@Override
	public String getCredentials() {
		return "";
	}

	/**
	 * Getter for the authenticated user object
	 */
	@Override
	public UserDetails getPrincipal() {
		return principal;
	}
}