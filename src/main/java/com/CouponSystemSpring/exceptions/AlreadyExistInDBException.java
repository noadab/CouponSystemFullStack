package com.CouponSystemSpring.exceptions;

public class AlreadyExistInDBException extends Exception {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private String message;
	
	public AlreadyExistInDBException() {
		super();
	}

	public AlreadyExistInDBException(String message) {
		super();
		this.message= message;
	} 
	@Override
	public String getMessage() {
		if (message!=null)
		{
			return message;
		}
		
		return "This Object already exist- Doesn't add to system";
	}
	
}
