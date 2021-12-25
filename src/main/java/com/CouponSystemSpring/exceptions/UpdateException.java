package com.CouponSystemSpring.exceptions;

public class UpdateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public UpdateException(String message) {
		this.message=message;
		System.err.println();
	}
	
	@Override
	public String getMessage() {
		return "Can't update "+message;	
	}
	
	
	

	
	
	

}
