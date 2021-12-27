package com.CouponSystemSpring.exceptions;

public class InvalidInputException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String input;
	
	public InvalidInputException(String input) {
		this.input=input;
	}
	@Override
	public String getMessage() {
		return "The input "+input+" is invalid. Enter again";
		
	}
}
