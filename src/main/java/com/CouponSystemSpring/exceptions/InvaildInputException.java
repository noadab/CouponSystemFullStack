package com.CouponSystemSpring.exceptions;

public class InvaildInputException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String input;
	
	public InvaildInputException(String input) {
		this.input=input;
	}
	@Override
	public String getMessage() {
		return "The input "+input+" is invaild. Enter again";
		
	}
}
