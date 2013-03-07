package war.controller;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class ConcreteAuthentication extends AbstractAuthenticationToken {
	
	private static final long serialVersionUID = 1L;
	
	private final UserDetails principal;

	public ConcreteAuthentication(UserDetails principal) {
		super(principal.getAuthorities());
		this.principal = principal;
	}

	@Override
	public String getCredentials() {
		return ""; // or anything, really - pass it into the constructor if this matters
	}

	@Override
	public UserDetails getPrincipal() {
		return principal;
	}
}