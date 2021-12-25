package com.CouponSystemSpring.exceptions;

public class LoginException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginException() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getMessage() {
		return "Can't sign in, the user is not found on system";
		
	}
}
