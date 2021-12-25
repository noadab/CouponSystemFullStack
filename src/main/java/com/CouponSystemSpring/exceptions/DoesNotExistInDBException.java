package com.CouponSystemSpring.exceptions;

public class DoesNotExistInDBException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String message;
	
	public DoesNotExistInDBException() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public DoesNotExistInDBException(String message) {
		super();
		this. message=message;
	}

	@Override
	public String getMessage() {
		if (message!=null)
		{
			return message;
		}
		return "The object doesn't Exist, can't do this operator";
		
	}

}
