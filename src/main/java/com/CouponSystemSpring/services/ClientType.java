package com.CouponSystemSpring.services;

public enum ClientType {
	
	ADMINISTOR("Administor"), 
	COMPANY("Company"), 
	CUSTOMER("Customer");
	
	private String ClientType;
	
	private ClientType(String ClientType) {
		this.ClientType=ClientType;
	}

	public String getClientType() {
		return ClientType;
	}

}
