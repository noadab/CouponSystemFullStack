package com.CouponSystemSpring.exceptions;

public class AllreadyExistInDBException extends Exception {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private String message;
	
	public AllreadyExistInDBException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AllreadyExistInDBException(String message) {
		super();
		this.message= message;
	} 
	@Override
	public String getMessage() {
		if (message!=null)
		{
			return message;
		}
		
		return "This Object allready exist- Doesn't add to system";	
	}
	
}
