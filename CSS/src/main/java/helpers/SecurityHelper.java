/**
 * Copyright (c) 2013, RMIT University, Australia.
 * All rights reserved.
 * 
 * This code is under the BSD license. See 'license.txt' for details.
 * Project hosted at: https://code.google.com/p/climate-smart-seaports/
 */
package helpers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import security.UserLoginService;

import war.model.User;
import war.model.UserStory;

/**
 * Helper class facilitating the interaction with Spring Security's 
 * 'SecurityContextHolder', and authentication in general.
 * @author Guillaume Prevost
 *
 */
public class SecurityHelper {
	
	/**
	 * Checks whether the currently logged user is matching a given user
	 */
	public static Boolean IsCurrentUserMatching(User user) {
		
		String loggedUsername = getCurrentlyLoggedInUsername();
		if (user.getUsername().equals(loggedUsername))
			return true;
		return false;
	}
	
	/**
	 * Checks whether the currently logged user is matching a given username
	 */
	public static Boolean IsCurrentUserMatching(String username) {
		if (username == null)
			throw new IllegalArgumentException();
		
		String loggedUsername = getCurrentlyLoggedInUsername();
		if (username.equals(loggedUsername))
			return true;
		return false;
	}
	
	/**
	 * Checks whether the currently logged user is allowed to access a given user story
	 */
	public static Boolean IsCurrentUserAllowedToAccess(UserStory story) {
		if (story.getOwner() == null)
			throw new IllegalArgumentException();
		
		return IsCurrentUserMatching(story.getOwner().getUsername()) || IsCurrentUserAdmin();
	}
	
	/**
	 * Checks whether the currently logged user has an Administrator role
	 */
	public static Boolean IsCurrentUserAdmin() {
		Collection<? extends GrantedAuthority> loggedUserAuthorities = getCurrentlyLoggedInAuthorities();
		
		for (Object obj : loggedUserAuthorities) {
			GrantedAuthority auth = (GrantedAuthority)(obj);
			if (auth.getAuthority().equals(UserLoginService.ROLE_ADMINISTRATOR))
				return true;
		}
		return false;
	}
	
	/**
	 * Retrieves the username of the currently logged user
	 * @return the username of the currently logged user, null otherwise
	 */
	public static String getCurrentlyLoggedInUsername() {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
			  userDetails = (UserDetails) principal;
			  return userDetails.getUsername();
			}
			return (String)principal;
			}
		catch (NullPointerException ex) {
			throw new AccessDeniedException(ex.getMessage());
		}
	}
	
	/**
	 * Retrieves the authorities of the currently logged user
	 * @return the authorities of the currently logged user
	 */
	public static Collection<? extends GrantedAuthority> getCurrentlyLoggedInAuthorities() {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
			  userDetails = (UserDetails) principal;
			  
			  return userDetails.getAuthorities();
			}
			return new ArrayList<GrantedAuthority>();
		}
		catch (NullPointerException ex) {
			throw new AccessDeniedException(ex.getMessage());
		}
	}
}
